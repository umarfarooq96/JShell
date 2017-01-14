package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.Item;
import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for the mv command (move an item)
 * 
 * @author Matthew Santos
 * 
 */
public class mv implements Command {

	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {

		FileSystem fs = FileSystem.getFileSystem();
		String from = wd.makeAbsolutePath(args[0]); // absolute location of old
													// item
		String to = wd.makeAbsolutePath(args[1]); // absolute location of new
													// item
		Item toMove = fs.getItem(from); // get the object of the item to move
		Directory newDirectory = fs.getDirectoryFromPath(to); // the new
																// directory
		String itemName = toMove.getName(); // name of the item we're moving

		System.out.println(newDirectory.getName());

		if (fs.isValidItem(to)) { // new location already exists?
			toMove.getParentDirectory().removeItem(itemName); // remove from old
																// dir

			if (newDirectory.itemExists(itemName)) { // item already exists with
														// name?
				newDirectory.removeItem(itemName); // remove it so we can
													// overwrite
			}

			newDirectory.addItem(toMove); // add item to new location
			toMove.setParentDirectory(newDirectory); // update parent directory
		} else {
			toMove.getParentDirectory().removeItem(itemName); // remove from old
																// dir

			System.out.println("not valid to");
			// TODO: move item to a new location, and rename it
		}

	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		FileSystem fs = FileSystem.getFileSystem();

		if (args.length != 2) { // only accept two arguments for this command

			System.out.println("mv only accepts two arguments.");
			return false;
		} else {
			String absolute = wd.makeAbsolutePath(args[0]);

			if (!fs.isValidItem(absolute) || // is the item valid?
					fs.getItem(absolute) == fs.getRootDirectory()) {

				System.out.println("You did not enter a valid item to move.");
				return false;
			} else {
				// can we move it to the new location?
				return verifyMoveInto(absolute, wd.makeAbsolutePath(args[1]));
			}

		}
	}

	/***
	 * Checks if we can move an item to a new location
	 * 
	 * @param from
	 *            The previous path (must be absolute)
	 * @param to
	 *            The new path to check (must be absolute)
	 * @return true if item can be moved to the new path, false if not possible
	 */
	private boolean verifyMoveInto(String from, String to) {

		FileSystem fs = FileSystem.getFileSystem();
		Item toMove = fs.getItem(from);
		Item toLocation = null;
		boolean toExists = fs.isValidItem(to);
		Item toItem = null;

		if (toExists) {
			toItem = fs.getItem(to);
		}

		if (toMove instanceof File) {
			if (toExists && toItem instanceof Directory) { // moving file to dir
				return true;
			} else if (toExists && toItem instanceof File) { // moving file to
																// file
				return true;
			} else if (!toExists) { // we are potentially renaming
				return false;
			} else {
				System.out.println("The item you are trying to move is not valid.");
				return false;
			}
		} else if (toMove instanceof Directory) {
			if (toExists && toItem instanceof File) { // moving dir to file
				return false;
			} else if (toExists && toItem instanceof Directory) {// dir to dir
				return true;
			} else if (!toExists) { // we are potentially renaming
				return false;
			} else {
				System.out.println("The item you are trying to move is not valid.");
				return false;
			}
		} else {
			System.out.println("The item you are trying to move is not valid.");
			return false;
		}

	}

	@Override
	public String getDocumentation() {
		return "Moves a given item to another path.";
	}
}
