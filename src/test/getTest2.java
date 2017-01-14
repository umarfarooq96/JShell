package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.WorkingDirectory;
import commands.get;

public class getTest2 {
	private final ByteArrayOutputStream outPrint = new ByteArrayOutputStream();

	public void after() {
		System.setOut(null);
	}

	@Test
	public void validUrltest1() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		WorkingDirectory wd = new WorkingDirectory();
		get command = new get();
		String[] strings = { "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt" };
		command.execute(strings, wd, null);
		assertEquals("", outPrint.toString());
		System.setOut(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidUrltest() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		WorkingDirectory wd = new WorkingDirectory();
		get command = new get();
		String[] strings = { "bad form url" };
		command.execute(strings, wd, null);
		assertEquals("Invalid URL was given!", outPrint.toString());
		System.setOut(null);
	}

	@Test
	public void noUrlTest() {
		FileSystem.resetFileSystem();
		FileSystem fs = FileSystem.getFileSystem();
		WorkingDirectory wd = new WorkingDirectory();
		get command = new get();
		String[] strings = {};
		command.execute(strings, wd, null);
		assertEquals("Invalid file was found!", outPrint.toString());
		System.setOut(null);
	}
}
