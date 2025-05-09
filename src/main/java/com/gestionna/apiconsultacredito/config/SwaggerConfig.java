package com.gestionna.apiconsultacredito.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Consulta de Créditos")
                        .description("Documentação automática via OpenAPI/Swagger")
                        .version("1.0.0"));
    }
}
