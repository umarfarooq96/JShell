package commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.Item;
import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for the grep command (print any lines containing regex)
 * 
 * @author Matthew Santos
 * 
 */
public class grep implements Command {

	FileSystem fs = FileSystem.getFileSystem();

	@Override
	public void execute(String[] args, WorkingDirectory wd, DataWriter d) {
		args = cleanArgs(String.join(" ", args)); // process args

		String path;
		String regex;

		if (args.length == 3) { // 3 args, extract [2] and [1] as path,regex
			path = wd.makeAbsolutePath(args[2]);
			regex = args[1];
		} else { // 2 args, extract [1] and [0] as path,regex
			path = wd.makeAbsolutePath(args[1]);
			regex = args[0];
		}

		// this will strip out double quotes on both ends of the regex
		if (regex.length() >= 2 && regex.charAt(0) == '\"' && regex.charAt(regex.length() - 1) == '\"') {
			regex = regex.substring(1, regex.length() - 1);
		}

		if (fs.isValidFile(path)) { // if it's a file, run it on "normal" grep
			grepNormal(path, regex, d, false);
		} else { // if it's a folder, run it on the recursive grep
			grepRecursive(path, regex, d);
		}

	}

	/***
	 * Performs grep normally on a file, (no recursion)
	 * 
	 * @param path
	 *            The path to the file to perform the regex match on
	 * @param regex
	 *            the regex string to match the file's lines
	 * @param d
	 *            The DataWriter object to output to
	 * @param extra
	 *            true to print path and line, false for just line
	 */
	public void grepNormal(String path, String regex, DataWriter d, boolean extra) {
		File file = (File) fs.getItem(path); // get the file as item, then cast
		String[] lines = file.getContents().split("\\n"); // get each line
		Pattern regexPattern = Pattern.compile(regex); // compile the regex
		Matcher regexMatcher; // holds the Matcher for the regex pattern

		for (String line : lines) { // go through each file
			regexMatcher = regexPattern.matcher(line); // match for the current
														// line

			if (regexMatcher.find()) { // if the regex matches that line:
				if (extra) { // print extra info, (path and comma)
					d.writeString(path + "," + line);
				} else { // just print the line's contents
					d.writeString(line);
				}
			}
		}
	}

	/***
	 * Performs grep recursively on a directory, (recursion)
	 * 
	 * @param path
	 *            The path to the folder to perform the regex match on
	 * @param regex
	 *            the regex string to match the file's lines
	 * @param d
	 *            The DataWriter object to output to
	 */
	public void grepRecursive(String path, String regex, DataWriter d) {
		Directory dir = (Directory) fs.getItem(path);
		Collection<Item> items = dir.getItems();

		for (Item item : items) { // go through each item in the directory
			if (item instanceof Directory) { // recursively search in
												// directories
				grepRecursive(addToPath(path, item.getName()), regex, d);
			} else if (item instanceof File) { // for files, we search using
												// regex
				grepNormal(addToPath(path, item.getName()), regex, d, true);
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

	/***
	 * Extracts args, and respects items within double quotes
	 * 
	 * @param args
	 *            Arguments as a string
	 * @return splits on space, respects elements within double quotes
	 */
	private String[] cleanArgs(String args) {
		List<String> cleanedArgs = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(args);

		while (m.find()) // go through each item matching the above regex
		{
			String toAdd = m.group(1);
			cleanedArgs.add(toAdd); // add to list
		}

		// convert the list to an array
		String[] array = new String[cleanedArgs.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = cleanedArgs.get(i);
			// System.out.println(array[i]);
		}

		return array;
	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		args = cleanArgs(String.join(" ", args));

		if (args.length == 3) {
			// 3 args, check for -r at beginning and valid directory
			String path = wd.makeAbsolutePath(args[2]);
			if (args[0].equalsIgnoreCase("-r") && fs.isValidDirectory(path)) {
				return true;
			} else {
				System.out.println(
						"For 3 arguments, grep expects -r as the first, and the last must " + "be a valid directory.");
				return false;
			}
		} else if (args.length == 2) {
			// 2 args, check for valid file
			String path = wd.makeAbsolutePath(args[1]);
			if (fs.isValidFile(path)) {
				return true;
			} else {
				System.out.println("grep expects a valid file as the last argument.");
				return false;
			}
		} else {
			System.out.println("grep expects either 2 or 3 arguments. If 3, -r must be the first.");
			return false;
		}
	}

	@Override
	public String getDocumentation() {
		return "Searches a given PATH and looks for lines matching a regex.";
	}
}
