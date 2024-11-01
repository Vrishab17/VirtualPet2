/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Actions;

import virtualpet2.Pet.Pet;
import virtualpet2.PetTypes.PetType;

/**
 * The Hunger class manages the hunger level of a pet in the Virtual Pet Simulator.
 * It allows for the gradual decrease of hunger over time and includes methods to 
 * feed the pet and check if it is starving. The hunger level is maintained within 
 * defined minimum and maximum limits.
 * 
 * @author vrishabchetty
 */
public class Hunger {

    // Current hunger level of the pet
    private double hungerLevel;

    // Rate at which the pet's hunger level decreases over time
    private final double hungerRate;

    // Constant representing the maximum allowable hunger level
    private static final int MAX_HUNGER = 100;

    // Constant representing the minimum allowable hunger level
    private static final int MIN_HUNGER = 0;

    /**
     * Constructor for the Hunger class.
     * Initializes the pet's hunger level from its current state and sets the rate
     * at which hunger increases based on the pet's type.
     *
     * @param pet The pet whose hunger level this instance will manage.
     * @param petType The type of the pet, which determines the hunger decay rate.
     */
    public Hunger(Pet pet, PetType petType) {
        this.hungerLevel = pet.getHunger();  // Set initial hunger level from the pet's current state
        this.hungerRate = petType.getHungerRate();  // Set hunger decay rate according to pet type
    }

    /**
     * Gets the current hunger level of the pet.
     *
     * @return The current hunger level as a double.
     */
    public double getHungerLevel() {
        return hungerLevel;  // Return the current hunger level
    }

    /**
     * Decreases the pet's hunger level over time, simulating the pet getting hungrier.
     * Ensures that the hunger level does not fall below the defined minimum.
     * If the hunger level reaches the minimum, the pet is considered starving.
     *
     * @param pet The pet whose hunger level is being decreased.
     */
    public void decreaseHunger(Pet pet) {
        if (hungerLevel > MIN_HUNGER) {  // Ensure hunger level is above minimum before decreasing
            hungerLevel = Math.max(hungerLevel - hungerRate, MIN_HUNGER);  // Decrease hunger level safely
            pet.setHunger(hungerLevel);  // Update pet's hunger attribute with the new value
        }
    }

    /**
     * Feeds the pet, increasing its hunger level.
     * Ensures that the hunger level does not exceed the maximum threshold.
     *
     * @param pet The pet being fed.
     */
    public void feedPet(Pet pet) {
        if (hungerLevel == MAX_HUNGER) {
        } else {
            // Increase hunger level safely, ensuring it does not exceed the maximum
            hungerLevel = Math.min(hungerLevel + 20, MAX_HUNGER);
            pet.setHunger(hungerLevel);  // Update pet's hunger level
        }
    }

    /**
     * Checks if the pet is starving, which is true if the hunger level is at the minimum.
     *
     * @return true if the pet is starving (hunger level at minimum), otherwise false.
     */
    public boolean isStarving() {
        return hungerLevel <= MIN_HUNGER;  // Returns true if hunger level is at or below the minimum
    }
}
