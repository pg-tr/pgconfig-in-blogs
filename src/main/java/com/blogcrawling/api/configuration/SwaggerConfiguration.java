package com.blogcrawling.api.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

/**
 * Swagger conf
 * @author atesel
 *
 */
@Configuration
public class SwaggerConfiguration {

  @Bean
  public GroupedOpenApi apiGroup() {
    return GroupedOpenApi.builder().group("Api").pathsToMatch("/api/**").build();
  }

  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI();
  }

}

