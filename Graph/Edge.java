package Graph;

public class Edge {
	
	private Node src;
	private Node dest;
	private int weight; 
	
	public Edge(Node node, Node neighbour, int weight ) {
		this.src = node;
		this.dest = neighbour;
		this.weight = weight;
	}
	
	public Node getSrc() {
		return src;
	}
	
	public Node getDest() {
		return dest;
	}
	
	public int getWeight() {
		return weight;
	}

}
