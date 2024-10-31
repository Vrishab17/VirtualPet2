/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
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

    private final Pet pet;
    private final PetInfoPanel petInfoPanel;
    private Timer statDecayTimer;

    public StatDecayTimer(Pet pet, PetInfoPanel petInfoPanel) {
        this.pet = pet;
        this.petInfoPanel = petInfoPanel;
        startStatDecay();
    }

    private void startStatDecay() {
        // Set up a Swing Timer to decay stats every 30 seconds
        statDecayTimer = new Timer(500, (ActionEvent e) -> {
            decayStats();
            petInfoPanel.updatePetInfo(pet); // Update GUI to reflect new stats
        });
        statDecayTimer.start();
    }

    private void decayStats() {
        // Decrease hunger, fun, and sleep by a small amount
        Hunger hunger = new Hunger(pet, PetType.convertStringToPetType(pet.getPetType()));
        Fun fun = new Fun(pet, PetType.convertStringToPetType(pet.getPetType()));
        Sleep sleep = new Sleep(pet, PetType.convertStringToPetType(pet.getPetType()));

        hunger.decreaseHunger(pet);
        fun.decreaseFun(pet);
        sleep.decreaseSleep(pet);

        try {
            PetSave.savePet(pet); // Save updated stats to the database
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating pet stats: " + ex.getMessage());
        }
    }

    public void stop() {
        if (statDecayTimer != null) {
            statDecayTimer.stop();
        }
    }
}
