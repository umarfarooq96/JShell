package storage;

import java.util.ArrayList;

/**
 * Stores history of commands used on JShell in an array.
 * 
 * @author Mohammad Umar Farooq
 * @since 05/11/2015
 *
 */
public class HistoryStorage {
	private static HistoryStorage singleReference = null;
	private ArrayList<String> historyOfCommands = new ArrayList<String>();

	/**
	 * Protected singleton constructor
	 */
	protected HistoryStorage() {
	}

	/**
	 * @return reference Singleton HistoryStorage constructor
	 */
	public static HistoryStorage createHistoryStorageInstance() {
		// Using singleton design because we never get rid of history
		if (singleReference == null) {
			singleReference = new HistoryStorage();
		}
		return singleReference;
	}

	/**
	 * @param command
	 *            String of command's name and arguments.
	 */
	public void addCommand(String command) {
		historyOfCommands.add(command);
	}

	/**
	 * @return historyOfCommands an arraylist of the commands.
	 */
	public ArrayList<String> getHistory() {
		return historyOfCommands;
	}

	/**
	 * Resets the history storage, removing all items and starting fresh
	 */
	public static void resetHistoryStorage() {
		if (singleReference != null) {
			singleReference = new HistoryStorage();
		}
	}

	/**
	 * Helper method to get the nth command from the history of commands.
	 * 
	 * @param n
	 *            Which command you'd like to get
	 * @return A string referring to the command
	 */
	public String getNthCommand(int n) {
		return historyOfCommands.get(n - 1);
	}

	/**
	 * Set the last entry to the string provided. (Overwrites last entry)
	 * 
	 * @param entry
	 *            The entry to be stored as the last entry
	 */
	public void setLastEntry(String entry) {
		historyOfCommands.set(historyOfCommands.size() - 1, entry);
	}

	/**
	 * Used to remove the last entry from the history of commands.
	 */
	public void removeLastEntry() {
		historyOfCommands.remove(historyOfCommands.size() - 1);
	}

}
