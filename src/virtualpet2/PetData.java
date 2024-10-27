/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2;

/**
 *
 * @author vrishab
 */
import java.io.Serializable;

public class PetData implements Serializable {
    // Instance variables to hold the pet's data
    private String name;  // The name of the pet
    private int hunger;   // The hunger level of the pet
    private int age;      // The age of the pet
    private int fun;      // The fun level of the pet
    private String owner; // The name of the pet's owner
    private String petType; // The type of the pet (e.g., Cat, Dog, Bird)
    private int sleep;    // The sleep level of the pet

    /**
     * Constructor to initialize a PetData object with all necessary attributes.
     * 
     * @param name The name of the pet
     * @param hunger The hunger level of the pet
     * @param age The age of the pet
     * @param fun The fun level of the pet
     * @param sleep The sleep level of the pet
     * @param petType The type of the pet (e.g., Cat, Dog, Bird)
     * @param owner The name of the pet's owner
     */
    public PetData(String name, int hunger, int age, int fun, int sleep, String petType, String owner) {
        this.name = name;         // Initialize the pet's name
        this.hunger = hunger;     // Initialize the pet's hunger level
        this.age = age;           // Initialize the pet's age
        this.fun = fun;           // Initialize the pet's fun level
        this.sleep = sleep;       // Initialize the pet's sleep level
        this.petType = petType;   // Initialize the pet's type
        this.owner = owner;       // Initialize the owner's name
    }

    // Getter and setter methods for each attribute

    /**
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the current hunger level of the pet
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * @param hunger the hunger level to set for the pet
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * @return the current age of the pet
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set for the pet
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the current fun level of the pet
     */
    public int getFun() {
        return fun;
    }

    /**
     * @param fun the fun level to set for the pet
     */
    public void setFun(int fun) {
        this.fun = fun;
    }

    /**
     * @return the current owner of the pet
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set for the pet
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the type of the pet (e.g., Cat, Dog, Bird)
     */
    public String getPetType() {
        return petType;
    }

    /**
     * @param petType the petType to set for the pet
     */
    public void setPetType(String petType) {
        this.petType = petType;
    }

    /**
     * @return the current sleep level of the pet
     */
    public int getSleep() {
        return sleep;
    }

    /**
     * @param sleep the sleep level to set for the pet
     */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
}