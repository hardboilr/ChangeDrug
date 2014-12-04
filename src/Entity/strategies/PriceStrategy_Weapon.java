/*
 /**
 * @author Tobias & Sebastian
 */
package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Random;

public class PriceStrategy_Weapon implements PriceStrategy {
    
    private double basePrice;
    private int baseAmount;
    private Random random;
    
    public PriceStrategy_Weapon() {
        basePrice = 10000;
        baseAmount = 10;
        random = new Random();
        
        
        
    }

    @Override
    public double calculateNewPrice() {
        int randomPercent = random.nextInt(95) + 1;
        double priceModifier;
        double newPrice;
        int randomUpOrDown = random.nextInt(2);
        priceModifier = (basePrice * randomPercent) / 100;
        if (randomUpOrDown == 1) {
            newPrice = basePrice + priceModifier;
            return newPrice;
        } else {
            newPrice = basePrice - priceModifier;
            return newPrice;
        }
    }

    @Override
    public int calculateNewAmount() {
        int randomPercent = random.nextInt(20) + 1;
        int amountModifier = (int) (baseAmount * randomPercent) / 100;
        int newAmount;
        int randomUpOrDown = random.nextInt(2);
        if (randomUpOrDown == 1) {
            newAmount = baseAmount + amountModifier;
            return newAmount;
        } else {
            newAmount = baseAmount - amountModifier;
            return newAmount;
        }
    }
}
