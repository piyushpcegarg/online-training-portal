package com.gargorg.Admin.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.hdiv.web.multipart.HdivCommonsMultipartResolver;
import org.hdiv.web.validator.EditableParameterValidator;
import org.hibernate.SessionFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Import(ApplicationMainConfiguration.class)
public class ApplicationServletConfiguration extends WebMvcConfigurerAdapter implements ServletContextAware {

	private @Value("${hibernate.dataSourceClassName}") String dataSourceClassName;
	private @Value("${dataSource.url}") String jdbcUrl;
	private @Value("${dataSource.username}") String username;
	private @Value("${dataSource.password}") String password;
	private @Value("${hibernate.hikari.idleTimeout}") Long idleTimeout;
	private @Value("${jdbc.dialect}") String jdbcDialect;
	
	private ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * Handles HTTP GET requests for /resources/** by efficiently serving up
	 * static resources in the ${webappRoot}/resources directory
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.tiles();
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer tilesConfigurer = new TilesConfigurer();
		/**
		 * Don't put underscore in template file name otherwise it will not be
		 * detected.
		 */
		final String[] definitionsArray = new String[] { "/WEB-INF/tiles-defs/templates.xml",
				"/WEB-INF/tiles-defs/trainingportaltemplates.xml" };
		tilesConfigurer.setDefinitions(definitionsArray);
		return tilesConfigurer;
	}

	/** Set the locale of application - Start */

	/**
     * We use springs reloadable message resource and set it to refresh every
     * second.
     */
	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		final List<String> baseNamesList = new ArrayList<String>();
		baseNamesList.add("resources/properties/messages");
		baseNamesList.add("resources/properties/Lables");
		baseNamesList.add("resources/properties/FormValidationAlert");
		baseNamesList.add("resources/properties/displayTagLabels");
		/**
		 * The below files are functionality specific files (For Online Training
		 * Portal) start
		 */
		baseNamesList.add("resources/TrainingPortal_properties/TrainingPortalLables");
		baseNamesList.add("resources/TrainingPortal_properties/TrainingPortalFormValidationAlert");
		baseNamesList.add("resources/TrainingPortal_properties/TrainingPortalDisplayTagLabels");
		baseNamesList.add("resources/TrainingPortal_properties/TrainingPortalMessages");
		/**
		 * The below files are functionality specific files (For Online Training
		 * Portal) end
		 */
		reloadableResourceBundleMessageSource.setBasenames(baseNamesList.toArray(new String[0]));
		reloadableResourceBundleMessageSource.setCacheSeconds(1);
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return reloadableResourceBundleMessageSource;
	}

	/**
     * Registering our validator with spring mvc for our i18n support
     */
    @Override
	public Validator getValidator() {
		return provideValidator();
	}
	
    @Bean
    public Validator provideValidator() {
    	
    	/**
         * This is the hdiv editable validator which works along with JSR-303. So if JSR-303
         * is found in classpath so need to configure latter explicitly.
         */
    	
    	if(CustomWebApplicationInitializer.isHdivEnabled) {
    		return new EditableParameterValidator ();
    	} else {
    		
    		/**
    	     * This is the validator we will provide to spring mvc to handle message
    	     * translation for the bean validation api (hibernate-validation)
    	     */
    		
    		final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
            bean.setValidationMessageSource(messageSource());
            bean.setProviderClass(HibernateValidator.class);
            return bean;
    	}
    	
        
    }
    
	@Bean
	public HandlerMapping handlerMapping() {
		final RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
		Object[] localeChangeInterceptors = {localeChangeInterceptor()};
		requestMappingHandlerMapping.setInterceptors(localeChangeInterceptors);
		return requestMappingHandlerMapping;
	}

	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	/** Set the locale of application - End */

	@Bean
	public CacheManager cacheManager() {
		final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("opgCache")));
		return simpleCacheManager;
	}

	@Bean
	public WebContentInterceptor webContentInterceptor() {
		final WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		return webContentInterceptor;
	}

	/** Database , hibernate and transaction management configuration - start */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		/**
		 * This approach takes database connection values from datasource ,
		 * where values are defined in jdbc.properties file
		 */
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setMappingJarLocations(new ServletContextResource(servletContext,"/WEB-INF/lib/gargorg-core-1.0.0-SNAPSHOT.jar"),
				new ServletContextResource(servletContext,"/WEB-INF/lib/OnlineTrainingPortal-1.0.0-SNAPSHOT.jar"));
		return sessionFactory;
	}

	/**
	 * The following configuration is for database connection , Hikari
	 * Connection Pool start
	 */
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {

		final HikariConfig hikariConfig = new HikariConfig(hikariProperties());

		hikariConfig.setIdleTimeout(idleTimeout);
		hikariConfig.setDataSourceClassName(dataSourceClassName);
		final DataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}

	/** Commons DBCP configuration - start */

	/*
	@Bean(destroyMethod="close") 
	public DataSource dataSource() { 
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(jdbcUrl); dataSource.setUsername(username);
		dataSource.setPassword(password); return dataSource;
	}
	*/ 
	/** Commons DBCP configuration - end */

	@SuppressWarnings("serial")
	private Properties hikariProperties() {
		return new Properties() {
			{
				setProperty("dataSource.url", jdbcUrl);
				setProperty("dataSource.user", username);
				setProperty("dataSource.password", password);
			}
		};
	}

	@SuppressWarnings("serial")
	private Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.dialect", jdbcDialect);
				setProperty("hibernate.show_sql", "true");
				setProperty("hibernate.format_sql", "false");
				setProperty("hibernate.generate_statistics", "true");
			}
		};
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	/**
	 * Post-processor to perform exception translation on @Repository classes
	 * (from native exceptions such as Hibernate/JPA PersistenceExceptions to
	 * Spring's DataAccessException hierarchy).
	 */	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/** Database , hibernate and transaction management configuration - end */
	
	 @Bean 
	 public MultipartResolver multipartResolver() {
		 
		if(CustomWebApplicationInitializer.isHdivEnabled) {
			/**
			 * Multipart configuration. Replace Spring MVCs MultipartResolver with HDIVs
			 * one to implement OWASP security. If commons fileupload is used for
			 * multipart processing:
			 */	
			final HdivCommonsMultipartResolver hdivCommonsMultipartResolver = new HdivCommonsMultipartResolver();
			/** specify maximum file size in bytes(10240 = 10KB) */
			hdivCommonsMultipartResolver.setMaxUploadSize(10240);
			return hdivCommonsMultipartResolver;
		} else {
			/**
			 * To tell Spring to use commons-upload library to handle the file upload
			 * form. Start
			 */
			 return new CommonsMultipartResolver();
			/**
			 * to tell Spring to use commons-upload library to handle the file upload
			 * form. End
			 */
		}
	 }

	@Bean
	public JavaMailSender mailSender() {
		final JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPort(25);
		javaMailSenderImpl.setUsername("piyushpcegarg@gmail.com");
		javaMailSenderImpl.setPassword(password);
		javaMailSenderImpl.setJavaMailProperties(javaMailProperties());
		return javaMailSenderImpl;
	}
	
	@SuppressWarnings("serial")
	private Properties javaMailProperties() {
		return new Properties() {
			{
				setProperty("mail.transport.protocol","smtp");
				setProperty("mail.smtp.auth","true");
				setProperty("mail.smtp.starttls.enable","true");
				setProperty("mail.debug","true");
			}
		};
	}
	
	/** Configuration for Performance monitoring of application - start */
	/** Enable AspectJ style of Spring AOP */
	@Bean
	public PerformanceMonitorInterceptor perfMonitor() {
		final PerformanceMonitorInterceptor performanceMonitorInterceptor = new PerformanceMonitorInterceptor();
		performanceMonitorInterceptor.setLogTargetClassInvocation(true);
		return performanceMonitorInterceptor;
	}

	// TODO Read AOP in java and rectify below code
	/*
	 * <aop:config> <aop:pointcut expression="execution(* *..service.*.*(..))"
	 * id="allServices"/> <aop:advisor pointcut-ref="allServices"
	 * advice-ref="perfMonitor" /> </aop:config>
	 */

	/** Configuration for Performance monitoring of application - end */

}
