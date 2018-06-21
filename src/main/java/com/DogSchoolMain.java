package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(value = {"com.dog.school"})
@PropertySource(value = "classpath:config.properties")
@EnableSwagger2
public class DogSchoolMain extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DogSchoolMain.class, args);
	}
}
