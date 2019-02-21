import java.util.*;

public class Game {
	//ATTRIBUTES
	private List<Asisten> listAsisten = new ArrayList<Asisten>();
	private List<Praktikan> listPraktikan = new ArrayList<Praktikan>();
	private List<Computer> listComputer = new ArrayList<Computer>();
	private List<Tembok> listTembok = new ArrayList<Tembok>();

	private Duktek duktek;
	private Difficulty difficulty;


	//GETTER
	public List<Asisten> getListAsisten() {
		return listAsisten;
	}

	public List<Praktikan> getListPraktikan() {
		return listPraktikan;
	}

	public List<Computer> getListComputer() {
		return listComputer;
	}

	public List<Tembok> getListTembok() {
		return listTembok;
	}

	public Difficulty getDifficulty() { 
		return difficulty;
	}

	public Duktek getDuktek() {
		return duktek;
	}


	//SETTER
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setDuktek(Duktek duktek) {
		this.duktek = duktek;
	}


	//METHODS
	public void addListAsisten(Asisten asisten) {
		listAsisten.add(asisten);
	}

	public void addListPraktikan(Praktikan praktikan) {
		listPraktikan.add(praktikan);
	}

	public void addListComputer(Computer computer) {
		listComputer.add(computer);
	}

	public void addListTembok(Tembok tembok) {
		listTembok.add(tembok);
	}

}