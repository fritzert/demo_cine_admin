package com.frodas.cine.admin.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
//import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

import static com.frodas.cine.admin.util.constants.ConstantsPath.*;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Fritz Rodas",
                        email = "contact@frodas.com",
                        url = "https://frodas.com/cine"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Luami",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/cine"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://luami.com/cine"
                )
        },
        security = { // TODOS LOS ENDPOINTS TOMARAN ESTA OPCION POR DEFECTO SI NO ESTA DEFINIDO LA ANOTACION @SecurityRequirement EN EL CONTROLLER
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@Order(1)
@Configuration
// AGREGAR CANDADO TOKEN A TODOS LOS CONTROLLER
// DE QUITARSE NO APARECE EL ICONO NI CON @SecurityRequirement EN LA CLASE CONTROLLER
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    GroupedOpenApi authApi(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder()
                .displayName(API_GROUP_AUTH)
                .group(API_GROUP_AUTH.toLowerCase())
                .pathsToMatch(PATH_AUTH + "/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new io.swagger.v3.oas.models.info.Info().title(API_GROUP_AUTH + " API").version(appVersion)))
                .build();
    }

    @Bean
    GroupedOpenApi clienteApi(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder()
                .displayName(API_GROUP_CLIENTES)
                .group(API_GROUP_CLIENTES.toLowerCase())
                .pathsToMatch(PATH_CLIENTES + "/**")
                .addOpenApiCustomizer(openApi ->
                    openApi.info(new io.swagger.v3.oas.models.info.Info()
                                .title(API_GROUP_CLIENTES + " API")
                                .version(appVersion)
                                .description("Servicio para gestionar clientes")
                                .contact(new io.swagger.v3.oas.models.info.Contact().name("Frodas").url("https://github.com/fertz").email("xxxxxxx"))
                                .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("https://github.com/fertz")))
                        .servers(List.of(new io.swagger.v3.oas.models.servers.Server().url("http://localhost:9002"), new io.swagger.v3.oas.models.servers.Server().url("http://localhost:9022")))
                        .tags(List.of(new Tag().name("ClienteService").description("Servicio de Gestion de Clientes")))
        )
                .build();
    }

    @Bean
    GroupedOpenApi configuracionApi(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder()
                .displayName(API_GROUP_CONFIGURACIONES)
                .group(API_GROUP_CONFIGURACIONES.toLowerCase())
                .pathsToMatch(PATH_CONFIGURACIONES + "/**")
                .build();
    }

    @Bean
    GroupedOpenApi rolApi(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder()
                .displayName(API_GROUP_ROLES)
                .group(API_GROUP_ROLES.toLowerCase())
                .pathsToMatch(PATH_ROLES + "/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new io.swagger.v3.oas.models.info.Info().title(API_GROUP_ROLES + " API").version(appVersion)))
                .build();
    }

    @Bean
    GroupedOpenApi recoveryApi(@Value("${springdoc.version}") String appVersion) {
        return GroupedOpenApi.builder()
                .displayName(API_GROUP_RECOVERY)
                .group(API_GROUP_RECOVERY.toLowerCase())
                .pathsToMatch(PATH_RECOVERY + "/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new io.swagger.v3.oas.models.info.Info().title(API_GROUP_RECOVERY + " API").version(appVersion)))
                .build();
    }

}
