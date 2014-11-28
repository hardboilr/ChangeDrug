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
    
    public void createPlayer(String input);
    
    public void calculateCredits(double price);
    
    //it might be enough to use getPlayer()!
    public double getCredits(); 
    
    public Player getPlayer();
    
    public void savePlayers(List<String> playerList, String filename);
    
    public List<String> loadPlayers(String filename);
    
    
    
}
