package com.adviqo.searchservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for empty result in a search which returns a http status 204.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Search criteria has no results...")
public class EmptySearchResultException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
