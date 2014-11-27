/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllere;

import Entities.World;
import Entities.Country;
import Entities.Drug;
import Interfaces.EngineInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tobias
 */
public class Engine implements EngineInterface {
    
    private Map countries;
    private String activeCountry;
    private final List<Integer> upOrDown;
    private int randomUpOrDown;
        
    public Engine() {
        countries = World.createWorld();
        upOrDown = new ArrayList<>();
        upOrDown.add(-1);
        upOrDown.add(1);
        randomUpOrDown = (int) (Math.random() * 1 + 0);
        
    }

    @Override
    public void setCountry(String input) {
        this.activeCountry = input;
    }

    @Override
    public void travel() {

        Country tempCountry = (Country) countries.get(activeCountry);
        List<Drug> tempList = tempCountry.getDrugs();
        countries.remove(activeCountry);

        for (Drug drug : tempList) {
            drug.setBasePrice(calculatePrice(drug.getBasePrice()));
            drug.setBaseAvail(calculateAvailability(drug.getBaseAvail()));
        }

        countries.put(tempCountry.getName(), tempCountry);

    }

    @Override
    public double calculatePrice(double basePrice) {

        int randomPercent = (int) (Math.random() * 85 + 1);
        double priceModifier;
        priceModifier = (basePrice * randomPercent) / 100;
        if (upOrDown.get(randomUpOrDown) > 0) {
            return basePrice + priceModifier;
        } else {
            return basePrice - priceModifier;
        }

    }

    @Override
    public int calculateAvailability(int baseAvail) {
        int randomPercent = (int) (Math.random() * 55 + 15);
        int availModifier = (int) (baseAvail * randomPercent) / 100;
        if (upOrDown.get(randomUpOrDown) > 0) {
            return baseAvail + availModifier;
        } else {
            return baseAvail - availModifier;
        }
    }
    
    
    
    
}
