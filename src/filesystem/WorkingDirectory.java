package filesystem;

public class WorkingDirectory {

	/**
	 * The path of the working directory
	 */
	private String workingDirectory;

	/**
	 * Creates the working directory, at the root of the filesystem
	 */
	public WorkingDirectory() {
		setWorkingDirectory("/");
	}

	/**
	 * Returns the working directory as a string
	 * 
	 * @return the working directory
	 */
	public String getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * Changes the working directory to a given string
	 * 
	 * @param workingDirectory
	 *            new working directory
	 */
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
		cleanWorkingDirectory(); // clean the path to remove "..", ".", etc.
	}

	/**
	 * Cleans the working directory ex. /home/yo/.././hey into /home/hey
	 */
	private void cleanWorkingDirectory() {
		FileSystem fs = FileSystem.getFileSystem();
		workingDirectory = fs.directoryToPath((Directory) fs.getItem(workingDirectory));
	}

	/**
	 * Given a relative path, returns an absolute path using the working
	 * directory
	 * 
	 * @param append
	 *            path to append to the working directory
	 * @return new path with the working directory added at the beginning
	 */
	public String appendPath(String append) {
		// if last char is a slash, we don't need to add one, otherwise, add one
		if (workingDirectory.charAt(workingDirectory.length() - 1) == '/') {
			return workingDirectory.concat(append);
		} else {
			return workingDirectory.concat("/").concat(append);
		}
	}

	/***
	 * Input a path and return the absolute path. If the path is already
	 * absolute, then it will simply return the same path as was entered.
	 * 
	 * @param path
	 * @return An absolute path
	 */
	public String makeAbsolutePath(String path) {
		if (path.length() > 0) { // if path contains anything
			if (path.charAt(0) == '/') { // if the path already is absolute
				return path; // return itself
			} else {
				return appendPath(path); // append to the working directory
			}
		} else {
			return "/"; // return root of filesystem
		}
	}
}
