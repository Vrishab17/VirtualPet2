/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishabchetty
 */
public class Dog extends PetType {

    /**
     * Returns the rate at which a dog gets hungry.
     * This rate determines how quickly the hunger level decreases over time.
     * 
     * @return the hunger rate for a dog, which is 5.
     */
    @Override
    public double getHungerRate() {
        return 0.08; // Dogs get hungry at a moderate rate
    }

    /**
     * Returns the rate at which a dog loses fun or gets bored.
     * This rate determines how quickly the fun level decreases over time.
     * 
     * @return the play rate for a dog, which is 8.
     */
    @Override
    public double getPlayRate() {
        return 0.13; // Dogs lose fun relatively quickly, needing more playtime
    }

    /**
     * Returns the rate at which a dog gets tired and needs sleep.
     * This rate determines how quickly the sleep level decreases over time.
     * 
     * @return the sleep rate for a dog, which is 3.
     */
    @Override
    public double getSleepRate() {
        return 0.05; // Dogs have a slower rate of getting tired compared to hunger and play rates
    }
}
