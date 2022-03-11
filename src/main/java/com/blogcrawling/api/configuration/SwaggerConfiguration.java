package com.blogcrawling.api.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Swagger conf
 * 
 * @author atesel
 *
 */
@Configuration
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi apiGroup() {
		return GroupedOpenApi.builder().group("api.json").pathsToMatch("/api/**").build();
	}

	@Bean
	public OpenAPI apiInfo() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList(securitySchemeName))
						.components(
						new Components().addSecuritySchemes(securitySchemeName,
								new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("...").description("...").version("1.0"));
	}

}
