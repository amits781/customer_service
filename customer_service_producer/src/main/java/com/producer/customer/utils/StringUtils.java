package com.producer.customer.utils;

/**
 * Utility class for string to provide common/reused functions
 * 
 * @author Amit kumar Sharma
 *
 */
public class StringUtils {

	private StringUtils() {
		throw new IllegalStateException("StringUtils class");
	}

	/**
	 * Function to mask the values of the sensitive field in the customer object
	 * 
	 * @param customerString
	 * @return masked string
	 */
	public static String maskCustomerFields(String customerString) {

		// masking last 4 digit of customerNumber
		customerString = customerString.replaceAll("(?<=customerNumber: .{6}).*", "****");
		// masking first 4 digit of birthdate
		customerString = customerString.replaceAll("(?<=birthdate: ).{2}-.{2}", "**-**");
		// masking first 4 characters of email
		customerString = customerString.replaceAll("(?<=email: ).{4}", "****");

		return customerString;
	}

}
