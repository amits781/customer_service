package com.producer.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Resource server to check the listed resource via OAuth tokens generated from
 * the Authorization server
 * 
 * @author Amit kumar Sharma
 *
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Secured all kafka endpoints and allowed other endpoints
		http.authorizeRequests().antMatchers("/kafka/**").authenticated().antMatchers("/").permitAll();
	}

	@Bean
	RestAuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	RestAccessDeniedHandler accessDeniedHandler() {
		return new RestAccessDeniedHandler();
	}

}
