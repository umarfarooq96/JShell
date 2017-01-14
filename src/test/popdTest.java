package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import commands.popd;
import storage.DirectoryStack;

public class popdTest {

	@BeforeClass
	public static void setUp() {
		DirectoryStack ds = DirectoryStack.createDirectoryStack();
		ds.pushDIR("test");
	}

	@Test
	public void testGetPopD() {
		popd c = new popd();
		assertEquals(c.getPopD(), "test");
	}

	@Test
	public void testEmptyStackPop() {
		popd c = new popd();
		assertEquals(c.isEmpty(), false);
	}

}
