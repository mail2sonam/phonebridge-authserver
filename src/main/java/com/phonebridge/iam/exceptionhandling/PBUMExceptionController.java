package com.phonebridge.iam.exceptionhandling;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.phonebridge.iam.exception.AccountDependentException;
import com.phonebridge.iam.exception.InvalidAccountException;
import com.phonebridge.iam.exception.InvalidParameterException;
import com.phonebridge.iam.exception.RecordNotFoundException;
import com.phonebridge.iam.exception.UserAlreadyExistsException;
import com.phonebridge.iam.exception.WrongAccountException;
import com.phonebridge.iam.rest.controller.v1.resp.ErrorResponse;
import com.phonebridge.iam.rest.controller.v1.resp.GenericResp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class PBUMExceptionController {

	Logger log = LoggerFactory.getLogger(PBUMExceptionController.class);
	
	/**
	 * handle invalid format exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidFormatException.class)
	public final ResponseEntity<Object> invalidFormatExceptions(InvalidFormatException ex) {
		log.error("invalidFormatExceptions:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Bad Request Invalid", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * handle json mapping exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> jsonExceptions(JsonMappingException ex) {
		log.error("jsonExceptions:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Bad Request JSON", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * handle invalid parameter exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex) {
		log.error("handleInvalidParameterException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Invalid parameter", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * handle user not found exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex) {
		log.error("handleUserNotFoundException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Record Not Found", details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * handle user not found exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UserAlreadyExistsException.class)
	public final ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		log.error("handleUserAlreadyExistsException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("User id is not available", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * handle invalid account exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidAccountException.class)
	public final ResponseEntity<Object> handleInvalidAccountException(InvalidAccountException ex) {
		log.error("handleInvalidAccountException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Invalid Account", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * handle account dependent exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AccountDependentException.class)
	public final ResponseEntity<Object> handleAccountDependentException(AccountDependentException ex) {
		log.error("handleAccountDependentException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Account is linked with an user", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * handle wrong account exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(WrongAccountException.class)
	public final ResponseEntity<Object> handleWrongAccountException(WrongAccountException ex) {
		log.error("handleWrongAccountException:::", ex);
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Account id should not be different", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	
	
	/**
	 * handle Access Denied
	 * exception handlers
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public final ResponseEntity<GenericResp> handleAccessDeniedException(HttpServletRequest request,Exception ex) {
		log.error("handleAccessDeniedException:::"+ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Access Denied", details);
		return new ResponseEntity<>(
				new GenericResp((String)request.getAttribute("requestId"),error)
				, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * handle all the remaining exceptions, if it is not handled by the above
	 * exception handlers
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
		log.error("handleAllExceptions:::"+ex.getMessage(),ex);
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Internal Server Error", details);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
