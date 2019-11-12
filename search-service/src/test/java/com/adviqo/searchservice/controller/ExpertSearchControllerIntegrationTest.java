package com.adviqo.searchservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adviqo.searchservice.SearchServiceApplication;
import com.adviqo.searchservice.entity.Expert;
import com.adviqo.searchservice.enums.AvailabilityEnum;
import com.adviqo.searchservice.test.AbstractIntegrationTest;

/**
 * Integration test class for ExpertSearchController.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@SpringBootTest(classes = SearchServiceApplication.class)
class ExpertSearchControllerIntegrationTest extends AbstractIntegrationTest{
	
	/**
	 * Successful test for search expert by name
	 * 
	 * @throws Exception
	 */
    @Test
    public void testSearchByNameSucess() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/name?name=pablo alves 1")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    	
    	Expert expertReturned = super.convertResultContent(result, Expert.class);
    	assertEquals("Pablo Alves 1" , expertReturned.getName());
    }
    
	/**
	 * Test for search expert by name with no content http status
	 * 
	 * @throws Exception
	 */
    @Test
    public void testSearchByNameEmptyResult() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/name?name=pablo")).andReturn();
    	assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }
    
    /**
     * Test for search experts by language ordered by availability
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByLanguageOrderByAvailability() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=eng&sort=availability")
    			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    	
    	List<Expert> expertsReturned = Arrays.asList(super.convertResultContent(result, Expert[].class));
    	
    	assertEquals(3, expertsReturned.size());
    	assertEquals(AvailabilityEnum.BUSY, expertsReturned.get(0).getAvailability());
    }

    /**
     * Test for search experts by language ordered by pricePerMinute
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByLanguageOrderByPricePerMinute() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=eng&sort=pricePerMinute")
    			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    	
    	List<Expert> expertsReturned = Arrays.asList(super.convertResultContent(result, Expert[].class));
    	
    	assertEquals(3, expertsReturned.size());
    	assertEquals(3.00d, expertsReturned.get(0).getPricePerMinute());
    }
    
    /**
     * Test for search experts by language with no content http status
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByLanguageEmptyResult() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=arabic")).andReturn();
    	assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }
    
    /**
     * Test for language parameter size fail with bad request http status.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByLanguageSizeFailure() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=a")).andReturn();
    	assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
    
    /**
     * Test for search experts by description with successful return of contents
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByDescription() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/description?description=is about")
    			.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    	assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    	
    	List<Expert> expertsReturned = Arrays.asList(super.convertResultContent(result, Expert[].class));
    	
    	assertEquals(7, expertsReturned.size());
    }
    
    /**
     * Test for search experts by description with no content http status
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByDescriptionEmptyResult() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/description?description=non existing text")).andReturn();
    	assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }
    
    /**
     * Test for description parameter size fail with bad request http status.
     * 
     * @throws Exception
     */
    @Test
    public void testSearchByDescriptionSizeFailure() throws Exception {
    	MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/description?description=a")).andReturn();
    	assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}
