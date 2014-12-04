/**
 * @author Tobias & Sebastian
 */
package Interface;

import Entity.Medicin;
import Entity.Product;
import Entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface EngineInterface {

    public void setActiveCountry(String input);

    public String getActiveCountry();

    public Map travel();

    public List getEvents();

    public Map getEventMap();

    public ArrayList getCountries();

    public Map<String, Medicin> getMedicin();

    public void addToInventory(Product drugInput);

    public void subtractFromInventory(String input);

    public Product getInventoryDrug(String key);

    public String getInventoryDrugName(String key);

    public void createPlayer(String input1, double input2);

    public void calculateCredits(double price);

    public double getCredits();

    public Player getPlayer();

    public void addPlayer();

    public void savePlayerToHighscore(String filename);

    public List<Player> loadPlayers(String filename);
}
