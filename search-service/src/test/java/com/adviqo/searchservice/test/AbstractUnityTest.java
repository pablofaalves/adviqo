package com.adviqo.searchservice.test;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class for unity tests basic configuration
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractUnityTest {

	@Autowired
	protected MockMvc mvc;
	
	protected ObjectMapper jsonMapper = new ObjectMapper();
	
	/**
	 * Utility method for converting json in a class.
	 * 
	 * @param <T>
	 * @param result
	 * @param clazz
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 */
	protected <T> T convertResultContent(MvcResult result, Class<T> clazz) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		return jsonMapper.readValue(result.getResponse().getContentAsString(),
				clazz);
	}
}
