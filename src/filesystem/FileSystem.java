package filesystem;

public class FileSystem {

	/**
	 * Stores the directories in the filesystem
	 */
	private Directory root;

	/**
	 * We make FileSystem follow the singleton design, this stores the instance
	 */
	private static FileSystem instance = null;

	/**
	 * Upon creation of the FileSystem, we create the root directory.
	 */
	protected FileSystem() {
		root = new Directory("", null); // creates root directory
	}

	/**
	 * Gets the file system, or makes a new one if the current one doesn't exist
	 */
	public static FileSystem getFileSystem() {
		if (instance == null) {
			instance = new FileSystem();
		}
		return instance;
	}

	/**
	 * Resets the file system, removing all items and starting fresh
	 */
	public static void resetFileSystem() {
		if (instance != null) {
			instance = new FileSystem();
		}
	}

	/**
	 * Returns the root directory of the file system
	 * 
	 * @return the root directory
	 */
	public Directory getRootDirectory() {
		return root;
	}

	/**
	 * Returns true if a path to an item is valid, false if invalid.
	 * 
	 * @param path
	 *            the string path to an item
	 * @return true if a path is valid, false if invalid
	 */
	public boolean isValidItem(String path) {
		return isValidFile(path) || isValidDirectory(path);
	}

	/**
	 * Given a directory object and an item, we add an item inside it
	 * 
	 * @param directory
	 *            Directory to add the item into
	 * @param add
	 *            The item to add into the directory
	 */
	public void addItem(Directory directory, Item add) {
		directory.addItem(add);
	}

	/**
	 * Get an item given an absolute path
	 * 
	 * @param path
	 *            The path that we want to get the item of
	 * @return the item
	 */
	public Item getItem(String path) {
		return pathToItem(path);
	}

	/**
	 * Gets a Directory object given a path
	 * 
	 * @param path
	 *            An absolute path to a directory
	 * @return Directory object at the given path
	 */
	public Directory getDirectoryFromPath(String path) {
		path = cleanPath(path);
		String[] paths = path.split("/");
		Directory currentDirectory = getRootDirectory(); // default to root

		for (int index = 0; index < paths.length; index++) {
			if (paths[index].equals("..")) { // .. for the parent directory
				if (currentDirectory.getParentDirectory() != null) {
					currentDirectory = currentDirectory.getParentDirectory(); // move
																				// to
																				// it
				}
			} else if (currentDirectory.itemExists(paths[index])) {
				if (currentDirectory.getItem(paths[index]) instanceof File) {
					break; // we hit a file, no more directories.
				} else {
					// move to the next directory
					currentDirectory = (Directory) currentDirectory.getItem(paths[index]);
				}

			} else if (!paths[index].equals(".")) {
				break;
			}
		}

		return currentDirectory;
	}

	/**
	 * Cleans a path string, used to remove unneccessary characters
	 * 
	 * @param path
	 *            A path that has not been processed by this method
	 * @return A cleaned path
	 */
	private String cleanPath(String path) {
		if (path.charAt(0) == '/') { // absolute path,
			if (path.length() > 1) {
				return path.substring(1); // remove the beginning slash
			} else { // just return empty string (root directory)
				return "";
			}
		} else { // it is absolute, we don't need to remove anything
			return path;
		}
	}

	/**
	 * Returns true if the path to a given file is valid, false if invalid
	 * 
	 * @param path
	 *            Path to a given file
	 * @return true if the file path is valid, false if not
	 */
	public boolean isValidFile(String path) {
		path = cleanPath(path);
		String[] paths = path.split("/"); // get the name of each item in path
		Directory currentDirectory = getRootDirectory(); // default to root

		for (int index = 0; index < paths.length; index++) {
			if (paths[index].equals("..")) { // .. for the parent directory
				if (currentDirectory.getParentDirectory() != null) { // move to
																		// parent
					currentDirectory = currentDirectory.getParentDirectory();
				} else {
					return false; // if the parent is null, then the path isn't
									// valid
				}
			} else if (index != paths.length - 1) { // not at last part of path
				if (currentDirectory.itemExists(paths[index]) && !paths[index].equals(".")) { // item
																								// exists
																								// and
																								// not
																								// a
																								// .
					// then we are gonna move into the next directory in the
					// path

					currentDirectory = (Directory) currentDirectory.getItem(paths[index]);
				} else {
					return false;
				}
			} else {
				// if the last part of the path doesn't exist or is not a file
				if (!currentDirectory.itemExists(paths[index])
						|| currentDirectory.getItem(paths[index]) instanceof Directory) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns true if the path to a given directory is valid, false if invalid
	 * 
	 * @param path
	 *            Path to a given directory
	 * @return true if the directory path is valid, false if not
	 */
	public boolean isValidDirectory(String path) {
		path = cleanPath(path);
		String[] paths = path.split("/");
		Directory currentDirectory = getRootDirectory(); // default to root

		if (paths.length == 1 && paths[0] == "") { // root directory
			return true;
		}

		for (int index = 0; index < paths.length; index++) {
			if (paths[index].equals("..")) { // .. for the parent directory
				if (currentDirectory.getParentDirectory() != null) { // move to
																		// parent
					currentDirectory = currentDirectory.getParentDirectory();
				} else { // path invalid, going to parent when it doesn't exist
					return false;
				}
			} else if (!paths[index].equals(".")) {
				if (currentDirectory.itemExists(paths[index]) // exists and
																// directory
						&& currentDirectory.getItem(paths[index]) instanceof Directory) {
					// move inside it
					currentDirectory = (Directory) currentDirectory.getItem(paths[index]);
				} else {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Given a directory, return the path to it
	 * 
	 * @param directory
	 *            The directory to get the path of
	 * @return The path to the directory given
	 */
	public String directoryToPath(Directory directory) {

		String path = "";

		while (directory.getParentDirectory() != null) {
			path = "/" + directory.getName() + path;
			directory = directory.getParentDirectory();
		}

		if (path.equals("")) {
			path = "/";
		}

		return path;
	}

	/**
	 * Given a path, returns the Item at that path
	 * 
	 * @param path
	 *            A path to an item
	 * @return the Item at the given path
	 */
	private Item pathToItem(String path) {
		path = cleanPath(path);
		String[] paths = path.split("/");
		Directory currentDirectory = getRootDirectory(); // default to root
		Item item = null;

		for (int index = 0; index < paths.length; index++) {
			if (paths[index].equals("..")) { // .. for the parent directory
				if (currentDirectory.getParentDirectory() != null) {
					currentDirectory = currentDirectory.getParentDirectory();
				}
			} else if (index != paths.length - 1) { // not at last part of path
				if (currentDirectory.itemExists(paths[index])) {
					currentDirectory = (Directory) currentDirectory.getItem(paths[index]);
				}
			} else if (!paths[index].equals(".")) { // ignore .
				if (currentDirectory.itemExists(paths[index])) {
					item = currentDirectory.getItem(paths[index]);
				}
			}
		}

		if (item == null) {
			item = currentDirectory;
		}

		return item;
	}
}
