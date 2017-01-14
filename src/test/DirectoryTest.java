package test;

import static org.junit.Assert.*;

import org.junit.Test;

import filesystem.Directory;
import filesystem.File;

public class DirectoryTest {

	@Test
	public void testGetParentDirectory() {
		Directory root = new Directory("", null);
		Directory child = new Directory("child", root);

		assertEquals(root, child.getParentDirectory());
		assertEquals(null, root.getParentDirectory());
	}

	@Test
	public void testAddGetFile() {
		Directory root = new Directory("", null);
		File file = new File("name", "contents", root);

		root.addItem(file);

		assertEquals(file, root.getItem("name"));
	}

	@Test
	public void testAddGetDirectory() {
		Directory root = new Directory("", null);
		Directory directory = new Directory("name", root);

		root.addItem(directory);

		assertEquals(directory, root.getItem("name"));
	}

	@Test
	public void testFileExists() {
		Directory root = new Directory("", null);
		File file = new File("name", "contents", root);

		root.addItem(file);

		assertEquals(true, root.itemExists("name"));
		assertEquals(false, root.itemExists("invalid"));
	}

	@Test
	public void testDirectoryExists() {
		Directory root = new Directory("", null);
		Directory directory = new Directory("name", root);

		root.addItem(directory);

		assertEquals(true, root.itemExists("name"));
		assertEquals(false, root.itemExists("invalid"));
	}

	@Test
	public void testToString() {
		Directory root = new Directory("", null);
		Directory directory = new Directory("name", root);

		root.addItem(directory);

		assertEquals("name ", root.toString());
		assertEquals("", directory.toString());
	}

	@Test
	public void testGetName() {
		Directory root = new Directory("", null);
		Directory directory = new Directory("name", root);

		root.addItem(directory);

		assertEquals("", root.getName());
		assertEquals("name", directory.getName());
	}

	@Test
	public void testRename() {
		Directory root = new Directory("", null);
		Directory directory = new Directory("name", root);

		root.addItem(directory);

		directory.rename("newname");

		assertEquals("", root.getName());
		assertEquals("newname", directory.getName());
	}

}
