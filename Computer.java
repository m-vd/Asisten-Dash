import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.lang.InterruptedException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Computer {
	//ATRIBUT
	private static int id;
	private Identity<String, Node> identity;	//Identity dari Komputer adalah ID komputer dan lokasinya
	private boolean active;
	private boolean repairable;
	private Node location;
	private BufferedImage[] sprites = new BufferedImage[3];
	private BufferedImage dress;
	
	//KONSTRUKTOR

	public Computer(Node location) {
		id++;
		Node loc = World.getNode(location);
		String index;

		/*melakukan komputasi untuk menentukan nama komputer dengan format "PC-XX" dimana
		XX adalah id komputer*/
		if (id/10 == 0) {
			index = ("0" + id);
		} else {
			index = ((Integer) id).toString();
		}
		String pcName = "PC-" + index;
		loc.setAvailable(true);
		loc.setComputer(this);
		loc.setBlocked();
		identity = new Identity<String, Node>(pcName, loc);
		active = true;
		repairable = true;
		dressUp();
	}

	//GETTER
	public String getName() {
		return identity.getFirstIdentity();
	}

	public Node getLocation() {
		return identity.getSecondIdentity();
	}

	public boolean isRepairable() {
		return repairable;
	}

	public BufferedImage getDress() {
		return dress;
	}
	
	//SETTER
	public void setState(boolean bool) {
		if (repairable) {
			this.active = bool;
			setDress(0);
		} else {}

	}

	//METHODS
	public void dressUp() {
		for(int i = 0; i < 3; i++) {
			try {
            	sprites[i] = ImageIO.read(new File("Sprites/Computer/"+ (i+1) +".png"));
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
        dress = sprites[0];
	}

	public void setDress(int i) {
		dress = sprites[i];
	}

	public boolean isActive() {
		return active;
	}
	
	public void rusak() {
		active = false;
		setDress(1);
    }

    public void meledak() {
    	rusak();
    	setDress(2);
    	repairable = false;
    	TextPanel.appendInformation("BOOM! ");
    }

    public void jalan() {
    	Random rand = new Random();
		int rng;

		/*komputer akan berjalan selama game masih bermain dan komputer masih dapat diperbaiki*/
		if (active) { 
			/*komputer akan melakukan komputasi apakah komputer akan rusak atau tidak
			setiap 10 detik + random(20)*/
			try {
				TimeUnit.SECONDS.sleep(7);
			}
			catch (InterruptedException e) {}
			/*komputer melakukan kalkulasi probabilitas dia rusak*/
			rng = rand.nextInt(100);
			if (rng < 20) {							//komputer memiliki kemungkinan 10% untuk rusak
				rng = rand.nextInt(100);
				if (rng < 10) {						//komputer memiliki kemungkinan 0.5% untuk meledak
					meledak();
				} else {
					TextPanel.appendInformation("\t: " + getName() + " " + getLocation().toString() + " rusak");
					rusak();
				}
			}
		} else {
			synchronized (this) {
				while (repairable && !isActive()) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
				/*komputer tidak melakukan apa-apa karena sedang rusak*/
		}
	}
		
    


}
