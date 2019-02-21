import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.locks.*;


public class TextPanel extends JPanel implements KeyListener {

	private static JTextArea informationArea = new JTextArea();
	private static JTextField answerArea = new JTextField();
	private static final int WIDTH = 320;
	private static final int HEIGHT = 724;
    private final static String newline = "\n";
    private static String text;

	public TextPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());

		informationArea.setSize(WIDTH, HEIGHT - 200);
		informationArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		informationArea.setLineWrap(true);
		informationArea.setEditable(false);
		informationArea.setBackground(Color.gray);
		informationArea.setFocusable(false);


		JScrollPane scrollPane = new JScrollPane(informationArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		answerArea.addKeyListener(this);
		answerArea.setFocusable(true);

		add(scrollPane, BorderLayout.CENTER);
		add(answerArea, BorderLayout.PAGE_END);


	}

	public static void appendInformation(String string) {
		informationArea.append("\n" + string);

	}

	public void keyTyped(KeyEvent evt) {}

	public void keyReleased(KeyEvent evt) {}

	public void keyPressed(KeyEvent evt) {

		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
	        text = answerArea.getText();
	        informationArea.append(newline + text);
	        answerArea.setText("");
		}
    }

    public static String getInput() {
    	return text;
    }



}