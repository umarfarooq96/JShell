package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.*;
import filesystem.WorkingDirectory;

public class mkdirTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		mkdir mkdirObj = new mkdir();

		mkdirObj.validate(empty, new WorkingDirectory());

		assertEquals("You did not enter any directories to make.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file.txt" };

		mkdir mkdirObj = new mkdir();

		mkdirObj.validate(argFile, new WorkingDirectory());

		assertEquals("", outPrint.toString());

		System.setOut(null);
	}
}
