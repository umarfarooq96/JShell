package test;

import static org.junit.Assert.*;
import commands.exit;
import filesystem.WorkingDirectory;
import org.junit.Test;

public class exitTest {

	@Test
	public void testExitValidation() {
		exit e = new exit();
		String[] args = { "blah" };
		assertEquals(e.validate(args, new WorkingDirectory()), false);
	}

}
