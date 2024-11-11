package com.ssafynity_b.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ssafinity",
                description = "SSAFY 수료생들을 위한 커뮤니티 API"
        )
)
public class SwaggerConfig {

}