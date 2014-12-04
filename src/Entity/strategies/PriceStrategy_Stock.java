/**
 * @author Tobias & Sebastian
 */
package Entity.strategies;

import Interface.PriceStrategy;
import java.util.ArrayList;
import java.util.Random;
import StockExchange.StockFinder;

public class PriceStrategy_Stock implements PriceStrategy {

    private double basePrice;
    private int baseAmount;
    private Random random;
    private ArrayList<String> companyNameList1;
    private ArrayList<String> companyNameList2;
    private ArrayList<Double> companyStockToPrice;
    private ArrayList<Double> companyStockToAmount;

    public PriceStrategy_Stock() {
        basePrice = 4000;
        baseAmount = 30;
        random = new Random();
        //companyToPrice
        companyNameList1 = new ArrayList<>();
        companyNameList2 = new ArrayList<>();
        companyNameList1.add("CAR");//1
        companyNameList1.add("AWRE"); //2
        companyNameList1.add("ACLS");//3
        companyNameList1.add("AXGN");//4
        companyNameList1.add("AXTI");//5
        companyNameList1.add("BCOM");//6
        companyNameList1.add("AWRE");//7
        companyNameList1.add("HAYN");//8
        companyNameList1.add("BEAV");//9
        companyNameList1.add("BIDU");//10
        companyNameList1.add("BCPC");//11
        companyNameList1.add("BWINA");//12
        companyNameList1.add("BWINB");//13
        companyNameList1.add("BLDP");//14
        companyNameList1.add("BANC");//15
        companyNameList1.add("NCIT");//16
        companyNameList1.add("POZN");//17
        companyNameList1.add("BANF");//18
        companyNameList1.add("STEM");//19
        companyNameList1.add("BKMU");//20
        //companyToAmount
        companyNameList2.add("MDIV");//1
        companyNameList2.add("FTGC");//2
        companyNameList2.add("UTEK");//3
        companyNameList2.add("HYLS");//4
        companyNameList2.add("YDIV");//5
        companyNameList2.add("SKYY");//6
        companyNameList2.add("CU");//7
        companyNameList2.add("PLTM");//8
        companyNameList2.add("AKRX");//9
        companyNameList2.add("QABA");//10
        companyNameList2.add("FONE");//11
        companyNameList2.add("ALSK");//12
        companyNameList2.add("QCLN");//13
        companyNameList2.add("CARZ");//14
        companyNameList2.add("RDVY");//15
        companyNameList2.add("QQEW");//16
        companyNameList2.add("QQXT");//17
        companyNameList2.add("QTEC");//18
        companyNameList2.add("FTSL");//19
        companyNameList2.add("FUNC");//20

        companyStockToPrice = StockFinder.getStockData(companyNameList1);
        companyStockToAmount = StockFinder.getStockData(companyNameList2);
        //------print arrays for testing purposes--------//
        /*for (int i = 0; i < companyStockToPrice.size(); i++) {
            System.out.println("CompanyStockToPrice is: " + companyStockToPrice.get(i));
        }
        for (int i = 0; i < companyStockToAmount.size(); i++) {
            System.out.println("CompanyStockToAmount is: " + companyStockToAmount.get(i));
        }*/

    }

    @Override
    public double calculateNewPrice() {
        double newPrice = 0;
        int arraySize = companyStockToPrice.size();
        if (arraySize > 0) {
            int indexPos = random.nextInt(companyStockToPrice.size());
            newPrice = companyStockToPrice.get(indexPos);
            if (newPrice > 0) {
                return newPrice;
            }
        }
        return basePrice;

    }

    @Override
    public int calculateNewAmount() {
        double newAmount = 0;
        int arraySize = companyStockToAmount.size();
        if (arraySize > 0) {
            int indexPos = random.nextInt(companyStockToPrice.size());
            newAmount = companyStockToPrice.get(indexPos);
            if (newAmount > 0) {
                return (int) newAmount;
            }
        }
        return baseAmount;
    }

}
