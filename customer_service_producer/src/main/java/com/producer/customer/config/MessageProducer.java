package com.producer.customer.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.producer.customer.constants.ProducerConstants;
import com.producer.customer.model.Customer;

/**
 * Producer class to produce messages to kafka
 * 
 * @author Amit kumar Sharma
 *
 */
@Component
public class MessageProducer {

	@Autowired
	private KafkaTemplate<String, Customer> customerKafkaTemplate;

	@Value(value = "${message.topic.name}")
	private String topicName;

	private final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	/**
	 * Method that receives the message object and push it into the kafka, along
	 * with additional headers
	 * 
	 * @param customerMesage - Message object
	 * @param transactionId  - Sent as header, represent unique id for transaction
	 * @param activityId     - Sent as header, represent unique id for activity
	 */
	public void sendMessage(Customer customerMesage, String transactionId, String activityId) {

		// Adding headers to the message
		List<Header> headers = new ArrayList<>();
		headers.add(new RecordHeader(ProducerConstants.TRANSACTION_ID, transactionId.getBytes()));
		headers.add(new RecordHeader(ProducerConstants.ACTIVITY_ID, activityId.getBytes()));

		// Creating record to be sent to kafka defining topic_name, partition, key,
		// message and headers
		ProducerRecord<String, Customer> customerRecord = new ProducerRecord<>(topicName, null, "customer_data",
				customerMesage, headers);

		ListenableFuture<SendResult<String, Customer>> future = customerKafkaTemplate.send(customerRecord);

		// callBack function to handle success/fail cases
		future.addCallback(new ListenableFutureCallback<SendResult<String, Customer>>() {

			@Override
			public void onSuccess(SendResult<String, Customer> result) {
				logger.info("Success: Message sent to kafka for transaction-id: {} | activity-id: {} | offset: {}",
						transactionId, activityId, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Failed: Message not sent to kafka for transaction-id: {} | activity-id: {} |  error: {}",
						transactionId, activityId, ex.getMessage());
				throw new KafkaProducerException(customerRecord, ex.getMessage(), ex);
			}
		});
	}

}