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
        HashMap countries = new HashMap();
        Country denmark = new Country("denmark");
        Country columbia = new Country("columbia");
        countries.put(denmark.getName(), denmark);
        countries.put(columbia.getName(), columbia);
        
        return countries;
    }

}
