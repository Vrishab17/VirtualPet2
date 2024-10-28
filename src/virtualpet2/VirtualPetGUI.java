/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class VirtualPetGUI extends JFrame {

    private final Player player;
    private Pet selectedPet;

    private final PetSelectionPanel petSelectionPanel;
    private final PetInfoPanel petInfoPanel;
    private final PetActionPanel petActionPanel;
    private Timer statDecayTimer; // Timer to manage stat decay

    public VirtualPetGUI(Player player) {
        this.player = player;
        setTitle("Virtual Pet Simulator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        petSelectionPanel = new PetSelectionPanel(player, this);
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setLayout(new BorderLayout());
        add(petSelectionPanel, BorderLayout.NORTH);
        add(petInfoPanel, BorderLayout.CENTER);
        add(petActionPanel, BorderLayout.SOUTH);

        // Start the stat decay timer if a pet is selected
        startStatDecay();
    }

    // Selects a pet and updates the information panel
    public void selectPet(String petName) {
        selectedPet = player.getPet(petName);
        petInfoPanel.updatePetInfo(selectedPet);
    }

    // Starts the stat decay timer to decrease hunger, fun, and sleep
    private void startStatDecay() {
        // Set up a Swing Timer to decay stats every 30 seconds
        statDecayTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPet != null) {
                    decayStats(selectedPet);
                    petInfoPanel.updatePetInfo(selectedPet); // Update GUI to reflect new stats
                }
            }
        });
        statDecayTimer.start();
    }

    // Decays the stats of the pet and saves updates to the database
    private void decayStats(Pet pet) {
        // Decrease hunger, fun, and sleep by a small amount
        Hunger hunger = new Hunger(pet, PetType.convertStringToPetType(pet.getPetType()));
        Fun fun = new Fun(pet, PetType.convertStringToPetType(pet.getPetType()));
        Sleep sleep = new Sleep(pet, PetType.convertStringToPetType(pet.getPetType()));

        hunger.decreaseHunger(pet); // Decrease hunger
        fun.decreaseFun(pet);       // Decrease fun
        sleep.decreaseSleep(pet);    // Decrease sleep

        try {
            PetSave.savePet(pet); // Save updated stats to the database
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating pet stats: " + ex.getMessage());
        }
    }

    // Stop the timer when the window is closed
    @Override
    public void dispose() {
        if (statDecayTimer != null) {
            statDecayTimer.stop();
        }
        super.dispose();
    }

    public Pet getSelectedPet() {
        return selectedPet;
    }

    public void refreshPetInfo() {
        petInfoPanel.updatePetInfo(selectedPet);
    }
}