/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
 *
 * @author vrishabchetty
 */
import virtualpet2.Actions.Fun;
import virtualpet2.Actions.Sleep;
import virtualpet2.Actions.Hunger;
import virtualpet2.PetTypes.PetType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import virtualpet2.Pet.Pet;
import virtualpet2.Pet.PetSave;
import virtualpet2.Player.Player;
import virtualpet2.VirtualPetApp;

public class VirtualPetGUI extends JFrame {

    private final Player player;
    private Pet selectedPet;

    private final PetSelectionPanel petSelectionPanel;
    private final PetInfoPanel petInfoPanel;
    private final PetActionPanel petActionPanel;
    private JPanel controlPanel;
    private Timer statDecayTimer; // Timer to manage stat decay

    public VirtualPetGUI(Player player) {
        this.player = player;
        this.selectedPet = null;

        setTitle("Virtual Pet Simulator - Select or Create Pet");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initControlPanel();

        petSelectionPanel = new PetSelectionPanel(player, this);
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(petSelectionPanel, BorderLayout.WEST);
        add(petInfoPanel, BorderLayout.CENTER);
        add(petActionPanel, BorderLayout.SOUTH);

        petInfoPanel.setVisible(false);
        petActionPanel.setVisible(false);

    }

    // Constructor that accepts both player and selectedPet
    public VirtualPetGUI(Player player, Pet selectedPet) {
        this.player = player;
        this.selectedPet = selectedPet;

        initControlPanel(); // Initialize the control panel with Logout and Exit buttons

        // Initialize only the necessary panels for direct pet interaction
        petSelectionPanel = null; // Explicitly set to null as it's not used in this constructor
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setTitle("Virtual Pet Simulator - Playing with " + selectedPet.getName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH); // Add the control panel at the top
        add(petInfoPanel, BorderLayout.CENTER);
        add(petActionPanel, BorderLayout.SOUTH);

        // Load the selected pet's information and start the stat decay
        petInfoPanel.updatePetInfo(selectedPet);
        startStatDecay();
    }

    private void initControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));  // Exits the application

        controlPanel.add(logoutButton);
        controlPanel.add(exitButton);

    }

    private void logout() {
        dispose();  // Close the current window
        SwingUtilities.invokeLater(() -> VirtualPetApp.main(new String[]{}));  // Restart the app for a new user
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
        statDecayTimer = new Timer(500, (ActionEvent e) -> {
            if (selectedPet != null) {
                decayStats(selectedPet);
                petInfoPanel.updatePetInfo(selectedPet); // Update GUI to reflect new stats
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

        hunger.decreaseHunger(pet);
        fun.decreaseFun(pet);
        sleep.decreaseSleep(pet);

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
