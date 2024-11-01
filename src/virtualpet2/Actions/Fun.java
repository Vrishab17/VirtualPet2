/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Actions;

import virtualpet2.Pet.Pet;
import virtualpet2.PetTypes.PetType;

/**
 * Fun class represents the fun or entertainment level of a pet within the Virtual Pet Simulator.
 * It contains methods to increase or decrease the pet’s fun level, ensuring that it remains within
 * the specified limits, and includes logic to inform the user if the pet is bored or fully entertained.
 * 
 * @author vrishabchetty
 */
public class Fun {

    // Current fun level of the pet
    private double funLevel; 

    // Rate at which the pet's fun level decreases over time
    private final double funRate;

    // Constant representing the maximum allowed fun level
    private static final int MAX_FUN = 100;

    // Constant representing the minimum allowed fun level
    private static final int MIN_FUN = 0;

    /**
     * Constructor to initialize a Fun object for a specific pet.
     * Sets the initial fun level based on the pet's current fun value and
     * assigns a fun decay rate according to the pet's type.
     * 
     * @param pet The pet whose fun level this instance will manage.
     * @param petType The type of pet, which determines the rate of fun decrease.
     */
    public Fun(Pet pet, PetType petType) {
        this.funLevel = pet.getFun(); // Initialize fun level from the pet's current fun level
        this.funRate = petType.getPlayRate(); // Set the decay rate based on the pet's type
    }

    /**
     * Getter method to retrieve the current fun level of the pet.
     * 
     * @return The current fun level as a double.
     */
    public double getFunLevel() {
        return funLevel;
    }

    /**
     * Method to decrease the pet's fun level over time. This simulates the pet
     * gradually getting bored. The method reduces the fun level by a specific rate
     * but does not allow it to fall below the defined minimum.
     * 
     * @param pet The pet whose fun level is being managed.
     */
    public void decreaseFun(Pet pet) {
        if (funLevel > MIN_FUN) {
            // Reduce fun level by the fun rate, ensuring it doesn’t go below the minimum
            funLevel = Math.max(funLevel - funRate, MIN_FUN);
            pet.setFun(funLevel); // Update pet's fun level in the pet object
        }
    }

    /**
     * Method to play with the pet, which increases its fun level. If the fun level
     * is already at the maximum, the user is informed that the pet is fully entertained.
     * Otherwise, this method increases the fun level but keeps it within the allowed maximum.
     * 
     * @param pet The pet with which the user is interacting.
     */
    public void playWithPet(Pet pet) {
        if (funLevel == MAX_FUN) {
        } else {
            // Increase the fun level by a set amount, up to the maximum limit
            funLevel = Math.min(funLevel + 20, MAX_FUN);
            pet.setFun(funLevel); // Update pet's fun level in the pet object
        }
    }

    /**
     * Method to check if the pet is bored, defined as the fun level reaching the minimum.
     * This method helps determine if user interaction is required to increase the fun level.
     * 
     * @return true if the pet is bored (fun level at minimum), false otherwise.
     */
    public boolean isBored() {
        return funLevel <= MIN_FUN;
    }
}
