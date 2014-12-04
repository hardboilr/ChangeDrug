package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Random;

/**
 * @author Tobias & Sebastian This implementation of the PriceStrategy interface
 * simply returns 1250000 each time.
 */
public class PriceStrategy_BasePrice implements PriceStrategy {

    private double basePrice;
    private int baseAmount;
    private Random random;

    public PriceStrategy_BasePrice() {
        basePrice = 1200;
        baseAmount = 20;
        random = new Random();
    }

    @Override
    public double calculateNewPrice() {
        int randomPercent = (int) ((Math.random() * 85) + 1);
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
        int randomPercent = (int) ((Math.random() * 55) + 15);
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
