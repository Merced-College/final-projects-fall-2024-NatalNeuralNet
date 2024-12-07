package finalCPSC39;

import java.util.Queue;

/**
 * Player Class
 * A child class of Characters, representing the player-controlled character.
 * Inherits attributes and methods from the Characters class, with additional features like a special attack.
 */
public class Player extends Characters{
	
    
     //Constructor
	public Player() {
		// Call the parent class (Characters) constructor
		super();
		// Set Stats for default Player objects
		this.getStats().setAttack(20);
		this.getStats().setHealth(100);
		
		// Add default items to the inventory
        this.inventory.addPotion(3);
        this.inventory.addWeapon("Excalibur", "Legendary", 1);
	}
	
    /**
     * Method: specialAttack
     * Description:
     * - Performs a special attack, doubling the player's attack power temporarily.
     * - Calls the attack method to deal damage to an enemy.
     * Parameters:
     * - allCharacters (Queue<Characters>): The queue of characters in the game.
     */
	
	public void specialAttack(Queue<Characters> allCharacters) {
		System.out.println(this.stats.getName()+" used a special attack, double attack");
		this.stats.setAttack(this.stats.getAttack() * 2);
		
		this.attack(allCharacters);
	
	}

}
