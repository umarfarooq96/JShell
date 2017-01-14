package commands;

import java.util.ArrayList;

import exceptions.InvalidArguments;
import filesystem.WorkingDirectory;
import output.ConsoleWriter;
import output.DataWriter;
import storage.HistoryStorage;

/**
 * history Class
 * 
 * Class that implements the history command. History returns the commands
 * executed on JShell. If an int is passed, it only returns that amount. Print
 * is in a table format.
 * 
 * @author Mohammad Umar Farooq
 * @since October 31, 2015
 *
 **/
public class history implements Command {
	private HistoryStorage referenceToHistoryStorage;

	/**
	 * History constructor Creates an instance(Singleton) of HistoryStorage to
	 * use
	 */
	public history() {
		// Making a reference to the HistoryStorage. Singleton so this is okay.
		referenceToHistoryStorage = HistoryStorage.createHistoryStorageInstance();
	}

	/**
	 * Checks if a string is an integer. Simply a helper method used locally.
	 * 
	 * @param string
	 *            Pass in a string to check
	 * @return boolean True, it is an int or False, not an int.
	 */
	public static boolean isStringAnInt(String s) {
		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false; // If it's not an integer, it caught an error.
		}
		return true;
	}

	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) {
			return true; // If no argument, this is fine.
		}
		if (args.length == 1) {
			if (!(isStringAnInt(args[0]))) {
				System.out.println("Please make sure your argument is an int.");
				return false; // If one argument but not an integer
			}
			return true;
		}
		System.out.println("History only accepts one argument.");
		return false; // If more than one then, invalid arguments.
	}

	/**
	 * Execute the history command. Also checks if the integer is within bounds.
	 * Will format print the result.
	 */
	public void execute(String[] s, WorkingDirectory workingDirectory, DataWriter dw) {
		if (s.length == 0) {
			// Using the ConsoleWriter and printing in a table format.
			dw.writeArrayListTable(getHistory(), 1);
		} else {
			int numberOfCommands = Integer.parseInt(s[0]);
			// If it's negative or more than the amount of commands we have,
			// error
			if (numberOfCommands < 0 || numberOfCommands > referenceToHistoryStorage.getHistory().size()) {
				System.out.println("Please choose a valid number. Number of commands" + " right now is "
						+ String.valueOf(referenceToHistoryStorage.getHistory().size()));
			} else {
				// -1 in there because index starts at 0, we want to start at 1.
				int start_index = referenceToHistoryStorage.getHistory().size() - (numberOfCommands) + 1;
				dw.writeArrayListTable(getHistory(numberOfCommands), start_index);
			}
		}
	}

	/**
	 * The purpose of this method is to get a specific number of commands from
	 * the history storage and then return them in type ArrayList.
	 * 
	 * @param numberOfCommands
	 *            number of recent commands required
	 * @return recentHistory An ArrayList consisting of recent commands
	 */
	public ArrayList<String> getHistory(int numberOfCommands) {
		// If there is a number, we will make a new ArrayList with those
		// commands
		ArrayList<String> recentHistory = new ArrayList<String>();
		int start_index = referenceToHistoryStorage.getHistory().size() - numberOfCommands;
		for (int i = start_index; i < referenceToHistoryStorage.getHistory().size(); i++) {
			recentHistory.add(referenceToHistoryStorage.getHistory().get(i));
		}
		return recentHistory;
	}

	/**
	 * The purpose of this method is to return the entire history in an
	 * ArrayList
	 * 
	 * @return recentHistory An ArrayList consisting of all commands executed.
	 */
	public ArrayList<String> getHistory() {
		// If there is no number, return the entire history.
		return referenceToHistoryStorage.getHistory();
	}

	@Override
	public String getDocumentation() {
		// TODO Auto-generated method stub
		return "This command will print out recent commands. If given an integer,"
				+ " it will only print that number of recent commands.";
	}
}
