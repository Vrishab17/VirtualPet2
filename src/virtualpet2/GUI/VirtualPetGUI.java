/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
 * VirtualPetGUI class represents the main game play interface for interacting with a selected pet.
 * It displays the pet's current stats, provides buttons for interacting with the pet, and includes
 * options to logout or exit the application. The class also initializes a timer to gradually decrease
 * the pet's stats over time.
 * 
 * @author vrishabchetty
 */

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import virtualpet2.Pet.Pet;
import virtualpet2.Player.Player;
import virtualpet2.StatDecayTimer;
import virtualpet2.VirtualPetApp;

public class VirtualPetGUI extends JFrame {

    // Player and selected pet being interacted with
    private final Player player;
    private Pet selectedPet;

    // Panels and components for displaying pet information and interaction controls
    private final PetInfoPanel petInfoPanel;
    private final PetActionPanel petActionPanel;
    private JPanel controlPanel;
    private JPanel imagePanel;
    
    // Timer to manage gradual decrease of pet stats
    private StatDecayTimer statDecayTimer;

    /**
     * Constructor for VirtualPetGUI.
     * Sets up the main game window, initializes panels, configures layout, and starts the stat decay timer.
     * 
     * @param player The player who owns the pet.
     * @param selectedPet The pet that the player is currently interacting with.
     */
    public VirtualPetGUI(Player player, Pet selectedPet) {
        this.player = player;
        this.selectedPet = selectedPet;

        initControlPanel(); // Initialize the control panel with Logout and Exit buttons
        initImagePanel(); // Initialize the image panel with pet image

        // Initialize panels for displaying and interacting with pet stats
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        // Configure main window properties
        setTitle("Virtual Pet Simulator - Playing with " + selectedPet.getName());
        setPreferredSize(new Dimension(550, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and add panels to different regions of the window
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.WEST);
        add(petInfoPanel, BorderLayout.EAST);
        add(petActionPanel, BorderLayout.SOUTH);
        pack();

        // Update pet info panel with selected pet's initial stats
        petInfoPanel.updatePetInfo(selectedPet);

        // Initialize StatDecayTimer to automatically decay pet's stats over time
        statDecayTimer = new StatDecayTimer(selectedPet, petInfoPanel);
    }

    /**
     * Initializes the control panel with Logout and Exit buttons.
     * The Logout button logs out the current user, while the Exit button closes the application.
     */
    private void initControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Logout button to return to the login screen
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        // Exit button to close the application
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));  // Exits the application

        controlPanel.add(logoutButton);
        controlPanel.add(exitButton);
    }

    /**
     * Initializes the image panel to display an image of the selected pet.
     * The image displayed corresponds to the type of pet (dog, cat, bird, etc.).
     */
    private void initImagePanel() {
        imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); // Set vertical layout for image and info

        JLabel petImageLabel = new JLabel();
        String petType = selectedPet.getPetType();
        URL imageUrl = null;

        // Load image based on pet type
        switch (petType.toLowerCase()) {
            case "dog":
                imageUrl = VirtualPetGUI.class.getResource("dog.png");
                break;
            case "cat":
                imageUrl = VirtualPetGUI.class.getResource("cat.png");
                break;
            case "bird":
                imageUrl = VirtualPetGUI.class.getResource("bird.png");
                break;
            default:
                imageUrl = VirtualPetGUI.class.getResource("default.jpg");
                break;
        }

        if (imageUrl != null) {
            // Scale and set pet image if available
            ImageIcon petIcon = new ImageIcon(imageUrl);
            Image scaledImage = petIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            petImageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            petImageLabel.setText("Image not found"); // Display text if the image is not found
        }
        imagePanel.add(petImageLabel);
    }

    /**
     * Logs out the current user by closing this frame and restarting the application.
     */
    private void logout() {
        dispose();  // Close the current window
        SwingUtilities.invokeLater(() -> VirtualPetApp.main(new String[]{}));  // Restart the app for a new user
    }

    /**
     * Disposes of the frame, stopping the StatDecayTimer to prevent it from running in the background.
     */
    @Override
    public void dispose() {
        if (statDecayTimer != null) {
            statDecayTimer.stop(); // Stop the stat decay timer
        }
        super.dispose();
    }

    /**
     * Getter for the selected pet.
     * 
     * @return The pet currently selected by the player.
     */
    public Pet getSelectedPet() {
        return selectedPet;
    }

    /**
     * Refreshes the pet info panel to display updated stats of the selected pet.
     * If no pet is selected, clears the pet info display.
     */
    public void refreshPetInfo() {
        if (selectedPet != null) {
            petInfoPanel.updatePetInfo(selectedPet); // Update pet info panel with current stats
        } else {
            petInfoPanel.clearInfo(); // Clear display if no pet is selected
        }
    }
}
