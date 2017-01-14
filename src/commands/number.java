package commands;

import filesystem.WorkingDirectory;
import output.DataWriter;
import storage.HistoryStorage;
import validation.CommandInterpreter;
import validation.Validator;

/**
 * Class for the echo command
 * 
 * @author Mohammad Umar Farooq
 * @since November 21, 2015
 */
public class number implements Command {
	/**
	 * Reference to the history storage.
	 */
	private HistoryStorage hs = HistoryStorage.createHistoryStorageInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see commands.Command#execute(java.lang.String[],
	 * filesystem.WorkingDirectory, output.DataWriter)
	 */
	@Override
	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {
		// Precondition: args is an integer >=1 and within bounds of history
		// First get the input, then run that shit.
		String inputToExecute = hs.getNthCommand(Integer.parseInt(args[0]));
		hs.setLastEntry(inputToExecute);
		String commandName = CommandInterpreter.getCommandName(inputToExecute);
		String[] arguments = CommandInterpreter.getArguments(inputToExecute);
		if (Validator.isValidName(commandName)) { // Is it a valid command name?
			// Is the correct stuff being passed in?
			if (Validator.isValidCommand(commandName, arguments, wd)) {
				// It is, so run the commmand.
				CommandInterpreter.executeCommand(commandName, arguments, wd, dw);
			}
		} else {// if the input is not a valid command this line is executed
			System.out.println(commandName + " is not recognized as a valid " + "command.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commands.Command#validate(java.lang.String[],
	 * filesystem.WorkingDirectory)
	 */
	public boolean validate(String[] args, WorkingDirectory wd) {
		return validate(args);
	}

	/**
	 * Over loaded method of validate. We don't need the working directory so no
	 * point in passing it in.
	 * 
	 * @param args
	 *            Arguments to be validated.
	 * @return
	 */
	public boolean validate(String[] args) {
		// We must remove our command entry because it's not validated yet.
		hs.removeLastEntry();
		if (!validInteger(args)) {
			System.out.println("This command must take in an integer after \"!\"");
			return false;
		}
		if (args.length > 1) {
			System.out.println("This command requires no arguments. Only !number");
			return false;
		}
		if (Integer.parseInt(args[0]) <= 0) {
			System.out.println("Please choose a number greater than 0");
			return false;
		}
		if (!isWithinHistoryBound(Integer.parseInt(args[0]))) {
			System.out.println(
					"Please choose a number within the history range." + " Consider using history command to check.");
			return false;
		}
		// Adding the command back as it is now valid.
		hs.addCommand(args[0]);
		return true;
	}

	/**
	 * Returns true iff the array has one integer.
	 * 
	 * @param args
	 *            The array to be validated.
	 * @return
	 */
	public boolean validInteger(String[] args) {
		try {
			// Try converting it to an integer.
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true iff the integer is less than or equal to number of commands.
	 * 
	 * @param n
	 *            Integer to be checked.
	 * @return
	 */
	public boolean isWithinHistoryBound(int n) {
		int numberOfCommands = hs.getHistory().size();
		return !(n > numberOfCommands);
	}

	@Override
	public String getDocumentation() {
		// TODO Auto-generated method stub
		return "This command will recall a command from history and run it.";
	}

}
