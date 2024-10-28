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
import java.awt.*;

public class PetInfoPanel extends JPanel {

    private final JLabel petInfoLabel;

    public PetInfoPanel() {
        setLayout(new BorderLayout());
        petInfoLabel = new JLabel("No pet selected");
        add(petInfoLabel, BorderLayout.NORTH);
    }

    // Updates the pet information display
    public void updatePetInfo(Pet pet) {
        if (pet != null) {
            petInfoLabel.setText("<html>" +
                    "<b>Pet Name:</b> " + pet.getName() + "<br>" +
                    "<b>Type:</b> " + pet.getPetType() + "<br>" +
                    "<b>Hunger:</b> " + pet.getHunger() + "/100<br>" +
                    "<b>Fun:</b> " + pet.getFun() + "/100<br>" +
                    "<b>Sleep:</b> " + pet.getSleep() + "/100<br>" +
                    "</html>");
        } else {
            petInfoLabel.setText("No pet selected");
        }
    }
}
