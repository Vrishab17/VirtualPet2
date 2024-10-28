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

public class PetSelectionPanel extends JPanel {

    private final JComboBox<String> petList;
    private final JButton refreshButton;
    private final VirtualPetGUI mainFrame;

    public PetSelectionPanel(Player player, VirtualPetGUI mainFrame) {
        this.mainFrame = mainFrame;

        petList = new JComboBox<>();
        refreshButton = new JButton("Refresh Pets");
        loadPetList(player);

        add(new JLabel("Select Pet:"));
        add(petList);
        add(refreshButton);

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


