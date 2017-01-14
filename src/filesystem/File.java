package filesystem;

/**
 * File Class
 * 
 * Class that houses a files and all its workings.
 * 
 * @author Mohammed Junaid Khan
 * @since October 21, 2015
 *
 */
public class File extends Item {

	/**
	 * Stores the contents of the file
	 */
	private String contents;

	/**
	 * File constructor
	 * 
	 * sets file name and contents of a file upon creation
	 * 
	 * @param fileName
	 *            input of the file's name
	 * @param contents
	 *            input of the file's contents
	 */
	public File(String fileName, String contents, Directory parentDirectory) {

		// take the incoming file name and set current file name to that sans
		// directory
		setName(fileName.split("/")[fileName.split("/").length - 1]);

		this.contents = contents; // take the incoming contents and set the
									// current
									// file's contents to that
		setParentDirectory(parentDirectory);
	}

	/**
	 * getContents method - returns the contents of the file
	 * 
	 * @return contents - returns the contents of the file
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * writeToFile method overwrites the current contents of the file with new
	 * "contents"
	 * 
	 * @param contents
	 *            - new content to replace "contents" with
	 */
	public void writeToFile(String contents) {
		this.contents = contents;
	}

	/**
	 * appendToFile method appends "contents" to the existing "contents"
	 * 
	 * @param contents
	 *            - new content to append to "contents"
	 */
	public void appendToFile(String contents) {
		this.contents = this.contents.concat("\n" + contents);
	}
}