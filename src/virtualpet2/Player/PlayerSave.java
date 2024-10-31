package virtualpet2.Player;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
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

    // Save Player to Database
    public static void savePlayer(Player player) throws SQLException {
    Connection conn = Database.getConnection();
    if (conn == null || conn.isClosed()) {
        System.err.println("No current connection.");
        return;
    }

    String insertPlayerSQL = "INSERT INTO vrishab.players (name) VALUES (?)";
    try (PreparedStatement pstmt = conn.prepareStatement(insertPlayerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, player.getPlayerName());
        pstmt.executeUpdate();

        // Retrieve generated ID and set it in the player object
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            player.setPlayerId(generatedKeys.getInt(1));
            System.out.println("Player saved with generated ID: " + player.getPlayerId());
        }

    } catch (SQLException e) {
    }
}

    // Load Player by Name
    public static Player loadPlayerByName(String playerName) {
        String selectPlayerSQL = "SELECT * FROM vrishab.players WHERE name = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPlayerSQL)) {

            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int playerId = rs.getInt("id");
                Player player = new Player(playerId, playerName);

                // Load the pets for this player
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                System.out.println("Player loaded from database.");
                return player;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    // Load All Players
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

                // Load pets for each player
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                players.add(player);
            }
            System.out.println("All players loaded from database.");

        } catch (SQLException e) {
        }

        return players;
    }

    // Find Player by ID
    public static Player findPlayerById(int playerId) {
        String selectPlayerSQL = "SELECT * FROM vrishab.players WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPlayerSQL)) {

            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String playerName = rs.getString("name");
                Player player = new Player(playerId, playerName);

                // Load pets for this player
                LinkedList<Pet> pets = PetSave.loadPetsByOwner(playerId);
                for (Pet pet : pets) {
                    player.addPet(pet);
                }

                System.out.println("Player found in database.");
                return player;
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
