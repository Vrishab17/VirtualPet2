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
        this.selectedPet = null; // No pet selected yet

        setTitle("Virtual Pet Simulator - Select or Create Pet");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        petSelectionPanel = new PetSelectionPanel(player, this); // Initialize pet selection panel
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setLayout(new BorderLayout());
        add(petSelectionPanel, BorderLayout.NORTH);
        add(petInfoPanel, BorderLayout.CENTER);
        add(petActionPanel, BorderLayout.SOUTH);

        // Hide pet info and action panels until a pet is selected
        petInfoPanel.setVisible(false);
        petActionPanel.setVisible(false);

        checkAndPromptForPet();
    }

    // Constructor that accepts both player and selectedPet
    public VirtualPetGUI(Player player, Pet selectedPet) {
        this.player = player;
        this.selectedPet = selectedPet;
        
        // Initialize only the necessary panels for direct pet interaction
        petSelectionPanel = null; // Explicitly set to null as it's not used in this constructor
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setTitle("Virtual Pet Simulator - Playing with " + selectedPet.getName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(petInfoPanel, BorderLayout.CENTER);
        add(petActionPanel, BorderLayout.SOUTH);

        // Load the selected pet's information and start the stat decay
        petInfoPanel.updatePetInfo(selectedPet);
        startStatDecay();
    }


    // Method to check if the player has pets and prompt to create or play with a pet
    private void checkAndPromptForPet() {
        if (player.getPets().isEmpty()) {
            int option = JOptionPane.showOptionDialog(this,
                    "You have no pets. Would you like to create a new pet?",
                    "No Pets Found",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Create New Pet", "Exit"},
                    "Create New Pet");

            if (option == JOptionPane.YES_OPTION) {
                createNewPet();
            } else {
                System.exit(0); // Exit if the user doesn't want to create a new pet
            }
        } else {
            int option = JOptionPane.showOptionDialog(this,
                    "Would you like to create a new pet or play with an existing one?",
                    "Select Action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Create New Pet", "Play with Existing Pet"},
                    "Play with Existing Pet");

            if (option == JOptionPane.YES_OPTION) {
                createNewPet();
            }
        }
    }

// Method to create a new pet and add it to the player's list
private void createNewPet() {
    String petName = JOptionPane.showInputDialog(this, "Enter a name for your new pet:");
    if (petName != null && !petName.trim().isEmpty()) {
        String[] petTypes = {"Cat", "Dog", "Bird"};
        String petType = (String) JOptionPane.showInputDialog(this, "Select pet type:",
                "Pet Type", JOptionPane.QUESTION_MESSAGE, null, petTypes, petTypes[0]);

        if (petType != null) {
            try {
                // Create a new pet and assign it default values
                Pet newPet = new Pet(0, petName.trim(), 100, 0, 100, 100, petType, player.getPlayerId());
                player.addPet(newPet); // Add to player's list
                PetSave.savePet(newPet); // Save the new pet to the database

                // Refresh the pet list without selecting any pet
                petSelectionPanel.loadPetList(player); // Reload the pet list in the dropdown

                // Set selectedPet to null to ensure no pet is selected by default
                selectedPet = null;
                petInfoPanel.clearInfo(); // Clear displayed pet info

                // Inform the user that the pet has been created successfully
                JOptionPane.showMessageDialog(this, "New pet created successfully. Please select it from the dropdown.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error creating pet: " + e.getMessage());
            }
        }
    }
}




    // Selects a pet and updates the information panel
public void selectPet(String petName) {
    selectedPet = player.getPet(petName);
    if (selectedPet != null) {
        petInfoPanel.updatePetInfo(selectedPet);
    } else {
        petInfoPanel.clearInfo(); // Clear info if no valid pet is selected
    }
}



    // Starts the stat decay timer to decrease hunger, fun, and sleep
    private void startStatDecay() {
        // Set up a Swing Timer to decay stats every 30 seconds
        statDecayTimer = new Timer(500, new ActionListener() {
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
    if (selectedPet != null) {
        petInfoPanel.updatePetInfo(selectedPet);
    } else {
        petInfoPanel.clearInfo();
    }
}
}