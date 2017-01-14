package test;

import org.junit.*;

import commands.history;
import storage.HistoryStorage;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class historyTest {

	public history h = new history();

	@BeforeClass
	public static void setUp() {
		HistoryStorage hs = HistoryStorage.createHistoryStorageInstance();
		hs.addCommand("first");
		hs.addCommand("second");
		hs.addCommand("third");
		hs.addCommand("fourth");

	}

	@AfterClass
	public static void tearDown() {
		HistoryStorage.resetHistoryStorage();
	}

	@Test
	public void testGetFullHistory() {
		@SuppressWarnings("serial")
		ArrayList<String> list = new ArrayList<String>() {
			{
				add("first");
				add("second");
				add("third");
				add("fourth");
			}
		};
		assertEquals(h.getHistory(), list);
	}

	@Test
	public void testGetHistoryWithNumber() {
		@SuppressWarnings("serial")
		ArrayList<String> list = new ArrayList<String>() {
			{
				add("third");
				add("fourth");
			}
		};
		assertEquals(h.getHistory(2), list);
	}

	@Test
	public void testGetHistoryZero() {
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(h.getHistory(0), list);
	}

	@Test
	public void testIsStringAnIntFalse() {
		String s = "a";
		assertEquals(history.isStringAnInt(s), false);
	}

	@Test
	public void testIsStringAnIntTrue() {
		String s1 = "1";
		assertEquals(history.isStringAnInt(s1), true);
	}

}
