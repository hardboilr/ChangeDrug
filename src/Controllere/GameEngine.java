/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllere;

import Entities.World;
import Interfaces.ControlInterface;
import java.util.HashMap;

/**
 *
 * @author Tobias
 */
public class GameEngine implements ControlInterface {
    
    private HashMap countries;
    
    public GameEngine() {
        countries = World.createWorld();
        
    }

    @Override
    public void setCountry(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void travel(String country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
