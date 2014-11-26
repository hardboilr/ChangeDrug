/**
 * @author Tobias & Sebastian
 */
package Interfaces;


public interface EngineInterface  {

    /**
     * Pre:
     * Post: set current country
     */
    public void setCountry(String input);
    
    public void travel(String country);
    
    public void createPlayer();
    
}
