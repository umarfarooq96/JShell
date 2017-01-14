package validation;

import java.util.Arrays;
import output.*;
import commands.Command;
import exceptions.InvalidArguments;
import filesystem.WorkingDirectory;

public class Validator {

	/**
	 * Contains all the valid command names.
	 */
	private static String[] cmdNames = { "exit", "mkdir", "cd", "ls", "pwd", "pushd", "popd", "history", "cat", "echo",
			"man", "cp", "mv", "get", "grep", "number" };

	/**
	 * Check if a command name is valid
	 * 
	 * @param cmd
	 *            The name of the command
	 * @return true if the command exists, false if it does not exist.
	 */
	public static boolean isValidName(String cmd) {
		return Arrays.asList(cmdNames).contains(cmd);
	}

	/**
	 * Determines whether or not a command represented as an array is valid by
	 * checking if it exists, and if it has the proper amount of parameters
	 * 
	 * @param cmd
	 *            A command string
	 * @param args
	 *            An array of arguments
	 * @return true if the command exists and is valid, otherwise false
	 */
	public static boolean isValidCommand(String cmd, String[] args, WorkingDirectory wd) {
		// If the redirection is required, the final two arguments are not part
		// of the command anymore. They are part of redirection.
		if (Validator.redirectionRequired(args)) {
			args = Arrays.copyOf(args, args.length - 2);
		}

		@SuppressWarnings("rawtypes")
		Class commandClass;

		// Try making the class and then validate it.
		try {
			commandClass = Class.forName("commands." + cmd);
			Command command = (Command) commandClass.newInstance();
			return command.validate(args, wd);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			return false;
		}

	}

	/**
	 * Returns the type of DataWriter required based on arguments given.
	 * 
	 * @param args
	 *            Arguments for the command
	 * @param wd
	 *            The current working directory which is required for files.
	 * @return
	 */
	public static DataWriter outputType(String[] args, WorkingDirectory wd) {
		if (args.length > 1) {
			boolean containsGreaterThan = (args[args.length - 2].equals(">>") || args[args.length - 2].equals(">"));
			if (containsGreaterThan) {
				return new FileWriter(args, wd);
			}
		}
		return new ConsoleWriter();
	}

	/**
	 * Method to see if redirection is required. Redirection is required when >
	 * file or >> file are given.
	 * 
	 * @param args
	 *            The arguments that may contain the redirection.
	 * @return
	 */
	public static boolean redirectionRequired(String[] args) {
		if (args.length > 1) {
			boolean containsGreaterThan = (args[args.length - 2].equals(">>") || args[args.length - 2].equals(">"));
			if (containsGreaterThan) {
				return true;
			}
		}
		return false;
	}

}
