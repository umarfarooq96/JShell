package commands;

import filesystem.WorkingDirectory;
import output.ConsoleWriter;
import output.DataWriter;

import java.util.HashMap;

/**
 * 
 * @author Shahrzad Rahbarnia
 * @since November 5,2015
 *
 */
public class man implements Command {
	/**
	 * the class for manual
	 */

	/**
	 * @param args
	 *            - String array of arguments
	 */
	public void execute(String[] args, WorkingDirectory workingDirectory, DataWriter dw) {
		@SuppressWarnings("rawtypes")
		Class commandClass = null;
		try {
			commandClass = Class.forName("commands." + args[0]);
			Command command = (Command) commandClass.newInstance();// create
																	// instance
			dw.writeString(command.getDocumentation()); // get documentations
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			dw.writeString("Invalid command was given!");
		} // get the class

	}

	// this checks to see if the input is valid
	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length != 1) {
			System.out.println("Incorrect argument count!");
			return false;

		}

		return true;

	}

	@Override
	public String getDocumentation() {
		return "Print documentation for given command";
	}
}
