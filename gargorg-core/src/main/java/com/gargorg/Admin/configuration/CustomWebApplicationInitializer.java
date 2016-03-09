package com.gargorg.Admin.configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.displaytag.filter.ResponseOverrideFilter;
import org.hdiv.filter.ValidatorFilter;
import org.hdiv.listener.InitListener;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.sf.ehcache.constructs.web.filter.GzipFilter;

public class CustomWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {
	 
	static boolean isHdivEnabled = true;
	static boolean isHttpsEnabled = true;
	
	static {
		if(System.getProperty("isHdivEnabled") != null)
			isHdivEnabled = System.getProperty("isHdivEnabled").equalsIgnoreCase("false") ? false : true;
		if(System.getProperty("isHttpsEnabled") != null)
			isHttpsEnabled = System.getProperty("isHttpsEnabled").equalsIgnoreCase("false") ? false : true;
	}
	
	// Create the 'root' Spring application context
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				ApplicationMainConfiguration.class,
				ApplicationSecurityConfiguration.class , 
				// This configutaion class handles all OWASP HDIV security configuration
				ApplicationHdivSecurityConfiguration.class 
		};
	}
	
	// Create the dispatcher servlet's Spring application context
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// This configuration class handles all the spring MVC configuration
		return new Class[] { ApplicationServletConfiguration.class };
	}
	
	@Override
	protected String getServletName() {
		return "gargorg";
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		addListenerToContext(servletContext);
		addFiltersToContext(servletContext);
	}
	
	private void addListenerToContext(ServletContext servletContext) {
		/* Following listener added for HDIV configuration to implement and protect application from OWASP top 10 security vulnerabilities - start  */
		addInitListener(servletContext);
		/* Following listener added for HDIV configuration to implement and protect application from OWASP top 10 security vulnerabilities - end  */
		addHttpSessionEventPublisher(servletContext);
		addRequestContextListener(servletContext);
	}
	
	private void addFiltersToContext(ServletContext servletContext) {
		// HDIV Validator Filter - HDIV filter configuration without Spring Security filter chain - start
		addHdivValidatorFilter(servletContext);
		// HDIV Validator Filter - HDIV filter configuration without Spring Security filter chain - end
		//addCompressionFilter(servletContext);
		addEncodingFiler(servletContext);
		addResponseOverrideFilter(servletContext);
	}
	
	/**
	 * Below Section adds all the listeners to servlet context
	 */
	
	private void addInitListener(ServletContext servletContext) {
		servletContext.addListener(new InitListener());
	}
	
	private void addHttpSessionEventPublisher(ServletContext servletContext) {
		servletContext.addListener(new HttpSessionEventPublisher());
	}
	
	private void addRequestContextListener(ServletContext servletContext) {
		servletContext.addListener(new RequestContextListener());
	}
	
	/**
	 * Below Section adds all the filters to servlet context
	 */
	private void addHdivValidatorFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic hdivValidatorFilter = servletContext.addFilter("hdivValidatorFilter", new ValidatorFilter());
		hdivValidatorFilter.addMappingForServletNames(EnumSet.allOf(DispatcherType.class), true, getServletName());
	}
	
	private void addCompressionFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic compressionFilter = servletContext.addFilter("compressionFilter", new GzipFilter());
		compressionFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}
	
	private void addEncodingFiler(ServletContext servletContext) {
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
	}
	
	private void addResponseOverrideFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic responseOverrideFilter = servletContext.addFilter("responseOverrideFilter", new ResponseOverrideFilter());
		responseOverrideFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		responseOverrideFilter.setInitParameter("buffer", "false");
	}

 }
