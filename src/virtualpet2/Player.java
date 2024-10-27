/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrish
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Player implements Serializable {
    private String playerName;  // The name of the player
    private final LinkedList<Pet> pets;  // The list of pets owned by the player

    // Constructor to initialize the player with a name and an empty list of pets
    public Player(String playerName) {
        this.playerName = playerName;
        this.pets = new LinkedList<>();
    }

    // Getter for playerName
    public String getPlayerName() {
        return playerName;
    }

    // Setter for playerName
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        // Update the owner name in all pets
        for (Pet pet : pets) {
            pet.setOwner(playerName);
        }
    }

    // Method to add a pet to the player's list of pets
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this.playerName); // Ensure the pet's owner name is up to date
    }

    // Method to get a specific pet by its name
    public Pet getPet(String petName) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(petName)) {
                return pet;
            }
        }
        return null;  // Return null if no pet with the given name is found
    }

    // Method to get the list of pets owned by the player
    public LinkedList<Pet> getPets() {
        // Return a list of pets that are owned by the player
        return pets.stream()
                   .filter(pet -> pet.getOwner().equals(this.playerName))
                   .collect(Collectors.toCollection(LinkedList::new));
    }
}

