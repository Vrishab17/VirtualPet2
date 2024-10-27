/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
public class Hunger {

    private int hungerLevel;  // The current hunger level of the pet
    private final int hungerRate;  // The rate at which the pet gets hungry
    private static final int MAX_HUNGER = 100;  // Maximum hunger level
    private static final int MIN_HUNGER = 0;  // Minimum hunger level

    /**
     * Constructor for the Hunger class.
     * Initializes the hunger level based on the pet's current state and sets 
     * the rate at which hunger decreases based on the pet's type.
     *
     * @param pet The pet whose hunger level is being managed
     * @param petType The type of the pet, which determines the hunger rate
     */
    public Hunger(Pet pet, PetType petType) {
        this.hungerLevel = pet.getHunger();  // Initialize hunger level from the pet's current state
        this.hungerRate = petType.getHungerRate();  // Set the hunger rate based on the pet type
    }

    /**
     * Gets the current hunger level of the pet.
     *
     * @return The current hunger level
     */
    public int getHungerLevel() {
        return hungerLevel;  // Return the current hunger level
    }

    /**
     * Decreases the pet's hunger level over time.
     * Ensures that the hunger level does not go below the minimum threshold.
     * If the hunger level reaches the minimum, the pet is considered starving.
     *
     * @param pet The pet whose hunger level is being decreased
     */
    public void decreaseHunger(Pet pet) {
        if (hungerLevel > MIN_HUNGER) {  // Check if the hunger level is above the minimum
            hungerLevel = Math.max(hungerLevel - hungerRate, MIN_HUNGER);  // Decrease hunger but not below MIN_HUNGER
            System.out.println(Art.YELLOW + "\nHunger level decreased. Current hunger: " + hungerLevel + Art.RESET + "\nPress 1 to Feed\n");
            pet.setHunger(hungerLevel);  // Update the pet's hunger level
        }

        if (hungerLevel == MIN_HUNGER) {  // Check if the pet is now starving
            System.out.println(Art.RED + pet.getName() + " is starving!" + Art.RESET + "\nPress 1 to Feed\n");
        }
    }

    /**
     * Feeds the pet, which increases its hunger level.
     * Ensures that the hunger level does not exceed the maximum threshold.
     *
     * @param pet The pet being fed
     */
    public void feedPet(Pet pet) {
        if (hungerLevel == MAX_HUNGER) {  // Check if the pet is already full
            System.out.println(Art.RED + pet.getName() + " Is Full!" + Art.RESET);
        } else {
            hungerLevel = Math.min(hungerLevel + 20, MAX_HUNGER);  // Increase hunger but not above MAX_HUNGER
            System.out.println(Art.GREEN + "Pet has been fed. Current hunger: " + hungerLevel + "\n" + Art.RESET);
            pet.setHunger(hungerLevel);  // Update the pet's hunger level
        }
    }

    /**
     * Checks if the pet is starving, which is true if the hunger level is at the minimum.
     *
     * @return True if the pet is starving, otherwise false
     */
    public boolean isStarving() {
        return hungerLevel <= MIN_HUNGER;  // Return true if hunger is at or below the minimum
    }
}