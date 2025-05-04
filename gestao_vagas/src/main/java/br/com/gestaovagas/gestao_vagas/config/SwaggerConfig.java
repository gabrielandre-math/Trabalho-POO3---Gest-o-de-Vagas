package br.com.gestaovagas.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Trabalho POO3 - Gestão de Vagas")
                .description("API responsável pela gestão de vagas.")
                .version("0.1"))
                .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT");
    }
}
/*
 * @OpenAPIDefinition(info = @Info(title = "Trabalho POO3 - Gestão de Vagas",
 * description = "API responsável pela gestão de vagas.", version = "0.1"))
 * 
 * @SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT",
 * type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
 */