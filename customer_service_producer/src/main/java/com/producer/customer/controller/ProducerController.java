package com.producer.customer.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.producer.customer.model.Customer;

/**
 * Main controller for kafka related endpoints
 * @author Amit kumar Sharma
 *
 */
@RestController
@RequestMapping("/kafka")
public class ProducerController {

  private final Logger logger = LoggerFactory.getLogger(ProducerController.class);

  /**
   * This function receives the Customer object as body
   * and push it into kafka.
   * @param transactionId
   * @param activityId 
   * @param customer 
   */
  @PostMapping("/customer")
  public void publishCustomer(@RequestHeader(name = "Transaction-Id") String transactionId,
      @RequestHeader(name = "Activity-Id") String activityId,
      @Valid @RequestBody Customer customer) {
    logger.info("Request received TransactionId: {} | ActivityId: {}", transactionId, activityId);
    logger.info("Request Body: \n {}", customer);
  }

}
