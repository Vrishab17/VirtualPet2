/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Actions;

import virtualpet2.Pet.Pet;
import virtualpet2.PetTypes.PetType;


/**
 *
 * @author vrishabchetty
 */
public class Sleep {

    // Tracks the current sleep level of the pet (from 0 to 100).
    private double sleepLevel;
    
    // The rate at which the pet gets tired over time (based on pet type).
    private final double sleepRate; 
    
    // Constant for the maximum sleep level the pet can have.
    private static final int MAX_SLEEP = 100;
    
    // Constant for the minimum sleep level (when the pet is fully tired).
    private static final int MIN_SLEEP = 0;

    /**
     * Constructor that initializes the sleep level based on the pet's current
     * state and pet type.
     * 
     * @param pet The pet object for which sleep is being managed.
     * @param petType The type of the pet which affects how fast it gets tired.
     */
    public Sleep(Pet pet, PetType petType) {
        this.sleepLevel = pet.getSleep(); // Initialize the sleep level from the pet object.
        this.sleepRate = petType.getSleepRate(); // Determine how quickly this pet type gets tired.
    }

    /**
     * Returns the current sleep level of the pet.
     * 
     * @return The current sleep level (an integer between 0 and 100).
     */
    public double getSleepLevel() {
        return sleepLevel;
    }

    
     //Decreases the pet's sleep level based on its sleep rate. If the sleep level
  
    public void decreaseSleep(Pet pet) {
        // Ensure that the sleep level does not drop below the minimum value.
        if (sleepLevel > MIN_SLEEP) {
            sleepLevel = Math.max(sleepLevel - sleepRate, MIN_SLEEP); // Decrease sleep level.
            
            // Inform the player that the sleep level has decreased.
            System.out.println("\nSleep level decreased. Current sleep: "+ sleepLevel);
            
            // Update the pet's sleep level.
            pet.setSleep(sleepLevel);
        }

    }

    /**
     * Allows the pet to rest and increases its sleep level.If the pet is fully 
 rested (MAX_SLEEP), it informs the player. Otherwise, the sleep level increase
     * @param pet
     */
    public void putPetToSleep(Pet pet) {
        // If the pet is already fully rested, inform the player.
        if (sleepLevel == MAX_SLEEP) {
        } else {
            // Increase the sleep level when the pet rests, ensuring it doesn't exceed MAX_SLEEP.
            sleepLevel = Math.min(sleepLevel + 20, MAX_SLEEP);
            System.out.println("Your pet has rested. Current sleep: " + sleepLevel);
            
            // Update the pet's sleep level.
            pet.setSleep(sleepLevel);
        }
    }

    /**
     * Checks if the pet is fully tired.This method is used to determine if
 the pet has reached the minimum sleep level.
     * @return 
     */
    public boolean isTired() {
        return sleepLevel <= MIN_SLEEP; // Return true if the pet is fully tired.
    }
}