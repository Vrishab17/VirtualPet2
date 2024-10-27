package virtualpet2;

import java.util.LinkedList;
import java.util.Scanner;

public class Start {

    public static void Start() {

        // Display the ASCII art for the game title.
        Art.displayAsciiArt();
        System.out.println(Art.CYAN + "Art by:patorjk.com\n" + Art.RESET);

        // Create a scanner to take user input.
        try ( Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to Virtual Pet Simulator\n");
            System.out.println(Art.YELLOW + "Type 'exit' at any point to quit\n" + Art.RESET);
            System.out.println("Enter your name");

            // Get the player's name.
            String playerName = scanner.nextLine();

            // Exit program if the user types "exit"
            if (playerName.equalsIgnoreCase("exit")) {
                System.out.println("Thanks For Playing");
                System.out.println("Goodbye!");
                return;
            }

            // Load existing pets and players from save files
            LinkedList<Pet> pets = PetSave.loadPets();
            LinkedList<Player> players = PlayerSave.loadPlayers();

            // Find the player based on the entered name, or create a new player if not found.
            Player player = PlayerSave.findPlayer(players, playerName);

            if (player == null) {
                System.out.println("\n**New User Created** \n");
                player = new Player(playerName);
                players.add(player);
                PlayerSave.savePlayers(players); // Save the new player
            } else {
                System.out.println("\n**Welcome back " + playerName + "**\n");
            }

            // Main game loop.
            OUTER:
            while (true) {

                 // Display the main menu.
                System.out.println("________________________");
                System.out.println(Art.GREEN + "Use numbers to choose" + Art.RESET);
                System.out.println("1: Create New Pet");
                System.out.println("2: Play With My Existing Pet");
                System.out.println("3: Show all Pets");
                System.out.println("4: Provide Feedback");
                
                //Get player choice
                String choice = scanner.next();

                // Exit the game if the player types "exit".
                if (choice.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }
                
                //Initilize variable
                int startOption;
                
                //Convert user input into integer
                try {
                    startOption = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input. Please enter a number (1, 2, or 3).");
                    continue; // Go back to the beginning of the loop
                }
                
                // Handle the user's choice based on the selected menu option
                switch (startOption) {
                    case 1: {
                        // Create a new pet
                        System.out.println("Enter a name for your new pet:");
                        scanner.nextLine(); // Consume newline
                        String petName = scanner.nextLine();
                        
                        // Check if the player wants to exit the game
                        if (petName.equalsIgnoreCase("exit")) {
                            System.out.println("Goodbye!");
                            break OUTER;// Exit the main loop
                        }
                        
                        // Check if the pet name already exists
                        Pet pet = PetSave.findPet(pets, petName);
                        if (pet != null) {
                            System.err.println("Pet already exists!");
                        } else {
                            String petType;
                            OUTER_1:
                            while (true) {
                                // Prompt the player to select a pet type
                                System.out.println(Art.GREEN + "Use numbers to choose" + Art.RESET);
                                System.out.println("Please choose your pet type:");
                                System.out.println("1: Cat");
                                System.out.println("2: Dog");
                                System.out.println("3: Bird");
                                int petChoice = Integer.parseInt(scanner.nextLine());

                                // Assign the selected pet type
                                switch (petChoice) {
                                    case 1:
                                        petType = "Cat";
                                        System.out.println("You've chosen " + petType);
                                        break OUTER_1; // Exit the inner loop
                                    case 2:
                                        petType = "Dog";
                                        System.out.println("You've chosen " + petType);
                                        break OUTER_1; // Exit the inner loop
                                    case 3:
                                        petType = "Bird";
                                        System.out.println("You've chosen " + petType);
                                        break OUTER_1; // Exit the inner loop
                                    default:
                                        System.err.println("Invalid choice. Please choose again.");
                                        break; // Stay in the loop for another attempt
                                }
                            }
                            // Create the new pet and add it to the player's collection
                            Pet newPet = new Pet(petName, 100, 0, 100, 100, petType, playerName);
                            System.out.println(newPet.getPetType());

                            player.addPet(newPet);
                            pets.add(newPet); // Add the new pet to the list of all pets
                            PetSave.savePets(pets); // Save the updated list of pets
                            PlayerSave.savePlayers(players); // Save the updated list of players

                            System.out.println(Art.YELLOW + "Pet " + petName + " has been created!\n" + Art.RESET);
                        }
                        break;
                    }

                    case 2: {
                        // Option 2: Play with an existing pet
                        System.out.println(Art.GREEN + "\nYour pets:" + Art.RESET);
                        for (Pet pet : player.getPets()) {
                            // Display the player's pets
                            System.out.println(Art.GREEN + "- " + Art.RESET + pet.toString());
                        }

                        System.out.println(Art.YELLOW + "\nType 'back' to go back" + Art.RESET);
                        System.out.println("Enter the name of the pet you'd like to play with:");
                        scanner.nextLine(); // Consume the leftover newline character
                        String petName = scanner.nextLine();

                        // Check if the player wants to exit the game
                        if (petName.equalsIgnoreCase("exit")) {
                            System.out.println("Goodbye!");
                            break OUTER; // Exit the main loop
                        }

                        // Allow the player to return to the previous menu
                        if (petName.equalsIgnoreCase("back")) {
                            continue; // Continue to the next iteration of the loop
                        }

                        // Find the selected pet and start the game with it
                        Pet chosenPet = player.getPet(petName);

                        if (chosenPet != null) {
                            System.out.println("You are now playing with " + petName + "!\n");
                            Game.Game(chosenPet, player); // Start the game with the chosen pet
                            break OUTER; // Exit the main loop after playing
                        } else {
                            System.err.println("No pet with that name was found.");
                        }
                        break;
                    }

                    case 3:
                        // Option 3: Show all pets
                        LinkedList<Pet> allPets = PetSave.loadPets(); // Load all pets from storage
                        if (allPets.isEmpty()) {
                            System.out.println("\nNo pets found.");
                        } else {
                            // Display a list of all pets with their owners
                            System.out.println("\nList of all pets:");
                            for (Pet pet : allPets) {
                                System.out.println(Art.GREEN + "- " + Art.RESET + pet.getName() + " the " + pet.getPetType() + " (Owner: " + pet.getOwner() + ")");
                            }
                        }
                        break;

                    case 4:
                        // Option 4: Provide feedback
                        Feedback.collectFeedback(); // Call the method to collect feedback from the player
                        break;

                    default:
                        // Handle invalid input by the player
                        System.err.println("Input not recognised, please try again");
                        break;
                }
            }
            System.out.println("Thanks For Playing");
        }
    }
}