package com.dog.school.conf;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig{

    @Bean
    public Docket api() {

        ResponseMessage error = new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Error")).build();

        ResponseMessage forbidden = new ResponseMessageBuilder().code(403).message("forbidden").build();

        List<ResponseMessage> response = Lists.newArrayList(error, forbidden);

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                // .apis(RequestHandlerSelectors.basePackage("com.dog.school.controller"))
                .paths(PathSelectors.any()).build().useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, response).apiInfo(apiInfo());

    }
    

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Practiv Technical Test", 
          "dog school microservice.", 
          "0.0.1", 
          "GNU", 
          new Contact("Richard Xue", "", "sirenxue@gmail.com"), 
          "License of API", "API license URL");
    }
}
