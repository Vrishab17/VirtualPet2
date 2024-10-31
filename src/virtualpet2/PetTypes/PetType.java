/*/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package virtualpet2.PetTypes;

/**
 *
 * @author vrishabchetty
 */
public abstract class PetType {

    // Abstract method to get the rate at which the pet gets hungry.
    public abstract double getHungerRate();

    // Abstract method to get the rate at which the pet's need for play increases.
    public abstract double getPlayRate();

    // Abstract method to get the rate at which the pet gets tired and needs sleep.
    public abstract double getSleepRate();

    /**
     * Converts a string representation of a pet type to its corresponding PetType object.
     * 
     * @param petTypeString The string representing the pet type (e.g., "cat", "dog", "bird").
     * @return The corresponding PetType object (e.g., Cat, Dog, Bird).
     * @throws IllegalArgumentException If the provided pet type string does not match any known pet type.
     */
    public static PetType convertStringToPetType(String petTypeString) {
        switch (petTypeString.toLowerCase()) {
            case "cat":
                return new Cat(); // Return a Cat object if the string is "cat"
            case "dog":
                return new Dog(); // Return a Dog object if the string is "dog"
            case "bird":
                return new Bird(); // Return a Bird object if the string is "bird"
            default:
                // Throw an exception if the string doesn't match any known pet type
                throw new IllegalArgumentException("Unknown pet type: " + petTypeString);
        }
    }
}