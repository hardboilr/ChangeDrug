/**
 * @author Tobias & Sebastian
 */
package Entity.strategies;

import Interface.PriceStrategy;
import StockExchange.ExchangeFinder;
import java.util.ArrayList;
import java.util.Random;

public class PriceStrategy_Exchange implements PriceStrategy {

    private String baseCurrency;
    private ArrayList<String> currencies;
    private ArrayList<Double> exchangeRate;
    Random random;

    public PriceStrategy_Exchange() {
        baseCurrency = "USD";
        currencies = new ArrayList();
        exchangeRate =  new ArrayList();
        currencies.add("JPY");
        currencies.add("GBP");
        currencies.add("AUD");
        currencies.add("EUR");
        currencies.add("ESP");
        currencies.add("GHS");
        currencies.add("ILS");
        currencies.add("KES");
        currencies.add("JOD");
        currencies.add("LKR");
        currencies.add("LVL");
        currencies.add("MAD");
        currencies.add("MWK");
        currencies.add("NOK");
        currencies.add("PHP");
        currencies.add("NOK"); //again!
        currencies.add("PKR");
        currencies.add("RUB");
        currencies.add("SGD");
        random = new Random();

        for (String currency : currencies) {
            exchangeRate.add(ExchangeFinder.getExchangeRate(baseCurrency, currency));
        }
    }

    @Override
    public double calculateNewPrice() {
        int randomIndex = random.nextInt(exchangeRate.size());
        double newPrice = exchangeRate.get(randomIndex) * 100;
        return newPrice;
    }

    @Override
    public int calculateNewAmount() {
        int randomIndex = random.nextInt(exchangeRate.size());
        double temp = exchangeRate.get(randomIndex);
        int newAmount = (int) temp;
        if (newAmount < 10) {
            newAmount = 10;
        }
        return newAmount;
    }
}
