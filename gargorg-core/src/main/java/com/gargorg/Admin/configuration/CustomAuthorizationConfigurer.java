package com.gargorg.Admin.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import com.gargorg.Admin.service.JdbcFilterInvocationSecurityMetadataSource;

/**
 * A base class for configuring the {@link FilterSecurityInterceptor}.
 *
 * <h2>Security Filters</h2>
 *
 * The following Filters are populated
 *
 * <ul>
 * <li>{@link FilterSecurityInterceptor}</li>
 * </ul>
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are populated to allow other {@link SecurityConfigurer}'s
 * to customize:
 * <ul>
 * <li>{@link FilterSecurityInterceptor}</li>
 * </ul>
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 * <ul>
 * <li>
 * {@link AuthenticationManager}
 * </li>
 * </ul>
 *
 *
 * @param <T> the CustomAuthorizationConfigurer
 * @param <H> the type of {@link HttpSecurityBuilder} that is being configured
 *
 */
@Component
public class CustomAuthorizationConfigurer
extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	private Boolean filterSecurityInterceptorOncePerRequest = true;

	private AccessDecisionManager accessDecisionManager;
	
	@Autowired
	private FilterInvocationSecurityMetadataSource metadataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//FilterInvocationSecurityMetadataSource metadataSource = createMetadataSource(http);
		if (metadataSource == null) {
			return;
		}
		FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor(
				http, metadataSource, http.getSharedObject(AuthenticationManager.class));
		if (filterSecurityInterceptorOncePerRequest != null) {
			securityInterceptor
					.setObserveOncePerRequest(filterSecurityInterceptorOncePerRequest);
		}
		securityInterceptor = postProcess(securityInterceptor);
		http.addFilter(securityInterceptor);
		http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);
	}

	/**
	 * @param http the builder to use
	 *
	 * @return the {@link FilterInvocationSecurityMetadataSource} to set on the
	 * {@link FilterSecurityInterceptor}. Cannot be null.
	 */
	/*private FilterInvocationSecurityMetadataSource createMetadataSource(HttpSecurity http) {
		return new JdbcFilterInvocationSecurityMetadataSource();
	}
*/
	/**
	 * @param http the builder to use
	 *
	 * @return the {@link AccessDecisionVoter} instances used to create the default
	 * {@link AccessDecisionManager}
	 */
	final List<AccessDecisionVoter<? extends Object>> getDecisionVoters(HttpSecurity http) {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix("");
		decisionVoters.add(roleVoter);
		decisionVoters.add(new AuthenticatedVoter());
		return decisionVoters;
	}

	/**
	 * Creates the default {@code AccessDecisionManager}
	 * @return the default {@code AccessDecisionManager}
	 */
	private AccessDecisionManager createDefaultAccessDecisionManager(HttpSecurity http) {
		AffirmativeBased result = new AffirmativeBased(getDecisionVoters(http));
		return postProcess(result);
	}

	/**
	 * If currently null, creates a default {@link AccessDecisionManager} using
	 * {@link #createDefaultAccessDecisionManager(HttpSecurityBuilder)}. Otherwise returns the
	 * {@link AccessDecisionManager}.
	 *
	 * @param http the builder to use
	 *
	 * @return the {@link AccessDecisionManager} to use
	 */
	private AccessDecisionManager getAccessDecisionManager(HttpSecurity http) {
		if (accessDecisionManager == null) {
			accessDecisionManager = createDefaultAccessDecisionManager(http);
		}
		return accessDecisionManager;
	}

	/**
	 * Creates the {@link FilterSecurityInterceptor}
	 *
	 * @param http the builder to use
	 * @param metadataSource the {@link FilterInvocationSecurityMetadataSource} to use
	 * @param authenticationManager the {@link AuthenticationManager} to use
	 * @return the {@link FilterSecurityInterceptor}
	 * @throws Exception
	 */
	private FilterSecurityInterceptor createFilterSecurityInterceptor(HttpSecurity http,
			FilterInvocationSecurityMetadataSource metadataSource,
			AuthenticationManager authenticationManager) throws Exception {
		FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
		securityInterceptor.setSecurityMetadataSource(metadataSource);
		securityInterceptor.setAccessDecisionManager(getAccessDecisionManager(http));
		securityInterceptor.setAuthenticationManager(authenticationManager);
		securityInterceptor.afterPropertiesSet();
		return securityInterceptor;
	}
}