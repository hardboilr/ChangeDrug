/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author sebastiannielsen
 */
public class Medicin {
    
    private String name;
    private double price;
    private int healing;
    
    
    public Medicin(String nameInput, double priceInput, int healingInput){
        this.name = nameInput;
        this.price = priceInput;
        this.healing = healingInput;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getHealing() {
        return healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }
    
    
    
    
    
}
