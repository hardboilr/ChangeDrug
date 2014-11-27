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
    
}
