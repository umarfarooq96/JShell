package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.cat;
import filesystem.*;

public class CatTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		cat catObj = new cat();

		catObj.validate(empty, new WorkingDirectory());

		assertEquals("cat: This command takes at least 1 argument.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onNonExistingFile() {
		System.setOut(new PrintStream(outPrint));

		String[] argFile = { "file.txt" };

		cat catObj = new cat();

		catObj.validate(argFile, new WorkingDirectory());

		assertEquals("", outPrint.toString());

		System.setOut(null);
	}
}
