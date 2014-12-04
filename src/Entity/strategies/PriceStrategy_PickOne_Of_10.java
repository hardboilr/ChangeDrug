package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Random;

/**
 * 
 * @author Tobias & Sebastian 
 * This implementation of the PriceStrategy interface 
 * simply selects a random price from the 10 hard-coded ones listed here.
 */
public class PriceStrategy_PickOne_Of_10 implements PriceStrategy
{
    //The list of prices to be selected from
    private double[] allPrices = new double[10];
    private int[] allAmounts = new int[10];
    private Random gen;
    
    public PriceStrategy_PickOne_Of_10()
    {
        gen = new Random();
        allPrices[0] = 150;
        allPrices[1] = 230;
        allPrices[2] = 180;
        allPrices[3] = 2350;
        allPrices[4] = 17;
        allPrices[5] = 360;
        allPrices[7] = 190;
        allPrices[8] = 440;
        allPrices[9] = 550;
        
        allAmounts[0] = 100;
        allAmounts[1] = 190;
        allAmounts[2] = 200000;
        allAmounts[3] = 2;
        allAmounts[4] = 95;
        allAmounts[5] = 30;
        allAmounts[7] = 165;
        allAmounts[8] = 185;
        allAmounts[9] = 250;  
    }    


    @Override
    public double calculateNewPrice() {
        return allPrices[ gen.nextInt( allPrices.length)];    
    }

    @Override
    public int calculateNewAmount() {
        return allAmounts[ gen.nextInt( allAmounts.length)]; 
    }
    
}
