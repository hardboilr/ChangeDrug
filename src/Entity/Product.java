/**
 * @author Tobias & Sebastian
 */
package Entity;

import Interface.PriceStrategy;

public class Product {

    private String type, name;
    private double price;
    private int amount, goldenNumber;
    private PriceStrategy priceStrategy;

    public Product(String typeInput, String nameInput, PriceStrategy strategyInput, int goldenNumber) {
        this.type = typeInput;
        this.name = nameInput;
        this.priceStrategy = strategyInput;
        this.goldenNumber = goldenNumber;
        calculateNewPrice_forThisProduct();
        calculateNewAmount_forThisProduct();
    }

    public void calculateNewPrice_forThisProduct() {
        price = priceStrategy.calculateNewPrice();
    }

    public void calculateNewAmount_forThisProduct() {
        amount = priceStrategy.calculateNewAmount();
    }

    @Override
    public String toString() {
        return "" + name + "," + price + "," + amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double priceInput) {
        this.price = priceInput;
    }
    
    public PriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amountInput) {
        this.amount = amountInput;
    }

    public int getGoldenNumber() {
        return goldenNumber;
    }

    public String getType() {
        return type;
    }
}
