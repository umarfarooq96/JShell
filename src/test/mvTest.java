package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.mv;
import filesystem.WorkingDirectory;

public class mvTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		mv mvObj = new mv();

		mvObj.validate(empty, new WorkingDirectory());

		assertEquals("mv only accepts two arguments.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file1.txt", "file2.txt" };

		mv mvObj = new mv();

		mvObj.validate(argFile, new WorkingDirectory());

		assertEquals("You did not enter a valid item to move.\n", outPrint.toString());

		System.setOut(null);
	}
}
