/**
 * @author Tobias & Sebastian
 */
package Interfaces;

import Entities.Player;
import java.util.ArrayList;
import java.util.List;


public interface EngineInterface  {

    /**
     * Pre:
     * Post: set current country
     */

    public void setActiveCountry(String input);
    
    public String getActiveCountry();
    
    public List travel();
    
    public ArrayList getCountries();
    
    public void createPlayer(String input1, double input2);
    
    public void calculateCredits(double price);
    
    //it might be enough to use getPlayer()!
    public double getCredits(); 
    
    public double getStartCredits();
    
    public Player getPlayer();
    
    public void addPlayer();
    
    public void savePlayer(String filename);
    
    public List<Player> loadPlayers(String filename);
    
    
    
}
