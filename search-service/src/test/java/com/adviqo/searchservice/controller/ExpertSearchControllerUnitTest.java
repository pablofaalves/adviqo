package com.adviqo.searchservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adviqo.searchservice.entity.Expert;
import com.adviqo.searchservice.enums.AvailabilityEnum;
import com.adviqo.searchservice.repository.IExpertRepository;
import com.adviqo.searchservice.test.AbstractUnityTest;

/**
 * Unit test class for ExpertSearchController.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@WebMvcTest(ExpertSearchController.class)
public class ExpertSearchControllerUnitTest extends AbstractUnityTest {

	@MockBean
	private IExpertRepository repository;

	private List<Expert> experts;

	/**
	 * Set up method for creating mock experts list
	 */
	@BeforeAll
	public void setUp() {

		this.experts = new ArrayList<Expert>();
		Expert expertPablo = Expert.builder().id(1L).name("Pablo Alves").description("Description about Pablo Alves")
				.language("German").pricePerMinute(10.01d).availability(AvailabilityEnum.ONLINE).build();

		experts.add(expertPablo);
		experts.add(Expert.builder().id(1L).name("Natan Hulhe").description("Description about Natan Hulhe")
				.language("German").pricePerMinute(15.21d).availability(AvailabilityEnum.BUSY).build());
	}

	/**
	 * Successful test for search expert by name
	 * 
	 * @throws Exception
	 */
	@Test
	public void findByNameSuccess() throws Exception {
		Mockito.when(repository.findByNameIgnoreCase("Pablo Alves")).thenReturn(Optional.of(experts.get(0)));

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/name?name=Pablo Alves")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		Expert expertReturned = super.convertResultContent(result, Expert.class);
		assertEquals("Pablo Alves", expertReturned.getName());
	}

	/**
	 * Test for search expert by name with no content http status
	 * 
	 * @throws Exception
	 */
	@Test
	public void findByNameFail() throws Exception {
		Mockito.when(repository.findByNameIgnoreCase("pablo")).thenReturn(Optional.empty());

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/name?name=pablo")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}

    /**
     * Success test for search experts by language
     * 
     * @throws Exception
     */
	@Test
	public void testSearchByLanguageSuccess() throws Exception {
		Mockito.when(repository.findAllByLanguageIgnoreCaseContaining("German", Sort.unsorted())).thenReturn(experts);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=German")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Expert> expertsReturned = Arrays.asList(super.convertResultContent(result, Expert[].class));

		assertEquals(2, expertsReturned.size());
		assertEquals(AvailabilityEnum.ONLINE, expertsReturned.get(0).getAvailability());
	}

    /**
     * Test for sort parameter fail with bad request http status.
     * 
     * @throws Exception
     */
	@Test
	public void testSearchByLanguageOrderFail() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get("/v1/experts/search/language?language=eng&sort=nothing").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

    /**
     * Test for search experts by language with no content http status
     * 
     * @throws Exception
     */
	@Test
	public void testSearchByLanguageEmptyResult() throws Exception {
		Mockito.when(repository.findAllByLanguageIgnoreCaseContaining("arabic", Sort.unsorted()))
				.thenReturn(new ArrayList<Expert>());

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/language?language=arabic"))
				.andReturn();
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}

    /**
     * Test for search experts by description with successful return of contents
     * 
     * @throws Exception
     */
	@Test
	public void testSearchByDescription() throws Exception {
		Mockito.when(repository.findAllByDescriptionIgnoreCaseContaining("about"))
			.thenReturn(experts);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/experts/search/description?description=about")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Expert> expertsReturned = Arrays.asList(super.convertResultContent(result, Expert[].class));

		assertEquals(2, expertsReturned.size());
	}

    /**
     * Test for search experts by description with no content http status
     * 
     * @throws Exception
     */
	@Test
	public void testSearchByDescriptionEmptyResult() throws Exception {
		Mockito.when(repository.findAllByDescriptionIgnoreCaseContaining("non existing text"))
		.thenReturn(new ArrayList<Expert>());
		
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/v1/experts/search/description?description=non existing text"))
				.andReturn();
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
}
