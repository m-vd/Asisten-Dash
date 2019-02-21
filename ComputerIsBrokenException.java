public class ComputerIsBrokenException extends Exception {
	private String message = "Computer is Broken, Fix it first!";

	public String getMessage() {
		return message;
	}
}