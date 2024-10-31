/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
public class Bird extends PetType {

    /**
     * Returns the rate at which a bird gets hungry.
     * This rate determines how quickly the hunger level decreases over time.
     * 
     * @return the hunger rate for a bird, which is 8.
     */
    @Override
    public double getHungerRate() {
        return 0.05;  // Birds get hungry more slowly compared to other pets
    }

    /**
     * Returns the rate at which a bird loses fun or gets bored.
     * This rate determines how quickly the fun level decreases over time.
     * 
     * @return the play rate for a bird, which is 2.
     */
    @Override
    public double getPlayRate() {
        return 0.03;  // Birds need less playtime, losing fun slowly
    }

    /**
     * Returns the rate at which a bird gets tired and needs sleep.
     * This rate determines how quickly the sleep level decreases over time.
     * 
     * @return the sleep rate for a bird, which is 9.
     */
    @Override
    public double getSleepRate() {
        return 0.15;  // Birds get tired quickly, needing frequent rest
    }
}