/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author vrishabchetty
 */
public class Art {
    
    
    //ANSI codes created by ChatGPT
    
    // ANSI escape codes for text colors
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String MAGENTA = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";

    // ANSI escape codes for background colors
    public static final String BLACK_BG = "\033[40m";
    public static final String RED_BG = "\033[41m";
    public static final String GREEN_BG = "\033[42m";
    public static final String YELLOW_BG = "\033[43m";
    public static final String BLUE_BG = "\033[44m";
    public static final String MAGENTA_BG = "\033[45m";
    public static final String CYAN_BG = "\033[46m";
    public static final String WHITE_BG = "\033[47m";

    // ANSI reset code
    public static final String RESET = "\033[0m";
    
    public static void displayAsciiArt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ascii_art.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(BLUE+WHITE_BG+line+RESET);
            }
        } catch (IOException e) {
            System.err.println("Error reading ASCII art from file: " + e.getMessage());
        }
    }
    
}
