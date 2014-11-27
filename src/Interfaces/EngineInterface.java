/**
 * @author Tobias & Sebastian
 */
package Interfaces;

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
    
    public void createPlayer();
    
    public void calculateCredits(double price);
    
    public double getCredits();
    
    public void savePlayers(List<String> playerList, String filename);
    
    public List<String> loadPlayers(String filename);
    
    
    
}
