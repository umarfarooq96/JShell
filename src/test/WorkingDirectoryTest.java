package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.WorkingDirectory;

public class WorkingDirectoryTest {

	@Test
	public void testRootDirectoryGet() {
		WorkingDirectory wd = new WorkingDirectory();

		assertEquals("/", wd.getWorkingDirectory());
	}

	@Test
	public void testSetGet() {
		WorkingDirectory wd = new WorkingDirectory();
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();

		fs.addItem(fs.getRootDirectory(), new Directory("home", fs.getRootDirectory()));

		fs.addItem(fs.getDirectoryFromPath("/home"), new Directory("test", fs.getDirectoryFromPath("/home")));

		wd.setWorkingDirectory("/home");
		assertEquals("/home", wd.getWorkingDirectory());

		wd.setWorkingDirectory("/home/test");
		assertEquals("/home/test", wd.getWorkingDirectory());

		wd.setWorkingDirectory("/");
		assertEquals("/", wd.getWorkingDirectory());
	}

	@Test
	public void testAppendPath() {
		WorkingDirectory wd = new WorkingDirectory();
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();

		fs.addItem(fs.getRootDirectory(), new Directory("home", fs.getRootDirectory()));

		fs.addItem(fs.getDirectoryFromPath("/home"), new Directory("test", fs.getDirectoryFromPath("/home")));

		assertEquals("/tester", wd.appendPath("tester"));

		wd.setWorkingDirectory("/home");
		assertEquals("/home/tester", wd.appendPath("tester"));

		wd.setWorkingDirectory("/home/test");
		assertEquals("/home/test/test2", wd.appendPath("test2"));

		wd.setWorkingDirectory("/");
		assertEquals("/test3", wd.appendPath("test3"));
	}

}
