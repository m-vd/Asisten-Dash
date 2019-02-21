import java.util.Scanner;

public class ScanInput {

	private static Scanner instance = new Scanner(System.in);

	public synchronized static String getString() {
		String returnValue = instance.nextLine();
		return returnValue;
	}

	public synchronized static String getStringLine() {
		String returnValue = instance.nextLine();

		return returnValue;
	}

	public synchronized static int getInteger() {
		int returnValue = instance.nextInt();
		return returnValue;
	}

}