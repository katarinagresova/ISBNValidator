package com.katarinagresova.isbntools;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidateISBNTest {

	private final String VALID_ISBN = "0140449116";

	@Test
	public void checkValidISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN(VALID_ISBN);
		assertTrue(result);
	}

}