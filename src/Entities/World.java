/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashMap;

/**
 *
 * @author Tobias
 */
public class World {
    
    public static HashMap createWorld() {
        HashMap countries = new HashMap<>();
        Country denmark = new Country("Denmark",300.00);
        Country columbia = new Country("Columbia",200.00);
        Country germany = new Country("Germany",500.00);
        Country usa = new Country("USA",2000.00);
        Country france = new Country("France",500.00);
        Country afghanistan = new Country("Afghanistan",400.00);
        countries.put(denmark.getName(), denmark);
        countries.put(columbia.getName(), columbia);
        countries.put(germany.getName(), germany);
        countries.put(usa.getName(), usa);
        countries.put(france.getName(), france);
        countries.put(afghanistan.getName(), afghanistan);
        return countries;
    }
    
    public static HashMap hospital(){
        HashMap medicin = new HashMap<>();
        Medicin med1 = new Medicin("Bandage", 50.00, 2);
        Medicin med2 = new Medicin("Aspirin", 150.00, 5);
        Medicin med3 = new Medicin("Bloodtransfusion", 500.00, 25);
        Medicin med4 = new Medicin("Surgery", 5000.00,50);
        Medicin med5 = new Medicin("Morphine", 200.00,15);
        
        medicin.put(med1.getName(), med1);
        medicin.put(med2.getName(), med2);
        medicin.put(med3.getName(), med3);
        medicin.put(med4.getName(), med4);
        medicin.put(med5.getName(), med5);
        
        return medicin;
        
    }
    
}


