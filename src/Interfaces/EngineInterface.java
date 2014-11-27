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
    
    public void travel();
    
    public double calculatePrice(double baseprice);
    
    public int calculateAvailability(int baseAvail);
    
    public void createPlayer();
    
}
