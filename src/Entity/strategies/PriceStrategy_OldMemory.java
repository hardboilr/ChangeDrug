package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Random;

/**
 * @author Tobias & Sebastian This implementation of the PriceStrategy interface
 * simply returns 1250000 each time.
 */
public class PriceStrategy_OldMemory implements PriceStrategy {

    private int baseAmount;
    private double basePrice;
    private Random random;
    private int randomUpOrDown;
    private int randomPercent;
    private int priceCount;
    private int amountCount;
    
    public PriceStrategy_OldMemory() {
        random = new Random();
        priceCount = 0;
        amountCount = 0;
        baseAmount = 100;
        basePrice = 1000;
    }

    @Override
    public double calculateNewPrice() {
        if (priceCount == 0) {
        }
        if (priceCount == 1) {
            randomUpOrDown = random.nextInt(2);
            randomPercent = random.nextInt(65) + 11;
            double priceModifier;
            priceModifier = (basePrice * randomPercent) / 100;
            if (randomUpOrDown == 1) {
                basePrice = basePrice + priceModifier + 75.00;
            } else {
                basePrice = basePrice - priceModifier + 75.00;
            }
        }
        if (priceCount >= 2) {
            randomUpOrDown = random.nextInt(2);
            randomPercent = random.nextInt(35) + 11;
            double priceModifier;
            priceModifier = (basePrice * randomPercent) / 100;
            if (randomUpOrDown == 1) {
                basePrice = basePrice + priceModifier + 17.00;
            } else {
                basePrice = basePrice - priceModifier + 17.00;
            }
        }
        priceCount++;
        return basePrice;
    }

    @Override
    public int calculateNewAmount() {
        if (amountCount == 0) {
        }
        if (amountCount == 1) {
            baseAmount+= 7;
        }
        if (amountCount >= 2) {
            baseAmount+= 13;
        }
        amountCount++;
        return baseAmount;
    }
}
