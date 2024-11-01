/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 * StatDecayTimer class is responsible for gradually reducing the pet’s stats
 * (such as hunger, fun, and sleep) over time, simulating the natural decay of
 * these values. It uses a Swing Timer to repeatedly perform this decay and
 * updates the GUI to reflect the pet’s current stats.
 * 
 * @author vrish
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import virtualpet2.Actions.Fun;
import virtualpet2.Actions.Hunger;
import virtualpet2.Actions.Sleep;
import virtualpet2.GUI.PetInfoPanel;
import virtualpet2.Pet.Pet;
import virtualpet2.Pet.PetSave;
import virtualpet2.PetTypes.PetType;

public class StatDecayTimer {

    // Reference to the Pet instance whose stats are decaying
    private final Pet pet;

    // Reference to the PetInfoPanel instance to update the pet's displayed stats
    private final PetInfoPanel petInfoPanel;

    // Timer that controls the rate of stat decay
    private Timer statDecayTimer;

    /**
     * Constructor for StatDecayTimer. Initializes the pet and petInfoPanel instances
     * and starts the timer for stat decay.
     * 
     * @param pet The pet whose stats will decay over time.
     * @param petInfoPanel The panel displaying the pet’s stats in the GUI.
     */
    public StatDecayTimer(Pet pet, PetInfoPanel petInfoPanel) {
        this.pet = pet;
        this.petInfoPanel = petInfoPanel;
        startStatDecay(); // Begin the decay process
    }

    /**
     * Initializes the stat decay timer. This Swing Timer triggers the decayStats() 
     * method every 500 milliseconds, or half a second. Each time decayStats() is called,
     * it reduces the pet's stats and updates the GUI to reflect the changes.
     */
    private void startStatDecay() {
        // Set up a Swing Timer to decay stats every 500 milliseconds
        statDecayTimer = new Timer(500, (ActionEvent e) -> {
            decayStats(); // Decrement pet stats
            petInfoPanel.updatePetInfo(pet); // Update GUI to reflect new stats
        });
        statDecayTimer.start(); // Start the timer
    }

    /**
     * Method to apply decay to the pet's stats. This method reduces the pet's
     * hunger, fun, and sleep levels by calling their respective decrease methods.
     * Once the stats are modified, it saves the updated stats to the database.
     * If an error occurs during saving, an error message is shown in a dialog box.
     */
    private void decayStats() {
        // Initialize stat objects with pet's type to control specific decays
        Hunger hunger = new Hunger(pet, PetType.convertStringToPetType(pet.getPetType()));
        Fun fun = new Fun(pet, PetType.convertStringToPetType(pet.getPetType()));
        Sleep sleep = new Sleep(pet, PetType.convertStringToPetType(pet.getPetType()));

        // Decrease hunger, fun, and sleep stats based on pet’s needs
        hunger.decreaseHunger(pet);
        fun.decreaseFun(pet);
        sleep.decreaseSleep(pet);

        try {
            // Save updated stats to the database
            PetSave.savePet(pet);
        } catch (SQLException ex) {
            // Show an error dialog if saving fails
            JOptionPane.showMessageDialog(null, "Error updating pet stats: " + ex.getMessage());
        }
    }

    /**
     * Stops the stat decay timer, pausing any further decay.
     * Useful for situations where the decay needs to be paused or halted.
     */
    public void stop() {
        if (statDecayTimer != null) {
            statDecayTimer.stop(); // Stop the timer if it’s running
        }
    }
}
