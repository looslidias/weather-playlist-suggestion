package com.looslidias.playlistsuggestion.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wather Playlist Suggestion - Web Service")
                .description("Service that searches playlists according to climatic evaluation")
                .version("1.0")
                .contact(new Contact("Rafael Loosli Dias", "https://www.linkedin.com/in/rafaellooslidias", "rafaldias@gmail.com"))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("playlistsuggestion-ws")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
