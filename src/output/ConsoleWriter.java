package output;

import java.util.*;

/**
 * ConsoleWriter is used to print to the console.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public class ConsoleWriter implements DataWriter {
	/**
	 * Constructor of ConsoleWriter
	 */
	public ConsoleWriter() {
	}

	/**
	 * The purpose of this method is to write a string to the console.
	 * 
	 * @param String
	 *            The string to be written to console
	 */
	public void writeString(String s) {
		System.out.println(s);
	}

	/**
	 * Used to print in a table format: index, content.
	 * 
	 * @param ArrayList
	 *            The list you want to print in table format
	 * @param start_index
	 *            The first index of the table.
	 */
	public void writeArrayListTable(ArrayList<String> a, int start_index) {
		for (int i = 0; i < a.size(); i++) {
			System.out.println((start_index + i) + " " + a.get(i));
		}
	}
}
