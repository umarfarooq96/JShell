package output;

import filesystem.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * FileWriter
 * 
 * class that allows the user to pipe commands into a file using ">" or ">>".
 * 
 * @author Mohammad Umar Farooq
 * @since November 18, 2015
 *
 */
public class FileWriter implements DataWriter {
	private FileSystem fs = FileSystem.getFileSystem(); // singleton FileSystem
	private String[] args; // stores the supplied arguments
	private boolean overwrite; // stores if its ">" or ">>", (true or false)
	private WorkingDirectory cwd; // stores current working directory

	/**
	 * FileWriter
	 * 
	 * constructor that sets up stuff
	 * 
	 * @param args
	 *            - given arguments
	 * @param wd
	 *            - current working directory
	 */
	public FileWriter(String[] args, WorkingDirectory wd) {

		// Will contain [>>, filename] or [>, filename]
		this.args = Arrays.copyOfRange(args, args.length - 2, args.length);
		this.overwrite = (this.args[0].equals(">")); // check what type of write
		this.cwd = wd; // set current working directory
	}

	/**
	 * isValidPathToFile
	 * 
	 * method that checks if the given file is a valid file name and if the path
	 * to the file actually exists.
	 * 
	 * @return - true if the above are true
	 */
	private boolean isValidPathToFile() {
		// makes an absolute path, and splits it by "/"
		String[] filePath = cwd.makeAbsolutePath(args[1]).split("/");
		String pathToFile = ""; // stores path to file (but not the file)

		// assembles each portion of the filePath (separated by "/")
		for (int i = 0; i < filePath.length - 1; i++) {
			pathToFile += (filePath[i] + "/");
		}

		// checks and returns if the given filePath has a valid file name and if
		// the
		// path to the file is valid or non-existant.
		if (isValidFileName(filePath[filePath.length - 1])
				&& (pathToFile.length() == 0 || fs.isValidItem(pathToFile))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * writeString
	 * 
	 * method that writes a given string to the class file above ^
	 * 
	 * @param s
	 *            - given string to write to file
	 */
	@Override
	public void writeString(String s) {

		// checks if the path to the current supplied file (at the time of
		// creation
		// of this FileWriter object) is valid
		if (isValidPathToFile()) {
			String path = cwd.makeAbsolutePath(args[1]);

			// checks if the file exists
			if (fs.isValidFile(path)) { // if it exists, append to it
				File file = (File) fs.getItem(path); // get file
				// if the command is ">" to overwrite
				if (overwrite) { // overwrite
					file.writeToFile(s);
					this.overwrite = false;
				} else { // append
					file.appendToFile(s);
				}
			} else { // if it doesn't exist, create a new file
				// create a new file with the valid filename and a the correct
				// code
				File newFile = new File(args[1], s, fs.getDirectoryFromPath(path));
				this.overwrite = false;
				// add the file the directory
				fs.addItem(fs.getDirectoryFromPath(path), newFile);
			}
		}

		// if the path is not valid, display an error
		else {
			System.out.println("ERROR: Invalid path to file provided.");
		}
	}

	@Override
	public void writeArrayListTable(ArrayList<String> a, int start_index) {
		// TODO Auto-generated method stub
		boolean temp = this.overwrite;
		int first_line = 0;
		if (this.overwrite) {
			first_line++;
			// To overwrite, we remove everything and just put the first line
			writeString(Integer.toString(first_line) + " " + a.get(0));
		}
		this.overwrite = false;
		for (int i = first_line; i < a.size(); i++) {
			// Then the remaining lines.
			writeString((start_index + i) + " " + a.get(i));
		}
		;
		this.overwrite = temp;
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
}
