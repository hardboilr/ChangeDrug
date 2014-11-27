/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Tobias
 */
public class Drug {
    private String name;
    private double basePrice;
    private int baseAvail;

    public Drug(String nameInput, double basePriceInput, int baseAvailInput) {
        this.name = nameInput;
        this.basePrice = basePriceInput;
        this.baseAvail = baseAvailInput;
    }
    
    public String toString(){
        return "" + name + "," + basePrice + "," + baseAvail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getBaseAvail() {
        return baseAvail;
    }

    public void setBaseAvail(int baseAvail) {
        this.baseAvail = baseAvail;
    }


}


