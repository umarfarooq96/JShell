package commands;

import filesystem.FileSystem;
import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for the cd command (change directory)
 * 
 * @author Matthew Santos
 * 
 */
public class cd implements Command {

	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {

		String newDirectory;

		if (args[0].charAt(0) == '/') { // absolute path
			newDirectory = args[0];
		} else { // relative path
			newDirectory = wd.appendPath(args[0]);
		}

		wd.setWorkingDirectory(newDirectory); // change to new dir
	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		FileSystem fs = FileSystem.getFileSystem();

		if (args.length != 1) { // only accept one argument for this command

			System.out.println("cd only accepts one argument.");
			return false;
		} else {
			if (args[0].charAt(0) == '/') { // absolute path
				if (!fs.isValidDirectory(args[0])) {
					System.out.println("You did not enter a valid directory.");
					return false;
				} else {
					return true;
				}
			} else { // relative path
				if (!fs.isValidDirectory(wd.appendPath(args[0]))) {
					System.out.println("You did not enter a valid directory.");
					return false;
				} else {
					return true;
				}
			}
		}
	}

	@Override
	public String getDocumentation() {
		return "Change the current working directory to the one given";
	}
}
