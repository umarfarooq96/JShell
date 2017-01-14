package driver;

import java.util.Arrays;
import exceptions.*;
import output.*;
import java.util.Scanner;

import filesystem.WorkingDirectory;
import storage.HistoryStorage;
import validation.CommandInterpreter;
import validation.Validator;

/**
 * JShell class
 * 
 * @author Mohammad Umar Farooq
 * @since October23, 2015
 *
 */
public class JShell {

	/**
	 * main class running JShell
	 * 
	 * @param args
	 *            - String array of arguments
	 * @throws InvalidName
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		WorkingDirectory workingDirectory = new WorkingDirectory();
		HistoryStorage referenceToHistoryStorage = HistoryStorage.createHistoryStorageInstance();
		while (true) {// this loop makes sure that "/#" is printed before every
						// new line
			System.out.print("/# ");
			String input = sc.nextLine().trim();
			if (!(input.equals(""))) {// takes care of the command they put and
										// do the
										// action related to that
				referenceToHistoryStorage.addCommand(input);
				String commandName = CommandInterpreter.getCommandName(input);
				String[] arguments = CommandInterpreter.getArguments(input);
				if (Validator.isValidName(commandName)) { // Is it a valid
															// command name?
					if (Validator.isValidCommand(commandName, arguments, workingDirectory)) { // Is
																								// the
																								// correct
																								// stuff
																								// being
																								// passed
																								// in?
						DataWriter dw = Validator.outputType(arguments, workingDirectory);
						CommandInterpreter.executeCommand(commandName, arguments, workingDirectory, dw);
					}
				} else {// if the input is not a valid command this line is
						// executed
					System.out.println(commandName + " is not recognized as a valid " + "command.");
				}
			}
		}
	}
}
