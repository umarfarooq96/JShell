package commands;

import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.Item;
import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for the mkdir command (makes a directory)
 * 
 * @author Matthew Santos
 *
 */
public class mkdir implements Command {

	FileSystem fs = FileSystem.getFileSystem();

	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {

		for (String arg : args) {
			String path = wd.makeAbsolutePath(arg);
			Item directory;
			String[] addition = path.split("/");
			String dirName = addition[addition.length - 1];

			// create the new directory, and put it inside the parent directory
			directory = new Directory(dirName, (Directory) fs.getItem(path));
			((Directory) fs.getItem(path)).addItem(directory);
		}
	}

	/***
	 * Input a path to check if a directory can be created inside.
	 * 
	 * @param path
	 *            Path to check
	 * @return true if the directory entered can be created
	 */
	private boolean checkDirectory(String path) {
		// makes an absolute path, and splits it by "/"
		String[] filePath = path.split("/");
		String pathToFile = ""; // stores path to file (but not the file)

		// assembles each portion of the filePath (separated by "/")
		for (int i = 0; i < filePath.length - 1; i++) {
			pathToFile += (filePath[i] + "/");

			if (!fs.isValidDirectory(pathToFile))
				return false; // check that portion of the path for validity
		}

		return true;
	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) {
			System.out.println("You did not enter any directories to make.");
			return false;
		} else {
			for (String argument : args) {
				String path = wd.makeAbsolutePath(argument);
				String[] addition = path.split("/"); // extract each path
				String dirName = addition[addition.length - 1]; // extract name

				if (!isValidDirectoryName(dirName)) { // no special chars
														// allowed
					System.out.println(
							"Directory name cannot contain characters" + " such as \"!@$&*()?:[]\"<>'`|={}\\/,;\"");
					return false;
				}
				// check for existing directory or file with same name
				if (fs.isValidDirectory(path) || fs.isValidFile(path)) {
					System.out.println("An item already exists with the specified name");
					return false;
				}
				if (!checkDirectory(path)) {
					System.out.println("Invalid path or directory.");
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * isValidDirectoryName method check if any given name is valid for a dir
	 * 
	 * @param directoryName
	 *            - given directory name
	 * @return boolean if the name is valid or not
	 */
	private boolean isValidDirectoryName(String directoryName) {
		String exclude = "!@$&*()?:[]\"<>'`|={}\\/,;"; // illegal characters for
														// file name

		// loop through directoryName and exclude to see if any characters in it
		// are to be excluded
		for (int i = 0; i < directoryName.length(); i++) {
			for (int j = 0; j < exclude.length(); j++) {
				if (directoryName.charAt(i) == exclude.charAt(j)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public String getDocumentation() {
		return "Create a directory";
	}

}
