package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.*;
import filesystem.WorkingDirectory;

public class grepTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		grep grepObj = new grep();

		grepObj.validate(empty, new WorkingDirectory());

		assertEquals("grep expects either 2 or 3 arguments. If 3, -r must be the first.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file1.txt", "file2.txt" };

		grep grepObj = new grep();

		grepObj.validate(argFile, new WorkingDirectory());

		assertEquals("grep expects a valid file as the last argument.\n", outPrint.toString());

		System.setOut(null);
	}

}
