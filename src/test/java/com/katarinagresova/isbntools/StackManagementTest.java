package com.katarinagresova.isbntools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StackManagementTest {

	private ExternalISBNDataService webService;
	private ExternalISBNDataService databaseService;
	private StockManager stockManager;

	@Before
	public void setup() {
		// We are not trying to test external service. We just need it to work exactly for this test.
		// We are testing how StockManager works with data from external service
		webService = mock(ExternalISBNDataService.class);
		databaseService = mock(ExternalISBNDataService.class);

		stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
	}

	@Test
	public void canGetCorrectLocatorCode() {

		when(webService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));
		when(databaseService.lookup(anyString())).thenReturn(null);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {

		// implementing only the part we need
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(0)).lookup(anyString());
	}

	@Test
	public void webServiceIsUsedIfDataIsNotPresentInDatabase() {

		// implementing only the part we need
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Man", "J. Steinbeck"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(databaseService, times(1)).lookup("0140177396");
		verify(webService, times(1)).lookup("0140177396");
	}

}