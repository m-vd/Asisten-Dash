import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Stack;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Mahasiswa {
	
	private Identity<String, String> identity;
	private Node currentLocation;
	private boolean suspended;
	private World world = World.getInstance();
	private BufferedImage[] sprites = new BufferedImage[12];
	private BufferedImage dress;

	//KONSTRUKTOR
	protected Mahasiswa(String name, String nim) {
		identity = new Identity<String, String>(name, nim);
		setCurrentLocation(0,0);
	}

	//GETTER
	protected String getName() { return identity.getFirstIdentity(); }

	protected String getNim() { return identity.getSecondIdentity(); }

	protected Node getCurrentLocation() { return currentLocation; }

	protected boolean isSuspended() { return suspended; }


	//SETTER
	protected void setCurrentLocation(int x, int y) {
		currentLocation = world.getNode(x, y);
	}


	protected void setCurrentLocation(Node location) {
		currentLocation = location;
	}

	public void dressUp(String name) {
		//Sprite dapat menghadap 4 sisi,
		//Setiap sisi memiliki 3 gambar.
		//Index gambar :
		//1-3 depan ; 4-6 kiri ; 7-9 kanan ; 10-12 belakang
		for(int i = 0; i < 12; i++) {
			try {
            	sprites[i] = ImageIO.read(new File("Sprites/"+name+"/"+ (i+1) +".png"));
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
        dress = sprites[1];
	}

	public void setDress(int i) {
		dress = sprites[i];
	}

	public BufferedImage getDress() {
		return dress;
	}

	protected void suspend() {
		suspended = true;
	}

	protected void unSuspend() {
		suspended = false;
	}


}