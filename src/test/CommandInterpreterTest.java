package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import validation.CommandInterpreter;

public class CommandInterpreterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCommandName() {
		assertEquals("history", CommandInterpreter.getCommandName("history 96"));
		assertEquals("man", CommandInterpreter.getCommandName("    man       ls"));
	}

	@Test
	public void testGetArguments() {
		assertEquals("96", CommandInterpreter.getArguments("history 96"));
		assertEquals("ls", CommandInterpreter.getArguments("    man       ls"));
	}

}
