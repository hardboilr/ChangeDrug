package Entity.strategies;

import Interface.PriceStrategy;

public class PriceStrategy_Simple2 implements PriceStrategy {

    private boolean flip_price; 
    private boolean flip_amount; 
    private final double one_Price = 90.00;
    private final double two_Price = 300.00;
    private final int one_Amount = 50;
    private final int two_Amount = 180;
    private double productPrice;
    private int productAmount;

    public PriceStrategy_Simple2() {
        flip_price = false;  
        flip_amount = false;
        productPrice = one_Price;
        productAmount = one_Amount;
    }

    @Override
    public double calculateNewPrice() {
        if (flip_price) {
            productPrice = one_Price;
            flip_price = false;
        } else {
            productPrice = two_Price;
            flip_price = true;
        }
        return productPrice;
    }

    @Override
    public int calculateNewAmount() {
        if (flip_amount) {
            productAmount = one_Amount;
            flip_amount = false;
        } else {
            productAmount = two_Amount;
            flip_amount = true;
        }
        return productAmount;
    }
}
