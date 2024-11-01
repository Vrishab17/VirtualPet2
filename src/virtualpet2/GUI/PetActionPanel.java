/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
 * PetActionPanel class represents a JPanel in the Virtual Pet GUI that allows users to 
 * interact with their pet by performing actions such as feeding, playing, and putting the pet to sleep.
 * This panel contains buttons for each action, which, when clicked, update the pet's state.
 * 
 * @author vrishabchetty
 */
import virtualpet2.Actions.Fun;
import virtualpet2.Actions.Sleep;
import virtualpet2.Actions.Hunger;
import virtualpet2.PetTypes.PetType;
import javax.swing.*;
import java.sql.SQLException;
import virtualpet2.Pet.Pet;
import virtualpet2.Pet.PetSave;

public class PetActionPanel extends JPanel {

    // Buttons for feeding, playing, and putting the pet to sleep
    private final JButton feedButton;
    private final JButton playButton;
    private final JButton sleepButton;

    // Reference to the main VirtualPetGUI frame to interact with selected pet
    private final VirtualPetGUI mainFrame;

    /**
     * Constructor for PetActionPanel.
     * Initializes the buttons for each pet action (feed, play, sleep),
     * and sets up action listeners to handle button clicks.
     *
     * @param mainFrame The main GUI frame for the Virtual Pet Simulator.
     */
    public PetActionPanel(VirtualPetGUI mainFrame) {
        this.mainFrame = mainFrame;

        // Initialize buttons with appropriate labels
        feedButton = new JButton("Feed");
        playButton = new JButton("Play");
        sleepButton = new JButton("Sleep");

        // Add buttons to the panel layout
        add(feedButton);
        add(playButton);
        add(sleepButton);

        // Set up action listeners for each button to trigger pet actions
        feedButton.addActionListener(e -> performAction("feed"));
        playButton.addActionListener(e -> performAction("play"));
        sleepButton.addActionListener(e -> performAction("sleep"));
    }

    /**
     * Performs the selected action on the currently selected pet.
     * Based on the action type (feed, play, or sleep), it adjusts the pet's 
     * stats accordingly and saves these changes to the database.
     * 
     * @param action The action to perform on the pet ("feed", "play", or "sleep").
     */
    private void performAction(String action) {
        // Retrieve the currently selected pet from the main frame
        Pet selectedPet = mainFrame.getSelectedPet();
        
        // Check if a pet is selected; if not, display a message and return
        if (selectedPet == null) {
            JOptionPane.showMessageDialog(this, "Please select a pet first.");
            return;
        }

        try {
            // Determine the pet type to apply specific behavior
            PetType petType = PetType.convertStringToPetType(selectedPet.getPetType());

            switch (action) {
                case "feed":
                    // Use Hunger class to feed the pet and increase its hunger level
                    Hunger hunger = new Hunger(selectedPet, petType);
                    hunger.feedPet(selectedPet); 
                    break;

                case "play":
                    // Use Fun class to play with the pet and increase its fun level
                    Fun fun = new Fun(selectedPet, petType);
                    fun.playWithPet(selectedPet); 
                    break;

                case "sleep":
                    // Use Sleep class to put the pet to sleep and increase its sleep level
                    Sleep sleep = new Sleep(selectedPet, petType);
                    sleep.putPetToSleep(selectedPet);
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Invalid action");
                    return;
            }

            // Save the pet's updated stats to the database after action
            PetSave.savePet(selectedPet);
            mainFrame.refreshPetInfo(); // Refresh pet info on the GUI to reflect changes

        } catch (SQLException e) {
            // Show error message if there's a database error when saving
            JOptionPane.showMessageDialog(this, "Error updating pet in database: " + e.getMessage());
        }
    }
}
