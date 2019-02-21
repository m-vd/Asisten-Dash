import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tembok{
	//ATRIBUT
	private Node loc;
	private BufferedImage dress;
	
	//KONSTRUKTOR
	public Tembok(int x, int y){
		dressUp();
		loc = World.getNode(x,y);
		loc.setBlocked();
	}
	
	//GETTER
	public int getLocX(){
		return loc.getX(); 
	}
	
	public int getLocY(){
		return loc.getY();
	}
	
	public void dressUp() {
		Random rand = new Random();
		int a = rand.nextInt(100) % 3 + 1;

		try {
        	dress = ImageIO.read(new File("Sprites/Plant/"+ a +".png"));
        } catch (IOException e) {
        	e.printStackTrace();
    	}
    }

	public BufferedImage getDress() {
		return dress;
	}
}
