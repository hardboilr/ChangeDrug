/**
 * @author Tobias & Sebastian
 */
package Controllere;

import Entities.Player;
import Entities.World;
import Entities.Country;
import Entities.Drug;
import Interfaces.EngineInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Engine implements EngineInterface {

    private Map<String, Country> countries;
    private Player player;
    private String activeCountry;
    private int randomUpOrDown;
    private Country tempCountry;

    public Engine() {
       countries = World.createWorld();
       // randomUpOrDown = (int) (Math.random() * 1 + 0);
        activeCountry = "denmark";
        tempCountry = countries.get("denmark");
        player = new Player("hardboilr", 100, 5000); //for now we instantiate our player here
    }

    @Override
    public ArrayList getCountries() {
        ArrayList<String> countryArrayList = new ArrayList<>();
        for (String key : countries.keySet()) {
            countryArrayList.add(key);
        }
        return countryArrayList;
    }

    @Override
    public void setActiveCountry(String countryInput) {
        this.activeCountry = countryInput;
    }
    @Override
    public String getActiveCountry(){
        return activeCountry;
    }

    @Override
    public List travel() {
        tempCountry = (Country) countries.get(activeCountry);
        List<Drug> tempList = tempCountry.getDrugs();
        countries.remove(activeCountry);

        for (Drug drug : tempList) {
            drug.setBasePrice(calculatePrice(drug.getBasePrice()));
            drug.setBaseAvail(calculateAvailability(drug.getBaseAvail()));
        }

        countries.put(tempCountry.getName(), tempCountry);

        return tempCountry.getDrugs();

    }

    private double calculatePrice(double basePrice) {

        int randomPercent = (int) (Math.random() * 85 + 1);
        double priceModifier;
        double sum;
        priceModifier = (basePrice * randomPercent) / 100;
        if (randomUpOrDown == 1) {
            sum = basePrice + priceModifier;
            System.out.println("adding" + sum);
            return sum;
            
        } else {
            sum = basePrice - priceModifier;
            System.out.println("sub" + sum);
            return sum;
        }

    }

    private int calculateAvailability(int baseAvail) {
        int randomPercent = (int) (Math.random() * 55 + 15);
        int availModifier = (int) (baseAvail * randomPercent) / 100;
        int sum;
        if (randomUpOrDown == 1) {
            sum = baseAvail + availModifier;
            System.out.println("adding: " + sum);
            return sum;
        } else {
            sum = baseAvail - availModifier;
            System.out.println("sub: " + sum);
            return sum;
        }
    }

    public void createPlayer() {

    }

}
