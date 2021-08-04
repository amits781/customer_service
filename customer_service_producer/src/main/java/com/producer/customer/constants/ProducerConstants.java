package com.producer.customer.constants;

/**
 * Hold the constant literals to be used throughout the application
 * 
 * @author Amit kumar Sharma
 *
 */
public class ProducerConstants {

	private ProducerConstants() {
		throw new IllegalStateException("Constant Variable class");
	}

	// Header Constants
	public static final String TRANSACTION_ID = "Transaction-Id";
	public static final String ACTIVITY_ID = "Activity-Id";

	// Response constants
	public static final String FAILED = "failed";
	public static final String SUCCESS = "success";
}
