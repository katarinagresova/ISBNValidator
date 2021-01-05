package com.katarinagresova.isbntools;

public class ValidateISBN
{

	public static final int LONG_ISBN_LENGTH = 13;
	public static final int SHORT_ISBN_LENGTH = 10;
	public static final int SHORT_ISBN_MULTIPLIER = 11;
	public static final int LONG_ISBN_MULTIPLIER = 10;
	public static final int SHORT_ISBN_VALUE_OF_X = 10;

	public boolean checkISBN(String isbn)
	{

		int length = isbn.length();

		if (length == SHORT_ISBN_LENGTH) {
			return checkShortISBN(isbn);
		}
		else if (length == LONG_ISBN_LENGTH) {
			return checkLongISBN(isbn);
		}
		else {
			throw new NumberFormatException("ISBN number must have 10 or 13 digits.");
		}
	}

	private boolean checkShortISBN(String isbn) {

		if (!areAllDigits(getPrefixExceptLast(isbn)) || !isDigitOrX(getLastCharacter(isbn))) {
			throw new NumberFormatException("ISBN number can only contains numeric digits, character 'x' is allowed on last position.");
		}

		int total = 0;

		for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
			int digit = convertCharOrXToDigit(isbn.charAt(i));
			total += digit * (SHORT_ISBN_LENGTH - i);
		}

		return total % SHORT_ISBN_MULTIPLIER == 0;
	}

	private boolean checkLongISBN(String isbn) {
		if (!areAllDigits(isbn)) {
			throw new NumberFormatException("ISBN number can only contains numeric digits");
		}

		int total = 0;

		for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
			int digit = convertCharOrXToDigit(isbn.charAt(i));
			if (i % 2 == 0) {
				total += digit * 1;
			} else {
				total += digit * 3;
			}
		}

		return total % LONG_ISBN_MULTIPLIER == 0;
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
			return SHORT_ISBN_VALUE_OF_X;
		} else {
			return Character.getNumericValue(character);
		}
	}
}