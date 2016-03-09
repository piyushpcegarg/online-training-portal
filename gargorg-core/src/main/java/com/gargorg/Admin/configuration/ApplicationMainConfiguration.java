package com.gargorg.Admin.configuration;

import java.util.Locale;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.spring3.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
 
@Configuration
@ComponentScan(basePackages = "com.gargorg")
@EnableWebMvc
@EnableTransactionManagement
@EnableCaching
@EnableAspectJAutoProxy
public class ApplicationMainConfiguration {

	@Bean
	public static PropertyPlaceholderConfigurer propertyConfigurer() {
		final EncryptablePropertyPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer = new EncryptablePropertyPlaceholderConfigurer(
				configurationEncryptor());
		Resource[] propertyFileLocations = new Resource[] { new ClassPathResource("jdbc.properties"),
				new ClassPathResource("SpringSecurity.properties") };
		encryptablePropertyPlaceholderConfigurer.setLocations(propertyFileLocations);
		return encryptablePropertyPlaceholderConfigurer;
	}

	@Bean
	public static StringEncryptor configurationEncryptor() {
		final StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setPassword("password");
		return standardPBEStringEncryptor;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		return sessionLocaleResolver;
	}
}
