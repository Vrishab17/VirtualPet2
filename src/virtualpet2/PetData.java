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
    private int petId;      // Unique ID of the pet
    private String name;     // The name of the pet
    private double hunger;      // The hunger level of the pet
    private int age;         // The age of the pet
    private double fun;         // The fun level of the pet
    private String petType;  // The type of the pet (e.g., Cat, Dog, Bird)
    private double sleep;       // The sleep level of the pet
    private int ownerId;     // ID of the pet's owner

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

    // Getter and setter methods for each attribute
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getFun() {
        return fun;
    }

    public void setFun(double fun) {
        this.fun = fun;
    }

    public double getSleep() {
        return sleep;
    }

    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}