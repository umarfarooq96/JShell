package commands;

import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * This command will terminate the JShell
 * 
 * @author Mohammad Umar Farooq
 *
 */
public class exit implements Command {

	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length == 0) {
			return true; // If no argument, this is fine.
		}
		System.out.println("Exit does not accept any arguments.");
		return false;
	}

	public void execute(String[] s, WorkingDirectory wd, DataWriter dw) {
		System.exit(0); // Exiting from JShell
	}

	@Override
	public String getDocumentation() {
		return "Exit the shell";
	}
}
