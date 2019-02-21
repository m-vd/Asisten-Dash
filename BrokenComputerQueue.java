import java.util.LinkedList;

public class BrokenComputerQueue {

	//ATTRIBUT
	private static BrokenComputerQueue instance = new BrokenComputerQueue();
	private static LinkedList<Computer> brokenComputerQueue =
			new LinkedList<Computer>();

	//KONSTRUKTOR
	private BrokenComputerQueue() {}

	//Mengeluarkan objek pertama dari antrian
	public static synchronized Computer getFromQueue() {
		return brokenComputerQueue.remove();
	}

	//Menambahkan objek ke dalam antrian
	public static synchronized void addToQueue(Computer computer) {
		brokenComputerQueue.add(computer);
	}

	//mengembalikan nilai apakah antrian sedang kosong
	public static synchronized boolean isEmpty() {
		return brokenComputerQueue.isEmpty();
	}

}