package com.adviqo.searchservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for invalid sort arguments which returns a http status 400.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid sort parameter!")
public class InvalidSortArgumentException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
