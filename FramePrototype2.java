import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FramePrototype2 extends JFrame {

	private static final int HEIGHT = 724;
	private static final int WIDTH = 960; 
	private static FramePrototype2 instance = null;
	private GamePanel gamePanel;
	private TextPanel textPanel;
	
	//iconAsisten = iconAsisten.getScaledInstance(64,64,Image.SCALE_FAST);
	//iconPraktikan = iconPraktikan.getScaledInstance(64,64,Image.SCALE_FAST);

	public FramePrototype2(Game game) {
		super("Praktikum Dash!");
		gamePanel = new GamePanel(game);
		textPanel = new TextPanel();

		getContentPane().setLayout(new BorderLayout());
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(textPanel, BorderLayout.LINE_END);

		this.setVisible(true);
	}


	
}
