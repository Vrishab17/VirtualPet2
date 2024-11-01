/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
 * PetSelectionFrame class provides a JFrame GUI for selecting or creating a pet.
 * It allows the player to select an existing pet from a drop down list or create a new pet,
 * displaying options to choose pet type and assign a name. This frame serves as the entry point 
 * to open the main game frame for interacting with the selected pet.
 * 
 * @author vrish
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import virtualpet2.Pet.Pet;
import virtualpet2.Pet.PetSave;
import virtualpet2.Player.Player;

public class PetSelectionFrame extends JFrame {

    // The player for whom this pet selection screen is displayed
    private final Player player;

    // Dropdown for selecting an existing pet
    private JComboBox<String> petDropdown;

    // Buttons for selecting or creating a pet
    private final JButton selectPetButton;
    private final JButton createPetButton;

    /**
     * Constructor for PetSelectionFrame.
     * Sets up the main frame properties, initializes components, and configures
     * the layout and action listeners for interacting with pets.
     * 
     * @param player The player object to access the player's pets and manage pet selection.
     */
    public PetSelectionFrame(Player player) {
        this.player = player;

        setTitle("Select Pet for " + player.getPlayerName());
        setPreferredSize(new Dimension(200, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Main panel with vertical alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel for pet selection dropdown
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        petDropdown = new JComboBox<>();
        loadPetList(); // Load pets from player's list
        selectionPanel.add(new JLabel("Select Pet:"));
        selectionPanel.add(petDropdown);
        selectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(selectionPanel);

        // Panel for buttons with vertical alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button to select an existing pet
        selectPetButton = new JButton("Play with Selected Pet");
        selectPetButton.setBackground(new Color(50, 147, 111));
        selectPetButton.setForeground(Color.BLACK);
        selectPetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectPetButton.addActionListener((ActionEvent e) -> {
            String selectedPetName = (String) petDropdown.getSelectedItem();
            if (selectedPetName != null && !selectedPetName.isEmpty()) {
                openMainFrameWithSelectedPet(selectedPetName); // Open main frame with selected pet
            } else {
                JOptionPane.showMessageDialog(PetSelectionFrame.this, "Please select a pet.");
            }
        });
        buttonPanel.add(selectPetButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons

        // Button to create a new pet
        createPetButton = new JButton("Create New Pet");
        createPetButton.setBackground(new Color(34, 116, 165));
        createPetButton.setForeground(Color.BLACK);
        createPetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPetButton.addActionListener((ActionEvent e) -> createNewPet()); // Trigger pet creation
        buttonPanel.add(createPetButton);

        // Add the button panel to main panel
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);
        pack();
    }

    /**
     * Loads the list of the player's pets into the dropdown menu.
     * This method is called when the panel is initialized and after a new pet is created.
     */
    private void loadPetList() {
        petDropdown.removeAllItems();
        for (Pet pet : player.getPets()) {
            petDropdown.addItem(pet.getName()); // Add each pet's name to dropdown
        }
    }

    /**
     * Opens the main VirtualPetGUI frame with the selected pet.
     * Closes the current frame after opening the main frame for interaction.
     * 
     * @param petName The name of the selected pet to load into the main game frame.
     */
    private void openMainFrameWithSelectedPet(String petName) {
        Pet selectedPet = player.getPet(petName);
        if (selectedPet != null) {
            VirtualPetGUI mainFrame = new VirtualPetGUI(player, selectedPet);
            mainFrame.setVisible(true); // Display the main frame
            dispose(); // Close the selection frame
        } else {
            JOptionPane.showMessageDialog(this, "Error: Pet not found.");
        }
    }

    /**
     * Prompts the user to enter a new pet's name and type, creates the pet, 
     * and saves it to the player's list and database. Refreshes the drop down with the new pet.
     */
    public void createNewPet() {
        // Prompt user for a new pet name
        String petName = JOptionPane.showInputDialog(this, "Enter a name for your new pet:");
        if (petName != null && !petName.trim().isEmpty()) {
            // Prompt user for pet type selection
            String[] petTypes = {"Cat", "Dog", "Bird"};
            String petType = (String) JOptionPane.showInputDialog(this, "Select pet type:",
                    "Pet Type", JOptionPane.QUESTION_MESSAGE, null, petTypes, petTypes[0]);

            if (petType != null) {
                try {
                    // Create a new pet with default stats and assign to the player
                    Pet newPet = new Pet(0, petName.trim(), 100, 0, 100, 100, petType, player.getPlayerId());
                    player.addPet(newPet); // Add new pet to player's list
                    PetSave.savePet(newPet); // Save new pet to database

                    loadPetList(); // Refresh the pet dropdown list
                    JOptionPane.showMessageDialog(this, "New pet created. Select it from the dropdown.");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error creating pet: " + e.getMessage());
                }
            }
        }
    }
}
