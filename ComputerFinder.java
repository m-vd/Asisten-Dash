import java.util.LinkedList;
import java.util.Stack;

public class ComputerFinder {
	private static ComputerFinder instance;
	private static World world = World.getInstance();

	private ComputerFinder() {}

	public synchronized static ComputerFinder getInstance() {
		if (instance == null) {
			instance = new ComputerFinder();
		}

		return instance;
	}

	public synchronized Node getNewPlace(Node current) {
		Node temp = null;
		LinkedList<Node> queue = new LinkedList<Node>();
		Node source = world.getNode(current);
		Node newPlace = null;

		source.markVisited();
		queue.add(source);

		while(!queue.isEmpty()) {
			temp = queue.remove();
			if (temp.isAvailable() && temp.computerIsActive()) {
				newPlace = temp;
				break;
			}

			for (Node child : temp.getAdjacentNodes()) {
				if (!child.isVisited()) {
					child.markVisited();
					child.setPreviousNode(temp);
					queue.add(child);
				}
			}
		}

		world.resetPath();

		return newPlace;
	}


}