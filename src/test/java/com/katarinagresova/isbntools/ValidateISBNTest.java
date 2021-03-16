package com.katarinagresova.isbntools;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ValidateISBNTest {

	private final List<String> VALID_ISBN_10 = Arrays.asList("0140449116", "0140177396");
	private final List<String> VALID_ISBN_13 = Arrays.asList("9781861978769", "9780306406157");
	private final List<String> INVALID_ISBN_10 = Arrays.asList("0140449117", "0140177397");
	private final List<String> INVALID_ISBN_13 = Arrays.asList("9781861978765", "9780306406155");

	@Test
	public void checkValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		for (String validISBN: VALID_ISBN_10) {
			boolean result = validator.checkISBN(validISBN);
			assertTrue(String.format("Check failed for ISBN %s", validISBN), result);
		}
	}
	@Test
	public void checkValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		for (String validISBN: VALID_ISBN_13) {
			boolean result = validator.checkISBN(validISBN);
			assertTrue(String.format("Check failed for ISBN %s", validISBN), result);
		}
	}

	@Test
	public void TenDigitISBNsEndingWithXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}

	@Test
	public void checkInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		for (String invalidISBN: INVALID_ISBN_10) {
			boolean result = validator.checkISBN(invalidISBN);
			assertFalse(String.format("Check failed for ISBN %s", invalidISBN), result);
		}
	}

	@Test
	public void checkInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		for (String invalidISBN: INVALID_ISBN_13) {
			boolean result = validator.checkISBN(invalidISBN);
			assertFalse(String.format("Check failed for ISBN %s", invalidISBN), result);
		}
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