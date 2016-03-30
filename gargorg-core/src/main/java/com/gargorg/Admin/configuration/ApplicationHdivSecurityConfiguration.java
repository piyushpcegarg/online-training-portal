package com.gargorg.Admin.configuration;

import java.util.Arrays;

import org.hdiv.config.annotation.EnableHdivWebSecurity;
import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.config.annotation.ValidationConfigurer;
import org.hdiv.config.annotation.builders.SecurityConfigBuilder;
import org.hdiv.config.annotation.configuration.HdivWebSecurityConfigurerAdapter;
import org.hdiv.config.xml.ConfigBeanDefinitionParser;
import org.hdiv.urlProcessor.FormUrlProcessor;
import org.hdiv.urlProcessor.LinkUrlProcessor;
import org.hdiv.web.servlet.support.HdivRequestDataValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

@Configuration
@EnableHdivWebSecurity
public class ApplicationHdivSecurityConfiguration extends HdivWebSecurityConfigurerAdapter {
	
	@Autowired
	protected FormUrlProcessor formUrlProcessor;

	@Autowired
	protected LinkUrlProcessor linkUrlProcessor;
	
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
	
	@Bean(name = ConfigBeanDefinitionParser.REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME)
	public RequestDataValueProcessor requestDataValueProcessor() {

		HdivRequestDataValueProcessor dataValueProcessor = new HdivRequestDataValueProcessor();
		dataValueProcessor.setFormUrlProcessor(this.formUrlProcessor);
		dataValueProcessor.setLinkUrlProcessor(this.linkUrlProcessor);
		return dataValueProcessor;
	}
	
}
