package Entity.strategies;

import Interface.PriceStrategy;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Tobias & Sebastian 
 * This implementation of the PriceStrategy interface 
 * returns a new price depending on the current time. 
 * The price is simply the seconds of the clock multiplied by 2
 * 
 */

public class PriceStrategy_SecondsOfClock implements PriceStrategy

{
    Calendar cal = Calendar.getInstance(TimeZone.getDefault() );
   

    @Override
    public double calculateNewPrice() {
        return ((double)(cal.get(Calendar.SECOND)+1 ) * 2); 
    }
    
    @Override
    public int calculateNewAmount() {
        return ( cal.get(Calendar.SECOND)+1); 
    }
}
