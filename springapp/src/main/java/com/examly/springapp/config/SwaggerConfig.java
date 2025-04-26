package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
 
@Configuration
public class SwaggerConfig {

    /* Define a bean for OpenAPI configuration */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                /* Set the API information */
                .info(new Info()
                    .title("PROKART") // Set the title of the API
                    .version("1.0.0") // Set the version of the API
                    .contact(new Contact()
                        .name("Contact") // Set the contact name
                        .email("abc@gmail.com") // Set the contact email
                        .url("#")) // Set the contact URL
                    .description("One Stop Solution")) // Set the description of the API
                /* Add security requirement for the API */
                .addSecurityItem(new SecurityRequirement()
                    .addList("Bearer Authentication"))
                /* Define components for the API */
                .components(new Components()
                    .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    /* Create a security scheme for API key */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT") // Set the bearer format to JWT
            .scheme("bearer"); // Set the scheme to bearer
    }
}

