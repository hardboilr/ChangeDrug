package StockExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * The class contains helper method to get prices (stock quotes) for stocks.
 *
 * Example of use:
 *
 *       //Create a new ArrayList to hold stock names: ArrayList<String> stockNames =
 * new ArrayList(); //Adding stock names stockNames.add("Z"); //Zillow
 * stockNames.add("GOOG"); //Google.com stockNames.add("MSDN"); //Microsoft
 *
 *       //New ArrayList to hold the answer: ArrayList<Double> result; result =
 * StockFinder.getStockData(stockNames); //Getting the answer
 *
 *       //Running through the answer arraylist for (Double price : result) {
 * System.out.println("Price: " + price); }
 *
 * @author Peter Lorensen
 */
public class StockFinder {

    /**
     * The method gets a list of stock names and returns the price for each one.
     *
     * @param stockShortNames ArrayList<String> of stock names. These must be
     * the short names used by NASDAQ (like GOOG for google.com).
     * @return ArrayList<Double> the corosponding prices from each stock.
     *
     * A default of 1.0 is used if the price for a stock cannot be found and
     * this is also printed: System.out.println("Could not get stock quote and
     * used default value of 1.0 instead.");
     */
    public static ArrayList<Double> getStockData(ArrayList<String> stockShortNames) {
        ArrayList<Double> answer = new ArrayList<Double>();
        String infoToGet = "g";  //Changing this will change the info you get.
        String allStocks = "";
        String temp = "";

        //Running through the parameter and getting all the stock names into a format that Yahoo finance needs                
        for (String stock : stockShortNames) {
            allStocks += stock + "+";
        }
        allStocks = allStocks.substring(0, allStocks.length() - 1); //Cutting of the last, extra '+'-sign

        try {
            //Creating a connection and support object and getting the stock prices from yahoo finance:
            URL yahoofinance = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + allStocks + "&f=" + infoToGet);
//            System.out.println(yahoofinance);
            URLConnection yc = yahoofinance.openConnection();  //Opening a connection
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream())); //Creating a bufferedreader to hold stuff and loading it up

            //Running through the answer            
            while ((temp = in.readLine()) != null) {
                if (temp.equalsIgnoreCase("N/A")) //If price is not there, then...
                {
                    temp = "1.0";                   //..default value of 1.0 is used instead
//                    System.out.println("Could not get stock quote and used default value of 1.0 instead.");
                }
//                System.out.println("Found price and adding to array!");
                try {
                answer.add(Double.parseDouble(temp));  //Adding price to result arraylist
                }
                catch (NumberFormatException ne) {
//                    System.out.println("Catched numberformatException!");
                    temp = "1.0";
                    answer.add(Double.parseDouble(temp));
                }
            }
            in.close(); //Closing the stream
            //Error handling:
        } catch (IOException ex) {
            System.out.println("Oops something went wrong in method getStockData(). Sorry. Try again.");
            System.out.println("Message from Exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException n) {
            System.out.println("Oops something went wrong in method getStockData(): A NumberFormatException was thrown because Double.parseDouble() tried to pass this: " + temp);
            System.out.println("Message from Exception: " + n.getMessage());
        }

        return answer;
    }
}
