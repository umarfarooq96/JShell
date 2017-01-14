package commands;

import java.util.Arrays;

import filesystem.*;
import output.ConsoleWriter;
import output.DataWriter;

/**
 * Class for the echo command
 * 
 * @author Mohammed Junaid Khan
 * @since November 2, 2015
 */
public class echo implements Command {

	/**
	 * Variable to store the running FileSystem.
	 */
	private FileSystem fs = FileSystem.getFileSystem();

	/**
	 * Variable to store the current file path.
	 */
	private String path;

	/**
	 * execute method for the echo command. Executes logic for echo.
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public void execute(String[] args, WorkingDirectory workingDirectory, DataWriter dw) {
		// sends a string of the arguments complied and cleaned to
		// ConsoleWritter
		// for writing to the console
		dw.writeString(buildAndCleanString(Arrays.copyOfRange(args, 0, args.length)));
	}

	/**
	 * buildAndCleanString method takes the given arguments, compiles them into
	 * a String and removes any quotations.
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @return the given arguments in a string without quotation marks
	 */
	private String buildAndCleanString(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		String returnString;

		// go through each item in the array and add it to a stringBuilder
		// string
		for (int i = 0; i < args.length; i++) {
			stringBuilder.append(args[i]);
			if (i < (args.length - 1)) {
				stringBuilder.append(" ");
			}
		}

		// assemble stringBuilder into a String
		returnString = stringBuilder.toString();

		// if the string starts with a " then purge it
		if (returnString.startsWith("\"")) {
			returnString = returnString.substring(1);
		}

		// if the string ends with a " then purge it
		if (returnString.endsWith("\"")) {
			returnString = returnString.substring(0, returnString.length() - 1);
		}

		return returnString; // return the new string
	}

	/**
	 * validate method for command and argument validation specific to echo
	 * class
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) { // if the args are empty, toss error.
			System.out.println("Invalid argument(s), echo requires atleast one argument.");
			return false; // return false to break the echo command
		} else if (args.length == 1 || (!args[args.length - 2].equals(">>") && !args[args.length - 2].equals(">"))) { // if
																														// there
																														// is
																														// only
																														// one
																														// argument
																														// or
																														// if
																														// there
																														// is
																														// no
																														// ">>"/".>"
																														// in
																														// the
																														// second
																														// last
																														// place,
																														// allow
																														// it
			return true; // return true to allow echo to continue
		} else if (args[args.length - 2].equals(">>") || args[args.length - 2].equals(">")) { // if
																								// there
																								// are
																								// ">>"/">"
																								// in
																								// the
																								// second
																								// last
																								// place,
																								// allow
																								// it
			if (isValidFileName(args[args.length - 1])) { // check for valid
															// file name
				return true; // return true to allow echo to continue
			}
		}
		return false; // if nothing triggers, return false.
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
		return ("This command will take in a string and a file name and either"
				+ " append or overwrite the string to the file depending on the" + " operator \">\" or \">>\".");
	}
}
