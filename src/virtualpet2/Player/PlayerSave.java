package virtualpet2.Player;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license.default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * PlayerSave class manages the database interactions for saving, retrieving, 
 * and loading player data. It includes methods to save players to the database,
 * load players by name or ID, and retrieve all players in the database.
 * 
 * @author vrish
 */

import virtualpet2.Pet.PetSave;
import virtualpet2.Pet.Pet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import virtualpet2.Database;

public class PlayerSave {

    /**
     * Saves a player to the database. If the player does not exist, it inserts 
     * a new record and assigns a generated ID to the player object.
     * 
     * @param player The player object to save.
     * @throws SQLException If a database access error occurs.
     */
    public static void savePlayer(Player player) throws SQLException {
        Connection conn = Database.getConnection();
        if (conn == null || conn.isClosed()) {
            System.err.println("No current connection.");
            return;
        }

        // SQL statement to insert a new player record
        String insertPlayerSQL = "INSERT INTO vrishab.players (name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertPlayerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, player.getPlayerName());
            pstmt.executeUpdate();

            // Retrieve generated ID and assign it to the player object
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                player.setPlayerId(generatedKeys.getInt(1));
                System.out.println("Player saved with generated ID: " + player.getPlayerId());
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Loads a player from the database by name.
     * If the player is found, loads all associated pets and returns the player object.
     * 
     * @param playerName The name of the player to load.
     * @return The player object if found, or null if not found.
     */
    public static Player loadPlayerByName(String playerName) {
        String selectPlayerSQL = "SELECT * FROM vrishab.players WHERE name = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPlayerSQL)) {

            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int playerId = rs.getInt("id");
                Player player = new Player(playerId, playerName);

                // Load pets associated with this player
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                System.out.println("Player loaded from database.");
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return null;
    }

    /**
     * Loads all players from the database, including each player's associated pets.
     * 
     * @return A LinkedList containing all players in the database.
     */
    public static LinkedList<Player> loadAllPlayers() {
        LinkedList<Player> players = new LinkedList<>();
        String selectAllPlayersSQL = "SELECT * FROM vrishab.players";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectAllPlayersSQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int playerId = rs.getInt("id");
                String playerName = rs.getString("name");
                Player player = new Player(playerId, playerName);

                // Load pets for each player and add to the player's list
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                players.add(player);
            }
            System.out.println("All players loaded from database.");

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }

        return players;
    }

    /**
     * Finds and loads a player from the database by ID.
     * If the player is found, loads all associated pets and returns the player object.
     * 
     * @param playerId The ID of the player to find.
     * @return The player object if found, or null if not found.
     */
    public static Player findPlayerById(int playerId) {
        String selectPlayerSQL = "SELECT * FROM vrishab.players WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPlayerSQL)) {

            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String playerName = rs.getString("name");
                Player player = new Player(playerId, playerName);

                // Load pets associated with this player
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                System.out.println("Player found in database.");
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return null;
    }
}
