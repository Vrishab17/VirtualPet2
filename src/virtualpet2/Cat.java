/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
public class Cat extends PetType {

    /**
     * Returns the rate at which a cat gets hungry.
     * This rate determines how quickly the hunger level decreases over time.
     * 
     * @return the hunger rate for a cat, which is 3.
     */
    @Override
    public int getHungerRate() {
        return 3;  // Cats get hungry at a slower rate compared to dogs
    }

    /**
     * Returns the rate at which a cat loses fun or gets bored.
     * This rate determines how quickly the fun level decreases over time.
     * 
     * @return the play rate for a cat, which is 5.
     */
    @Override
    public int getPlayRate() {
        return 5;  // Cats lose fun at a moderate rate, balancing between play and relaxation
    }

    /**
     * Returns the rate at which a cat gets tired and needs sleep.
     * This rate determines how quickly the sleep level decreases over time.
     * 
     * @return the sleep rate for a cat, which is 9.
     */
    @Override
    public int getSleepRate() {
        return 9;  // Cats get tired more quickly, needing frequent naps
    }
}
