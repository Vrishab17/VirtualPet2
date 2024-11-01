/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.GUI;

/**
 * PetInfoPanel class represents a JPanel in the Virtual Pet Simulator GUI that 
 * displays the pet's current hunger, fun, and sleep levels using progress bars.
 * This panel provides a visual representation of these stats, which are updated 
 * in real-time based on the pet's state.
 * 
 * @author vrishabchetty
 */

import javax.swing.*;
import java.awt.*;
import virtualpet2.Pet.Pet;

public class PetInfoPanel extends JPanel {

    // Progress bars for displaying the pet's hunger, fun, and sleep levels
    private final JProgressBar hungerBar;
    private final JProgressBar funBar;
    private final JProgressBar sleepBar;

    /**
     * Constructor for PetInfoPanel.
     * Initializes and configures the progress bars to display the pet's stats,
     * setting up the layout to include labels alongside each progress bar.
     */
    public PetInfoPanel() {
        setLayout(new GridLayout(3, 2, 5, 5)); // 3 rows, 2 columns layout with 5px spacing

        // Initialize progress bars for each pet stat, ranging from 0 to 100
        hungerBar = new JProgressBar(0, 100);
        hungerBar.setStringPainted(true); // Show numeric value on the bar
        funBar = new JProgressBar(0, 100);
        funBar.setStringPainted(true);
        sleepBar = new JProgressBar(0, 100);
        sleepBar.setStringPainted(true);

        // Add labels and respective progress bars to the panel
        add(new JLabel("Hunger:"));
        add(hungerBar);
        add(new JLabel("Fun:"));
        add(funBar);
        add(new JLabel("Sleep:"));
        add(sleepBar);
    }
    
    /**
     * Updates the displayed values of the progress bars based on the pet's current stats.
     * This method retrieves the pet's hunger, fun, and sleep levels and sets them as the values of the bars.
     * 
     * @param pet The pet object whose stats will be reflected in the progress bars.
     */
    public void updatePetInfo(Pet pet) {
        if (pet != null) {
            // Set progress bar values to match the pet's current hunger, fun, and sleep levels
            hungerBar.setValue((int) pet.getHunger());
            funBar.setValue((int) pet.getFun());
            sleepBar.setValue((int) pet.getSleep());
        }
    }
    
    /**
     * Clears the progress bar values, setting them to zero.
     * Useful for resetting the display when no pet is selected or when clearing the panel.
     */
    public void clearInfo() {
        hungerBar.setValue(0);
        funBar.setValue(0);
        sleepBar.setValue(0);
    }
}
