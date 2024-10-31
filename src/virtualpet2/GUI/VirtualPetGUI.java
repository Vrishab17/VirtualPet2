/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
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

    private final Player player;
    private Pet selectedPet;

    private final PetInfoPanel petInfoPanel;
    private final PetActionPanel petActionPanel;
    private JPanel controlPanel;
    private JPanel imagePanel;
    private StatDecayTimer statDecayTimer; // Use StatDecayTimer instead of Timer

    public VirtualPetGUI(Player player, Pet selectedPet) {
        this.player = player;
        this.selectedPet = selectedPet;

        initControlPanel(); // Initialize the control panel with Logout and Exit buttons
        initImagePanel(); // Initialize the image panel with pet image
        petInfoPanel = new PetInfoPanel();
        petActionPanel = new PetActionPanel(this);

        setTitle("Virtual Pet Simulator - Playing with " + selectedPet.getName());
        setPreferredSize(new Dimension(550, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.WEST);
        add(petInfoPanel, BorderLayout.EAST);
        add(petActionPanel, BorderLayout.SOUTH);
        pack();

        petInfoPanel.updatePetInfo(selectedPet);

        // Initialize StatDecayTimer with selectedPet and petInfoPanel
        statDecayTimer = new StatDecayTimer(selectedPet, petInfoPanel);
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
            ImageIcon petIcon = new ImageIcon(imageUrl);
            Image scaledImage = petIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            petImageLabel.setIcon(new ImageIcon(scaledImage)); // Set the image icon if it is loaded correctly
        } else {
            petImageLabel.setText("Image not found"); // Display text if the image is not found
        }
        imagePanel.add(petImageLabel);
    }

    private void logout() {
        dispose();  // Close the current window
        SwingUtilities.invokeLater(() -> VirtualPetApp.main(new String[]{}));  // Restart the app for a new user
    }
    
    
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
