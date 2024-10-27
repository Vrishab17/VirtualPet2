/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author vrish
 */
public class Feedback {

    private static final String FEEDBACK_FILE = "feedback.txt"; // The file where feedback will be stored

    // Method to collect feedback from the player
    public static void collectFeedback() {
        Scanner scanner = new Scanner(System.in); // Initialize a Scanner to read user input
        System.out.println("Please enter your feedback about the game:"); // Prompt the user for feedback
        String feedback = scanner.nextLine(); // Capture the user's feedback

        saveFeedback(feedback); // Save the feedback to the file
        System.out.println("Thank you for your feedback!"); // Acknowledge receipt of the feedback
    }

    // Method to save feedback to a file
    private static void saveFeedback(String feedback) {
        // Try-with-resources block to automatically close the BufferedWriter after writing
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            writer.write(feedback); // Write the feedback to the file
            writer.newLine(); // Move to a new line after writing the feedback
            writer.write("----------");  // Add a separator between different feedback entries
            writer.newLine(); // Move to a new line after the separator
        } catch (IOException e) {
            // If an error occurs while saving the feedback, print an error message
            System.err.println("Error saving feedback: " + e.getMessage());
        }
    }
}