import java.util.List;
import javax.swing.*;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

	private Game game;
	private BufferedImage worldBackground;

	private List<Asisten> listAsisten;
	private List<Praktikan> listPraktikan;
	private List<Computer> listComputer;
	private List<Tembok> listTembok;
	private Duktek duktek;

	private List<Thread> listThreadAsisten = GameInitiator.getThreadAsisten();
	private List<Thread> listThreadPraktikan = GameInitiator.getThreadPraktikan();
	//private List<Thread> listThreadComputer = GameInitiator.getThreadComputer();

	private Thread thread;

	public GamePanel(Game game) {
		this.game = game;
		try {
			//NANTI GANTI AMBIL SPRITES DARI OBJEKNYA!
			File file = new File("Sprites/board.png");
			FileInputStream fis = new FileInputStream(file);
			worldBackground = ImageIO.read(fis);

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		listAsisten = game.getListAsisten();
		listPraktikan = game.getListPraktikan();
		listComputer = game.getListComputer();
		listTembok = game.getListTembok();
		duktek = game.getDuktek();

		g.drawImage(worldBackground, 0, 0, null);

		int i;

		for (Computer c : listComputer) {
			int x = c.getLocation().getX() * 64;
			int y = c.getLocation().getY() * 64 + 64;
			g.drawImage(c.getDress(), x, y, null);
		
		} 

		for (Tembok t : listTembok) {
			g.drawImage(t.getDress(), (t.getLocX() * 64), (t.getLocY() * 64 + 74), null);
		}

		i = 0;
		for (Praktikan p : listPraktikan) {
			if (true) {
				int x = listPraktikan.get(i).getCurrentLocation().getX() * 64;
				int y = listPraktikan.get(i).getCurrentLocation().getY() * 64 + 64;
				g.drawImage(p.getDress(), x, y, null);
				g.drawString(p.getName(), x, y);
				i++;
			}
		}

		g.drawImage(duktek.getDress(), duktek.getCurrentLocation().getX() * 64, duktek.getCurrentLocation().getY() * 64 + 64, null);

		i = 0;
		for (Thread t : listThreadAsisten) {
			if (t.isAlive()) {
				int x = listAsisten.get(i).getCurrentLocation().getX() * 64;
				int y = listAsisten.get(i).getCurrentLocation().getY() * 64 + 64;
				g.drawImage(listAsisten.get(i).getDress(), x, y, null);
				i++;
			}
		}


	}


	public void run() {
		while (!GameCondition.isOver()) {
			repaint();
		}
	}


}