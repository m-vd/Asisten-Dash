public class World {

	//ATTRIBUTES
	private static World instance = null;
	private static int numOfInstances = 0;
	private static Node[][] nodes = new Node[10][10];


	//CONSTRUCTOR
	private World() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
	}


	//GETTER
	public static final synchronized World getInstance() {
		if (instance == null) {
			instance = new World();
			numOfInstances++;
		} 
		return instance;
	}

	public static Node getNode(int x, int y) {
		return nodes[x][y];
	}

	public static Node getNode(Node location) {
		return (nodes[location.getX()][location.getY()]);
	}


	//METHODS
	public static void resetPath() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				nodes[i][j].resetVisited();
				nodes[i][j].resetPreviousNode();
			}
		}
	}

}
