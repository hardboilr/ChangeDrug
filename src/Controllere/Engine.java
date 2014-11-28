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
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Engine implements EngineInterface {

    private Map<String, Country> countries;
    private Player player;
    private String activeCountry;
    private int randomUpOrDown;
    private Country tempCountry;
    private final int DAY_CYCLE = 20;
    private int day;
    
    Random random;

    public Engine() {
        countries = World.createWorld();
        random = new Random();
        activeCountry = "Denmark";
       // player = new Player("hardboilr"); //for now we instantiate our player here
        day = DAY_CYCLE;
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
    public String getActiveCountry() {
        return activeCountry;
    }

    @Override
    public List travel() {
        tempCountry = (Country) countries.get(activeCountry);
        System.out.println(tempCountry);
        List<Drug> tempList = tempCountry.getDrugs();
        countries.remove(activeCountry);

        for (Drug drug : tempList) {
            drug.setModifiedPrice(calculatePrice(drug.getBasePrice()));
            drug.setModifiedAvail(calculateAvailability(drug.getBaseAvail()));
        }

        countries.put(tempCountry.getName(), tempCountry);
        return tempList;

    }
    
    @Override
    public void createPlayer(String input) {
        player = new Player(input);
    }

    private double calculatePrice(double basePrice) {

        int randomPercent = (int) (Math.random() * 85 + 1);
        double priceModifier;
        double sum;
        randomUpOrDown = random.nextInt(2);
        priceModifier = (basePrice * randomPercent) / 100;
        if (randomUpOrDown == 1) {
            sum = basePrice + priceModifier;
            return sum;

        } else {
            sum = basePrice - priceModifier;
            return sum;
        }

    }

    private int calculateAvailability(int baseAvail) {
        int randomPercent = (int) (Math.random() * 55 + 15);
        int availModifier = (int) (baseAvail * randomPercent) / 100;
        int sum;
        randomUpOrDown = random.nextInt(2);
        if (randomUpOrDown == 1) {
            sum = baseAvail + availModifier;
            return sum;
        } else {
            sum = baseAvail - availModifier;
            return sum;
        }
    }

    @Override
    public void calculateCredits(double price) {
        double credits = player.getCredits() + price;
        player.setCredits(credits);
    }

    @Override
    public double getCredits() {
        return player.getCredits();
    }
    
    @Override
    public Player getPlayer() {
        return player;
        
    }

    @Override
    public void savePlayers(List<String> playerList, String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Player> loadPlayers(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
  

   


}
