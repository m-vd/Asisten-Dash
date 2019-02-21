import java.util.*;
import java.io.*;

public class GameBuilder implements GameBuilderInterface {

	private Game game = new Game();
	private String levelPath;

	public void setDifficulty(Difficulty diff) {
		game.setDifficulty(diff);
		if (diff == Difficulty.HARD) {
			levelPath = "Txt/Hard/";
		} else {
			levelPath = "Txt/Easy/";
		}
	}

	public void buildAsisten() {
		String nama;
		String nim;

		//PENGAMBILAN DATA ASISTEN DARI FILE EXTERNAL
		try (BufferedReader reader = new BufferedReader(new FileReader(levelPath+"Asisten.txt"))) { //Penambahan data praktikan dari file "praktikan.txt"
			while((nama = reader.readLine()) != null) {			//Mengambil nama asisten
				nim = reader.readLine();						//Mengambil nim asisten
				game.addListAsisten(new Asisten(nama, nim));	//Membuat objek asisten dan menyimpannya pada list
				Collections.shuffle(game.getListAsisten());		//Mengacak list asisten
            }
		}
		catch (IOException e) {
			System.err.println("error reading names from file praktikan.txt");
			e.printStackTrace();
		}
	}

	public void buildPraktikan() {
		String nama;
		String nim;

		//PENGAMBILAN DATA PRAKTIKAN DARI FILE EXTERNAL
		try (BufferedReader reader = new BufferedReader(new FileReader(levelPath+"praktikan.txt"))) { //Penambahan data praktikan dari file "praktikan.txt"
			while((nama = reader.readLine()) != null) {			//Mengambil nama praktikan
				nim = reader.readLine();						//Mengambil nim praktikan
				game.addListPraktikan(new Praktikan(nama, nim));//Membuat objek praktikan dan menyimpannya pada list
				Collections.shuffle(game.getListPraktikan());	//Mengacak list praktikan
            }
		}
		catch (IOException e) {
			System.err.println("error reading names from file praktikan.txt");
			e.printStackTrace();
		}
	}

	public void buildComputer() {

		//PENGAMBILAN DATA KOORDINAT DARI FILE EXTERNAL
		try {
            Scanner fileScanner = new Scanner(new FileReader(levelPath+"coord.txt")); //Membaca koordinat praktikan dari file 'coord.txt'
			String line;
			while (fileScanner.hasNextLine()) {                           
				//Membaca koordinat pada baris saat ini
				line = fileScanner.nextLine();
				String[] coord = line.replaceAll("\\(|\\)", "").split(",");
				int x = Integer.valueOf(coord[0]);							//membaca koordinat x
				int y = Integer.valueOf(coord[1]);							//membaca koordinat y

				game.addListComputer(new Computer(World.getNode(x, y)));	//membuat objek komputer pada posisi (x,y) dan menyimpannya pada list
				Collections.shuffle(game.getListComputer());				//mengacak list komputer
			}
        }
        catch (IOException e) {
			System.err.println("error reading names from file coord.txt");
			e.printStackTrace();
		}
	}

	public void buildTembok() {
		try {
            Scanner fileScanner = new Scanner(new FileReader("Txt/tembok.txt")); //Membaca koordinat praktikan dari file 'coord.txt'
			String line;
			List<Node> listPosisiTembok = new ArrayList<Node>();
			while (fileScanner.hasNextLine()) {             
				//Membaca koordinat pada baris saat ini
				line = fileScanner.nextLine();
				String[] coord = line.replaceAll("\\(|\\)", "").split(",");
				int x = Integer.valueOf(coord[0]);							//membaca koordinat x
				int y = Integer.valueOf(coord[1]);							//membaca koordinat y

				listPosisiTembok.add(World.getNode(x, y));					//membuat objek tembok pada posisi (x,y)
				Collections.shuffle(listPosisiTembok);						//mengacak list tembok
				Collections.shuffle(listPosisiTembok);
				Collections.shuffle(listPosisiTembok);
			}

			int max;

			/*mengatur jumlah tembok yang digunakan tergantung pada kesulitan game yang dipilih*/
			if (game.getDifficulty() == Difficulty.HARD) {
				max = 8;
			} else {
				max = 3;
			}

			for (int i = 0; i < max; i++) {
				Node toBeBlocked = listPosisiTembok.get(i);
				game.addListTembok(new Tembok(toBeBlocked.getX(), toBeBlocked.getY()));
			}
        }
        catch (IOException e) {
			System.err.println("error reading names from file coord.txt");
			e.printStackTrace();
		}
	}

	public void buildOthers() {
		game.setDuktek(Duktek.getInstance());
	}


	public Game getGame() {
		return this.game;
	}

}