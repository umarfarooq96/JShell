package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.cp;
import filesystem.WorkingDirectory;

public class cpTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		cp cpObj = new cp();

		cpObj.validate(empty, new WorkingDirectory());

		assertEquals("cp: error: command only takes 2 arguments.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file1.txt", "file2.txt" };

		cp catObj = new cp();

		catObj.validate(argFile, new WorkingDirectory());

		assertEquals("cp: error: You did not enter a valid item to copy.\n", outPrint.toString());

		System.setOut(null);
	}
}
