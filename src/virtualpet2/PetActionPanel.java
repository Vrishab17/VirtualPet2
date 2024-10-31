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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PetActionPanel extends JPanel {

    private final JButton feedButton;
    private final JButton playButton;
    private final JButton sleepButton;
    private final VirtualPetGUI mainFrame;

    public PetActionPanel(VirtualPetGUI mainFrame) {
        this.mainFrame = mainFrame;

        // Initialize buttons for actions
        feedButton = new JButton("Feed");
        playButton = new JButton("Play");
        sleepButton = new JButton("Sleep");

        add(feedButton);
        add(playButton);
        add(sleepButton);

        // Action listeners for each button
        feedButton.addActionListener(e -> performAction("feed"));
        playButton.addActionListener(e -> performAction("play"));
        sleepButton.addActionListener(e -> performAction("sleep"));
    }

    // Performs the selected action on the pet
    private void performAction(String action) {
        Pet selectedPet = mainFrame.getSelectedPet();
        if (selectedPet == null) {
            JOptionPane.showMessageDialog(this, "Please select a pet first.");
            return;
        }

        try {
            // Get the pet type and create instances for Hunger, Fun, and Sleep
            PetType petType = PetType.convertStringToPetType(selectedPet.getPetType());

            switch (action) {
                case "feed":
                    // Use Hunger class to feed the pet, setting hunger to max
                    Hunger hunger = new Hunger(selectedPet, petType);
                    hunger.feedPet(selectedPet); 
                    break;

                case "play":
                    // Use Fun class to play with the pet, setting fun to max
                    Fun fun = new Fun(selectedPet, petType);
                    fun.playWithPet(selectedPet); 
                    break;

                case "sleep":
                    // Use Sleep class to put the pet to sleep, setting sleep to max
                    Sleep sleep = new Sleep(selectedPet, petType);
                    sleep.putPetToSleep(selectedPet);
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Invalid action");
                    return;
            }

            // Save the updated pet stats to the database
            PetSave.savePet(selectedPet);
            mainFrame.refreshPetInfo(); // Refresh the pet info on the GUI
            JOptionPane.showMessageDialog(this, "Action performed: " + action);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating pet in database: " + e.getMessage());
        }
    }
}
