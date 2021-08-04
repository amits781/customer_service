package com.producer.customer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.producer.customer.config.MessageProducer;
import com.producer.customer.constants.ProducerConstants;
import com.producer.customer.model.Customer;
import com.producer.customer.model.CustomerResponse;

/**
 * Main controller for kafka related endpoints
 * 
 * @author Amit kumar Sharma
 *
 */
@RestController
@RequestMapping("/kafka")
public class ProducerController {

	private final Logger logger = LoggerFactory.getLogger(ProducerController.class);

	@Autowired
	MessageProducer customerProducer;

	/**
	 * This function receives the Customer object as body and push it into kafka.
	 * 
	 * @param transactionId
	 * @param activityId
	 * @param customer
	 */
	@PostMapping("/customer")
	public ResponseEntity<CustomerResponse> publishCustomer(
			@RequestHeader(name = ProducerConstants.TRANSACTION_ID) String transactionId,
			@RequestHeader(name = ProducerConstants.ACTIVITY_ID) String activityId,
			@Valid @RequestBody Customer customer) {

		logger.info("Request received TransactionId: {} | ActivityId: {}", transactionId, activityId);
		logger.info("Request Body: \n {}", customer);

		customerProducer.sendMessage(customer, transactionId, activityId);

		CustomerResponse response = new CustomerResponse();
		response.setCode(201);
		response.setStatus(ProducerConstants.SUCCESS);
		response.setMessage("customer object with Transaction-Id: " + transactionId + " | Activity-Id: " + activityId
				+ " pushed to kafka");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
