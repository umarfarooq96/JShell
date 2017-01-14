package test;

import static org.junit.Assert.*;

import org.junit.Test;

import commands.pushd;
import filesystem.WorkingDirectory;
import storage.DirectoryStack;

public class pushdTest {

	public pushd c = new pushd();
	public WorkingDirectory wd = new WorkingDirectory();
	public DirectoryStack ds = DirectoryStack.createDirectoryStack();

	/*
	 * We don't have to test validate or execute beyond this because it uses cd,
	 * which has already been tested and confirmed in previous sprints.
	 */

	@Test
	public void testPush() {
		wd.setWorkingDirectory("testerDIR");
		c.pushDIR(wd);
		assertEquals(ds.empty(), false);
	}

}
