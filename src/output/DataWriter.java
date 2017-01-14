package output;

import java.util.ArrayList;

/**
 * An interface used for DataWriter. A DataWriter is used to output Data.
 * 
 * @author Mohammad Umar Farooq
 *
 */
public interface DataWriter {
	/**
	 * Used for writing a basic string.
	 * 
	 * @param s
	 *            The string to write.
	 */
	public abstract void writeString(String s);

	/**
	 * A useful function for format printing an Array List of strings to a
	 * table. Type: (i,i+1...n) (ArrayList in order) Example: 3 third 4 fourth 5
	 * fifth
	 * 
	 * @param a
	 *            The array list you want to format print
	 * @param i
	 *            The first index you'd like to start at.
	 */
	public abstract void writeArrayListTable(ArrayList<String> a, int i);
}
