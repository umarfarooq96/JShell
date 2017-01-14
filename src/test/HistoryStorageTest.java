package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import storage.HistoryStorage;

public class HistoryStorageTest {

	HistoryStorage hs = HistoryStorage.createHistoryStorageInstance();

	@Before
	public void setUp() {
		HistoryStorage.resetHistoryStorage();
	}

	@Test
	public void testAddAndGetHistory() {
		hs.addCommand("first");
		hs.addCommand("second");
		;
		@SuppressWarnings("serial")
		ArrayList<String> list = new ArrayList<String>() {
			{
				add("first");
				add("second");
			}
		};
		assertEquals(hs.getHistory(), list);
	}

	@Test
	public void testSetLastEntry() {
		hs.addCommand("previous last");
		hs.setLastEntry("new last");
		ArrayList<String> list = new ArrayList<String>() {
			{
				add("new last");
			}
		};
		assertEquals(hs.getHistory(), list);
	}

	@Test
	public void testRemoveLastEntry() {
		hs.addCommand("first");
		hs.addCommand("second");
		hs.removeLastEntry();
		ArrayList<String> list = new ArrayList<String>() {
			{
				add("first");
			}
		};
		assertEquals(hs.getHistory(), list);
	}

	@Test
	public void getNthCommand() {
		hs.addCommand("first");
		hs.addCommand("second");
		assertEquals(hs.getNthCommand(2), "second");
	}

}
