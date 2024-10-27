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
    public int getHungerRate() {
        return 3;  // Birds get hungry more slowly compared to other pets
    }

    /**
     * Returns the rate at which a bird loses fun or gets bored.
     * This rate determines how quickly the fun level decreases over time.
     * 
     * @return the play rate for a bird, which is 2.
     */
    @Override
    public int getPlayRate() {
        return 2;  // Birds need less playtime, losing fun slowly
    }

    /**
     * Returns the rate at which a bird gets tired and needs sleep.
     * This rate determines how quickly the sleep level decreases over time.
     * 
     * @return the sleep rate for a bird, which is 9.
     */
    @Override
    public int getSleepRate() {
        return 9;  // Birds get tired quickly, needing frequent rest
    }
}