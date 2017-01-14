package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import commands.echo;
import filesystem.*;
import output.DataWriter;

public class EchoTest {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	@Test
	public void onEmptyArgument() {
		System.setOut(new PrintStream(outPrint));

		String[] empty = {};

		echo echoObj = new echo();

		echoObj.validate(empty, new WorkingDirectory());

		assertEquals("Invalid argument(s), echo requires atleast one argument.\n", outPrint.toString());

		System.setOut(null);
	}

	@Test
	public void onInvalidFileName() {
		System.setOut(new PrintStream(outPrint));

		String[] args = { "hello", ">>", "invalidFilename!.txt" };

		echo echoObj = new echo();

		echoObj.validate(args, new WorkingDirectory());

		assertEquals("File name cannot have an illegal character" + " such as \"!@$&*()?:[]\"<>'`|={}\\/,;\"\n",
				outPrint.toString());

		System.setOut(null);
	}
}
