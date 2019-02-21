import java.util.Stack;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Duktek implements Runnable {

	
	private Identity<String, String> identity;
	//Identitas pertama adalah nama, kedua NIP
	private static Duktek instance;
	private Computer patient;
	private Node currentLocation = World.getNode(9,9); 
	private BufferedImage dress;
	private BufferedImage[] sprites = new BufferedImage[12];

	//CONSTRUCTOR
	private Duktek() {
		identity = new Identity<String, String> ("Duktek", "1350529024");
		dressUp();
	}

	//GETTER
	public String getName() { return identity.getFirstIdentity(); }

	public String getNip() { return identity.getSecondIdentity(); }
	
	public static synchronized Duktek getInstance() {
		if (instance == null) {
			instance = new Duktek();
		}

		return instance;
	}

	public Node getCurrentLocation() {
		return currentLocation;
	}
	//SETTER
	private void setCurrentLocation(Node location) {
		currentLocation = location;
	}

	public void dressUp() {
		//Sprite dapat menghadap 4 sisi,
		//Setiap sisi memiliki 3 gambar.
		//Index gambar :
		//1-3 depan ; 4-6 kiri ; 7-9 kanan ; 10-12 belakang
		for(int i = 0; i < 12; i++) {
			try {
            	sprites[i] = ImageIO.read(new File("Sprites/Duktek/"+ (i+1) +".png"));
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


	//METHODS
/*	method fixComputer adalah method yang mengambil sebuah komputer rusak dari antrian komputer rusak
	kemudian mencari jalan menuju komputer tersebut dan memperbaikinya. Untuk memperbaiki komputer
	dibutuhkan waktu selama 10 detik
*/	private void fixComputer() {
		patient = BrokenComputerQueue.getFromQueue();
		if (!patient.isActive()) {
			Stack<Node> path = Pathfinder.getInstance().getPath(currentLocation, patient.getLocation());

			path.pop();						//membuang path pertama karena = tempat awal

			/*melakukan perpindahan menuju tempat komputer*/
			while (!path.isEmpty()) {
				Node temp = path.pop();
				setCurrentLocation(temp);
			}

			setCurrentLocation(patient.getLocation());

			/*memperbaiki komputer yang rusak*/
			try {
				Thread.currentThread().sleep(3000);
				TextPanel.appendInformation("Duktek sedang memperbaiki " + patient.getLocation().toString());
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				BrokenComputerQueue.addToQueue(patient);		//bila sewaktu memperbaiki duktek terganggu, komputer tidak jadi diperbaiki
				return;
			}
			synchronized (patient) {
				patient.notify();
			}
			patient.setState(true);
			TextPanel.appendInformation("Komputer di " + patient.getLocation().toString() + " sudah diperbaiki");
		}
		patient = null;
		setCurrentLocation(World.getNode(9,9));
	}

	@Override
	public void run() {
		while (!GameCondition.isOver()) {
			synchronized (instance) {
				while (BrokenComputerQueue.isEmpty()) {
					try {
						instance.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			fixComputer();
		}
	}
		
	
}