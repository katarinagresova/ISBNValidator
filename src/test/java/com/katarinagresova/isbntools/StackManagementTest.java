package com.katarinagresova.isbntools;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StackManagementTest {

	@Test
	public void canGetCorrectLocatorCode() {

		// We are not trying to test external service. We just need it to work exactly for this test.
		// We are testing how StockManager works with data from external service
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		when(webService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));

		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		when(databaseService.lookup(anyString())).thenReturn(null);

		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

		// implementing only the part we need
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));

		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(0)).lookup(anyString());
	}

	@Test
	public void webServiceIsUsedIfDataIsNotPresentInDatabase() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

		// implementing only the part we need
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));

		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(1)).lookup("0140177396");
	}

}