package com.katarinagresova.isbntools;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackManagementTest {

	@Test
	public void canGetCorrectLocatorCode() {

		// We are not trying to test external service. We just need it to work exactly for this test.
		// We are testing how StockManager works with data from external service
		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn, "Of Mice and Man", "J. Steinbeck");
			}
		};

		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
			@Override
			public Book lookup(String isbn) {
				return null;
			}
		};

		StockManager stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDatabaseService(testDatabaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
}