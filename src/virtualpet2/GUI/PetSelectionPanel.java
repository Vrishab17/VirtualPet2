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
import java.awt.event.ActionEvent;
import virtualpet2.Pet.Pet;
import virtualpet2.Player.Player;

public final class PetSelectionPanel extends JPanel {

    private final JComboBox<String> petList;
    private final JButton refreshButton;

public PetSelectionPanel(Player player, VirtualPetGUI mainFrame) {

        // Set layout to GridLayout for alignment without gaps
        setLayout(new GridLayout(2, 2, 2, 2));

        // Remove border if there's any default padding
        setBorder(BorderFactory.createEmptyBorder());

        petList = new JComboBox<>();
        refreshButton = new JButton("Refresh Pets");
        loadPetList(player);

        // Add components
        add(new JLabel("Select Pet:"));
        add(petList);
        add(refreshButton);

        // Set actions
        petList.addActionListener(e -> mainFrame.selectPet((String) petList.getSelectedItem()));
        refreshButton.addActionListener(e -> loadPetList(player));
    }

    public void loadPetList(Player player) {
        petList.removeAllItems();
        for (Pet pet : player.getPets()) {
            petList.addItem(pet.getName());
        }
    }
}

