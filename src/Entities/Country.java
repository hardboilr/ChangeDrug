/**
 * @author Tobias & Sebastian
 */
package Entities;

import java.util.HashMap;
import java.util.Map;

public class Country {

    private String drugType = "Drug";
    private String weaponType = "Weapon";
    private String friendType = "Friend";
    private String clothingType = "Clothes";
    private String behavoiurType = "Behaviour";
    private String name;
    private double ticketPrice;
    private Map<String, Product> products;
    private Product heroin = new Product(drugType, "Heroin", 1600.00, 0.0, 15, 0, 10);
    private Product cocaine = new Product(drugType, "Cocaine", 1200.00, 0.0, 30, 0, 15);
    private Product amphetamine = new Product(drugType, "Amphetamine", 200.00, 0.0, 50, 0, 7);
    private Product acid = new Product(drugType, "Acid", 550, 0.0, 33, 0, 5);
    private Product angelDust = new Product(drugType, "Angel Dust", 400, 0.0, 60, 0, 7);
    private Product crystalMeth = new Product(drugType, "Crystal Meth", 800, 0.0, 38, 0, 12);
    private Product hash = new Product(drugType, "Hash", 180, 0.0, 100, 0, 4);
    private Product weed = new Product(drugType, "Weed", 150, 0.0, 115, 0, 5);
    private Product mushrooms = new Product(drugType, "Mushrooms", 120, 0.0, 95, 0, 7);
    private Product valium = new Product(drugType, "Valium", 290, 0.0, 80, 0, 7);
    private Product gun = new Product(weaponType, "Beretta92F", 10500, 0.0, 5, 0, 10);
    private Product highfriends = new Product(friendType, "High friends", 15000, 0.0, 1, 0, 15);
    private Product generous = new Product(behavoiurType, "Generous", 12000, 0.0, 1, 0, 15);
    private Product niceclothes = new Product(clothingType, "Nice clothes", 30000, 0.0, 1, 0, 10);
    private Product firstClass = new Product(behavoiurType, "Travel 1.Class", 20000, 0.0, 1, 0, 5);

    public Country(String nameInput, double ticketInput) {
        this.name = nameInput;
        this.ticketPrice = ticketInput;
        products = new HashMap();
        products.put(heroin.getName(), heroin);
        products.put(cocaine.getName(), cocaine);
        products.put(amphetamine.getName(), amphetamine);
        products.put(acid.getName(), acid);
        products.put(angelDust.getName(), angelDust);
        products.put(crystalMeth.getName(), crystalMeth);
        products.put(hash.getName(), hash);
        products.put(weed.getName(), weed);
        products.put(mushrooms.getName(), mushrooms);
        products.put(valium.getName(), valium);
        products.put(gun.getName(), gun);
        products.put(highfriends.getName(), highfriends);
        products.put(generous.getName(), generous);
        products.put(niceclothes.getName(), niceclothes);
        products.put(firstClass.getName(), firstClass);

    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Map getDrugs() {
        return products;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
