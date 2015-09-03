package org.itevents.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 * Created by volodya on 06.08.2015.
 */
@Configuration
@EnableSwagger2
public class SwaggerCustomConfig {

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(not(withClassAnnotation(ApiIgnore.class))).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "ITEvents API",
                "The project' RESTful API documentation",
                "1.0",
                "Terms of service",
                "Contact Email",
                "Licence Type",
                "License URL"
        );
        return apiInfo;
    }
}
