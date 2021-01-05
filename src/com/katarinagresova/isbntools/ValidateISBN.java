package com.katarinagresova.isbntools;

public class ValidateISBN
{
	public boolean checkISBN(String isbn)
	{

		int length = isbn.length();

		if (length == 10) {
			return checkISBN10(isbn);
		}
		else if (length == 13) {
			return checkISBN13(isbn);
		}
		else {
			throw new NumberFormatException("ISBN number must have 10 or 13 digits.");
		}
	}

	private boolean checkISBN10(String isbn) {

		if (!areAllDigits(getPrefixExceptLast(isbn)) || !isDigitOrX(getLastCharacter(isbn))) {
			throw new NumberFormatException("ISBN number can only contains numeric digits, character 'x' is allowed on last position.");
		}

		int total = 0;

		for (int i = 0; i < 10; i++) {
			int digit = convertCharOrXToDigit(isbn.charAt(i));
			total += digit * (10 - i);
		}

		return total % 11 == 0;
	}

	private boolean checkISBN13(String isbn) {
		if (!areAllDigits(isbn)) {
			throw new NumberFormatException("ISBN number can only contains numeric digits");
		}

		int total = 0;

		for (int i = 0; i < 13; i++) {
			int digit = convertCharOrXToDigit(isbn.charAt(i));
			if (i % 2 == 0) {
				total += digit * 1;
			} else {
				total += digit * 3;
			}
		}

		return total % 10 == 0;
	}

	private char getLastCharacter(String word) {
		return word.charAt(word.length() - 1);
	}

	private String getPrefixExceptLast(String word) {
		return word.substring(0, word.length() - 1);
	}

	private boolean isDigitOrX(char character) {
		return Character.isDigit(character) || Character.toLowerCase(character) == 'x';
	}

	private boolean areAllDigits(String word) {
		return word.chars().allMatch(Character::isDigit);
	}

	private int convertCharOrXToDigit(char character) {
		if (Character.toLowerCase(character) == 'x') {
			return 10;
		} else {
			return Character.getNumericValue(character);
		}
	}
}