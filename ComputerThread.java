import java.util.List;

public class ComputerThread extends Thread {
	private List<Computer> listComputer;

	public ComputerThread(Game game) {
		super("Thread - AllComputer");
		listComputer = game.getListComputer();
	}

	public void run() {
		while (!GameCondition.isOver()) {
			for (Computer c : listComputer) {
				if (c.isRepairable()) {
					c.jalan();
				}
			}
		}
	}


}