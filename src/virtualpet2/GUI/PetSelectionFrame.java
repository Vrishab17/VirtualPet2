/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import virtualpet2.Pet.Pet;
import virtualpet2.Pet.PetSave;
import virtualpet2.Player.Player;

/**
 *
 * @author vrish
 */
public class PetSelectionFrame extends JFrame {

    private final Player player;
    private JComboBox<String> petDropdown;
    private final JButton selectPetButton;
    private final JButton createPetButton;

    public PetSelectionFrame(Player player) {
        this.player = player;

        setTitle("Select Pet for " + player.getPlayerName());
        setPreferredSize(new Dimension(200, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel for pet selection dropdown
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        petDropdown = new JComboBox<>();
        loadPetList();
        selectionPanel.add(new JLabel("Select Pet:"));
        selectionPanel.add(petDropdown);
        selectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(selectionPanel);

        // Panel for buttons with BoxLayout for vertical alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button to select an existing pet
        selectPetButton = new JButton("Play with Selected Pet");
        selectPetButton.setBackground(new Color(50,147,111));
        selectPetButton.setForeground(Color.WHITE);
        selectPetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectPetButton.addActionListener((ActionEvent e) -> {
            String selectedPetName = (String) petDropdown.getSelectedItem();
            if (selectedPetName != null && !selectedPetName.isEmpty()) {
                openMainFrameWithSelectedPet(selectedPetName);
            } else {
                JOptionPane.showMessageDialog(PetSelectionFrame.this, "Please select a pet.");
            }
        });
        buttonPanel.add(selectPetButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons

        // Button to create a new pet
        createPetButton = new JButton("Create New Pet");
        createPetButton.setBackground(new Color(34,116,165));
        createPetButton.setForeground(Color.WHITE);
        createPetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPetButton.addActionListener((ActionEvent e) -> createNewPet());
        buttonPanel.add(createPetButton);

        // Add the button panel to main panel
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);
        pack();
    }

    // Load pets from player's list into the dropdown
    private void loadPetList() {
        petDropdown.removeAllItems();
        for (Pet pet : player.getPets()) {
            petDropdown.addItem(pet.getName());
        }
    }

    // Opens the main VirtualPetGUI frame with the selected pet
    private void openMainFrameWithSelectedPet(String petName) {
        Pet selectedPet = player.getPet(petName);
        if (selectedPet != null) {
            VirtualPetGUI mainFrame = new VirtualPetGUI(player, selectedPet);
            mainFrame.setVisible(true);
            dispose(); // Close the selection frame
        } else {
            JOptionPane.showMessageDialog(this, "Error: Pet not found.");
        }
    }

    // Create a new pet and add it to the player's list
    public void createNewPet() {
        String petName = JOptionPane.showInputDialog(this, "Enter a name for your new pet:");
        if (petName != null && !petName.trim().isEmpty()) {
            String[] petTypes = {"Cat", "Dog", "Bird"};
            String petType = (String) JOptionPane.showInputDialog(this, "Select pet type:",
                    "Pet Type", JOptionPane.QUESTION_MESSAGE, null, petTypes, petTypes[0]);

            if (petType != null) {
                try {
                    Pet newPet = new Pet(0, petName.trim(), 100, 0, 100, 100, petType, player.getPlayerId());
                    player.addPet(newPet);
                    PetSave.savePet(newPet);

                    // Reload the pet list in the dropdown
                    loadPetList();
                    JOptionPane.showMessageDialog(this, "New pet created. Select it from the dropdown.");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error creating pet: " + e.getMessage());
                }
            }
        }
    }
}