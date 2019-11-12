package com.adviqo.searchservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adviqo.searchservice.entity.Expert;
import com.adviqo.searchservice.exception.EmptySearchResultException;
import com.adviqo.searchservice.exception.InvalidSortArgumentException;
import com.adviqo.searchservice.repository.IExpertRepository;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for experts search.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@Api(value = "ExpertSearchController")
@RestController
@RequestMapping("/v1/experts/search")
@RequiredArgsConstructor
@Validated
public class ExpertSearchController {

	private final IExpertRepository expertRepository;

	/**
	 * GET HTTP method for search experts by name. 
	 * 
	 * @param name
	 * @return an Expert
	 * @throws EmptySearchResultException
	 */
	@GetMapping(path = "/name", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Expert findByName(
			@Size(max = 70, message = "Name cannot have more than 70 characters!") 
			@NotBlank(message = "Name cannot be blank!") String name) throws EmptySearchResultException {
		return expertRepository.findByNameIgnoreCase(name).orElseThrow(EmptySearchResultException::new);
	}

	/**
	 *  GET HTTP method for search experts by language with possibility to sort by 'pricePerMinute' and 'availability'. 
	 * 
	 * @param language
	 * @param sort
	 * @return list of Expert
	 * @throws InvalidSortArgumentException
	 * @throws EmptySearchResultException
	 */
	@GetMapping(path = "/language", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<Expert> findByLanguage(
			@Size(min = 3, max = 20, message = "Language cannot have less than 3 and more than 20 characters!") 
			@NotBlank(message = "Language cannot be blank!") String language,
			Sort sort) throws InvalidSortArgumentException, EmptySearchResultException {
		
		// Check sorting options requested
		if (sort.isSorted()) {
			Optional.ofNullable(Optional.ofNullable(sort.getOrderFor("pricePerMinute")).orElse(sort.getOrderFor("availability")))
					.orElseThrow(InvalidSortArgumentException::new);
		}
		
		List<Expert> result = expertRepository.findAllByLanguageIgnoreCaseContaining(language, sort);
		
		if (CollectionUtils.isEmpty(result)) {
			throw new EmptySearchResultException();
		}
		
		return result;
	}

	/**
	 * GET HTTP method for search experts by part of their description. 
	 * 
	 * @param description
	 * @return
	 * @throws EmptySearchResultException
	 */
	@GetMapping(path = "/description", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<Expert> findByDescription(
			@Size(min = 3, message = "Description cannot have less than 3 characters!") 
			@NotBlank(message = "Description cannot be blank!")  String description) throws EmptySearchResultException {
		List<Expert> result = expertRepository.findAllByDescriptionIgnoreCaseContaining(description);
		
		if (CollectionUtils.isEmpty(result)) {
			throw new EmptySearchResultException();
		}
		
		return result;
	}
}
