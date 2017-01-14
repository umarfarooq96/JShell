package commands;

import filesystem.*;
import output.*;

/**
 * Class for the cp command
 * 
 * @author Mohammed Junaid Khan
 * @since November 12, 2015
 *
 */
public class cp implements Command {

	private FileSystem fs = FileSystem.getFileSystem(); // call the FileSystem
														// instance

	/**
	 * execute method for cp command. Executes the command.
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {

	}

	/**
	 * validate method for validating the arguments relative to cp
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public boolean validate(String[] args, WorkingDirectory wd) {
		if ((args.length == 3 && args[0].equalsIgnoreCase("-r")) || args.length == 2) { // if
																						// 2
																						// args
																						// or
																						// 3
																						// with
																						// 1st
																						// being
																						// "-r",
																						// allow
			// convert given paths to absolute paths
			String pathFrom = wd.makeAbsolutePath(args[args.length - 2]);
			String pathTo = wd.makeAbsolutePath(args[args.length - 1]);

			if (fs.isValidItem(pathFrom) && isValidFileName(pathTo)) {
				return true;
			}

			return false;

			/*
			 * if (args[0].charAt(0) == '/') { // absolute path if (args.length
			 * == 3 && !fs.isValidFile(args[1])) { // if file is not // valid //
			 * break the command and display the error System.out
			 * .println("Invalid argument, non-existant file name provided.");
			 * return false; } else if (args.length == 2 &&
			 * !fs.isValidFile(args[0])) { // break the command and display the
			 * error System.out
			 * .println("Invalid argument, non-existant file name provided.");
			 * return false; } else { // if the file is valid, continue return
			 * true; } } else { // relative path // if file is not valid if
			 * (args.length == 3 && !fs.isValidFile(wd.appendPath(args[1]))) {
			 * // break the command and display the error System.out
			 * .println("Invalid argument, non-existant file name provided.");
			 * return false; } else if (args.length == 2 &&
			 * !fs.isValidFile(wd.appendPath(args[0]))) { // break the command
			 * and display the error System.out
			 * .println("Invalid argument, non-existant file name provided.");
			 * return false; } else { // if the file is valid, continue return
			 * true; } }
			 */
		} else { // if not then
			System.out.println("Invalid argument(s), the cp command takes either 2 arguments, or 3 "
					+ "arguments with the first being '-r'.");
			return false; // continue the cp command.
		}
	}

	/**
	 * isValidFileName method check if any given file name is a valid file.
	 * 
	 * @param fileName
	 *            - given file name
	 * @return boolean if the file name is valid or not
	 */
	private boolean isValidFileName(String fileName) {
		String exclude = "!@$&*()?:[]\"<>'`|={}\\/,;"; // illegal characters for
														// file name

		fileName = fileName.split("/")[fileName.split("/").length - 1];

		// loop through fileName and exclude to see if any characters in
		// fileName
		// are to be excluded
		for (int i = 0; i < fileName.length(); i++) {
			for (int j = 0; j < exclude.length(); j++) {
				if (fileName.charAt(i) == exclude.charAt(j)) {
					System.out.println(
							"File name cannot have an illegal character" + " such as \"!@$&*()?:[]\"<>'`|={}\\/,;\""); // if
																														// there
																														// are
																														// invalid
																														// characters
																														// prompt
																														// the
																														// console
					return false; // break the echo code to stop it from running
				}
			}
		}
		return true; // if the code doesn't break, allow echo to continue
	}

	/**
	 * getDocumentation
	 * 
	 * method that returns the documentation string of the class.
	 */
	@Override
	public String getDocumentation() {
		return ("This command copies a file or directory and pastes it into another" + " directory.");
	}
}
