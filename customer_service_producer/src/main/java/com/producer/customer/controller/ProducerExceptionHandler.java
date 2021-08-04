package com.producer.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.producer.customer.constants.ProducerConstants;
import com.producer.customer.model.CustomerErrorResponse;

@RestControllerAdvice
public class ProducerExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(ProducerExceptionHandler.class);
	private static final String ERROR_LOG = "Request failed for TransactionId: {} | ActivityId: {} | Error : {}";

	/**
	 * To handle 401 exceptions, i.e not authenticated
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<CustomerErrorResponse> handleAuthenticationException(AuthenticationException exception,
			WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(401);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(exception.getLocalizedMessage());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * To handle 403 exceptions, i.e not authorized
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<CustomerErrorResponse> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(403);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(exception.getLocalizedMessage());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	/**
	 * To handle 404 exceptions, i.e page not found
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<CustomerErrorResponse> handleNoHandlerFound(NoHandlerFoundException exception,
			WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(404);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(exception.getLocalizedMessage());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * To handle validation exceptions (400)
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomerErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception,
			WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		StringBuilder errors = new StringBuilder();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.append(fieldName + " : " + errorMessage + " | ");
		});
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(400);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(errors.toString());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * To handle exceptions related to kafka
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(KafkaException.class)
	public ResponseEntity<CustomerErrorResponse> handleKafkaTimeOutException(KafkaException exception,
			WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(503);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(exception.getLocalizedMessage());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	/**
	 * To handle any other exceptions (500)
	 * 
	 * @param exception
	 * @param request
	 * @return CustomerErrorResponse with respective values
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomerErrorResponse> handleGenericException(Exception exception, WebRequest request) {
		logger.error(ERROR_LOG, request.getHeader(ProducerConstants.TRANSACTION_ID),
				request.getHeader(ProducerConstants.ACTIVITY_ID), exception.getLocalizedMessage());
		CustomerErrorResponse response = new CustomerErrorResponse();
		response.setCode(500);
		response.setErrorType(exception.getClass().getSimpleName());
		response.setMessage(exception.getLocalizedMessage());
		response.setStatus(ProducerConstants.FAILED);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}