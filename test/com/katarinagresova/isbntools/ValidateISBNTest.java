package com.katarinagresova.isbntools;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ValidateISBNTest {

	private final List<String> VALID_ISBN = Arrays.asList("0140449116", "0140177396");
	private final String INVALID_ISBN = "0140449117";

	@Test
	public void checkValidISBN() {
		ValidateISBN validator = new ValidateISBN();
		for (String validISBN: VALID_ISBN) {
			boolean result = validator.checkISBN(validISBN);
			assertTrue(String.format("Check failed for ISBN %s", validISBN), result);
		}
	}

	@Test
	public void checkInvalidISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN(INVALID_ISBN);
		assertFalse(result);
	}

	// "expected = NumberFormatException.class" is telling the test that valid outcome is when NumberFormatException
	// exception is thrown. Otherwise test will end as failed.
	@Test(expected = NumberFormatException.class)
	public void nineDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.checkISBN("123456789");
	}

	@Test(expected = NumberFormatException.class)
	public void nonNumericISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.checkISBN("helloworld");
	}
}