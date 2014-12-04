package Interface;

/**
 * This interface captures that which changes in the program:
 * -the algorithm that calculates a price for the car.
 * 
 * @author pelo
 */
public interface PriceStrategy
{    

    /**
     * This method must calculate and return a new price for what ever
     * class that implements it. In this program it calculates prices
     * for cars. 
     * The algorithm for the price is different for each implementation.
     * @return int the new price of the car.
     */
    public double calculateNewPrice();
    
    public int calculateNewAmount();
}
