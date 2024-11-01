/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

import virtualpet2.Player.Player;
import virtualpet2.Player.PlayerSave;
import virtualpet2.GUI.PetSelectionFrame;
import javax.swing.*;
import java.sql.SQLException;

/**
 * VirtualPetApp serves as the main entry point for the Virtual Pet Simulator application.
 * It initializes the database connection, sets up the necessary tables, manages player creation or retrieval,
 * and launches the main GUI for selecting and interacting with virtual pets.
 * 
 * @author vrishabchetty
 */
public class VirtualPetApp {

    /**
     * The main method initiates the application by establishing a database connection,
     * creating the required tables, initializing the player, and opening the pet selection interface.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        try {
            // Connect to the database and ensure tables are created
            Database.startServer();
            Database.connect();
            Database.createPlayerTable();
            Database.createPetTable();

            // Initialize the player by either loading or creating a new player based on input
            Player player = initializePlayer();

            // Open the pet selection frame on the Swing event dispatch thread for the specified player
            SwingUtilities.invokeLater(() -> new PetSelectionFrame(player).setVisible(true));
            
        } catch (SQLException e) {
            // Display an error dialog if there is an issue connecting to the database
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + e.getMessage());
        }
    }

    /**
     * Initializes the player based on user input. If a player with the entered name exists in the database,
     * that player is loaded. Otherwise, a new player is created and saved to the database.
     *
     * @return Player object representing the current user.
     * @throws SQLException if there is an error during database access or creation.
     */
    private static Player initializePlayer() throws SQLException {
        // Prompt user for their name to either load an existing player or create a new one
        String playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Welcome to Virtual Pet Simulator", JOptionPane.QUESTION_MESSAGE);

        // If user cancels input or leaves it empty, display a message and exit the game
        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No name entered. Exiting the game.");
            System.exit(0); // Exit if no name is provided to avoid proceeding without a valid player
        }

        // Attempt to load player by name from the database
        Player player = PlayerSave.loadPlayerByName(playerName.trim());
        if (player == null) {
            // If the player does not exist, create a new player with the entered name
            player = new Player(0, playerName.trim()); // Assign ID 0, assuming the database will handle the actual ID assignment
            PlayerSave.savePlayer(player); // Save the new player to the database
            JOptionPane.showMessageDialog(null, "New player created. Welcome, " + playerName + "!");
        } else {
            // If the player already exists, welcome them back
            JOptionPane.showMessageDialog(null, "Welcome back, " + playerName + "!");
        }
        return player; // Return the player object to the main application
    }
}
