package com.dog.school.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(value ="health test")
@Controller
public class TestController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${version}")
    private String version;

    @RequestMapping(value = "/healthz", method = RequestMethod.GET)
    @ApiOperation(value = "health test",
            notes = "if program is health return OK otherwise return 404 page not found",
            httpMethod = "GET",
            response = String.class)
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    @ResponseBody
    public String health() {
        logger.info("health done");
        return "OK";

    }

    @GetMapping(value = "/version")
    @ApiOperation(value = "show version",
            response = String.class)
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "version")
    @ResponseBody
    public String version() {
        logger.info("version");
        return version;

    }

}