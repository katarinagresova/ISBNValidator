package com.katarinagresova.isbntools;

public class ValidateISBN
{
	public boolean checkISBN(String isbn) {

		if (isbn.length() != 10) {
			throw new NumberFormatException("ISBN number must have 10 digits");
		}

		int total = 0;

		for (int i = 0; i < 10; i++) {
			int digit = isbn.charAt(i);
			total += digit * (10 - i);
		}

		return total % 11 == 0;
	}
}