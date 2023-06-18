package Graph;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Graph {

    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    
    public List<Edge> getEdges() {
		return edges;
	}


	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}


	public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }
	
	public void addEdge(Node nodeA, Node nodeB, int weight) {
	    Edge edge = new Edge(nodeA, nodeB, weight);
	    edges.add(edge);
	    nodeA.addDestination(nodeB, weight);
	    nodeB.addDestination(nodeA, weight);
	}

    

	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getNode(String name) {
	    for(Node node : nodes) {
	        if(node.getName().equals(name)) {
	            return node; 
	        }
	    }
	    System.out.println("Error cannot find node with name : " + name);
	    return null;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

    // getters and setters 
    
    
}