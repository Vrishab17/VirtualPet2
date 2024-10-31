/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author vrish
 */
public class PetSelectionFrame extends JFrame {

    private final Player player;
    private JComboBox<String> petDropdown;
    private JButton selectPetButton;
    private JButton createPetButton;

    public PetSelectionFrame(Player player) {
        this.player = player;

        setTitle("Select Pet for " + player.getPlayerName());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        // Dropdown for selecting existing pets
        petDropdown = new JComboBox<>();
        loadPetList();
        add(new JLabel("Select Pet:"));
        add(petDropdown);

        // Button to select an existing pet
        selectPetButton = new JButton("Play with Selected Pet");
        selectPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPetName = (String) petDropdown.getSelectedItem();
                if (selectedPetName != null && !selectedPetName.isEmpty()) {
                    openMainFrameWithSelectedPet(selectedPetName);
                } else {
                    JOptionPane.showMessageDialog(PetSelectionFrame.this, "Please select a pet.");
                }
            }
        });
        add(selectPetButton);

        // Button to create a new pet
        createPetButton = new JButton("Create New Pet");
        createPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewPet();
            }
        });
        add(createPetButton);
    }

    // Load pets from player's list into the dropdown
    private void loadPetList() {
        petDropdown.removeAllItems();
        for (Pet pet : player.getPets()) {
            petDropdown.addItem(pet.getName());
        }
    }

    // Opens the main VirtualPetGUI frame with the selected pet
// Inside PetSelectionFrame class
private void openMainFrameWithSelectedPet(String petName) {
    Pet selectedPet = player.getPet(petName);
    if (selectedPet != null) {
        VirtualPetGUI mainFrame = new VirtualPetGUI(player, selectedPet); // Use new constructor
        mainFrame.setVisible(true);
        dispose(); // Close the selection frame
    } else {
        JOptionPane.showMessageDialog(this, "Error: Pet not found.");
    }
}


    // Create a new pet and add it to the player's list
    private void createNewPet() {
        String petName = JOptionPane.showInputDialog(this, "Enter a name for your new pet:");
        if (petName != null && !petName.trim().isEmpty()) {
            String[] petTypes = {"Cat", "Dog", "Bird"};
            String petType = (String) JOptionPane.showInputDialog(this, "Select pet type:",
                    "Pet Type", JOptionPane.QUESTION_MESSAGE, null, petTypes, petTypes[0]);

            if (petType != null) {
                try {
                    // Create the new pet and add it to the database
                    Pet newPet = new Pet(0, petName.trim(), 100, 0, 100, 100, petType, player.getPlayerId());
                    player.addPet(newPet);
                    PetSave.savePet(newPet);

                    // Reload the pet list in the dropdown
                    loadPetList();
                    JOptionPane.showMessageDialog(this, "New pet created. Select it from the dropdown.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error creating pet: " + e.getMessage());
                }
            }
        }
    }
}
