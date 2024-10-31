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
import virtualpet2.Pet.Pet;

public class PetInfoPanel extends JPanel {

    private final JProgressBar hungerBar;
    private final JProgressBar funBar;
    private final JProgressBar sleepBar;

    public PetInfoPanel() {
        setLayout(new GridLayout(3, 2, 5, 5)); // Set up layout with labels and progress bars

        // Initialize progress bars for each stat
        hungerBar = new JProgressBar(0, 100);
        hungerBar.setStringPainted(true);
        funBar = new JProgressBar(0, 100);
        funBar.setStringPainted(true);
        sleepBar = new JProgressBar(0, 100);
        sleepBar.setStringPainted(true);

        // Add labels and progress bars to the panel
        add(new JLabel("Hunger:"));
        add(hungerBar);
        add(new JLabel("Fun:"));
        add(funBar);
        add(new JLabel("Sleep:"));
        add(sleepBar);
    }
    
        public void updatePetInfo(Pet pet) {
        if (pet != null) {
            hungerBar.setValue((int) pet.getHunger());
            funBar.setValue((int) pet.getFun());
            sleepBar.setValue((int) pet.getSleep());
        }
    }
    
        public void clearInfo() {
        hungerBar.setValue(0);
        funBar.setValue(0);
        sleepBar.setValue(0);
    }
}
