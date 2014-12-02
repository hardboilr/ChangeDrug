/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tobias
 */
public class Country {
    
    private String name;
    private double ticketPrice;
    private Map<String, Drug> drugs;
    private Drug heroin = new Drug("Heroin", 1600.00, 0.0, 15, 0, 10);
    private Drug cocaine = new Drug("Cocaine", 1200.00, 0.0, 30, 0, 15);
    private Drug amphetamine = new Drug("Amphetamine", 200.00, 0.0, 50, 0, 7); 
    private Drug acid = new Drug("Acid", 550, 0.0, 33, 0, 5);
    private Drug angelDust = new Drug("Angel Dust", 400, 0.0, 60, 0, 7);
    private Drug crystalMeth = new Drug("Crystal Meth", 800, 0.0, 38, 0, 12);
    private Drug hash = new Drug ("Hash", 180, 0.0, 100, 0, 4);
    private Drug weed = new Drug ("Weed", 150, 0.0, 115, 0, 5);
    private Drug mushrooms = new Drug ("Mushrooms", 120, 0.0, 95, 0, 7);
    private Drug valium = new Drug("Valium", 290, 0.0, 80, 0, 7);
    
    
    public Country(String nameInput, double ticketInput){
        this.name = nameInput;
        this.ticketPrice = ticketInput;
        drugs = new HashMap();
        drugs.put(heroin.getName(),heroin);
        drugs.put(cocaine.getName(), cocaine);
        drugs.put(amphetamine.getName(), amphetamine);
        drugs.put(acid.getName(), acid);
        drugs.put(angelDust.getName(), angelDust);
        drugs.put(crystalMeth.getName(), crystalMeth);
        drugs.put(hash.getName(), hash);
        drugs.put(weed.getName(), weed);
        drugs.put(mushrooms.getName(), mushrooms);
        drugs.put(valium.getName(), valium);
           
    }

    @Override
    public String toString() {
        return  name;
    }

    public String getName() {
        return name;
    }

    public Map getDrugs() {
        return drugs;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    
    

    
    
    
    
    
}
