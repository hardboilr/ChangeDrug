/**
 * @author Tobias & Sebastian
 */
package Entity;

import Entity.strategies.PriceStrategy_BasePrice;
import Entity.strategies.PriceStrategy_Exchange;
import Entity.strategies.PriceStrategy_OldMemory;
import Entity.strategies.PriceStrategy_PickOne_Of_10;
import Entity.strategies.PriceStrategy_SecondsOfClock;
import Entity.strategies.PriceStrategy_Simple2;
import Entity.strategies.PriceStrategy_Stock;
import Entity.strategies.PriceStrategy_Weapon;
import Entity.strategies.PriceStrategy_tenPercent;
import Interface.PriceStrategy;
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
    
    //Product(String typeInput, String nameInput, PriceStrategy strategyInput, int goldenNumber)
    
//    private PriceStrategy oldMemory = new PriceStrategy_OldMemory();
    private PriceStrategy oldMemory = new PriceStrategy_OldMemory();
    private PriceStrategy simple2 = new PriceStrategy_Simple2();
    private PriceStrategy secondsOfClock = new PriceStrategy_SecondsOfClock();
    private PriceStrategy tenPercent = new PriceStrategy_tenPercent();
    private PriceStrategy pickOneOfTen = new PriceStrategy_PickOne_Of_10();
    private PriceStrategy basePrice = new PriceStrategy_BasePrice();
    private PriceStrategy stock = new PriceStrategy_Stock();
    private PriceStrategy exchange = new PriceStrategy_Exchange();
    private PriceStrategy weapon = new PriceStrategy_Weapon();
    private Product heroin = new Product(drugType, "Heroin", basePrice, 10);
    private Product cocaine = new Product(drugType, "Cocaine", oldMemory, 15);
    private Product amphetamine = new Product(drugType, "Amphetamine", stock, 7);
    private Product acid = new Product(drugType, "Acid", exchange, 5);
    private Product angelDust = new Product(drugType, "Angel Dust", tenPercent, 7);
    private Product crystalMeth = new Product(drugType, "Crystal Meth", basePrice, 12);
    private Product hash = new Product(drugType, "Hash", simple2, 4);
    private Product weed = new Product(drugType, "Weed", pickOneOfTen, 5);
    private Product mushrooms = new Product(drugType, "Mushrooms", secondsOfClock, 7);
    private Product valium = new Product(drugType, "Valium", basePrice, 7);
    
    private Product gun = new Product(weaponType, "Beretta92F", weapon, 10);
    private Product highfriends = new Product(friendType, "High friends", basePrice, 15);
    private Product generous = new Product(behavoiurType, "Generous", basePrice, 15);
    private Product niceclothes = new Product(clothingType, "Nice clothes", basePrice, 10);
    private Product firstClass = new Product(behavoiurType, "Travel 1.Class", basePrice, 5);

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
