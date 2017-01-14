package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.number;
import storage.HistoryStorage;

public class numberTest {

	HistoryStorage hs = HistoryStorage.createHistoryStorageInstance();
	number num = new number();

	@Before
	public void setUp() {
		HistoryStorage.resetHistoryStorage();
	}

	@Test
	public void testValidateNonNumber() {
		String[] args = { "b" };
		hs.addCommand("!b");
		assertEquals(num.validate(args), false);
	}

	@Test
	public void testValidateNegativeNumber() {
		String[] args = { "-4" };
		hs.addCommand("!-4");
		assertEquals(num.validate(args), false);
	}

	@Test
	public void testValidateTooManyArgs() {
		String[] args = { "b", "extra" };
		hs.addCommand("!b");
		assertEquals(num.validate(args), false);
	}

	@Test
	public void testValidateOutOfBounds() {
		String[] args = { "800" };
		hs.addCommand("!800");
		assertEquals(num.validate(args), false);
	}

}
