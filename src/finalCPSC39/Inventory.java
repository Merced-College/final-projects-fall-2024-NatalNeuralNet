package finalCPSC39;
import java.util.HashMap;
import java.util.Map;



public class Inventory {
	
	// Map, stores items by type; nested by map of items and their quantities
	private Map<String, Map<Item, Integer>> categories;
	
	// Constructor initializes the categories map
	public Inventory() {
		categories = new HashMap<>();
	}
	
	public void addPotion(int quantity) {
		Item potion = new Item();
        potion.setName("Potion");
        potion.setType("Potion");
        potion.setRarity("Common");
        this.addItem(potion, quantity);
		
	}
	
	public void addWeapon(String name, String rarity, int quantity) {
		Item potion = new Item();
        potion.setName(name);
        potion.setType("Weapon");
        potion.setRarity(rarity);
        this.addItem(potion, quantity);
		
	}
	
	public void usePotion(Characters target) {
		
	    // Check if a potion exists in the inventory
	    if (this.containsItem("Potion")) {
	        // Heal the character
	    	int healAmount = 20;
	    	int targetHp= target.getStats().getHealth();
	    	
	    	if(targetHp<100) {
	        target.getStats().setHealth(targetHp + healAmount);
	    	}
	    	else {
	    		target.getStats().setHealth(100);
	    	}
	        // Remove the potion
	        this.removeItem("Potion", 1);
	        System.out.println(target.getStats().getName() + " used a Potion! Restored 20 HP.");
	        System.out.println("Remaining HP: " + target.getStats().getHealth() + "/100");
	    } else {
	        System.out.println("No potions left to use.");
	    }
	}
	
	public void useExcalibur(Characters target) {
	    // Check if a potion exists in the inventory
	    if (this.containsItem("Excalibur")) {
	        // Heal the character
	    	int damageIncrease = 20;
	    	int targetAtk = target.getStats().getAttack();
	    	

	        target.getStats().setAttack(targetAtk + damageIncrease);
	    	}

	        // Remove the potion
	        this.removeItem("Excalibur", 1);
	        System.out.println(target.getStats().getName() + " equiped Excalibur! +20 Atk.");
	        System.out.println("New Attack: " + target.getStats().getAttack());
	    } 
	
	
    /**
     * Adds an item to the inventory.
     * If the item type doesn't exist, a new category is created.
     * If the item already exists in the category, its quantity is updated.
     * @param item the item to add
     * @param quantity the quantity to add
     */
	public void addItem(Item item, int quantity) {
		categories.computeIfAbsent(item.getType(), k -> new HashMap<>()).merge(item, quantity, Integer::sum);
	
	}
	

	/**
	 * Removes a specified quantity of an item from the inventory.
	 * If the item's quantity becomes zero or less, it is removed from the category.
	 * @param name the name of the item to remove
	 * @param quantity the quantity to remove
	 */
	public void removeItem(String name, int quantity) {
	    for (Map.Entry<String, Map<Item, Integer>> categoryEntry : categories.entrySet()) {
	        Map<Item, Integer> items = categoryEntry.getValue();
	        // Iterate through the items in this category
	        for (Map.Entry<Item, Integer> itemEntry : items.entrySet()) {
	            Item currentItem = itemEntry.getKey();
	            int currentQuantity = itemEntry.getValue();

	            if (currentItem.getName().equalsIgnoreCase(name)) {
	                // Update the quantity
	                int newQuantity = currentQuantity - quantity;
	                if (newQuantity > 0) {
	                    items.put(currentItem, newQuantity); // Update the quantity
	                    System.out.println("Removed " + quantity + "" + name + ".");
	                    System.out.println("Remaining " + name + ": " + newQuantity);
	                } else {
	                    items.remove(currentItem); // Remove the item if quantity <= 0
	                    System.out.println("Removed " + quantity  + " " + name + ".");
	                    System.out.println("No " + name + " left in inventory.");
	                }
	                // Remove the category if it is empty
	                if (items.isEmpty()) {
	                    categories.remove(categoryEntry.getKey());
	                }
	                return;
	            }
	        }
	    }
	    System.out.println("Item " + name + " not found in inventory.");
	}

		
	
    /**
     * Finds an item by name in the inventory.
     * @param name the name of the item to find
     * @return the Item object if found, or null if not found
     */
    public Item findItemByName(String name) {
        for (Map<Item, Integer> items : categories.values()) {
            for (Item item : items.keySet()) {
                if (item.getName().equalsIgnoreCase(name)) {
                    return item;
                }
            }
        }
        return null; // Item not found
    }

    /**
     * Checks if an item exists in the inventory by name.
     * @param name the name of the item to check
     * @return true if the item exists, false otherwise
     */
    public boolean containsItem(String name) {
        return findItemByName(name) != null;
    }
	
	
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory Contents:\n");
        for (String category : categories.keySet()) {
            sb.append("Category: ").append(category).append("\n");
            for (Map.Entry<Item, Integer> entry : categories.get(category).entrySet()) {
                sb.append("  - ").append(entry.getKey()).append(", Quantity: ").append(entry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
	
	public static class Item{
		
		// Class Attributes 
		private String name;
		private String type;
		private String rarity;
		
		// Constructor
		public Item () {
			this.name = "";
			this.type = "";
			this.rarity = "";
		}
		
		// Acessors (Getters)
		public String getName() {
			return name;
		}
		public String getType() {
			return type;
		}
		public String getRarity() {
			return rarity;
		}
		
		// Mutators (Setters)
		public void setName(String name) {
			this.name = name;
		}
		public void setType(String type) {
			this.type = type;	
		}
		public void setRarity(String rarity) {
			this.rarity = rarity;
		}
		
		@Override
		public String toString() {
			return name + " (Type: " + type +  ", Rarity: " + rarity + ")";
		}
		
	}
}
