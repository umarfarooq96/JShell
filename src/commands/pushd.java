// **********************************************************
// Assignment2:
// Student1: Mohammad Umar Farooq
// UTOR user_name: farooq72
// UT Student #: 1001666422
// Author: Mohammad Umar Farooq
//
// Student2: Matthew Santos
// UTOR user_name: santos75
// UT Student #: 1001309443
// Author: Matthew Santos
//
// Student3: Mohammed Junaid Khan
// UTOR user_name: khanmo78
// UT Student #: 1001715753
// Author: Mohammed Junaid Khan
//
// Student4: Shahrzad Rahbarnia
// UTOR user_name: rahbarn2
// UT Student #: 1001720113
// Author: Shahrzad Rahbarnia
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package commands;

import filesystem.WorkingDirectory;
import output.ConsoleWriter;
import output.DataWriter;
import storage.DirectoryStack;

/**
 * Class that implements the pushd command. Will save the current working
 * directory into a Stack and cd into the argument.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public class pushd implements Command {
	private DirectoryStack referenceToDirectoryStack;

	/**
	 * Constructor that makes use of singleton Directory Stack.
	 */
	public pushd() {
		referenceToDirectoryStack = DirectoryStack.createDirectoryStack();
	}

	public boolean validate(String[] args, WorkingDirectory workingDirectory) {
		if (args.length != 0) {
			cd cdCommand = new cd();
			return cdCommand.validate(args, workingDirectory);
			// Can simply reuse the code from CD and if it's "cd-able" then
			// no reason the command isn't valid.
		}

		System.out.println("Pushd does not take zero arguments.");
		return false;

	}

	public void execute(String[] args, WorkingDirectory wd, DataWriter dw) {
		// Add to the stack and then cd into the argument.
		pushDIR(wd);
		cd cdCommand = new cd();
		cdCommand.execute(args, wd, dw);
	}

	/**
	 * @param wd
	 *            Working Directory to be pushed in.
	 */
	public void pushDIR(WorkingDirectory wd) {
		referenceToDirectoryStack.pushDIR(wd.getWorkingDirectory());
	}

	@Override
	public String getDocumentation() {
		// TODO Auto-generated method stub
		return "Saves the current working directory by pushing onto the directory"
				+ " stack and then changes to new DIR supplied.";
	}
}
