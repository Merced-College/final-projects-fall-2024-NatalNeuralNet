package finalCPSC39;

import java.util.Scanner;
import java.util.Queue;

/**
 * Parent class for game characters. Represents shared attributes and methods.
 * Includes attack logic, auto-actions, and player actions.
 */
public class Characters {
	
	// Global Scanner for user input
	public static Scanner scnr = new Scanner(System.in);
	
    /**
     * ATTRIBUTES:
     * - active (boolean): Tracks whether the character is still active in the game (e.g., alive).
     * - stats (Stats): Object containing the character's health, attack, defense, and name.
     * - inventory (Inventory): Object containing the character's items.
     */
	private boolean active;
	protected Stats stats;
	protected Inventory inventory;
		
	// Constructor initializes character's state, stats, and inventory.
	public Characters() {
	this.active = true;
	this.stats = new Stats();
	this.inventory = new Inventory();
	}

    // -------------------
    // METHODS OVERVIEW
    // -------------------
    /**
     * Methods in this class:
     * 
     * 1. **attack(Queue<Characters> allCharacters)**:
     *    - Performs an attack on the first valid target in the queue.
     * 
     * 2. **autoActions(Queue<Characters> allCharacters)**:
     *    - Automates actions for non-player characters, including healing or attacking.
     * 
     * 3. **actions(Queue<Characters> allCharacters)**:
     *    - Handles player-controlled actions, including attacking, using items, or escaping.
     * 
     * 4. **getActive()**:
     *    - Returns whether the character is currently active.
     * 
     * 5. **setActive(boolean active)**:
     *    - Sets the character's active state.
     * 
     * 6. **getStats()**:
     *    - Retrieves the character's stats.
     * 
     * 7. **toString()**:
     *    - Returns the character's name as a string.
     */

    // -------------------
    // METHODS
    // -------------------

    /**
     * Method: attack
     * Description:
     * - The character performs an attack on the first valid target in the queue.
     * - Reduces the target's health based on the character's attack power.
     * - Checks if the target is defeated and deactivates it if necessary.
     * Parameters:
     * - allCharacters (Queue<Characters>): The queue of characters in the game.
     */
	public void attack(Queue<Characters> allCharacters) {
		// Select a target from the queue
	    Characters target = null;
	    
	    // Find the first valid target in the queue
	    for (Characters character : allCharacters) {
	        if (!character.equals(this) && character.getStats().getHealth() > 0) {
	            target = character;
	            break;
	        }// End if
	    }// End for

	    // Check if a valid target is found
	    if (target == null) {
	        System.out.println("No valid targets available to attack.");
	        return; 
	    }// End if

	    // Perform the attack
	    target.getStats().setHealth(target.getStats().getHealth() - this.stats.getAttack());
	    
	    // Display attack information
	    System.out.println(this.stats.getName() + " attacked " + target.getStats().getName() + "! " + 
	                       target.getStats().getName() + "'s new health: " + target.getStats().getHealth());
	    
	    // Check if the target is defeated
	    if (target.getStats().getHealth() <= 0) {
	        target.setActive(false);
	        System.out.println(target.getStats().getName() + " has been defeated!");
	    }
	}
    /**
     * Method: autoActions
     * Description:
     * - This method is used by non-player characters to perform automated actions.
     * - If health is below 25%, the character uses a potion if available.
     * - Otherwise, the character attacks the first valid target.
     * Parameters:
     * - allCharacters (Queue<Characters>): The queue of characters in the game.
     */
	public void autoActions(Queue<Characters> allCharacters) {
		// Check if the enemy is defeated
		if (this.getStats().getHealth() <= 0) {
	        return;
		}

	    // Check if health is below 25% and a potion is available
	    double healthPercentage = (double) this.getStats().getHealth() / 100;
	    if (healthPercentage <= 0.25 && this.inventory.containsItem("Potion")) {
	        System.out.println(this.getStats().getName() + " is using a potion!");
	        
	        // Heal for 25, max 100
	        this.getStats().setHealth(Math.min(this.getStats().getHealth() + 25, 100));
	        
	        // Remove one potion from inventory
	        this.inventory.removeItem("Potion", 1);
	    } else {
	        // Auto-attack the first valid target
	    	attack(allCharacters);
	    }
	}
		
