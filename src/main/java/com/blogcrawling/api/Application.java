package com.blogcrawling.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.blogcrawling.crawlingmodul.ParameterSearch;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ClassNotFoundException {
		//SpringApplication.run(Application.class, args);
		ParameterSearch crawling = new ParameterSearch();
		crawling.search("https://gurjeet.singh.im/blog/", "Postgres Hibernator");
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Application() {
		log.debug("Postgres blog crawling API Application is starting...");
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
