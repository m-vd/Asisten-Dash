import java.util.LinkedList;

public class QuestionerQueue {

	//ATTRIBUT
	private static QuestionerQueue instance = new QuestionerQueue();
	private static LinkedList<Praktikan> questionerQueue;

	//KONSTRUKTOR
	private QuestionerQueue() {
		questionerQueue = new LinkedList<Praktikan>();
	}

	public static QuestionerQueue getInstance() {
		return instance;
	}

	//Mengeluarkan objek pertama dari antrian
	public synchronized Praktikan getFromQueue() throws Exception {
		if (isEmpty()) {
			throw new Exception();
		} else {
			return questionerQueue.remove();
		}
	}

	//Menambahkan objek ke dalam antrian
	public void addToQueue(Praktikan praktikan) {
		synchronized (instance) {
			questionerQueue.add(praktikan);
			instance.notifyAll();
		}
	}

	//mengembalikan nilai apakah antrian sedang kosong
	public synchronized boolean isEmpty() {
		return questionerQueue.isEmpty();
	}

	public synchronized boolean contains(Praktikan praktikan){
		return questionerQueue.contains(praktikan);
	}

}