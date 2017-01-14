package filesystem;

public class Item {

	/**
	 * The name of the item
	 */
	private String name;

	/**
	 * The parent directory of the item
	 */
	private Directory parentDirectory;

	/**
	 * Returns the parent directory of the item
	 * 
	 * @return parent directory of the item
	 */
	public Directory getParentDirectory() {
		return parentDirectory;
	}

	/**
	 * Changes the parent directory of an item to the given directory
	 * 
	 * @param parentDirectory
	 *            The new parent directory of the item
	 */
	public void setParentDirectory(Directory parentDirectory) {
		this.parentDirectory = parentDirectory;
	}

	/**
	 * Returns the name of the item
	 * 
	 * @return name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Changes the current name of an item to the given name
	 * 
	 * @param name
	 *            The new name of the item
	 */
	public void setName(String name) {
		this.name = name;
	}
}
