package com.adviqo.searchservice.test;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class for integration tests basic configuration
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

	protected MockMvc mvc;
	protected ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	WebApplicationContext webApplicationContext;

	/**
	 * Set up MockMvc object before all tests
	 */
	@BeforeAll
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
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
