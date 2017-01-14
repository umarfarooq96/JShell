package commands;

import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * This interface is to for any command of JShell. Every command has specific
 * validation requirements so it has a validate method. Every command must be
 * executable so it has an execute method.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public interface Command {
	/**
	 * @param args
	 *            Arguments organized in Array of Strings
	 * @param wd
	 *            The current working directory (unique to JShell)
	 */
	public abstract void execute(String[] args, WorkingDirectory wd, DataWriter d);

	/**
	 * @param args
	 *            Arguments organized in Array of Strings
	 * @param wd
	 *            The current working directory (unique to JShell)
	 * @return boolean The validity of this command call.
	 */
	public abstract boolean validate(String[] args, WorkingDirectory wd);

	/**
	 * returns the text block to be displayed when man is called on a particular
	 * command
	 * 
	 * @return the associated documentation of the command
	 */
	public abstract String getDocumentation();

}
