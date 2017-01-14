package commands;

import filesystem.WorkingDirectory;
import output.ConsoleWriter;
import output.DataWriter;
import storage.DirectoryStack;

/**
 * Class that implements the popd command. Will pop from DirectoryStack and then
 * cd into it.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public class popd implements Command {
	private DirectoryStack referenceToDirectoryStack;

	/**
	 * 
	 */
	public popd() {
		referenceToDirectoryStack = DirectoryStack.createDirectoryStack();
	}

	public boolean validate(String[] args, WorkingDirectory wd) {
		if (args.length > 0) {
			System.out.println("This command requires no arguments.");
			return false; // Need no arguments at all
		}
		return true;
	}

	public void execute(String[] s, WorkingDirectory workingDirectory, DataWriter dw) {
		if (isEmpty()) {
			// If it's empty, we can't pop anything.
			dw.writeString("There is no directory saved.");
		} else {
			String[] args = { getPopD() }; // Using a helper method to pop
			cd cdCommand = new cd();
			cdCommand.execute(args, workingDirectory, dw);// Now cd-ing into the
															// directory
		}
	}

	/**
	 * @return String Getting the popped directory string from storage.
	 */
	public String getPopD() {
		return referenceToDirectoryStack.popDIR();
	}

	/**
	 * @return boolean True if and only if the directory stack is not empty.
	 */
	public boolean isEmpty() {
		return referenceToDirectoryStack.empty();
	}

	@Override
	public String getDocumentation() {
		// TODO Auto-generated method stub
		return "Removes the top entry from the directory stack and makes it the"
				+ "current working directory. Directory stack" + " can be added to using pushd command.";
	}
}
