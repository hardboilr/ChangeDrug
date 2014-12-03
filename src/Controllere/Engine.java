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
import Entities.Medicin;
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
    private Map<String, Medicin> medicin;
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
        medicin = World.hospital();
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
    public Map<String, Medicin> getMedicin(){
        return medicin;
        
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
        if (inventoryDrug != null) {
            System.out.println("Found it!");
            return inventoryDrug;
        }
        return null;
    }

    @Override
    public Map travel() {
        tempCountry = (Country) countries.get(activeCountry);
        Map<String, Product> tempMap = tempCountry.getDrugs();
        countries.remove(activeCountry);
        eventMap.clear();
        calculateLoanWithInterestRate();
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
                player.setLife((int) - (player.getLife() * event.getLifeModifier()));
                player.setCredits(player.getCredits() - (player.getCredits() * event.getCreditsModifier()));
                for (Product product : inv.values()) {
                    product.setModifiedAvail((int) (product.getModifiedAvail() * event.getDrugModifier()));
                }
            }
        }
        return eventDescrp;
    }

    private void createEvents() {
        Event event1 = new Event("customAuthority", "You are captured by the Custom Authority and lost half of your drugs", 10, 0.10, 0.00, 0.50);
        Event event2 = new Event("angryPusher", "You met an angry Pusher", 10, 0.20, 0.00, 1);
        Event event3 = new Event ("mafiaTerritory", "You have entered the local mafias territory and lost all your money and halfof your drugs", 10, 0.10, 1.00, 0.50);
        Event event4 = new Event ("hospital", "You have been injured, and you need to go to the hospital", 5, 0.40, 0.00, 1);
        Event event5 = new Event("minionPusher", "You have hired a local pusher, and gets his profits", 5, 0.00, -0.15, 1);
        Event event6 = new Event("girlfriend", "You are lucky and got a new Girlfriend", 5, 0.00, -0.5, 1);
        Event event7 = new Event("loanShark", "You were beaten by the loan shark, because you have an unpaid deposit", 0, .40, 0.00, 1);
        eventMap.put(event1.getName(), event1);
        eventMap.put(event2.getName(), event2);
        eventMap.put(event3.getName(), event3);
        eventMap.put(event4.getName(), event4);
        eventMap.put(event5.getName(), event5);
        eventMap.put(event6.getName(), event6);
        eventMap.put(event7.getName(), event7);
        
        
        for (Event event : eventMap.values()) {
            switch (event.getName()) {

                case "customAuthority":
                    if (inv.containsKey("High friends")) {
                        event.setProbability(-2);     
                    }
                    if (inv.containsKey("Nice clothes")){
                        event.setProbability(-1);
                    }
                    if(inv.containsKey("Travel 1.Class")){
                        event.setProbability(-1);
                    }
                break;
                
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
                break;
                
                case "mafiaTerritory":
                    if (inv.containsKey("High friends")) {
                        event.setProbability(-2);
                    }
                    if (inv.containsKey("Beretta92F")) {
                        event.setProbability(-1);    
                    }
                break;
                
                case "minionPusher":
                    if (inv.containsKey("High friends")) {
                        event.setProbability(+4);
                    }
                    if (inv.containsKey("Generous")) {
                        event.setProbability(+2);
                    }
                    if (inv.containsKey("Nice clothes")){
                        event.setProbability(+2);
                    }
                break;
                
                case "girlfriend":
                    if (inv.containsKey("Nice clothes")){
                        event.setProbability(+3);
                    }
                    if(inv.containsKey("Travel 1.Class")){
                        event.setProbability(+2);
                    }
                    if (inv.containsKey("High friends")) {
                        event.setProbability(+3);
                    }
                    if (inv.containsKey("Generous")) {
                        event.setProbability(+4);
                    }
                break;
                
                case "loanShark":
                    if (player.getLoanDays() == 0 && player.getLoan() > 1) {
                        event.setProbability(+40);    
                    }
                break;
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
    
    private void calculateLoanWithInterestRate(){
        double interestRate = 0.35;
        double newLoan = player.getLoan() + (player.getLoan()* interestRate);
        player.setLoan(newLoan);
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
