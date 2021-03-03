package com.katarinagresova.isbntools;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackManagementTest {

	@Test
	public void canGetCorrectLocatorCode() {
		String isbn = "0140177396";
		StockManager stockManager = new StockManager();
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
}