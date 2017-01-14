package filesystem;

import java.util.Collection;
import java.util.HashMap;

public class Directory extends Item {

	/**
	 * Stores the name of the directory
	 */
	private String directoryName;

	/**
	 * Contains the items in the directory
	 */
	private HashMap<String, Item> items;

	/**
	 * creates a directory given a name and parent directory
	 * 
	 * @param directoryName
	 *            The name of the directory
	 * @param parentDirectory
	 *            The parent directory
	 */
	public Directory(String directoryName, Directory parentDirectory) {
		this.directoryName = directoryName;
		setParentDirectory(parentDirectory);
		items = new HashMap<String, Item>();
	}

	/**
	 * Adds a given Item into the directory
	 * 
	 * @param item
	 *            Item to add into the directory
	 */
	public void addItem(Item item) {
		items.put(item.getName(), item);
	}

	public Collection<Item> getItems() {
		return items.values();
	}

	/***
	 * Removes an item from the directory
	 * 
	 * @param name
	 *            the name of the item to remove
	 */
	public void removeItem(String name) {
		items.remove(name);
	}

	/**
	 * Given the name of an item, return the object from inside the directory.
	 * 
	 * @param name
	 *            Name of the item.
	 * @return The item found.
	 */
	public Item getItem(String name) {
		return items.get(name);
	}

	/**
	 * Given the name of an item, return true if the item exists, otherwise
	 * false
	 * 
	 * @param name
	 *            Name of the item
	 * @return If the item exists or not
	 */
	public boolean itemExists(String name) {
		return items.containsKey(name);
	}

	/**
	 * Returns the contents of the directory.
	 * 
	 * @return Contents of the directory
	 */
	public String toString() {
		String output = "";

		for (Item item : items.values()) {
			output = output.concat(item.getName() + " ");
		}

		return output;
	}

	/**
	 * Returns the name of the directory
	 * 
	 * @return The name of the directory
	 */
	public String getName() {
		return this.directoryName;
	}

	/**
	 * Changes the name of a directory given the new name
	 * 
	 * @param directoryName
	 *            The new name of the directory
	 */
	public void rename(String directoryName) {
		this.directoryName = directoryName;
	}

}
