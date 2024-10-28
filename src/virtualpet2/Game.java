/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

    public static void Game(Pet pet, Player player) throws SQLException {

        boolean restart = false; // Flag to indicate if the game should return to the main menu
        Scanner scanner;

        // Initialize the pet's hunger, fun, and sleep management systems
        Hunger hunger = new Hunger(pet, PetType.convertStringToPetType(pet.getPetType()));
        Fun fun = new Fun(pet, PetType.convertStringToPetType(pet.getPetType()));
        Sleep sleep = new Sleep(pet, PetType.convertStringToPetType(pet.getPetType()));

        Timer timer = new Timer(true); // Timer to periodically decrease pet stats

        // Welcome message displaying player and pet information
        System.out.println(Art.GREEN + "Welcome " + player.getPlayerName() + " \nYou are playing with "
                + pet.getName() + " the " + pet.getPetType() + Art.RESET);

        // Schedule the timer to decrease hunger, fun, and sleep levels every 30 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                hunger.decreaseHunger(pet); // Decrease hunger level
                fun.decreaseFun(pet); // Decrease fun level
                sleep.decreaseSleep(pet); // Decrease sleep level
                try {
                    PetSave.savePet(pet);
                } catch (SQLException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("______________________"); // Divider for console output
            }
        }, 0, 30000); // 30 seconds in milliseconds

        // Initialize the scanner for user input
        scanner = new Scanner(System.in);

        // Main gameplay loop
        OUTER:
        while (true) {
            // Display options to the user
            System.out.println(Art.GREEN + "\nUse numbers to select:" + Art.RESET);
            System.out.println("1: Feed");
            System.out.println("2: Play");
            System.out.println("3: Sleep");
            System.out.println("4: Check Pet");
            System.out.println("5: Main Menu");
            String choice = scanner.nextLine();

            // Exit the game if the user types 'exit'
            if (choice.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game...\n");
                break; // Break out of the loop to save and exit
            }

            // Handle user input
            switch (choice) {
                case "1":
                    hunger.feedPet(pet); // Feed the pet
                    PetSave.savePet(pet);
                    break;
                case "2":
                    fun.playWithPet(pet); // Play with the pet
                    PetSave.savePet(pet);
                    break;
                case "3":
                    sleep.putPetToSleep(pet); // Put the pet to sleep
                    PetSave.savePet(pet);
                    break;
                case "4":
                    // Display the pet's current stats
                    System.out.println("\n" + pet.getName() + " Age: " + pet.getAge() + Art.GREEN + "\nHunger Level: " + Art.RESET + hunger.getHungerLevel() + "/100"
                            + Art.GREEN + "\nSleep Level: " + Art.RESET + sleep.getSleepLevel() + "/100" + Art.GREEN + "\nBoredom: " + Art.RESET + fun.getFunLevel() + "/100");
                    PetSave.savePet(pet);
                    break;
                case "5":
                    restart = true; // Set the flag to restart the game (go to main menu)
                    System.out.println("Returning to Main Menu\n\n");
                    PetSave.savePet(pet);
                    break OUTER; // Exit the game loop
                default:
                    System.out.println("Invalid choice, please try again."); // Handle invalid input
                    break;
            }
        }

        timer.cancel(); // Stop the timer when the game ends

        // Restart the game if the user chose to return to the main menu
        if (restart) {
            Start.Start();
        }
    }
}
