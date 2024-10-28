/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

import javax.swing.*;
import java.sql.SQLException;

/**
 *
 * @author vrishabchetty
 */


public class VirtualPetApp {

    public static void main(String[] args) {
        try {
            Database.connect();

            // Get or create player
            Player player = initializePlayer();

            // Run the main GUI in the Swing event dispatch thread
            SwingUtilities.invokeLater(() -> new VirtualPetGUI(player).setVisible(true));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + e.getMessage());
        }
    }

    private static Player initializePlayer() throws SQLException {
        // Ask for player's name
        String playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Welcome to Virtual Pet Simulator", JOptionPane.QUESTION_MESSAGE);

        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No name entered. Exiting the game.");
            System.exit(0); // Exit if no name is provided
        }

        // Load or create player based on input name
        Player player = PlayerSave.loadPlayerByName(playerName.trim());
        if (player == null) {
            // If player does not exist, create a new player
            player = new Player(0, playerName.trim());
            PlayerSave.savePlayer(player);
            JOptionPane.showMessageDialog(null, "New player created. Welcome, " + playerName + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Welcome back, " + playerName + "!");
        }
        return player;
    }
}


