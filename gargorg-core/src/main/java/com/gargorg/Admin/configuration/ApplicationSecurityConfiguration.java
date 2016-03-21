package com.gargorg.Admin.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.gargorg.Admin.dao.CustomDaoAuthenticationProvider;
import com.gargorg.Admin.service.CustomAuthenticationFailureHandler;
import com.gargorg.Admin.service.CustomAuthenticationSuccessHandler;
import com.gargorg.Admin.service.CustomPostAuthenticationChecks;
import com.gargorg.Admin.service.CustomPreAuthenticationChecks;
import com.gargorg.Admin.service.CustomSecurityContextLogoutHandler;
import com.gargorg.Admin.service.JdbcFilterInvocationSecurityMetadataSource;
import com.gargorg.Admin.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Import(ApplicationMainConfiguration.class)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private @Value("${httpPort1}") Integer httpPort1;
	private @Value("${httpsPort1}") Integer httpsPort1;
	private @Value("${httpPort2}") Integer httpPort2;
	private @Value("${httpsPort2}") Integer httpsPort2;
	private @Value("${ExpiredUrl}") String expiredUrl;
	private @Value("${logoutPageUrl}") String logoutUrl;
	private @Value("${logoutSuccessUrl}") String logoutSuccessUrl;
	private @Value("${loginPageUrl}") String loginPageUrl;
	private @Value("${authenticationCheck}") String authenticationCheck;
	private @Value("${loginfailedPageUrl}") String loginfailedPageUrl;
	private @Value("${homePageUrl}") String defaultSuccessUrl;
	private @Value("${changePasswordPageUrl}") String changePasswordPageUrl;
	private @Value("${accessDenied}") String accessDeniedUrl;
	private @Value("${stringEncryptorPassword}") String stringEncryptorPassword;
	private @Value("${stringEncryptorAlgorithm}") String stringEncryptorAlgorithm;
	
	@Autowired
	private ApplicationMainConfiguration applicationMainConfiguration;
	
	@Override
	protected void configure(AuthenticationManagerBuilder registry)
	        throws Exception {
		registry.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				// ignore all URLs that start with /resources/
				.antMatchers("/resources/**",logoutSuccessUrl);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
			if(CustomWebApplicationInitializer.isHttpsEnabled) {
		http
		
		.requiresChannel()
			.anyRequest().requiresSecure();
			} else {
		http	
		
		.requiresChannel()
			.anyRequest().requiresInsecure();	
			}
			
		http	
		
		.portMapper().http(httpPort1).mapsTo(httpsPort1)
			.and()
		.portMapper().http(httpPort2).mapsTo(httpsPort2)
		
			.and()
		.securityContext()
		
		.and()
			.csrf().disable()
		
		.logout()
			.logoutUrl(logoutUrl)
			.logoutSuccessUrl(logoutSuccessUrl)
			.addLogoutHandler(customSecurityContextLogoutHandler())
		
			.and()
		.formLogin()
			.loginPage(loginPageUrl)
			.loginProcessingUrl(authenticationCheck)
			.usernameParameter("username")
			.passwordParameter("password")
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
		
			.and()
		.requestCache()
			
			.and()
		.sessionManagement()
			.invalidSessionUrl("/login").maximumSessions(1).expiredUrl(expiredUrl)
			.and()
			.sessionFixation().newSession()
		
			.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint())
		.accessDeniedHandler(accessDeniedHandler())
		
			.and()
		.addFilter(filterSecurityInterceptor(http))
			;
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		final CustomDaoAuthenticationProvider customDaoAuthenticationProvider = new CustomDaoAuthenticationProvider();
		customDaoAuthenticationProvider.setUserDetailsService(customUserDetailsService());
		customDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		customDaoAuthenticationProvider.setPreAuthenticationChecks(customPreAuthenticationChecks());
		customDaoAuthenticationProvider.setPostAuthenticationChecks(customPostAuthenticationChecks());
		return customDaoAuthenticationProvider;
	}
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsChecker customPreAuthenticationChecks() {
		return new CustomPreAuthenticationChecks();
	}
	
	@Bean
	public UserDetailsChecker customPostAuthenticationChecks() {
		return new CustomPostAuthenticationChecks();
	}
	
	@Bean
	public SecurityContextRepository securityContextRepository() {
		final HttpSessionSecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();
		httpSessionSecurityContextRepository.setDisableUrlRewriting(true);
		return httpSessionSecurityContextRepository;
	}
	
	@Bean
	public LogoutHandler customSecurityContextLogoutHandler() {
		return new CustomSecurityContextLogoutHandler();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
		customAuthenticationSuccessHandler.setDefaultTargetUrl(defaultSuccessUrl);
		customAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
		customAuthenticationSuccessHandler.setLocaleResolver(applicationMainConfiguration.localeResolver());
		return customAuthenticationSuccessHandler;
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		final CustomAuthenticationFailureHandler customAuthenticationFailureHandler = new CustomAuthenticationFailureHandler();
		customAuthenticationFailureHandler.setDefaultFailureUrl(loginfailedPageUrl);
		customAuthenticationFailureHandler.setExceptionMappings(getFailureUrlMap());
		customAuthenticationFailureHandler.setLocaleResolver(applicationMainConfiguration.localeResolver());
		return customAuthenticationFailureHandler;
	}
	
	private Map<String, String> getFailureUrlMap() { 
		final Map<String, String> failureUrlMap = new HashMap<String, String>();
		failureUrlMap.put("org.springframework.security.authentication.LockedException",loginfailedPageUrl+"/accountLocked");
        failureUrlMap.put("org.springframework.security.authentication.DisabledException",loginfailedPageUrl+"/accountDisabled");
        failureUrlMap.put("org.springframework.security.authentication.AccountExpiredException",loginfailedPageUrl+"/accountExpired");
        failureUrlMap.put("org.springframework.security.authentication.BadCredentialsException",loginfailedPageUrl+"/badCredentials");
        failureUrlMap.put("org.springframework.security.authentication.CredentialsExpiredException",changePasswordPageUrl+"/credentialExpired");
        failureUrlMap.put("com.gargorg.Admin.Exception.OtpRequiredException",loginfailedPageUrl+"/otpRequired");
        failureUrlMap.put("com.gargorg.Admin.Exception.InvalidOtpException",loginfailedPageUrl+"/invalidOtpException");
        return failureUrlMap;
	}
	
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		final LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(loginPageUrl);
		if(CustomWebApplicationInitializer.isHttpsEnabled) {
			loginUrlAuthenticationEntryPoint.setForceHttps(true);
		} else {
			loginUrlAuthenticationEntryPoint.setForceHttps(false);
		}
		return loginUrlAuthenticationEntryPoint;	
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		final AccessDeniedHandlerImpl accessDeniedHandlerImpl = new AccessDeniedHandlerImpl();
		accessDeniedHandlerImpl.setErrorPage(accessDeniedUrl);
		return accessDeniedHandlerImpl;
	}
	
	@Bean 
	public StringEncryptor stringEncryptor() {
		StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
		stringEncryptor.setPassword(stringEncryptorPassword);
		stringEncryptor.setAlgorithm(stringEncryptorAlgorithm);
		return stringEncryptor;
	}
	
	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor(HttpSecurity http) {
		FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
		securityInterceptor.setSecurityMetadataSource(securityMetadataSource());
		securityInterceptor.setAccessDecisionManager(accessDecisionManager());
		securityInterceptor.setAuthenticationManager(authenticationManager());
		return securityInterceptor;
	}
	
	@Bean
	public FilterInvocationSecurityMetadataSource securityMetadataSource() {
		return new JdbcFilterInvocationSecurityMetadataSource();
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		AccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters());
		return accessDecisionManager;
	}
	
	@Bean
	public List<AccessDecisionVoter<? extends Object>> decisionVoters() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix("");
		decisionVoters.add(roleVoter);
		decisionVoters.add(new AuthenticatedVoter());
		return decisionVoters;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> authenticationProviders = new ArrayList<AuthenticationProvider>();
		authenticationProviders.add(daoAuthenticationProvider());
		AuthenticationManager authenticationManager = new ProviderManager(authenticationProviders);
		return authenticationManager;
	}
}