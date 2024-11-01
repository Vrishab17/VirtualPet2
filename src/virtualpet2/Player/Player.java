/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Player;

/**
 *
 * @author vrish
 */


import virtualpet2.Pet.Pet;
import java.io.Serializable;
import java.util.LinkedList;

public class Player implements Serializable {
    private int playerId;         // Unique ID for the player
    private String playerName;    // Name of the player
    private final LinkedList<Pet> pets;  // List of pets owned by the player

    // Constructor to initialize the player with an ID, name, and an empty list of pets
    public Player(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.pets = new LinkedList<>();
    }

    // Getter for playerId
    public int getPlayerId() {
        return playerId;
    }

    // Setter for playerId
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    // Getter for playerName
    public String getPlayerName() {
        return playerName;
    }

    // Setter for playerName
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // Method to add a pet to the player's list of pets
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwnerId(this.playerId); // Update the pet’s owner ID to match the player ID
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
        return pets;
    }
}

