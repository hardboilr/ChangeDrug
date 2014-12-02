/**
 * @author Tobias & Sebastian
 */
package Interfaces;

import Entities.Drug;
import Entities.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface EngineInterface  {

    /**
     * Pre:
     * Post: set current country
     */

    public void setActiveCountry(String input);
    
    public String getActiveCountry();
    
    public Map travel();
    
    public List getEvents();
    
    public ArrayList getCountries();
    
    public void addToInventory(Drug drugInput);
    public void removeFromInventory(String input);
    
    
    public Drug getInventoryDrug(String key);
    
    public Map<String, Drug> getInventory();
    
    public void removeInventoryDrug(String drugkey);
    
    public void createPlayer(String input1, double input2);
    
    public void calculateCredits(double price);
    
    //it might be enough to use getPlayer()!
    public double getCredits(); 
    
    public double getStartCredits();
    
    public Player getPlayer();
    
    public void savePlayerToHighscore(String filename);
    
    public List<Player> loadHighscore(String filename);
    
    
    
    
}
