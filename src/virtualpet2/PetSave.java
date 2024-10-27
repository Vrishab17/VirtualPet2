/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrish
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class PetSave {

    // Constants for the file names used to store pet data.
    private static final String FILE_NAME = "pets.txt";  // Name of the file that stores the pet data
    private static final String TEMP_FILE_NAME = "pets_tmp.txt"; // Temporary file used for safely updating pet data

    /**
     * Saves the list of pets to a file. If a pet already exists in the file, 
     * its data is updated; otherwise, new pets are added to the file.
     * 
     * @param pets A LinkedList of Pet objects that need to be saved.
     */
    public static void savePets(LinkedList<Pet> pets) {
        File originalFile = new File(FILE_NAME);  // Reference to the original file
        File tempFile = new File(TEMP_FILE_NAME); // Reference to the temporary file

        // Ensure the original file exists, or create it
        try {
            if (!originalFile.exists()) {
                originalFile.createNewFile();  // Create the original file if it does not exist
            }

            // Create the temporary file for writing updated data
            tempFile.createNewFile();

            try (
                BufferedReader reader = new BufferedReader(new FileReader(originalFile)); // Reader to read the original file
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile)) // Writer to write to the temporary file
            ) {
                String line;
                
                // Read the original file line by line
                while ((line = reader.readLine()) != null) {
                    // Split the line into pet data fields
                    String[] petData = line.split(",");
                    
                    // Check if the line has the expected number of fields
                    if (petData.length == 7) {
                        String petName = petData[0];  // Extract the pet's name
                        
                        // Find the corresponding pet in the current list
                        Pet matchingPet = findPet(pets, petName);

                        if (matchingPet != null) {
                            // Write updated pet data to the temporary file
                            writer.write(
                                matchingPet.getName() + "," + 
                                matchingPet.getHunger() + "," + 
                                matchingPet.getAge() + "," +
                                matchingPet.getFun() + "," + 
                                matchingPet.getSleep() + "," + 
                                matchingPet.getPetType() + "," + 
                                matchingPet.getOwner()
                            );
                            writer.newLine();
                            
                            // Remove the pet from the list since it has been processed
                            pets.remove(matchingPet);
                        } else {
                            // Write the original line (no changes needed)
                            writer.write(line);
                            writer.newLine();
                        }
                    } else {
                        // Write the line as is if it doesn't match the expected format
                        writer.write(line);
                        writer.newLine();
                    }
                }

                // Write any remaining pets that were not already in the file
                for (Pet pet : pets) {
                    writer.write(
                        pet.getName() + "," + 
                        pet.getHunger() + "," + 
                        pet.getAge() + "," + 
                        pet.getFun() + "," +
                        pet.getSleep() + "," + 
                        pet.getPetType() + "," + 
                        pet.getOwner()
                    );
                    writer.newLine();
                }

            } catch (IOException e) {
                // Handle any exceptions that occur during file read/write operations
                System.err.println("Error updating pet data: " + e.getMessage());
                return;
            }

            // Ensure the original file is closed before replacing it
            if (originalFile.exists() && !originalFile.delete()) {
                // If the original file cannot be deleted, print an error message
                System.err.println("Failed to delete the original file before renaming.");
                return;
            }

            // Rename the temporary file to replace the original file
            if (!tempFile.renameTo(originalFile)) {
                // If renaming fails, print an error message
                System.err.println("Failed to rename source file to destination file.");
            } else {
                // Confirm that the pet data has been successfully saved
                System.out.println(Art.YELLOW + "Pet has been saved" + Art.RESET);
            }

        } catch (IOException e) {
            // Handle any exceptions that occur during file creation
            System.err.println("Error creating or accessing file: " + e.getMessage());
        }
    }

    /**
     * Finds a pet in the provided list by its name.
     * 
     * @param pets A LinkedList of Pet objects.
     * @param name The name of the pet to find.
     * @return The Pet object if found, or null if not found.
     */
    public static Pet findPet(LinkedList<Pet> pets, String name) {
        // Loop through the list of pets to find a pet with the matching name
        for (Pet pet : pets) {
            if (pet.getName().equals(name)) {
                return pet;  // Return the pet if the name matches
            }
        }
        return null;  // Return null if no pet with the given name is found
    }

    /**
     * Loads the list of pets from a file. If the file does not exist,
     * it returns an empty list.
     * 
     * @return A LinkedList of Pet objects loaded from the file.
     */
    public static LinkedList<Pet> loadPets() {
        LinkedList<Pet> pets = new LinkedList<>();  // List to hold loaded pets
        File file = new File(FILE_NAME);  // Reference to the file containing pet data

        if (!file.exists()) {
            // If the file does not exist, print an error message and return an empty list
            System.err.println("No pet data file found. Starting with an empty list.");
            return pets;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                String[] petData = line.split(",");
                
                // Ensure the line has the correct number of fields
                if (petData.length == 7) {
                    String name = petData[0];  // Pet name
                    int hunger = Integer.parseInt(petData[1]);  // Pet hunger level
                    int age = Integer.parseInt(petData[2]);  // Pet age
                    int fun = Integer.parseInt(petData[3]);  // Pet fun level
                    int sleep = Integer.parseInt(petData[4]);  // Pet sleep level
                    String petType = petData[5];  // Pet type
                    String owner = petData[6];  // Pet owner name

                    // Create a new Pet object using the data from the file
                    Pet pet = new Pet(name, hunger, age, fun, sleep, petType, owner);
                    
                    // Add the pet to the list
                    pets.add(pet);
                }
            }
        } catch (IOException e) {
            // Handle any exceptions that occur during file reading
            System.err.println("Error loading pets from file: " + e.getMessage());
        }

        // Return the list of pets that were loaded from the file
        return pets;
    }

}
