package virtualpet2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vrish
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.io.File;

public class PlayerSave {

    // Constants for file names used to store player data.
    private static final String FILE_NAME = "players.txt";
    private static final String FILE_NAME_TEMP = "players_tmp.txt";

    /**
     * Saves the list of players and their pets to a file. This method first 
     * reads the existing file content, updates it with new data, and then 
     * writes the updated content to a temporary file. Finally, the temporary 
     * file replaces the original file.
     * 
     * @param players The list of players to be saved.
     */
    public static void savePlayers(LinkedList<Player> players) {
        File file = new File(FILE_NAME);
        LinkedList<String> fileContent = new LinkedList<>();

        // Read the existing file content
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line); // Add each line to the fileContent list
                }
            } catch (IOException e) {
                System.err.println("Error reading player data from file: " + e.getMessage());
                return;
            }
        }

        // Remove old data for each player
        for (Player player : players) {
            int start = -1;
            int end = -1;
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(player.getPlayerName())) {
                    start = i; // Mark the start of the player's data block
                }
                if (start != -1 && fileContent.get(i).equals("---")) {
                    end = i; // Mark the end of the player's data block
                    break;
                }
            }
            if (start != -1 && end != -1) {
                // Remove the old block of data for this player
                for (int i = end; i >= start; i--) {
                    fileContent.remove(i);
                }
            }
        }

        // Write the updated file content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_TEMP))) {
            // Write remaining (unaffected) data
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }

            // Write new/updated data for each player
            for (Player player : players) {
                writer.write(player.getPlayerName());
                writer.newLine();
                for (Pet pet : player.getPets()) {
                    // Save the pet name, owner, and type
                    writer.write(pet.getName() + "," + pet.getOwner() + "," + pet.getPetType());
                    writer.newLine();
                }
                writer.write("---");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing player data to file: " + e.getMessage());
            return;
        }

        // Replace the original file with the temporary file
        replaceFile(FILE_NAME_TEMP, FILE_NAME);
    }

    /**
     * Replaces the original file with the updated file.
     * 
     * @param tempFileName The name of the temporary file.
     * @param originalFileName The name of the original file to be replaced.
     */
    private static void replaceFile(String tempFileName, String originalFileName) {
        File tempFile = new File(tempFileName);
        File originalFile = new File(originalFileName);

        // Delete the original file if it exists
        if (originalFile.exists()) {
            if (!originalFile.delete()) {
                System.err.println("Failed to delete the original file: " + originalFileName);
                return;
            }
        }

        // Rename the temporary file to the original file's name
        if (!tempFile.renameTo(originalFile)) {
            System.err.println("Failed to rename the temporary file: " + tempFileName);
        }

        // Clean up the temporary file if it still exists
        if (tempFile.exists()) {
            if (!tempFile.delete()) {
                System.err.println("Failed to delete the temporary file: " + tempFileName);
            }
        }
    }

    /**
     * Loads all players and their pets from the file. This method reads the 
     * data from the file, reconstructs the player and pet objects, and adds 
     * them to a list of players.
     * 
     * @return A list of players loaded from the file.
     */
    public static LinkedList<Player> loadPlayers() {
        LinkedList<Player> players = new LinkedList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            // Return an empty list if the file doesn't exist
            return players;
        }
        LinkedList<Pet> allPets = PetSave.loadPets(); // Load all pets from the pet save file

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Player currentPlayer = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    currentPlayer = null; // End of the current player's data block
                } else if (currentPlayer == null) {
                    currentPlayer = new Player(line); // Create a new player object
                    players.add(currentPlayer);
                } else {
                    String[] petData = line.split(",");
                    if (petData.length == 3) { // Expecting name, owner, and type
                        String name = petData[0];
                        String owner = petData[1];
                        String petType = petData[2];
                        Pet pet = PetSave.findPet(allPets, name);
                        if (pet != null) {
                            currentPlayer.addPet(pet); // Use the full pet object
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading players from file: " + e.getMessage());
        }

        return players;
    }

    /**
     * Finds a player by name in the list of players.
     * 
     * @param players The list of players to search.
     * @param playerName The name of the player to find.
     * @return The Player object if found, or null if not found.
     */
    public static Player findPlayer(LinkedList<Player> players, String playerName) {
        for (Player player : players) {
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null; // Return null if the player is not found
    }

}