    /**
     * Method: actions
     * Description:
     * - Handles player-controlled actions during their turn.
     * - Allows the player to attack, use items, or escape.
     * - Manages temporary buffs like Excalibur and ensures they expire after use.
     * Parameters:
     * - allCharacters (Queue<Characters>): The queue of characters in the game.
     */
	public void actions(Queue<Characters> allCharacters) {
		// Tracks if the player's turn is complete
		boolean turnCompleted = false;
		// Tracks if a temporary buff is active
	    boolean temporaryBuffActive = false;
	    // Stores the selected item
	    String pickItem ="";

	    while (!turnCompleted) {
	    	// Display available actions
	        System.out.println("Pick an action\nA: Attack\nI: Items\nQ: Escape");
	        String input = scnr.next();
	        char response = Character.toUpperCase(input.charAt(0));

	        switch (response) {
	        	// Attack
	            case 'A':
	                this.attack(allCharacters);
	             // End the turn after attack
	                turnCompleted = true;
	                break;
	            // Items
	            case 'I':
	                System.out.println(this.inventory);
	                System.out.println("Type item to use || B to back");

	                while (true) {
	                	// User Selects an Item
	                    pickItem = scnr.next();
	                    
	                    // Exit inventory menu
	                    if (pickItem.equalsIgnoreCase("b")) {
	                    	System.out.println("Exiting inventory...");
	                        break;
	                    }

	                    if (this.inventory.containsItem(pickItem)) {
	                        // Use the selected item
	                        if (pickItem.equalsIgnoreCase("Potion")) {
	                        	
	                        	// Use Potion
	                            this.inventory.usePotion(this);
	                            // Turn ends after using an item
	                            turnCompleted = true;
	                        } else if (pickItem.equalsIgnoreCase("Excalibur")) {
	                            this.inventory.useExcalibur(this); // Example for another item
	                            // Turn continues with equipped Item
	                            turnCompleted = false; 
	                            // Set buff as active
	                            temporaryBuffActive = true;
	                        }
	                        // Exit the inventory menu after item use
	                        break;
	                    } else {
	                        System.out.println("Item not found in inventory. Please try again or type 'b' to go back.");
	                    }
	                    System.out.println("Type item to use || B to back");
	                }
	                break;
	            
	            // Escape    
	            case 'Q':
	                System.out.println(this.getStats().getName() + " has escaped!");
	                // Deactivate the character
	                this.setActive(false);
	                // End the turn after escaping
	                turnCompleted = true;
	                break;
	             // Invalid input
	            default:
	                System.out.println("Invalid option. Please try again.");
	        }

	        if (!turnCompleted) {
	            System.out.println("You must attack or heal to end your turn.");
	        }
	        
	        // Handle buff expiration
	        if (temporaryBuffActive && turnCompleted) {
	            System.out.println("Temporary Buffs expired");
	            this.getStats().setAttack(this.getStats().getAttack() - 20);
	            temporaryBuffActive = false; // Reset the buff state
	        }
	    }

	}

	//getters
	public boolean getActive() {
		return active;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	//setters
	public void setActive(boolean active) {
		this.active = active;
	}

	
/**
 * Nested static class for character stats. Includes attributes like health, attack, and defense.
*/
	public static class Stats {
		
        /**
         * ATTRIBUTES:
         * - name (String): The name of the character.
         * - player_health (int): The character's current health points (HP).
         * - attack (int): The character's attack power, determines damage dealt.
         * - defense (int): The character's defense stat, reduces incoming damage.
         */
		private
		String name;
		int player_health;
		int attack;
		int defense;
		
		// Constructor initializes stats to default values
		public Stats(){
			this.name = "";
			this.player_health =  0;
			this.attack = 0;
			this.defense = 0;
			
		}
		
		//getters
		public String getName() {
			return name;
		}
		
		public int getHealth() {
			return player_health;
		}
		
		public int getAttack() {
			return attack;
		}
		
		public int getDefense() {
			return defense;
		}
		
		//setters
		public void setName(String name) {
			this.name = name;
		}
		
		public void setHealth(int player_health) {
			this.player_health = player_health;
		}
		
		public void setAttack(int attack) {
			this.attack = attack;
		}
		
		public void setDefense(int defense) {
			this.defense = defense;
		}
	}
	
	@Override
	public String toString() {
		// Returns the character's name.
	    return this.stats.getName();
	}
	
	

}
