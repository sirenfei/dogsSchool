package com.dog.school.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	protected static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	  private static final ObjectMapper objectMapper = new ObjectMapper();

	  
	  public static <T> List<T> fromJson(String jsonString, TypeReference<List<T>> clazz) 
	  {
		  
		  List<T> beanList = null;
		try {
			beanList = objectMapper.readValue(jsonString, new TypeReference<List<T>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		} 
		  
		return beanList;
	  }
	  
	  
	  public static <T> T fromJson(String jsonString, Class<T> clazz)
	  {
	    if (StringUtils.isEmpty(jsonString)) {
	      return null;
	    }
	    try
	    {
	      return objectMapper.readValue(jsonString, clazz);
	    }
	    catch (IOException e) {
	      logger.error("IOException parse json string error:" + jsonString, e);
	      return null;
	    }
	    catch (Exception e) {
	      logger.error("parse json string error:" + jsonString, e);
	    }return null;
	  }

	  public static <T> T fromJson(Reader reader, Class<T> clazz) {
	    if (reader == null) {
	      logger.error("error parse  reader is null");
	      return null;
	    }
	    try
	    {
	      return objectMapper.readValue(reader, clazz);
	    } catch (IOException e) {
	      logger.error("parse reader error:", e);
	    }return null;
	  }

	  public static String toJson(Object object)
	  {
	    try
	    {
	      return objectMapper.writeValueAsString(object);
	    } catch (IOException e) {
	      logger.warn("write to json string error:" + object, e);
	    }return null;
	  }



	  public static ObjectMapper getMapper()
	  {
	    return objectMapper;
	  }
}
