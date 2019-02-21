//Asisten.java
import java.util.*;

public class Asisten extends Mahasiswa implements Runnable {
	
	private static int toHelp = 5;							//maksimum bantuan yang diberikan sebelum memanggil asisten lain
	private int countWorkDone;								//jumlah pertanyaan yang telah dijawab
	private Praktikan inquirer;								//praktikan yang sedang dilayani
	private Direction direction;
	private Pathfinder pathfinder = Pathfinder.getInstance();

	//KONSTRUKTOR
	public Asisten(String name, String nim) {
		super(name, nim);
		countWorkDone = 0;
		dressUp(name);
	}
	

	//GETTER
	@Override
	public String getName() { return super.getName(); }

	@Override
	public String getNim() { return super.getNim(); }
	
	@Override
	public Node getCurrentLocation() { return super.getCurrentLocation(); }
	

	//SETTER
	@Override
	public void setCurrentLocation(int x, int y) {
		super.setCurrentLocation(x, y);
		getCurrentLocation().setAvailable(false);
	}

	@Override
	public void setCurrentLocation(Node location) {
		super.setCurrentLocation(location);
		getCurrentLocation().setAvailable(false);
	}


/*	method moveTo memindahkan asisten dari posisi semula ke posisi praktikan
	dengan mengimplementasikan method dari kelas Pathfinder untuk mencari
	posisi praktikan yang dituju
*/	private void moveTo(Node destination) {
		
		destination.unblock();
		Stack<Node> path = pathfinder.getPath(getCurrentLocation(), destination);		//mengambil jalur untuk sampai tujuan dengan implementasi BFS

		/*proses berpindah tempat */
		while (!path.empty()) {
			Node temp = path.pop();
			Direction a = getCurrentLocation().getDirectionTo(temp);

			try {
				if (a == Direction.SOUTH) {
					for (int i = 0; i < 3; i ++) {
						setDress(i);
						Thread.currentThread().sleep(120);
					}
				} else if (a == Direction.NORTH) {
					for (int i = 0; i < 3; i ++) {
						setDress(i+9);
						Thread.currentThread().sleep(120);
					}
				} else if (a == Direction.EAST) {
					for (int i = 0; i < 3; i ++) {
						setDress(i+6);
						Thread.currentThread().sleep(120);
					}
				} else if (a == Direction.WEST) {
					for (int i = 0; i < 3; i ++) {
						setDress(i+3);
						Thread.currentThread().sleep(120);
					}
				}
				Thread.currentThread().sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!path.empty()) {
				setCurrentLocation(temp);
			} else {
				a = getCurrentLocation().getDirectionTo(destination);

				if (a == Direction.NORTH) {
					setDress(10);
				} else if (a == Direction.SOUTH) {
					setDress(1);
				} else if (a == Direction.EAST) {
					setDress(7);
				} else if (a == Direction.WEST) {
					setDress(4);
				}
			}
		}
		destination.setBlocked();
	}

/*	method answerQuestion menerima input jawaban dari pemain
*/	private String answerQuestion(Question question) {
		String answer = ScanInput.getString();
		return answer;
	}

/*	method handleInquirer adalah pekerjaan sehari-hari asisten dimana asisten
	akan mengambil seorang penanya dari antrian penanya bila tidak kosong,
	berpindah ke posisinya dan menjawab pertanyaan atau permintaannya
*/	private void handleInquirer() {
		if (!QuestionerQueue.getInstance().isEmpty()) {

			/*mengambil praktikan dari antrian penanya dengan melakukan try catch
			untuk memastikan tidak terjadi error */
			try {
				inquirer = QuestionerQueue.getInstance().getFromQueue();
			} catch (Exception e) {
				return ;
			}

			/*berpindah ke posisi praktikan*/
			moveTo(inquirer.getCurrentLocation());

			/*bangunkan thread praktikan yang sedang menunggu*/
			synchronized(inquirer) {
				inquirer.notify();
			}

			/*menangani praktikan sesuai dengan permintaannya*/
			if (inquirer.getInquiry() == Inquiry.FIX_COMPUTER) {				//bila diminta untuk perbaiki komputer
				TextPanel.appendInformation(getName() + "\t: " + inquirer.getName() + ", coba pindah komputer dulu ya..");
				inspectComputer(inquirer.getCurrentLocation().getComputer());
				inquirer.moveToNewComputer();									//meminta praktikan untuk pindah ke komputer lain
			} else if (inquirer.getInquiry() == Inquiry.QUESTION) {				//bila diminta menjawab pertanyaan
				inquirer.receiveAnswer(answerQuestion(inquirer.askQuestion()));	//jawab pertanyaan
			}
			
			inquirer = null;					//reset penanya yang dilayani
			countWorkDone++;					//menambah hitungan pekerjaan yang telah dilakukan
		}

		if (toHelp > (-1)) {					//counter sampai asisten kewalahan
			toHelp--;
		}
	}

	private void inspectComputer(Computer computer) {
		if (computer.isRepairable()) {
			notifyDuktek(computer);
		} else {

		}
	}

	private void notifyDuktek(Computer computer) {
		BrokenComputerQueue.addToQueue(computer);
		synchronized (Duktek.getInstance()) {
			Duktek.getInstance().notify();
		}
	}

/*	bila asisten menjawab 10 pertanyaan, asisten akan pingsan selama 5 detik
*/	private void pingsan() {
		try { 
			TextPanel.appendInformation(getName() + " pingsan");
			Thread.currentThread().sleep(5000);
		} 
		catch(InterruptedException e) {}
		countWorkDone = 0;	//jumlah pertanyaan yang telah dijawab di set menjadi 0 kembali
	}
	
/*	asisten akan meminta bantuan asisten lainnya saat ia mulai kewalahan
*/	public void panggilBantuan() {
		TextPanel.appendInformation(getName() + "\t: " + "Oi asisten lain bantu dong!");
		GameInitiator.callAsisten();
	}

	@Override
	public void run() {
		while (!GameCondition.isOver()) {
			if (!QuestionerQueue.getInstance().isEmpty()) {			//bila ada penanya
				if (countWorkDone == 10) {				//bila sudah menjawab banyak pertanyaan
					pingsan();							//asisten pingsan
				} else {								
					handleInquirer();
				}
				if (toHelp == 0) {						//asisten kewalahan
					panggilBantuan();					//memanggil bantuan asisten lainnya
				}

			} else { 
				try {									//bila antrian kosong, tunggu sampe ada
					synchronized (QuestionerQueue.getInstance()) {
						while (QuestionerQueue.getInstance().isEmpty()) {
							QuestionerQueue.getInstance().wait();
						}
					} 
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.currentThread().sleep(1000);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
