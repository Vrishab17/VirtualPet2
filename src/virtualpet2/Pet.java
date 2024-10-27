/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrish
 *
 *
 */
import java.io.Serializable;

public class Pet extends PetData implements Serializable {

    /**
     * Constructor for the Pet class. Initializes a Pet object with the specified
     * attributes and checks that the pet type is not null or empty.
     *
     * @param name The name of the pet
     * @param hunger The hunger level of the pet
     * @param age The age of the pet
     * @param fun The fun level of the pet
     * @param sleep The sleep level of the pet
     * @param petType The type of the pet (e.g., Cat, Dog, Bird)
     * @param owner The name of the pet's owner
     */
    public Pet(String name, int hunger, int age, int fun, int sleep, String petType, String owner) {
        // Call the constructor of the superclass (PetData) to initialize the pet's data
        super(name, hunger, age, fun, sleep, petType, owner);

        // Validate that the petType is not null or empty
        if (petType == null || petType.isEmpty()) {
            // If the pet type is invalid, throw an exception
            throw new IllegalArgumentException("Pet type cannot be null or empty.");
        }
    }

    /**
     * Overrides the toString method to provide a simple string representation
     * of the pet. This method only returns the pet's name, which can be used
     * when listing pets or in other simple contexts.
     *
     * @return A string representation of the pet, showing only the pet's name
     */
    @Override
    public String toString() {
        // Return the pet's name in a formatted string
        return "Pet Name: " + getName();
    }

}
