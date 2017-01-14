package exceptions;

public class InvalidName extends Exception {

	public InvalidName(String arg0) {
		super(arg0 + " is not recognised as a valid command");
		// TODO Auto-generated constructor stub
	}

}
