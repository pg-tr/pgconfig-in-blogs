package com.blogcrawling.api.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenFilter jwtTokenFilter;

	@Value("${springdoc.api-docs.path}")
	private String restApiDocPath;

	@Value("${springdoc.swagger-ui.path}")
	private String swaggerPath;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http = http.cors().and().csrf().disable();
		
		 http = http
	              .sessionManagement()
	              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	              .and();
		
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers(String.format("%s/**", restApiDocPath)).permitAll()
            .antMatchers(String.format("%s/**", swaggerPath)).permitAll()
            .antMatchers(String.format("%s/**", "/swagger-ui")).permitAll()
            .anyRequest().authenticated();
		
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
