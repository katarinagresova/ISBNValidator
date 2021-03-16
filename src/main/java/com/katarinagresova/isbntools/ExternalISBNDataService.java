package com.katarinagresova.isbntools;

public interface ExternalISBNDataService {
	public Book lookup(String isbn);
}
