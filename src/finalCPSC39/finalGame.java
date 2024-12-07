package finalCPSC39;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class finalGame {
	
	// Global Scanner for user input
	public static Scanner scnr = new Scanner(System.in);
	
	//main
	public static void main(String[] args) {
		
		// Boolean to control game loop
		boolean playAgain =true;
		
		// Make an object of the Player class
		Player player1 = new Player();
		
		// User Enters Name
		System.out.println("Enter player name: ");
		player1.stats.setName(scnr.next());
		
        // Enemy parralell data arrays
        String[] enemyNames = {"Goblin", "Orc", "Dog"};
        int[] enemyAttacks = {10, 15, 14};
        int[] enemyHealths = {30, 50, 40};
        int[] enemyDefenses = {3, 5, 2};
        

        while (playAgain) {
            // Randomly select an enemy
        	// Based on length of parallel enemy data arrays
            Random random = new Random();
            int enemyIndex = random.nextInt(enemyNames.length);
            
            // Creates an enemy from the Characters class
            // Future updates should have a unique enemy class
            Characters enemy = new Characters();
            
            // Uses picked index to apply correct enemy data
            enemy.stats.setName(enemyNames[enemyIndex]);
            enemy.stats.setAttack(enemyAttacks[enemyIndex]);
            enemy.stats.setHealth(enemyHealths[enemyIndex]);
            enemy.stats.setDefense(enemyDefenses[enemyIndex]);
            
            // Inform the player about the enemy
            System.out.println("A wild " + enemy.getStats().getName() + " has appeared!");
            
            // Creates a queue to represent the turn order
            Queue<Characters> turnQueue = new LinkedList<>();
        
            // Add players/enemies to the queue
            turnQueue.offer(player1);
            turnQueue.offer(enemy);

            // Main battle loop
            while (turnQueue.size() > 1 && (player1.getActive()==true)) {
            	 // Get the next character in the turn queue
            	Characters currentCharacter = turnQueue.poll();
            	
            	// If the character is alive, let them take their turn
            	if (currentCharacter.getStats().getHealth() > 0) {
            		System.out.println(currentCharacter + "'s turn");
            		if (currentCharacter instanceof Player) {
            			currentCharacter.actions(turnQueue); // Player chooses actions manually
            		}// End nested if 
            		else {
                    currentCharacter.autoActions(turnQueue); // Enemy performs auto-actions
            		}// End nested else
            	}// End if
            	System.out.println("----------------");
            	// If the character is still alive, add them back to the queue
            	if (currentCharacter.getStats().getHealth() > 0) {
            		turnQueue.offer(currentCharacter); // Add back to the queue if still alive
            	}// End If
            }// End Else 
            System.out.println("Play again?(Y/N)");
            String response = scnr.next();
            if (response.equals("N")){
            	playAgain=false;
            }else {
            // Reset player health if they want to play again
            	if (player1.getStats().getHealth() <= 0) {
            		player1.getStats().setHealth(100); // Reset player health
            		System.out.println(player1.getStats().getName() + "'s health has been restored!");
            	}// End if
        	}// End Else
        }// End of Game
        System.out.println("Game Over");
	}// End Main
}// End Class
