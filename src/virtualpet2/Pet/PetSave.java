/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license.default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Pet;

/**
 * PetSave class manages the database interactions for saving, updating, and loading
 * pet data. It includes methods to save pets to the database, load pets by name and
 * owner, and retrieve all pets owned by a specific player.
 * 
 * @author vrish
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import virtualpet2.Database;

public class PetSave {

    /**
     * Saves a pet to the database. If the pet already exists, updates its information;
     * otherwise, inserts it as a new record. Also sets the pet's ID if a new record is created.
     * 
     * @param pet The pet to save or update in the database.
     * @throws SQLException If a database access error occurs.
     */
    public static void savePet(Pet pet) throws SQLException {
        Connection conn = Database.getConnection();
        if (conn == null || conn.isClosed()) {
            System.err.println("No current connection.");
            return;
        }

        // SQL to check if the pet already exists in the database
        String checkPetSQL = "SELECT id FROM vrishab.pets WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkPetSQL)) {
            checkStmt.setInt(1, pet.getPetId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // If pet exists, update its information
                String updatePetSQL = "UPDATE vrishab.pets SET name = ?, type = ?, hunger = ?, fun = ?, sleep = ?, owner = ? WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updatePetSQL)) {
                    updateStmt.setString(1, pet.getName());
                    updateStmt.setString(2, pet.getPetType());
                    updateStmt.setInt(3, (int) pet.getHunger());
                    updateStmt.setInt(4, (int) pet.getFun());
                    updateStmt.setInt(5, (int) pet.getSleep());
                    updateStmt.setInt(6, pet.getOwnerId());
                    updateStmt.setInt(7, pet.getPetId());
                    updateStmt.executeUpdate();
                }
            } else {
                // If pet does not exist, insert a new record
                String insertPetSQL = "INSERT INTO vrishab.pets (name, type, hunger, fun, sleep, owner) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertPetSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, pet.getName());
                    insertStmt.setString(2, pet.getPetType());
                    insertStmt.setInt(3, (int) pet.getHunger());
                    insertStmt.setInt(4, (int) pet.getFun());
                    insertStmt.setInt(5, (int) pet.getSleep());
                    insertStmt.setInt(6, pet.getOwnerId());

                    insertStmt.executeUpdate();
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        pet.setPetId(generatedKeys.getInt(1)); // Set the ID for the new pet
                        System.out.println("New pet saved with generated ID: " + pet.getPetId());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Loads a pet from the database by name and owner ID.
     * 
     * @param petName The name of the pet to load.
     * @param ownerId The ID of the pet's owner.
     * @return The pet object if found, or null if not found.
     */
    public static Pet loadPetByNameAndOwner(String petName, int ownerId) {
        String selectPetSQL = "SELECT * FROM vrishab.pets WHERE name = ? AND owner = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPetSQL)) {

            pstmt.setString(1, petName);
            pstmt.setInt(2, ownerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Create and return a pet object based on retrieved data
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

    /**
     * Loads all pets belonging to a specific owner from the database.
     * 
     * @param ownerId The ID of the owner whose pets are to be loaded.
     * @return A LinkedList containing all pets belonging to the specified owner.
     */
    public static LinkedList<Pet> loadPetsByOwner(int ownerId) {
        LinkedList<Pet> pets = new LinkedList<>();
        String selectPetsByOwnerSQL = "SELECT * FROM vrishab.pets WHERE owner = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectPetsByOwnerSQL)) {

            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create pet objects for each record and add to the list
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

    /**
     * Loads all pets from the database.
     * 
     * @return A LinkedList containing all pets in the database.
     */
    public static LinkedList<Pet> loadAllPets() {
        LinkedList<Pet> pets = new LinkedList<>();
        String selectAllPetsSQL = "SELECT * FROM vrishab.pets";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectAllPetsSQL)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Create pet objects for each record and add to the list
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
