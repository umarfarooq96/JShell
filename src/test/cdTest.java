package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.*;
import filesystem.WorkingDirectory;

public class cdTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		cd cdObj = new cd();

		cdObj.validate(empty, new WorkingDirectory());

		assertEquals("cd only accepts one argument.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file.txt" };

		cd cdObj = new cd();

		cdObj.validate(argFile, new WorkingDirectory());

		assertEquals("You did not enter a valid directory.\n", outPrint.toString());

		System.setOut(null);
	}
}
