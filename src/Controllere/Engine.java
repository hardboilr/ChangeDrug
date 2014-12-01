/**
 * @author Tobias & Sebastian
 */
package Controllere;

import Entities.Player;
import Entities.World;
import Entities.Country;
import Entities.Drug;
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
    private Map<String, Drug> inv;
    private List<Event> events;
    private Player player;
    private String activeCountry;
    private int randomUpOrDown;
    private Country tempCountry;
    private final int DAY_CYCLE = 20;
    private final double START_CREDITS = 5000.00;
    private int day;
    private List<Player> playerList;

    Random random;

    public Engine() {
        countries = World.createWorld();
        random = new Random();
        activeCountry = "Denmark";
        playerList = new ArrayList<>();
        events = new ArrayList<>();
        inv = new HashMap();
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
    public void addToInventory(Drug drugInput) {
        Drug drug = drugInput;
        String key = drug.getName();
        if (inv.containsKey(key)) {
            Drug temp = inv.get(key);
            inv.remove(key);
            temp.setModifiedAvail(temp.getModifiedAvail() + 1);
            inv.put(temp.getName(), temp);
        } else {
            inv.put(key, drug);
        }
    }

    @Override
    public void removeFromInventory(String input) {
        String key = input;
        if (inv.containsKey(key)) {
            Drug temp = inv.get(key);
            inv.remove(key);
            temp.setModifiedAvail(temp.getModifiedAvail() - 1);
            inv.put(key, temp);
            if (temp.getModifiedAvail() == 0) {
                inv.remove(key);
            }
        }
    }

    @Override
    public Drug getInventoryDrug(String key) {
        Drug inventoryDrug = inv.get(key);
        return inventoryDrug;
    }

    @Override
    public Map travel() {
        tempCountry = (Country) countries.get(activeCountry);
        Map<String, Drug> tempMap = tempCountry.getDrugs();
        countries.remove(activeCountry);

        for (Drug drug : tempMap.values()) {
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

    private List createEvents() {
        Event event1 = new Event("customAuthority", "You are captured by the Custom Authority", 5, 0.10, 0.00, 0.50);
        events.add(event1);

        return events;
    }

    @Override
    public List getEvents() {
        List<String> tempArray = new ArrayList();
        for (Event event : events) {
            Random random = new Random();
            int prob = random.nextInt(100) + 1;
            if (prob <= event.getProbability()) {
                tempArray.add(event.getDescription());

            }
        }
        return tempArray;
    }

    @Override
    public void createPlayer(String input1, double input2) {
        player = new Player(input1, input2);
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

    @Override
    public double getStartCredits() {
        return START_CREDITS;
    }

    @Override
    public Player getPlayer() {
        return player;

    }

    @Override
    public void addPlayer() {
        playerList.add(player);

    }

    @Override
    public void savePlayer(String filename) {
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
