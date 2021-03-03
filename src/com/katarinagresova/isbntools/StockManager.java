package com.katarinagresova.isbntools;

import java.util.Arrays;

public class StockManager {

	private ExternalISBNDataService service;

	public String getLocatorCode(String isbn) {
		Book book = service.lookup(isbn);
		StringBuilder locator = new StringBuilder();
		locator.append(isbn.substring(isbn.length() - 4));
		locator.append(book.getAuthor().charAt(0));
		locator.append(book.getTitle().split(" ").length);

		return locator.toString();
	}

	public void setService(ExternalISBNDataService service) {
		this.service = service;
	}
}