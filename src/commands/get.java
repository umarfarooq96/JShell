package commands;

/**
 * @author Shahrzad Rahbarnia 
 */
import java.net.*;
import java.io.*;

import output.ConsoleWriter;
import output.DataWriter;
import filesystem.*;
import filesystem.File;
import filesystem.FileSystem;

public class get implements Command {
	FileSystem fs = FileSystem.getFileSystem();

	@Override
	/**
	 * takes a url and adds the file located there to the working directory
	 * 
	 * @param args
	 *            list of arguments
	 * @param workingDirectory
	 *            current working directory
	 * @param dataWriter
	 *            class to output to screen
	 */
	public void execute(String[] args, WorkingDirectory wd, DataWriter d) {

		try {
			URL link = new URL(args[0]); // establishes connection to url
			URLConnection connection = link.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String input;
			String[] fileName = args[0].split("/");

			String contents = "";
			while ((input = reader.readLine()) != null) {
				contents += input;
			}
			File file = new File(fileName[fileName.length - 1], contents,
					(Directory) fs.getItem(wd.makeAbsolutePath(wd.toString())));
			fs.addItem((Directory) fs.getItem(wd.makeAbsolutePath(wd.toString())), file); // creates
																							// file
																							// to
																							// add
			file.writeToFile(contents); // writes contents of url to file
			reader.close();
		} catch (MalformedURLException e) { // exceptions for invalid url
			System.out.println("Invalid URL was given!");
		} catch (IOException e) {
			System.out.println("Invalid file was found!");
			;
		}

	}

	@Override
	/**
	 * returns the help documentation for get
	 */
	public String getDocumentation() {
		return "Retrieve the file at that URL and add it to the current working" + "directory";
	}

	@Override
	/**
	 * checks to see if the arguments are of a valid length ie. only 1
	 */
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) {
			System.out.println("No URL was given!");
			return false;
		} else {
			return true;
		}
	}
}
