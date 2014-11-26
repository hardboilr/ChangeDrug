/**
 * @author Tobias & Sebastian
 */
package Controllere;

import Entities.Player;
import Entities.World;
import Interfaces.EngineInterface;
import java.util.HashMap;


public class Engine implements EngineInterface {
    
    private HashMap countries;
    private Player player;
    
    public Engine() {
        countries = World.createWorld();
        player = new Player("hardboilr",100,5000); //for now we instantiate our player here
    }

    @Override
    public void setCountry(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void travel(String country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void createPlayer()  {
        
        
    }
    
}
