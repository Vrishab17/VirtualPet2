/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrish
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PetSave {

    // Save Pet to Database
public static void savePet(Pet pet) throws SQLException {
    Connection conn = Database.getConnection();
    if (conn == null || conn.isClosed()) {
        System.err.println("No current connection.");
        return;
    }

    // Check if pet already exists in the database
    String checkPetSQL = "SELECT id FROM vrishab.pets WHERE id = ?";
    try (PreparedStatement checkStmt = conn.prepareStatement(checkPetSQL)) {
        checkStmt.setInt(1, pet.getPetId());
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            // Pet exists, perform an UPDATE
            String updatePetSQL = "UPDATE vrishab.pets SET name = ?, type = ?, hunger = ?, fun = ?, sleep = ?, owner = ? WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updatePetSQL)) {
                updateStmt.setString(1, pet.getName());
                updateStmt.setString(2, pet.getPetType());
                updateStmt.setInt(3, pet.getHunger());
                updateStmt.setInt(4, pet.getFun());
                updateStmt.setInt(5, pet.getSleep());
                updateStmt.setInt(6, pet.getOwnerId());
                updateStmt.setInt(7, pet.getPetId());
                updateStmt.executeUpdate();
                System.out.println("Pet updated in database with ID: " + pet.getPetId());
            }
        } else {
            // Pet does not exist, perform an INSERT
            String insertPetSQL = "INSERT INTO vrishab.pets (name, type, hunger, fun, sleep, owner) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertPetSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, pet.getName());
                insertStmt.setString(2, pet.getPetType());
                insertStmt.setInt(3, pet.getHunger());
                insertStmt.setInt(4, pet.getFun());
                insertStmt.setInt(5, pet.getSleep());
                insertStmt.setInt(6, pet.getOwnerId());

                insertStmt.executeUpdate();
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    pet.setPetId(generatedKeys.getInt(1)); // Set the generated ID for the new pet
                    System.out.println("New pet saved with generated ID: " + pet.getPetId());
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    // Load Pet by Name and Owner
    public static Pet loadPetByNameAndOwner(String petName, int ownerId) {
        String selectPetSQL = "SELECT * FROM vrishab.pets WHERE name = ? AND owner = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPetSQL)) {

            pstmt.setString(1, petName);
            pstmt.setInt(2, ownerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int petId = rs.getInt("id");
                String type = rs.getString("type");
                int hunger = rs.getInt("hunger");
                int fun = rs.getInt("fun");
                int sleep = rs.getInt("sleep");
                return new Pet(petId, petName, hunger, 0, fun, sleep, type, ownerId); // Assuming age = 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Load All Pets for a Specific Owner
    public static LinkedList<Pet> loadPetsByOwner(int ownerId) {
        LinkedList<Pet> pets = new LinkedList<>();
        String selectPetsByOwnerSQL = "SELECT * FROM vrishab.pets WHERE owner = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPetsByOwnerSQL)) {

            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int petId = rs.getInt("id");
                String petName = rs.getString("name");
                String type = rs.getString("type");
                int hunger = rs.getInt("hunger");
                int fun = rs.getInt("fun");
                int sleep = rs.getInt("sleep");
                pets.add(new Pet(petId, petName, hunger, 0, fun, sleep, type, ownerId)); // Assuming age = 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    // Load All Pets (all records in pets table)
    public static LinkedList<Pet> loadAllPets() {
        LinkedList<Pet> pets = new LinkedList<>();
        String selectAllPetsSQL = "SELECT * FROM vrishab.pets";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectAllPetsSQL)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int petId = rs.getInt("id");
                String petName = rs.getString("name");
                String type = rs.getString("type");
                int hunger = rs.getInt("hunger");
                int fun = rs.getInt("fun");
                int sleep = rs.getInt("sleep");
                int ownerId = rs.getInt("owner");
                pets.add(new Pet(petId, petName, hunger, 0, fun, sleep, type, ownerId)); // Assuming age = 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }
}