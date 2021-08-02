package com.producer.customer.constants;

import org.springframework.stereotype.Component;

/**
 * Hold the constant literals to be used throughout the application
 * 
 * @author Amit kumar Sharma
 *
 */
@Component
public class ProducerConstants {

	private ProducerConstants() {
		super();
	}

	public static final String TRANSACTION_ID = "Transaction-Id";
	public static final String ACTIVITY_ID = "Activity-Id";
	public static final String FAILED = "failed";
}
