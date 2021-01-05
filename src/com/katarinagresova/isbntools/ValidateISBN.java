package com.katarinagresova.isbntools;

public class ValidateISBN
{
	public boolean checkISBN(String isbn) {

		if (isbn.length() != 10) {
			throw new NumberFormatException("ISBN number must have 10 digits.");
		}

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