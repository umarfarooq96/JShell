package commands;

import filesystem.WorkingDirectory;
import output.DataWriter;

/**
 * class for pwd (prints the working directory)
 * 
 * @author Matthew Santos
 *
 */
public class pwd implements Command {

	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {
		// gets the working directory, and outputs it using ConsoleWriter
		dw.writeString(wd.getWorkingDirectory());
	}

	@Override
	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length > 0) { // pwd does not use any arguments.
			System.out.println("This command is not expecting any arguments.");
		}
		return args.length == 0;
	}

	@Override
	public String getDocumentation() {
		return "Print the current working directory of the shell";
	}

}
