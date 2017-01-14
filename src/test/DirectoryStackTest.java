package test;

import static org.junit.Assert.*;
import org.junit.Test;

import storage.DirectoryStack;

public class DirectoryStackTest {

	@Test
	public void testPush() {
		DirectoryStack ds = DirectoryStack.createDirectoryStack();
		ds.pushDIR("test");
		assertEquals(ds.search("test"), 1);
	}

	@Test
	public void testPop() {
		DirectoryStack ds = DirectoryStack.createDirectoryStack();
		ds.pushDIR("test");
		assertEquals(ds.popDIR(), "test");
	}

	@Test
	public void testSearch() {
		DirectoryStack ds = DirectoryStack.createDirectoryStack();
		ds.pushDIR("test");
		assertEquals(ds.search("test"), 1);
	}

}
