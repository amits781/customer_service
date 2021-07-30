package com.example.demo.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Customer;

@RestController
@RequestMapping("/kafka")
public class ProducerController {

  Logger logger = LoggerFactory.getLogger(ProducerController.class);

  @PostMapping("/customer")
  public void publishCustomer(@RequestHeader(name = "Transaction-Id") String transactionId,
      @RequestHeader(name = "Activity-Id") String activityId,
      @Valid @RequestBody Customer customer) {
    logger.info("Request received TransactionId: {} | ActivityId: {}", transactionId, activityId);
    logger.info("Request Body: \n {}", customer);
  }

}
