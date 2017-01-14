package commands;

import java.util.Collection;

import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.Item;
import filesystem.File;
import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for ls (view contents of a directory, or print file name)
 * 
 * @author Matthew Santos
 *
 */

public class ls implements Command {

	FileSystem fs = FileSystem.getFileSystem();

	@Override
	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {

		if (args.length == 0) { // ls without params
			// use working directory since there are no params
			String wdStr = wd.getWorkingDirectory();
			dw.writeString(fs.getDirectoryFromPath(wdStr).toString());
		} else { // ls with params

			boolean recursive = args[0].equalsIgnoreCase("-r");

			for (int index = 0; index < args.length; index++) {
				String arg = args[index];

				if (!(recursive && index == 0)) { // skip first index if
													// recursive
					String path = wd.makeAbsolutePath(arg);

					if (!recursive) {
						// call non-recursive ls, print extra info if more than
						// one path
						lsNormal(path, dw, args.length > 1);
					} else {
						// call recursive ls, print extra info if more than one
						// path
						lsRecursive(path, dw, args.length > 2);
					}
				}
			}
		}
	}

	/***
	 * Performs ls non-recursively on a directory or file
	 * 
	 * @param path
	 *            The path to perform ls on
	 * @param dw
	 *            the DataWriter object to write output to
	 * @param extra
	 *            print extra info (file path)
	 */
	public void lsNormal(String path, DataWriter dw, boolean extra) {
		if (fs.isValidFile(path)) { // file, print name
			File file = (File) fs.getItem(path);

			if (extra) { // print path, otherwise just the name
				dw.writeString(path); // output path
			} else {
				dw.writeString(file.getName()); // output name
			}

		} else if (fs.isValidDirectory(path)) { // directory, print contents
			Directory dir = fs.getDirectoryFromPath(path);

			if (extra) { // print directory path too, since we requested extra
							// info
				dw.writeString(fs.directoryToPath(dir) + " contains:");
			}

			dw.writeString(dir.toString()); // output contents
		}
	}

	/***
	 * Performs ls recursively on a directory
	 * 
	 * @param path
	 *            The path to perform ls on
	 * @param dw
	 *            the DataWriter object to write output to
	 * @param extra
	 *            print extra info (file path)
	 */
	public void lsRecursive(String path, DataWriter dw, boolean extra) {
		if (fs.isValidDirectory(path)) { // only run if this is a valid
											// directory
			Directory dir = (Directory) fs.getItem(path);
			Collection<Item> items = dir.getItems(); // get items inside the
														// directory

			lsNormal(path, dw, true); // print the contents of this directory

			for (Item item : items) { // go through each item in the directory
				if (item instanceof Directory) { // recursively search in
													// directories
					lsRecursive(addToPath(path, item.getName()), dw, true);
				}
			}
		}
	}

	/***
	 * Accepts a path an an addition to it, checks for a /, if none exists at
	 * the end, add it and concat the addition
	 * 
	 * @param path
	 *            The original path
	 * @param addition
	 *            The addition to the path
	 * @return the path with the addition
	 */
	private String addToPath(String path, String addition) {
		if (path.charAt(path.length() - 1) == '/') {
			return path.concat(addition);
		} else {
			return path.concat("/" + addition);
		}
	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) {
			return true;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("-r")) // ls with just -r? not allowed.
				return false;

			String path = wd.makeAbsolutePath(args[0]);

			// make sure the path leads to a valid dir or file
			boolean invalid = !fs.isValidDirectory(path) && !fs.isValidFile(path);

			if (invalid) {
				System.out.println("The path you entered is invalid.");
			}

			return !invalid;
		} else {
			boolean recursive = args[0].equalsIgnoreCase("-r");
			boolean stillRun = false; // we still set to true if at least one
										// item is
										// valid
			int i = 0;

			if (recursive)
				i++; // increase i to skip the -r if it is recursive

			while (i < args.length) { // check if each argument is a valid item
				String arg = args[i];
				String path = wd.makeAbsolutePath(arg);

				if (!fs.isValidDirectory(path) && !fs.isValidFile(path)) {
					System.out.println(path + " is not a valid path, skipping it.");
				} else {
					stillRun = true;
				}

				i++; // go to next element
			}

			return stillRun;
		}
	}

	@Override
	public String getDocumentation() {
		return "Output names of files and directories given a path.";
	}
}
