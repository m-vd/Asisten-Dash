//Praktikan.java
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.lang.InterruptedException;
import java.util.ArrayDeque;
import java.util.List;

public class Praktikan extends Mahasiswa implements Runnable {

	private Inquiry inquiry;			//jenis permintaan saat ini
	private QuestionList qlist; 		//Daftar pertanyaan praktikan
	private Question currentQuestion;	//Pertanyaan saat ini
	private String answer;
	private int askRate;				//kemungkinan untuk bertanya
	private ComputerFinder computerFinder = ComputerFinder.getInstance();
	//KONSTRUKTOR
	public Praktikan(String name, String nim) {
		super(name, nim);
		qlist = new QuestionList();
		Random rand = new Random();
		askRate = rand.nextInt(40) + 60;
		dressUp();
		setDress(10);
	}

	//GETTER
	@Override
	public String getName() { return super.getName(); }

	@Override
	public String getNim() { return super.getNim(); }
	
	@Override
	public Node getCurrentLocation() { return super.getCurrentLocation(); }
	
	public Inquiry getInquiry() { return inquiry; }


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
	
	public void dressUp() {
		super.dressUp("Praktikan");
	}

	//METHODS

/*	method raiseHand memasukan objek praktikan yang menggunakan method tersebut
	kedalam antrian penanya, selain itu praktikan juga menyimpan nilai permintaan
	yang dilakukan (bisa berupa pertanyaan, atau permintaan untuk perbaiki 
		komputer)
*/	private void raiseHand() {
		setDress(4);
		if (!getCurrentLocation().computerIsActive()) {
			inquiry = Inquiry.FIX_COMPUTER;
			currentQuestion = null;
		} else {
			inquiry = Inquiry.QUESTION;
			currentQuestion = qlist.getRandomQuestion();
		}
		String someLine = String.format("%-7s %s", getName(), ": KAK... KAK... ");
		TextPanel.appendInformation(someLine);
		if (!QuestionerQueue.getInstance().contains(this)) {
			QuestionerQueue.getInstance().addToQueue(this);
		}
	}

/*	method askQuestion menampilkan nama penanya dan pertanyaannya ke layar,
	serta mengembalikan pertanyaannya
*/	public Question askQuestion() {
		TextPanel.appendInformation(getName() + ": " + currentQuestion.getStringQuestion());
		return currentQuestion;
	}

/*	method askAgain akan memanggil asisten lagi untuk menjawab pertanyaan
	sebelumnya dan memasukan praktikan ke dalam antrian penanya lagi
*/	private void askAgain() {
		try {
			Thread.currentThread().sleep(3500);
		} catch (InterruptedException e) {}
		setDress(4);
		QuestionerQueue.getInstance().addToQueue(this);
		TextPanel.appendInformation(this.getName() + "\t: KAK... KAK... Ini masih salah...");
	}

/*	method receiveAnswer menerima jawaban dan membandingkannya dengan kunci 
	jawaban yang ada. bila jawaban benar, pertanyaan yang disimpan akan dibuang
	dan berterimakasih pada asisten
*/	public void receiveAnswer(String answer) {
		TextPanel.appendInformation(getName() + "\t: Terima kasih kak");
		this.answer = answer;
		setDress(11);
	}

	public void checkAnswer() {
		if (!(answer == null)) {
			if (!answer.equals(currentQuestion.getAnsQuestion())) {	//jawaban sala
				askAgain();
				GameCondition.subScores();											//mengurangi scores
			} else {																					//jawaban benar
				currentQuestion = null;		
				inquiry = null;
				GameCondition.addScore();											//menambahkan scores
			}	
			answer = null;		
		}

	}

/*	method moveToNewComputer memindahkan praktikan dari posisi saat ini ke posisi
	komputer baru, menggunakan method yang terdapat pada kelas Pathfinder. 
*/	public void moveToNewComputer() {
		Node newPlace = null;
		try {
			newPlace = (computerFinder.getNewPlace(getCurrentLocation()));
		} catch (NullPointerException e) {
			TextPanel.appendInformation(getName() + "\t: Ga ada komputer lagi nih");
		}
		if (newPlace != null) {
			getCurrentLocation().setAvailable(true);
			setCurrentLocation(newPlace);
			TextPanel.appendInformation("\t: Praktikan " + getName() + " pindah ke " + newPlace.toString());
			inquiry = null;
			setDress(10);
		} else {
			suspend();
		}
	}

/*	method checkForComputer ini sama dengan method moveToNewComputer, perbedaannya method
	ini digunakan saat kondisi praktikan suspended (berhenti dari semua kegiatannya) karena
	tidak ada komputer aktif yang dapat digunakan
*/	private boolean checkForComputer() {
		Node newPlace = null;
		try {
			newPlace = computerFinder.getNewPlace(getCurrentLocation());
		} catch (NullPointerException e) { return false; }
		if (newPlace != null) {
			getCurrentLocation().setAvailable(true);
			setCurrentLocation(newPlace);
			TextPanel.appendInformation("\t: Praktikan " + getName() + " pindah ke " + newPlace.toString());
			setDress(10);
			unSuspend();
			inquiry = null;
			return true;
		} else {
			return false;
		}
	}


	@Override
	public void run() {
		Random rand = new Random();
		int eta;

		while (!GameCondition.isOver()) {
			if (!isSuspended()) {
				eta = rand.nextInt(5) + rand.nextInt(10) + 10;

				try {
					for (int i = 0; i < eta; i++) {
						TimeUnit.SECONDS.sleep(1);
						if (!getCurrentLocation().computerIsActive()) {
							throw new ComputerIsBrokenException();

						}
					}
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				catch (ComputerIsBrokenException e) {
					raiseHand();
				}

				if (inquiry == null) {
					int rng = rand.nextInt(100);
					if (askRate > rng) {
						raiseHand();
					}
				} else if (inquiry == Inquiry.QUESTION) {
					/*menunggu asisten untuk datang */
					synchronized(this) {
						while (answer == null) {
							try { wait(); }
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					/*menerima jawaban*/
					checkAnswer();
				}
				
			} else {
				try {
					Thread.currentThread().sleep(1000);	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				checkForComputer();
			}
		}
	}


}
