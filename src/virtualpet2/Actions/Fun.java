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
public class Fun {

    private double funLevel; // The current level of fun the pet has
    private final double funRate; // The rate at which the pet loses fun over time
    private static final int MAX_FUN = 100; // The maximum level of fun
    private static final int MIN_FUN = 0; // The minimum level of fun

    // Constructor that initializes the fun level and the rate at which it decreases
    public Fun(Pet pet, PetType petType) {
        this.funLevel = pet.getFun(); // Initialize fun level from the pet's current fun level
        this.funRate = petType.getPlayRate(); // Set the rate at which the pet loses fun based on its type
    }

    // Getter method for the current fun level
    public double getFunLevel() {
        return funLevel;
    }

    // Method to decrease the pet's fun level over time
    public void decreaseFun(Pet pet) {
        if (funLevel > MIN_FUN) {
            // Decrease the fun level by the fun rate, ensuring it doesn't go below the minimum
            funLevel = Math.max(funLevel - funRate, MIN_FUN);
            // Print the updated fun level and prompt the user to play with the pet
            System.out.println("\nFun level decreased. Current fun: " + funLevel );
            pet.setFun(funLevel); // Update the pet's fun level
        }

        // If the fun level has reached the minimum, notify the user that the pet is bored
        if (funLevel == MIN_FUN) {
            
        }
    }

    // Method to play with the pet, increasing its fun level
    public void playWithPet(Pet pet) {
        if (funLevel == MAX_FUN) {
            // If the fun level is already at its maximum, inform the user that the pet is already entertained
            
        } else {
            // Increase the fun level, ensuring it doesn't exceed the maximum
            funLevel = Math.min(funLevel + 20, MAX_FUN);
            // Print the updated fun level after playing with the pet
            
            pet.setFun(funLevel); // Update the pet's fun level
        }
    }

    // Method to check if the pet is bored (i.e., if the fun level is at its minimum)
    public boolean isBored() {
        return funLevel <= MIN_FUN;
    }
}