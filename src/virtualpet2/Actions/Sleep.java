/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Actions;

import virtualpet2.Pet.Pet;
import virtualpet2.PetTypes.PetType;

/**
 * Sleep class manages the sleep level of a pet in the Virtual Pet Simulator.
 * This class handles the pet’s sleep level, allowing for both gradual
 * decreases in sleep as the pet becomes tired and increases when the pet
 * rests. The sleep level is kept within a defined minimum and maximum.
 * 
 * @author vrishabchetty
 */
public class Sleep {

    // Tracks the current sleep level of the pet (0 = fully tired, 100 = fully rested).
    private double sleepLevel;
    
    // The rate at which the pet’s sleep level decreases, based on its type.
    private final double sleepRate; 
    
    // Maximum sleep level for the pet.
    private static final int MAX_SLEEP = 100;
    
    // Minimum sleep level, indicating the pet is fully tired.
    private static final int MIN_SLEEP = 0;

    /**
     * Constructor that initializes the pet's sleep level from its current state
     * and sets the rate of sleep decay based on the pet's type.
     * 
     * @param pet The pet whose sleep level will be managed.
     * @param petType The type of pet, which affects the sleep decay rate.
     */
    public Sleep(Pet pet, PetType petType) {
        this.sleepLevel = pet.getSleep(); // Initialize the sleep level from the pet’s current state.
        this.sleepRate = petType.getSleepRate(); // Set the sleep decay rate based on pet type.
    }

    /**
     * Retrieves the current sleep level of the pet.
     * 
     * @return The pet’s current sleep level as a double.
     */
    public double getSleepLevel() {
        return sleepLevel;
    }

    /**
     * Decreases the pet's sleep level gradually, simulating the pet becoming tired.
     * This decrease respects the minimum sleep level, ensuring it doesn’t go below zero.
     * 
     * @param pet The pet whose sleep level is being decreased.
     */
    public void decreaseSleep(Pet pet) {
        // Ensure that the sleep level does not fall below the minimum value.
        if (sleepLevel > MIN_SLEEP) {
            sleepLevel = Math.max(sleepLevel - sleepRate, MIN_SLEEP); // Safely decrease sleep level.
            
            pet.setSleep(sleepLevel); // Update the pet’s sleep level
        }
    }

    /**
     * Increases the pet's sleep level to simulate rest, up to the defined maximum.
     * If the pet is already fully rested, it informs the user; otherwise, it
     * increases the sleep level without exceeding the maximum.
     * 
     * @param pet The pet that is resting to increase its sleep level.
     */
    public void putPetToSleep(Pet pet) {
        // Check if the pet is already fully rested.
        if (sleepLevel == MAX_SLEEP) {
        } else {
            // Increase the sleep level, ensuring it does not exceed MAX_SLEEP.
            sleepLevel = Math.min(sleepLevel + 20, MAX_SLEEP);
            
            pet.setSleep(sleepLevel); // Update the pet’s sleep level after resting
        }
    }

    /**
     * Checks if the pet is fully tired, determined by the sleep level reaching the minimum.
     * Useful to prompt the user that the pet needs rest.
     * 
     * @return true if the pet is fully tired, false otherwise.
     */
    public boolean isTired() {
        return sleepLevel <= MIN_SLEEP; // Returns true if the pet needs rest.
    }
}
