
import jade.core.Agent;

import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.*;

import Graph.Graph;
import Graph.Node;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DictionaryAgent extends Agent {
	
	    
	    private Graph graph = new Graph(); 

	    protected void setup() {
	        System.out.println("Dictionary agent " + getAID().getName() + " is ready.");
	        
	        try {
				populateDictionary();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Define behavior for receiving a word and sending a definition
	        addBehaviour(new CyclicBehaviour() {
	            public void action() {
	                // Receive a message
	                ACLMessage msg = receive();
	                if (msg != null) {
	                    // Check if the message contains a word
	                    if (msg.getPerformative() == ACLMessage.REQUEST) {
	                        String word = msg.getContent();
	                        
	                        System.out.println("Hi Word Agent! I'll help you out just now! ");
	                        String[] words = word.split(":"); 
	                        
	                        String word1 = words[0];
	                        String  word2 = words[1];
	                        
	                        Node firstWordNode = graph.getNode(word1); 
	                        Node secondWordNode = graph.getNode(word2);
	                        

	                        // Find the definition of the word using BFS
	                        String definition = breadthFirstSearch(firstWordNode, secondWordNode);

	                        // Create a message containing the definition
	                        ACLMessage reply = msg.createReply();
	                        reply.setPerformative(ACLMessage.INFORM);
	                        reply.setContent(definition);
	                        System.out.println("Dictionary Agent got the definition For you ! it is - " + definition + "\n");
	                        // Send the message
	                        send(reply);
	                    } else {
	                        System.out.println("Unexpected message received from Dictionary");
	                    }
	                } else {
	                    block();
	                }
	            }
	        });
	    }
	    
	    
	    //Populate Dictionary Function that reads form a file
	    private void populateDictionary() throws FileNotFoundException {
			// TODO Auto-generated method stub
	    	Scanner myScanner = new Scanner(new File("data.txt")); 
	    	
	    	while(myScanner.hasNextLine()) {
	    		String line = myScanner.nextLine(); 
	    		String[] tokens = line.split(":");
	    		
	    		String word = tokens[0]; 
	    		String definition = tokens[1]; 
	    		
	    		Node node = new Node(word); 
	    		node.setDefinition(definition);
	    		
	    		graph.addNode(node);
	    			    			
	    	}
	    	
	    	//Add Edges between all Nodes
	    	List<Node> nodes = graph.getNodes();

	    	for (int i = 0; i < nodes.size(); i++) {
	    	    Node nodeA = nodes.get(i);

	    	    for (int j = i+1; j < nodes.size(); j++) {
	    	        Node nodeB = nodes.get(j);
	    	        
	    	            graph.addEdge(nodeA, nodeB, 1);
	    	        }
	    	    }
	    	
	    	}
			
		

//		// Method to perform breadth-first search for the definition of a word
	    private String breadthFirstSearch(Node startNode, Node targetNode) {
	        Queue<Node> queue = new LinkedList<>();
	        Set<Node> visited = new HashSet<>();

	        // Add the starting node to the queue
	        queue.add(startNode);

	        while (!queue.isEmpty()) {
	            // Dequeue the next node
	            Node currentNode = queue.poll();
	            // Check if the current node is the target node
	            if (currentNode.equals(targetNode)) {
	                // Return the definition of the node
	                return currentNode.getDefinition();
	            }

	            // Add the neighbors of the current node to the queue
	            for (Node neighbor : currentNode.getAdjacentNodes().keySet()) {
	                if (!visited.contains(neighbor)) {
	                    queue.add(neighbor);
	                    visited.add(neighbor);
	                }
	            }
	        }

	        // If the target node was not found, return an error message
	        return "Sorry, the definition of " + targetNode.getName() + " could not be found.";
	    }

	    
}

