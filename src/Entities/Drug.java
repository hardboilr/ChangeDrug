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
    private double basePrice, modifiedPrice;
    private int baseAvail, modifiedAvail;

    public Drug(String nameInput, double basePriceInput, double modifiedPriceInput, int baseAvailInput, int modifiedAvailInput) {
        this.name = nameInput;
        this.basePrice = basePriceInput;
        this.modifiedPrice = modifiedPriceInput;
        this.baseAvail = baseAvailInput;
        this.modifiedAvail = modifiedAvailInput;
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

    public double getModifiedPrice() {
        return modifiedPrice;
    }

    public void setModifiedPrice(double modifiedPrice) {
        this.modifiedPrice = modifiedPrice;
    }

    public int getModifiedAvail() {
        return modifiedAvail;
    }

    public void setModifiedAvail(int modifiedAvail) {
        this.modifiedAvail = modifiedAvail;
    }

    public int getBaseAvail() {
        return baseAvail;
    }

    public void setBaseAvail(int baseAvail) {
        this.baseAvail = baseAvail;
    }


}


