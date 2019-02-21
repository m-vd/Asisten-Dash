import java.util.Timer;
import java.util.TimerTask;

public class GameCondition {
	
	private static Timer timer = new Timer();
	private static int seconds = 0;
	private static int scores = 0;
	private static final int max = 120;				//permainan akan berlangsung selama 2 menit
	private static boolean isOver = false;			//inisialisasi keadaan yang artinya permainan belum berakhir
	
	//method-method
	public static synchronized void MonitorGame() {
	
		TimerTask task;
		
		task = new TimerTask() {
			@Override
			public void run() {
				if (seconds == 0) {
					GameInitiator.clearScreen();
					TextPanel.appendInformation("\t: START!");
				}
				if (seconds <= max) {
					seconds++;				//increment detik hingga max
				} else {
					// stop the timer
					cancel();
					isOver = true;
					TextPanel.appendInformation("\n\n\t: Times up ...");
					TextPanel.appendInformation("\t: Your score is : " + scores);
					if (win()) {			//pengecekan keadaan menang atau tidak
						TextPanel.appendInformation("\t:	You Won! "); 
					} else {
						TextPanel.appendInformation("\t: BOO! You Lost..");
					}
					System.exit(0); 		//menghentikan game
				}
				
				if (seconds == (max - 60)) {
					TextPanel.appendInformation("\t: 1 minutes remaining...");	//reminder waktu tinggal 1 menit lagi
				}
				
				if (seconds == (max - 30)) {
					TextPanel.appendInformation("\t: 30 seconds remaining...");	//reminder waktu tinggal 30 detik lagi
				}

				if (seconds == (max - 10)) {
					TextPanel.appendInformation("\t: 10 seconds remaining...");	//reminder waktu tinggal 10 detik lagi
				}	
			}
		};
		timer.schedule(task, 0, 1000);		//melakukan increment seconds setelah 1000 mili secods, timer dalam satuan miliseconds

	}
	
	public static boolean isOver() {
		return (isOver);
	}
	
	public static synchronized void addScore() {
		scores = scores + 20; //kalo jawabannya bener ditambah 20
	}
	
	public static synchronized void subScores() {
		scores = scores - 10; //kalo jawabannya salah dikurang 10
	}
	
	public static boolean win() {
		return (scores > 499);	//apabila scores >= 500, pemain menang permainan
	}
	
	//GETTER
	public static int getScores() {
		return scores;
	}
	
}
