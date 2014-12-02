/**
 * @author Tobias & Sebastian
 */
package Controllere;

import Entities.Player;
import Entities.World;
import Entities.Country;
import Entities.Product;
import Entities.Event;
import Entities.FileHandler;
import Interfaces.EngineInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Engine implements EngineInterface {

    private Map<String, Country> countries;
    private Map<String, Product> inv;
    private Map<String, Event> eventMap;
    private Player player;
    private String activeCountry;
    private int randomUpOrDown;
    private Country tempCountry;
    private final int DAY_CYCLE = 20;
//    private final double START_CREDITS = 50000.00;
    private int day;
    private List<Player> playerList;

    Random random;

    public Engine() {
        countries = World.createWorld();
        random = new Random();
        activeCountry = "Denmark";
        playerList = new ArrayList<>();
        eventMap = new HashMap<>();
        inv = new HashMap();
        day = DAY_CYCLE;
    }

    @Override
    public ArrayList<Country> getCountries() {
        ArrayList<Country> possibleTravelDestinations = new ArrayList<>();
        for (Country country : countries.values()) {
            if (!country.getName().equals(activeCountry)) {
                possibleTravelDestinations.add(country);
            }
        }
        return possibleTravelDestinations;
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
    public void addToInventory(Product drugInput) {
        Product drug = drugInput;
        String key = drug.getName();
        if (inv.containsKey(key)) {
            Product temp = inv.get(key);
            inv.remove(key);
            temp.setModifiedAvail(temp.getModifiedAvail() + 1);
            inv.put(temp.getName(), temp);
        } else {
            inv.put(key, drug);
        }
    }

    @Override
    public void subtractFromInventory(String input) {
        String key = input;
        if (inv.containsKey(key)) {
            Product temp = inv.get(key);
            inv.remove(key);
            temp.setModifiedAvail(temp.getModifiedAvail() - 1);
            inv.put(key, temp);
            if (temp.getModifiedAvail() == 0) {
                inv.remove(key);
            }
        }
    }

    @Override
    public Product getInventoryDrug(String key) {
        Product inventoryDrug = inv.get(key);
        return inventoryDrug;
    }

    @Override
    public Map travel() {
        tempCountry = (Country) countries.get(activeCountry);
        Map<String, Product> tempMap = tempCountry.getDrugs();
        countries.remove(activeCountry);
        eventMap.clear();
        if (player.getDays() != player.getDayCycle()) {
            createEvents();
        }
        for (Product drug : tempMap.values()) {
            drug.setModifiedPrice(calculatePrice(drug.getBasePrice()));
            int goldenNumber = (int) ((Math.random() * 100) + 1);
            randomUpOrDown = random.nextInt(2);
            if (goldenNumber >= 1 && goldenNumber <= drug.getGoldenNumber()) {
                if (randomUpOrDown == 1) {
                    drug.setModifiedPrice(drug.getModifiedPrice() * 10);
                } else {
                    drug.setModifiedPrice(drug.getModifiedPrice() / 10);
                }
            }
            drug.setModifiedAvail(calculateAvailability(drug.getBaseAvail()));
        }
        countries.put(tempCountry.getName(), tempCountry);
        return tempMap;
    }

    @Override
    public void createPlayer(String input1, double input2) {
        player = new Player(input1, input2);
    }

    @Override
    public List getEvents() {
        List<String> eventDescrp = new ArrayList();
        for (Event event : eventMap.values()) {
            Random random = new Random();
            int prob = random.nextInt(100) + 1;
            if (prob <= event.getProbability()) {
                eventDescrp.add(event.getDescription());
                player.setLife((int) (player.getLife() - (player.getLife() * event.getLifeModifier())));
                player.setCredits(player.getCredits() - (player.getCredits() * event.getCreditsModifier()));
                for (Product drug : inv.values()) {
                    drug.setModifiedAvail((int) (drug.getModifiedAvail() * event.getDrugModifier()));
                }
            }
        }
        return eventDescrp;
    }

    private void createEvents() {
        Event event1 = new Event("customAuthority", "You are captured by the Custom Authority", 100, 0.10, 0.00, 0.50);
        Event event2 = new Event("angryPusher", "You met an angry Pusher", 100, 0.20, 0.00, 1);
        eventMap.put(event1.getName(), event1);
        eventMap.put(event2.getName(), event2);
        
        for (Event event : eventMap.values()) {
            switch (event.getName()) {

                case "customAuthority":
                    if (inv.containsKey("High friends")) {
                        event.setProbability(-2);
                        
                    }
                System.out.println("Prob for customAuthority: " + event.getProbability());
                case "angryPusher":
                    if (inv.containsKey("Beretta92F")) {
                        event.setProbability(-2);
                        
                    }
                    if (inv.containsKey("High friends")) {
                        event.setProbability(-1);
                    }
                    if (inv.containsKey("Generous")) {
                        event.setProbability(-1);
                    }
                System.out.println("Prob for angryPusher: " + event.getProbability());
            }
        }
    }

    private double calculatePrice(double basePrice) {

        int randomPercent = (int) ((Math.random() * 85) + 1);
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
        int randomPercent = (int) ((Math.random() * 55) + 15);
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

//    @Override
//    public double getStartCredits() {
//        return START_CREDITS;
//    }

    @Override
    public Player getPlayer() {
        return player;

    }

    @Override
    public void addPlayer() {
        playerList.add(player);

    }

    @Override
    public void savePlayerToHighscore(String filename) {
        playerList.add(player);
        FileHandler.savePlayer(playerList, filename);
    }

    @Override
    public List<Player> loadPlayers(String filename) {
        playerList = FileHandler.loadPlayers(filename);
        Collections.sort(playerList);
        return playerList;
    }

}
