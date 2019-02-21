import java.util.LinkedList;

class Node {
	private int posX;
	private int posY;
	private boolean visited;
	private Node previousNode;
	private boolean blocked;			//ada tembok atau ngga
	private boolean available;			//diduduki seseorang atau ngga
	private Computer computer;			//ada komputer atau ngga


	//KONSTRUKTOR
	public Node(int posX, int posY) {
		setPosX(posX);
		setPosY(posY);
	}

	public Node(Node node) {
		setPosX(node.getX());
		setPosY(node.getY());
		visited = false;
		previousNode = null;
		blocked = node.isBlocked();
	}

	//GETTER
	public int getY() { return this.posY; }

	public int getX() { return this.posX; }

	public boolean isVisited() { return visited; }

	public boolean isAvailable() { return available; }

	public Computer getComputer() { return computer; }

	public boolean isBlocked() { return this.blocked; }

	public Node getPreviousNode() { return this.previousNode; }

	public boolean computerIsActive() { return computer.isActive(); }


	//SETTER
	private void setPosX(int posX) { this.posX = posX; }

	private void setPosY(int posY) { this.posY = posY; }
	
	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public void setBlocked() {
		blocked = true;
	}

	public void unblock() {
		blocked = false;
	}

	public void resetPreviousNode() {
		previousNode = null;
	}

	public void markVisited() {
		this.visited = true;
	}

	public void resetVisited() {
		this.visited = false;
	}

	public void setAvailable(boolean bool) {
		available = bool;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}



	//METHODS
	public String toString() {
		return ("("+this.posX+","+this.posY+")");
	}

	public boolean isEqual(Node compare) {
		return ((this.posX == compare.getX()) && (this.posY == compare.getY()));
	}

/* fungsi mengembalikan node yang berada disekitar node acuan
*/	public LinkedList<Node> getAdjacentNodes() {
		LinkedList<Node> queue = new LinkedList<Node>();

		if ((this.posX + 1) < 10) {
			if (!World.getNode((this.posX + 1), this.posY).isBlocked()) {
				queue.add(World.getNode((this.posX + 1), this.posY));
			}
		}
		if ((this.posY + 1) < 10) {
			if (!World.getNode(this.posX, (this.posY + 1)).isBlocked()) {
				queue.add(World.getNode(this.posX, (this.posY + 1)));
			}
		}
		if ((this.posX - 1) >= 0) {
			if (!World.getNode((this.posX-1), this.posY).isBlocked()) {
				queue.add(World.getNode((this.posX-1), this.posY));
			}
		}
		if ((this.posY - 1) >= 0) {
			if (!World.getNode(this.posX, (this.posY - 1)).isBlocked()) {
				queue.add(World.getNode(this.posX, (this.posY - 1)));
			}
		}

		return queue;
	}

	public Direction getDirectionTo(Node node) {
		Direction returnValue;

		if (posX + 1 == node.getX() && posY == node.getY()) {
			return Direction.EAST;
		} else if (posX - 1 == node.getX() && posY == node.getY()) {
			return Direction.WEST;
		} else if (posX == node.getX() && posY - 1 == node.getY()) {
			return Direction.NORTH;
		} else if (posX == node.getX() && posY + 1 == node.getY()) {
			return Direction.SOUTH;
		} else {
			return null;
		}


	}

}