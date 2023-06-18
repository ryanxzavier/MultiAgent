

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class WordAgent extends Agent{
	   private String words;
	   private Scanner myScanner; 

	    protected void setup() {
	        System.out.println("Word agent " + getAID().getName() + " is ready.");
	        
	        try {
				 myScanner = new Scanner(new File("data2.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
	        List<String> _words = new ArrayList<>();
	        
	        while(myScanner.hasNext()) {
	        	String word = myScanner.next(); 
	        	_words.add(word);
	        }
	       
	        
	        Random rand = new Random();
	        int index1 = rand.nextInt(_words.size());
	        int index2 = rand.nextInt(_words.size() - 1);
	        if (index2 >= index1) {
	            index2++;
	        }

	        String word1 = _words.get(index1);
	        String word2 = _words.get(index2);
	        
	        setWords(word1+":"+word2);

	        // Define behavior for requesting a word
	        addBehaviour(new OneShotBehaviour() {
	            public void action() {
	                // Create a message requesting a word
	                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	                msg.addReceiver(new AID("dictionaryAgent", AID.ISLOCALNAME));
	                msg.setContent(words);
	                System.out.println(
	                		"Hi Dictionary Agent! I randomly picked on " + word1 + "\n" 
	                		+ "On your list but I would Like to Know word " + word2 + "\n");
	                // Send the message
	                send(msg);
	            }
	        });

	        // Define behavior for receiving a definition
	        addBehaviour(new CyclicBehaviour() {
	            public void action() {
	                // Receive a message
	                ACLMessage msg = receive();
	                if (msg != null) {
	                        String definition = msg.getContent();
	                       
	                        System.out.println("Word Agent now knows that a " + word2 +" is " + definition + "\n");
	                } else {
	                    block();
	                }
	            }
	        });
	    }

	    public void setWords(String words) {
	        this.words = words;
	    }

}
