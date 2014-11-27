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
        Country denmark = new Country("Denmark");
        Country columbia = new Country("Columbia");
        Country germany = new Country("Germany");
        Country usa = new Country("USA");
        Country france = new Country("France");
        Country afghanistan = new Country("Afghanistan");
        
        countries.put(denmark.getName(), denmark);
        countries.put(columbia.getName(), columbia);
        countries.put(germany.getName(), germany);
        countries.put(usa.getName(), usa);
        countries.put(france.getName(), france);
        countries.put(afghanistan.getName(), afghanistan);
        
        return countries;
    }

}
