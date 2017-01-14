package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.*;
import filesystem.WorkingDirectory;

public class lsTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		ls lsObj = new ls();

		lsObj.validate(empty, new WorkingDirectory());

		assertEquals("", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingPath() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file.txt" };

		ls lsObj = new ls();

		lsObj.validate(argFile, new WorkingDirectory());

		assertEquals("The path you entered is invalid.\n", outPrint.toString());

		System.setOut(null);
	}
}
