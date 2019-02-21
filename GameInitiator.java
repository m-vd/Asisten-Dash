import java.util.*;
import java.io.IOException;

public class GameInitiator {
	
	private static GameInitiator instance = null;
	private GameBuilder gameBuilder;
	private static Game game;

	private static List<Thread> threadAsisten = new ArrayList<Thread>();
	private static List<Thread> threadPraktikan = new ArrayList<Thread>();
	//private static List<Thread> threadComputer = new ArrayList<Thread>();
	private static ComputerThread threadComputer;
	private static Thread threadDuktek; 

	private GameInitiator(GameBuilder gameBuilder) {
		this.gameBuilder = gameBuilder;
	}

	public static final synchronized void singleCreate(GameBuilder gameBuilder) {
		if (instance == null) {
			instance = new GameInitiator(gameBuilder);
		}
	}

	public static List<Thread> getThreadAsisten() { return threadAsisten; }

	public static List<Thread> getThreadPraktikan() { return threadPraktikan; }
	
	//public static List<Thread> getThreadComputer() { return threadComputer; }

	public static final synchronized GameInitiator getInstance() {
		return instance;
	}

	public static void clearScreen() {
		try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else {
	            System.out.print("\033[H\033[2J");  
	            System.out.flush();
	        }
    	} catch (IOException | InterruptedException ex) {}
	}

	private void introductionToGame() {
/*		try {
			clearScreen();
			System.out.println("\t: Hello there, ");
			Thread.currentThread().sleep(1500);
			System.out.println("\t: Welcome to Praktikum Dash! - made by OOPs");
			Thread.currentThread().sleep(500);
			System.out.println("\t                  (Ivan, Mira, Adit, Syafi)");
			Thread.currentThread().sleep(1500);
			System.out.println("\t: Let me explain the game first");
			Thread.currentThread().sleep(1500);   
			System.out.print("\t. ");
			Thread.currentThread().sleep(800);
			System.out.print(". ");
			Thread.currentThread().sleep(800);
			System.out.println(". ");
			Thread.currentThread().sleep(1000);
			System.out.println("\t: Actually nevermind. Go learn how to play it by yourself");
			Thread.currentThread().sleep(1500);
			System.out.println("\t: .. (You can just read the readme.txt included tho)");
			Thread.currentThread().sleep(2000);
			System.out.println("\t: .. (But I'm not going to tell you that HAHAHA)");
			Thread.currentThread().sleep(2000);
			System.out.println("\t: So, which level do you want to play? [1/2]");
			System.out.println("\t\t1. Easy");
			System.out.println("\t\t2. Hard");
		} catch (InterruptedException e) {}*/

		int chooseLevel = ScanInput.getInteger();

		if (chooseLevel == 1) {
			System.out.println("\t: Easy mode? Pfft. \n");
			gameBuilder.setDifficulty(Difficulty.EASY);
		} else {
			System.out.println("\t: Well, you chose this. Get ready \n");
			gameBuilder.setDifficulty(Difficulty.HARD);
		}

		try {
			Thread.currentThread().sleep(1500);
			System.out.println("\t: Allright you ready? ");
			Thread.currentThread().sleep(3000);
			System.out.println("\t: OK then, Good luck and have fun! ");
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {}
	}

/*	method seating digunakan untuk menentukan praktikan duduk dimana 
*/	private void seating(Game game) {
		for (int i = 0; i < game.getListPraktikan().size(); i++) {
			Praktikan praktikan = game.getListPraktikan().get(i);
			Node computerLocation = game.getListComputer().get(i).getLocation();
			praktikan.setCurrentLocation(computerLocation);

			String someLine = String.format("%-7s %3s %s", praktikan.getName(), "at", praktikan.getCurrentLocation().toString());
			TextPanel.appendInformation(someLine);
		}
		System.out.println();
	}

/*	method prepareGame digunakan untuk menyiapkan thread-thread yang akan digunakan
*/	private void prepareGame() {

		introductionToGame();

		gameBuilder.buildPraktikan();
		gameBuilder.buildAsisten();
		gameBuilder.buildComputer();
		gameBuilder.buildTembok();
		gameBuilder.buildOthers();

		game = gameBuilder.getGame();

		seating(game);

		for (Asisten iterator : game.getListAsisten()) {
			Thread toBeAdded = new Thread(iterator);
			toBeAdded.setName("Thread - " + iterator.getName());
			threadAsisten.add(toBeAdded);
		}
		for (Praktikan iterator : game.getListPraktikan()) {
			Thread toBeAdded = new Thread(iterator);
			toBeAdded.setName("Thread - " + iterator.getName());
			threadPraktikan.add(toBeAdded);
		}
		threadComputer = new ComputerThread(game);
		threadDuktek = new Thread(game.getDuktek());
		threadDuktek.setName("Thread - Duktek");

	}

	public static Game getGame() {
		return game;
	}
	
/* method yang digunakan untuk start semua thread yang ada
 * mengaktifkan thread asisten, thread duktek, thread computer, dan thread praktikan
*/	public void startGame() {
		prepareGame();

		threadAsisten.get(0).start();				//mengaktifkan thread asisten yang pertama
		threadComputer.start();

		for (Thread iterator : threadPraktikan) {
			iterator.start();
		}

		threadDuktek.start();

	}

	public static void callAsisten() {		//setelah menyeleaikan 5 pertanyaan memanggil asisten lain untuk membantu
		for (Thread iterator : threadAsisten) {
			if (!iterator.isAlive()) {
				iterator.start();
			}
		}
	}


}
