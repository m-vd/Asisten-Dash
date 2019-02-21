import java.util.Stack;
import java.util.LinkedList;

public class Pathfinder {

	private static Pathfinder instance;
	private static World world = World.getInstance();

	private Pathfinder() {}

	public synchronized static Pathfinder getInstance() {
		if (instance == null) {
			instance = new Pathfinder();
		}
		return instance;
	}

	public synchronized Stack<Node> getPath(Node current, Node destination) {
		Node temp = null;

		LinkedList<Node> queue = new LinkedList<Node>();
		Stack<Node> path = new Stack<Node>();

		Node source = world.getNode(current);

		source.markVisited();			//menandai posisi sekarang sudah pernah didatangi
		queue.add(source);				//memasukan posisi sekarang kedalam queue

		while(!queue.isEmpty()) {
			temp = queue.remove();		//mengambil posisi acuan baru dari queue
			/*sudah sampai tujuan*/
			if (temp.isEqual(destination)) {
				break;
			}

			/*mencari node yang dapat diakses dari posisi saat ini*/
			for (Node child : temp.getAdjacentNodes()) {
				if (!child.isVisited()) {
					child.markVisited();			//menandai node telah didatangi / akan didatangi
					child.setPreviousNode(temp);	//menandai dari mana node tersebut didatangi
					queue.add(child);				//menambahkan node tersebut kedalam queue
				}
			}
		}

		/*melakukan traceback jalan yang dilewati untuk sampai tujuan*/
		if (temp != null) {
			path.push(temp);
			while (temp.getPreviousNode() != null) {
				temp = temp.getPreviousNode();
				path.push(temp);
			}
		} else {}

		/*membersihkan tanda-tanda pada node yang dibuat*/
		world.resetPath();

		return path;
	
	}


}