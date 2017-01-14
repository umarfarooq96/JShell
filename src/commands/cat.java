package commands;

import filesystem.*;
import output.ConsoleWriter;
import output.DataWriter;

/**
 * Class for the cat command
 * 
 * @author Mohammed Junaid Khan
 * @since October 28, 2015
 */
public class cat implements Command {

	/**
	 * Holds a singleton instance of FileSystem
	 */
	private FileSystem fs = FileSystem.getFileSystem(); // call the FileSystem
														// instance

	/**
	 * execute method for cat command. Executes the command.
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public void execute(String[] args, WorkingDirectory workingDirectory, DataWriter dw) {
		for (int i = 0; i < args.length; i++) {

			if (validateFiles(args[i], workingDirectory)) {
				String path = workingDirectory.makeAbsolutePath(args[i]);

				File file = (File) fs.getItem(path); // get the file from fs

				dw.writeString(file.getContents()); // send the file contents to
													// ConsoleWriter to print
													// the
													// contents
			} else { // if each file is invalid print an error
				System.out.println("cat: " + args[i].split("/")[args[i].split("/").length - 1] + ": No such file.");
			}
		}
	}

	/**
	 * validate method for validating the arguments relative to cat
	 * 
	 * @param args
	 *            - String array or arguments passed to the command
	 * @param workingDirectory
	 *            - WorkingDirectory object of the current WD.
	 */
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) { // if args are empty throw error
			System.out.println("cat: This command takes at least 1 argument.");
			return false; // break the cat command
		}
		return true; // otherwise continue
	}

	private boolean validateFiles(String arg, WorkingDirectory wd) {
		arg = wd.makeAbsolutePath(arg);

		if (!fs.isValidFile(arg)) { // if file is not valid
			// break the command and display the error
			return false;
		} else { // if the file is valid, continue
			return true;
		}
	}

	/**
	 * getDocumentation
	 * 
	 * method that returns the documentation string of the class.
	 */
	@Override
	public String getDocumentation() {
		return ("This command will take in a valid file as an argument and print it's" + " contents to the console");
	}
}
