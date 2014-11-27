/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tobias
 */
public class Country {
    
    private String name;
    private List<Drug> drugs;
    private Drug heroin = new Drug("Heroin", 1600.00, 0.0, 15, 0);
    private Drug cocaine = new Drug("Cocaine", 1200.00, 0.0, 30, 0);
    private Drug amphetamine = new Drug("Amphetamine", 200.00, 0.0, 50, 0); 
    
    public Country(String nameInput){
        this.name = nameInput;
        drugs = new LinkedList();
        drugs.add(heroin);
        drugs.add(cocaine);
        drugs.add(amphetamine);
           
    }

    @Override
    public String toString() {
        return  name;
    }
    
    

    public String getName() {
        return name;
    }

    public List getDrugs() {
        return drugs;
    }

    
    
    
    
    
}
