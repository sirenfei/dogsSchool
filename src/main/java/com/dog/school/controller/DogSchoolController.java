package com.dog.school.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dog.school.domain.Dogs;
import com.dog.school.dto.Results;
import com.dog.school.service.DogsService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Api(value ="dog School Test",tags="dog School Test")
@RestController
public class DogSchoolController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DogsService dogsService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "Register a dog api",
            notes = "if success return Success and registered information else return failed",
            httpMethod = "POST",
            response = String.class)
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
    public Results<Dogs> register(@ApiParam(value="dog name",required= true) String dogName) {
        logger.info("register dogName = {}", dogName);
        
         if(StringUtils.isBlank(dogName) )
         {
             return new Results<Dogs>(Results.FAIL, null);
         }
         if(dogName.length() > 255 )
         {
             return new Results<Dogs>(Results.TOO_LONG, null);
         }
         
         Dogs data = dogsService.addDogs(new Dogs(dogName.trim()));
         Results<Dogs> result = new Results<Dogs>(Results.SUCC, data);
         
         return result;
    }

    @GetMapping(value = "/findall")
    @ApiOperation(value = "List all dogs api",
            notes = "Return all dogs of the schaol. If no dogs,return []",
            response = List.class)
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
    public List<Dogs> findall() {
        logger.info("findall");
        return dogsService.findAllDogs();

    }

    @GetMapping(value = "/detail/{id}")
    @ApiOperation(value = "get the details of a dog",
            notes = "get the details of a dog by dog id ,the id must be a int greater than 0 less than Integer.MAX,Otherwise,  return {} ",
            response = Dogs.class)
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success")
    public Dogs detail(@ApiParam(value="dog id",required= true) @PathVariable("id") Integer id) {
        logger.info("detail id = {}", id);
        if(id<= 0  || id > Integer.MAX_VALUE)
        {
            return new Dogs();
        }  
        
        return dogsService.detail(id);

    }

    @RequestMapping("/deregister/{id}")
    @ApiOperation(value = "deregister a dog",
    notes = "deregister a dog by dog id if the dog exist and return OK,Otherwise,  return Failed",
    response = String.class)
    public String deregister(@ApiParam(value="dog id",required= true) @PathVariable("id") Integer id) {
        logger.info("deregister id = {}", id);

        return dogsService.removeDogs(id) ? "OK" : "Failed";
    }

    @RequestMapping("/search")
    @ApiOperation(value = "filter dogs api",
    notes = "filter dogs with a partial match of more than 3 characters and less than 255 characters when listing Otherwise, return []",
    response = List.class)
    @ResponseBody
    public List<Dogs> search(@ApiParam(value="dog name",required= true) String dogName) {
        logger.info("search name = {}", dogName);
        if(null == dogName || (dogName.length() <3 && dogName.length() >255))
                return Lists.newArrayList();
        return dogsService.findDogsByName(dogName);
    }

}