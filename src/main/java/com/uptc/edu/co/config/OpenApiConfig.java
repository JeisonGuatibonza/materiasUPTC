package com.uptc.edu.co.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Sistema Académico UPTC")
                        .description("Servicio HTTP con operaciones CRUD para Estudiantes, Materias e Inscripciones")
                        .version("1.0.0"));
    }
}