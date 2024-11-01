/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license.default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package virtualpet2.Pet;

/**
 * PetData class represents the essential data of a virtual pet, encapsulating
 * information like hunger, fun, and sleep levels, as well as the pet's ID, age, and type.
 * This class is serializable to facilitate easy storage and retrieval of pet data.
 * 
 * @author: vrishab
 */

import java.io.Serializable;

public class PetData implements Serializable {
    // Unique ID of the pet
    private int petId;

    // The name of the pet
    private String name;

    // The hunger level of the pet, ranging from 0 to 100
    private double hunger;

    // The age of the pet
    private int age;

    // The fun level of the pet, ranging from 0 to 100
    private double fun;

    // The type of the pet (e.g., Cat, Dog, Bird)
    private String petType;

    // The sleep level of the pet, ranging from 0 to 100
    private double sleep;

    // ID of the pet's owner
    private int ownerId;

    /**
     * Constructor to initialize a PetData object with all necessary attributes.
     *
     * @param petId   The unique ID of the pet
     * @param name    The name of the pet
     * @param hunger  The hunger level of the pet
     * @param age     The age of the pet
     * @param fun     The fun level of the pet
     * @param sleep   The sleep level of the pet
     * @param petType The type of the pet (e.g., Cat, Dog, Bird)
     * @param ownerId The ID of the pet's owner
     */
    public PetData(int petId, String name, double hunger, int age, double fun, double sleep, String petType, int ownerId) {
        this.petId = petId;
        this.name = name;
        this.hunger = hunger;
        this.age = age;
        this.fun = fun;
        this.sleep = sleep;
        this.petType = petType;
        this.ownerId = ownerId;
    }

    // Getter and setter methods for each attribute to enable controlled access and modification.

    /**
     * Gets the pet's unique ID.
     * @return the pet's ID.
     */
    public int getPetId() {
        return petId;
    }

    /**
     * Sets the pet's unique ID.
     * @param petId the ID to assign to the pet.
     */
    public void setPetId(int petId) {
        this.petId = petId;
    }

    /**
     * Gets the pet's name.
     * @return the pet's name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     * @param name the name to assign to the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pet's hunger level.
     * @return the hunger level as a double.
     */
    public double getHunger() {
        return hunger;
    }

    /**
     * Sets the pet's hunger level.
     * @param hunger the hunger level to assign to the pet.
     */
    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    /**
     * Gets the pet's age.
     * @return the pet's age as an integer.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the pet's age.
     * @param age the age to assign to the pet.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the pet's fun level.
     * @return the fun level as a double.
     */
    public double getFun() {
        return fun;
    }

    /**
     * Sets the pet's fun level.
     * @param fun the fun level to assign to the pet.
     */
    public void setFun(double fun) {
        this.fun = fun;
    }

    /**
     * Gets the pet's sleep level.
     * @return the sleep level as a double.
     */
    public double getSleep() {
        return sleep;
    }

    /**
     * Sets the pet's sleep level.
     * @param sleep the sleep level to assign to the pet.
     */
    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    /**
     * Gets the pet's type.
     * @return the pet's type as a String (e.g., Cat, Dog, Bird).
     */
    public String getPetType() {
        return petType;
    }

    /**
     * Sets the pet's type.
     * @param petType the type to assign to the pet (e.g., Cat, Dog, Bird).
     */
    public void setPetType(String petType) {
        this.petType = petType;
    }

    /**
     * Gets the owner's ID.
     * @return the owner's ID as an integer.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the owner's ID.
     * @param ownerId the ID of the owner to assign to the pet.
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
