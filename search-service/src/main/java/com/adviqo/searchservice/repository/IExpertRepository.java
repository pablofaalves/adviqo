package com.adviqo.searchservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adviqo.searchservice.entity.Expert;

/**
 * JPA repository interface for the Expert entity.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@Repository
public interface IExpertRepository
		extends JpaRepository<Expert, Long> {

	/**
	 * Find one expert by name with no case sensitive.
	 * 
	 * @param name
	 * @return
	 */
	Optional<Expert> findByNameIgnoreCase(String name);
	
	/**
	 * Find for all experts according to its language and sort capabilities.
	 * 
	 * @param language
	 * @param sort
	 * @return
	 */
	List<Expert> findAllByLanguageIgnoreCaseContaining(String language, Sort sort);
	
	/**
	 * Find all experts by its description.
	 * 
	 * @param description
	 * @return
	 */
	List<Expert> findAllByDescriptionIgnoreCaseContaining(String description);
}
