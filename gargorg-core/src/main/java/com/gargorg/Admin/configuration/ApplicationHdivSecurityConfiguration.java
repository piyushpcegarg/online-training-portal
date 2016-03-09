package com.gargorg.Admin.configuration;

import java.util.Arrays;

import org.hdiv.config.annotation.EnableHdivWebSecurity;
import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.config.annotation.ValidationConfigurer;
import org.hdiv.config.annotation.builders.SecurityConfigBuilder;
import org.hdiv.config.annotation.configuration.HdivWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHdivWebSecurity
public class ApplicationHdivSecurityConfiguration extends HdivWebSecurityConfigurerAdapter {
	
	@Override
    public void configure(SecurityConfigBuilder builder) {
		
		String[] excludedExtensions = {"css","png","jpg","js","eot","ttf","properties"};
		builder.build().setExcludedExtensions(Arrays.asList(excludedExtensions));
		builder.errorPage("/errorPage");
		builder.randomName(true);
		builder.showErrorPageOnEditableValidation(false);
		builder.maxPagesPerSession(8);
		builder.sessionExpired().homePage("/home");
		builder.sessionExpired().loginPage("/login");
    }

	@Override
    public void addExclusions(ExclusionRegistry registry) {
        registry.addUrlExclusions("/login").method("GET");
        registry.addUrlExclusions("/authenticationCheck").method("POST");
        registry.addUrlExclusions("/.*").method("GET");
    }
	
	@Override
    public void configureEditableValidation(ValidationConfigurer validationConfigurer) {
        validationConfigurer.addValidation("/.*");
    }
	
}
