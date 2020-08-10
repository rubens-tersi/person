package com.autopass.person.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.autopass.person.PersonApplication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(basePackage(PersonApplication.class.getPackage().getName())).build()
                .apiInfo(apiInfo()).useDefaultResponseMessages(false).tags(new Tag("Person", "Person's basic information management"),
                        new Tag("PersonEmailApi", "Do email operations in previously registered persons"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Autopass - Person").description("Person management API.").version("1.0").build();
    }

}
