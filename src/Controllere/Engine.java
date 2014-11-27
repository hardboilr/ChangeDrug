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
    
    private Map countries;
    private Player player;
    private String activeCountry;
    private int randomUpOrDown;
        
    public Engine() {
        countries = World.createWorld();
        randomUpOrDown = (int) (Math.random() * 1 + 0);
        
        player = new Player("hardboilr",100,5000); //for now we instantiate our player here
    }



    @Override
    public void travel(String countryInput) {
        this.activeCountry = countryInput;
        Country tempCountry = (Country) countries.get(activeCountry);
        List<Drug> tempList = tempCountry.getDrugs();
        countries.remove(activeCountry);

        for (Drug drug : tempList) {
            drug.setBasePrice(calculatePrice(drug.getBasePrice()));
            drug.setBaseAvail(calculateAvailability(drug.getBaseAvail()));
        }

        countries.put(tempCountry.getName(), tempCountry);

    }

    
    private double calculatePrice(double basePrice) {

        int randomPercent = (int) (Math.random() * 85 + 1);
        double priceModifier;
        priceModifier = (basePrice * randomPercent) / 100;
        if (randomUpOrDown == 1) {
            return basePrice + priceModifier;
        } else {
            return basePrice - priceModifier;
        }

    }

  
    private int calculateAvailability(int baseAvail) {
        int randomPercent = (int) (Math.random() * 55 + 15);
        int availModifier = (int) (baseAvail * randomPercent) / 100;
        if (randomUpOrDown == 1) {
            return baseAvail + availModifier;
        } else {
            return baseAvail - availModifier;
        }
    }
    
    
    
    
    public void createPlayer()  {
        
        
    }
    
}
