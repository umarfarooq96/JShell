package validation;

import java.util.Arrays;

import commands.Command;
import output.*;
import filesystem.WorkingDirectory;

public class CommandInterpreter {

	/**
	 * Executes a command given the name, arguments, and working directory.
	 * 
	 * @param commandName
	 *            Name of the command to execute
	 * @param args
	 *            The arguments that the command should use
	 * @param workingDirectory
	 *            The working directory to use
	 */
	public static String getCommandName(String input) {
		String[] splittedInput = input.split("\\s+");// splits the input
		if (splittedInput[0].startsWith("!")) {
			return "number";
		}
		return splittedInput[0];
	}

	public static String[] getArguments(String input) {
		String[] splittedInput = input.split("\\s+");// splits the input
		if (splittedInput[0].startsWith("!")) {
			splittedInput[0] = splittedInput[0].substring(1);
			return splittedInput;
		}
		return Arrays.copyOfRange(splittedInput, 1, splittedInput.length);
	}

	public static void executeCommand(String commandName, String[] args, WorkingDirectory workingDirectory,
			DataWriter dw) {
		if (Validator.redirectionRequired(args)) {
			args = Arrays.copyOf(args, args.length - 2);
		}

		@SuppressWarnings("rawtypes")
		Class commandClass;

		try {
			commandClass = Class.forName("commands." + commandName); // get the
																		// class
			Command command = (Command) commandClass.newInstance(); // create
																	// instance
			command.execute(args, workingDirectory, dw); // execute the command

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
