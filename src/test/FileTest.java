package test;

import static org.junit.Assert.*;
import org.junit.Test;
import filesystem.Directory;
import filesystem.File;

public class FileTest {

	@Test
	public void testGetParentDirectory() {
		Directory root = new Directory("", null);
		File file = new File("file.txt", "anything", root);

		assertEquals(root, file.getParentDirectory());
	}

	@Test
	public void testGetContents() {
		Directory root = new Directory("", null);
		File file = new File("file.txt", "Hello world", root);

		assertEquals("Hello world", file.getContents());
	}

	@Test
	public void testWriteToFile() {
		Directory root = new Directory("", null);
		File file = new File("file.txt", "", root);

		file.writeToFile("Hello world");

		assertEquals("Hello world", file.getContents());
	}

	@Test
	public void testAppendToFile() {
		Directory root = new Directory("", null);
		File file = new File("file.txt", "First line", root);

		file.appendToFile("Second line");

		assertEquals("First line\nSecond line", file.getContents());
	}

	@Test
	public void testOverwriteToFile() {
		Directory root = new Directory("", null);
		File file = new File("file.txt", "This existed before", root);

		file.writeToFile("This overwrote it");

		assertEquals("This overwrote it", file.getContents());
	}

}
