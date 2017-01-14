package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.man;
import filesystem.WorkingDirectory;

public class manTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outPrint));
	}

	@Test
	public void testManNoParam() {
		String[] empty = {};

		man manObj = new man();

		manObj.validate(empty, new WorkingDirectory());
		assertEquals("Incorrect argument count!\n", outPrint.toString());

	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}
}
