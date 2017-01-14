package test;

import static org.junit.Assert.*;

import org.junit.Test;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;

public class FileSystemTest {

	@Test
	public void testAddGetItem() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);
		File file1 = new File("file1", "contents", child2);
		File file2 = new File("file2", "contents", child3);

		child2.addItem(file1);
		child3.addItem(file2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(fs.getRootDirectory(), fs.getItem("/"));
		assertEquals(child1, fs.getItem("/child1"));
		assertEquals(child2, fs.getItem("/child1/child2"));
		assertEquals(child3, fs.getItem("/child1/child2/child3"));
		assertEquals(file1, fs.getItem("/child1/child2/file1"));
		assertEquals(file1, fs.getItem("/child1/child2/file1/"));
		assertEquals(file2, fs.getItem("/child1/child2/child3/file2"));
		assertEquals(file2, fs.getItem("/child1/child2/child3/../child3/file2"));
	}

	@Test
	public void testGetDirectoryFromPath() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);
		File file1 = new File("file1", "contents", child2);
		File file2 = new File("file2", "contents", child3);

		child2.addItem(file1);
		child3.addItem(file2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(fs.getRootDirectory(), fs.getDirectoryFromPath("/"));
		assertEquals(child1, fs.getDirectoryFromPath("/child1"));
		assertEquals(child2, fs.getDirectoryFromPath("/child1/child2"));
		assertEquals(child3, fs.getDirectoryFromPath("/child1/child2/child3"));
		assertEquals(child2, fs.getDirectoryFromPath("/child1/child2/file1"));
		assertEquals(child2, fs.getDirectoryFromPath("/child1/child2/file1/"));
		assertEquals(child3, fs.getDirectoryFromPath("/child1/child2/child3/file2"));
		assertEquals(child3, fs.getDirectoryFromPath("/child1/child2/child3/../child3/file2"));
	}

	@Test
	public void testIsValidFile() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);
		File file1 = new File("file1", "contents", child2);
		File file2 = new File("file2", "contents", child3);

		child2.addItem(file1);
		child3.addItem(file2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(false, fs.isValidFile("/"));
		assertEquals(true, fs.isValidFile("/child1/child2/file1"));
		assertEquals(true, fs.isValidFile("/child1/child2/file1/"));
		assertEquals(true, fs.isValidFile("/child1/child2/../child2/file1"));
		assertEquals(true, fs.isValidFile("/child1/child2/child3/file2"));
		assertEquals(false, fs.isValidFile("/child1/child2/file2"));
		assertEquals(false, fs.isValidFile("/child1/"));
		assertEquals(false, fs.isValidFile("/child1"));
	}

	@Test
	public void testIsValidDirectory() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(true, fs.isValidDirectory("/"));
		assertEquals(true, fs.isValidDirectory("/child1"));
		assertEquals(true, fs.isValidDirectory("/child1/child2"));
		assertEquals(true, fs.isValidDirectory("/child1/child2/child3"));
		assertEquals(false, fs.isValidDirectory("/child1/child2/child4"));
		assertEquals(false, fs.isValidDirectory("/child9/child2/child4"));
	}

	@Test
	public void testDirectoryToPath() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(fs.getRootDirectory(), fs.getDirectoryFromPath("/"));
		assertEquals("/child1", fs.directoryToPath(child1));
		assertEquals("/child1/child2", fs.directoryToPath(child2));
		assertEquals("/child1/child2/child3", fs.directoryToPath(child3));
	}

	@Test
	public void testPathToItemForDirectory() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);

		assertEquals(child3, fs.getItem("/child1/child2/child3"));
		assertEquals(child3, fs.getItem("/child1/child2/child3/../child3"));
	}

	@Test
	public void testPathToItemForFile() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		Directory child1 = new Directory("child1", fs.getRootDirectory());
		Directory child2 = new Directory("child2", child1);
		Directory child3 = new Directory("child3", child2);
		File file = new File("myfile", "", child3);

		fs.addItem(fs.getRootDirectory(), child1);
		fs.addItem(child1, child2);
		fs.addItem(child2, child3);
		fs.addItem(child3, file);

		assertEquals(file, fs.getItem("/child1/child2/child3/myfile"));
		assertEquals(file, fs.getItem("/child1/child2/child3/../child3/myfile"));
	}

}
