package storage;

import java.util.*;

/**
 * The purpose of this class is to store pushed and return popped directories
 * from a Stack. This is for the implementation of pushd and popd.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public class DirectoryStack {
	private static DirectoryStack singleReference = null;
	private Stack<String> DirectoryStack = new Stack<String>();

	/**
	 * Protected constructor as we are implementing singleton design
	 */
	protected DirectoryStack() {
	}

	/**
	 * @return singleReference Singleton design, reference pointing to the one.
	 */
	public static DirectoryStack createDirectoryStack() {
		// Using singleton design because we never get rid of history
		if (singleReference == null) {
			singleReference = new DirectoryStack();
		}
		return singleReference;
	}

	/**
	 * @param DIR
	 *            A directory in the form of a string.
	 */
	public void pushDIR(String DIR) {
		DirectoryStack.push(DIR);
	}

	/**
	 * @return String The string popped from the Stack.
	 */
	public String popDIR() {
		return DirectoryStack.pop();
	}

	/**
	 * @param o
	 *            Object to be searched.
	 * @return Position where the object is on this stack.
	 */
	public int search(Object o) {
		return DirectoryStack.search(o);
	}

	/**
	 * @return boolean True if and only if stack is empty.
	 */
	public boolean empty() {
		return DirectoryStack.empty();
	}

}
