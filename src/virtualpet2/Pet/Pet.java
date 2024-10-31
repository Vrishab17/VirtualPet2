/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Pet;

/**
 *
 * @author vrish
 *
 *
 */
import java.io.Serializable;

public class Pet extends PetData implements Serializable {

    /**
     * Constructor for the Pet class.
     * Initializes a Pet object with the specified attributes.
     *
     * @param petId   The unique ID of the pet
     * @param name    The name of the pet
     * @param hunger  The hunger level of the pet
     * @param age     The age of the pet
     * @param fun     The fun level of the pet
     * @param sleep   The sleep level of the pet
     * @param petType The type of the pet (e.g., Cat, Dog, Bird)
     * @param ownerId The ID of the pet's owner
     */
    public Pet(int petId, String name, int hunger, int age, int fun, int sleep, String petType, int ownerId) {
        super(petId, name, hunger, age, fun, sleep, petType, ownerId);
    }

    /**
     * Overrides the toString method to provide a simple string representation
     * of the pet, showing only the pet's name and type.
     *
     * @return A string representation of the pet
     */
    @Override
    public String toString() {
        return "Pet Name: " + getName() + ", Type: " + getPetType();
    }
}
