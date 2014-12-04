/**
 * @author sebastiannielsen
 */
package Entity;

public class Medicin {

    private String name;
    private double price;
    private int healing;

    public Medicin(String nameInput, double priceInput, int healingInput) {
        this.name = nameInput;
        this.price = priceInput;
        this.healing = healingInput;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getHealing() {
        return healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }
}
