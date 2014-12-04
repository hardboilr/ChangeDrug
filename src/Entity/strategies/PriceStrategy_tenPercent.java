package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Random;

/**
 * @author Tobias & Sebastian This implementation of the PriceStrategy interface
 * simply returns 1250000 each time.
 */
public class PriceStrategy_tenPercent implements PriceStrategy {

    private int baseAmount;
    private double basePrice;
    int priceCount;
    int amountCount;

    public PriceStrategy_tenPercent() {

        baseAmount = 40;
        basePrice = 180;
        priceCount = 0;
        amountCount = 0;

    }

    @Override
    public double calculateNewPrice() {
        if (priceCount >= 1) {
            return basePrice * 1.1;
        }
        priceCount++;
        return basePrice;

    }

    @Override
    public int calculateNewAmount() {
        if (amountCount >= 1) {
            baseAmount = (int) (baseAmount * 1.1);
            return baseAmount;
        }
        amountCount++;
        return baseAmount;
    }
}
