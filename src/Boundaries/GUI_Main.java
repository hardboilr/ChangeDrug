/**
 * @author Tobias & Sebastian
 */
package Boundaries;

import Controllere.Engine;
import Entities.Country;
import Entities.Product;
import Entities.Medicin;
import Interfaces.EngineInterface;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends javax.swing.JFrame {

    private Map<String, Product> marketMap;
    private final Map<String, Medicin> medicinMap;
    private Map<String,String> purchasedMedicin; 

    private final int typeColumn = 0;
    private final int nameColumn = 1;
    private final int qtyColumn = 2;
    private final int priceColumn = 3;

    //Images
    private final ImageIcon selectionArrow_right_pressed_icon;
    private final ImageIcon selectionArrow_right_icon;
    private final ImageIcon selectionArrow_left_pressed_icon;
    private final ImageIcon selectionArrow_left_icon;
    private final ImageIcon clemenza1_icon;
    private final ImageIcon brasi2_icon;
    private final ImageIcon deNiro3_icon;
    private final ImageIcon kay4_icon;
    private final ImageIcon michael5_icon;
    private final ImageIcon sollozo6_icon;
    private final ImageIcon talia7_icon;
    private final ImageIcon vitelli8_icon;
    private final ImageIcon vito9_icon;

    //variables
    private int nextImg;
    private int avail;
    private double MarketPrice;

    private final EngineInterface engine;
    private final DecimalFormat doubleCreditFormat;

    public GUI_Main() {
        initComponents();
        this.setLocationRelativeTo(null); //place window in center of screen
        engine = new Engine();
        engine.createPlayer("", 0.00);
        marketMap = engine.travel();
        medicinMap = engine.getMedicin();
        purchasedMedicin = new HashMap<>();
        //prepareRound();
        setLocationText();
        formatTables();
        engine.loadPlayers("players.txt");
        doubleCreditFormat = new DecimalFormat("0.00");

        nextImg = 0;
        //init icons
        selectionArrow_right_pressed_icon = new ImageIcon("./src/art/gui/selctionArrow_right_pressed.png");
        selectionArrow_right_icon = new ImageIcon("./src/art/gui/selctionArrow_right.png");
        selectionArrow_left_pressed_icon = new ImageIcon("./src/art/gui/selctionArrow_left_pressed.png");
        selectionArrow_left_icon = new ImageIcon("./src/art/gui/selctionArrow_left.png");
        clemenza1_icon = new ImageIcon("./src/art/characters/1_clemenza.png");
        brasi2_icon = new ImageIcon("./src/art/characters/2_brasi.png");
        deNiro3_icon = new ImageIcon("./src/art/characters/3_deNiro.png");
        kay4_icon = new ImageIcon("./src/art/characters/4_kay.png");
        michael5_icon = new ImageIcon("./src/art/characters/5_michael.png");
        sollozo6_icon = new ImageIcon("./src/art/characters/6_sollozo.png");
        talia7_icon = new ImageIcon("./src/art/characters/7_talia.png");
        vitelli8_icon = new ImageIcon("./src/art/characters/8_vitelli.png");
        vito9_icon = new ImageIcon("./src/art/characters/9_vito.png");

        //hide images related to [create player]
        jLabel_characterPic.setVisible(false);
        jLabel_selectionLeft.setVisible(false);
        jLabel_selectionRight.setVisible(false);
        jLabel_name.setVisible(false);
        jTextField_inputName.setVisible(false);
        jLabel_money.setVisible(false);
        jLabel_loan.setVisible(false);
        jButton_confirm.setVisible(false);

        //deactivate buttons
        jButton_travel.setEnabled(false);
        jButton_buy.setEnabled(false);
        jButton_buyMedicin.setEnabled(false);
        jButton_sell.setEnabled(false);
        jButton_bulkBuy.setEnabled(false);
        jButton_bulkSell.setEnabled(false);

        //setup progressBars
        jProgressBar_days.setVisible(false);
        jProgressBar_life.setVisible(false);

    }

    private void setLocationText() {
        //---------------------------------------------------------
        //Updates the [jLabel_location]-label with current country
        //---------------------------------------------------------
        jLabel_location.setText("Location: " + engine.getActiveCountry().toUpperCase().charAt(0)
                + engine.getActiveCountry().substring(1));
    }

    private void prepareRound() {
        //---------------------------------------------
        //Fill [jList_countries]-list with countries 
        //fill [jTable_market]-table with drugs
        //---------------------------------------------
        purchasedMedicin.clear();
        marketMap = engine.travel();
        List<String> eventList = engine.getEvents();

        if (eventList.size() > 0) {
            for (String eventDescrip : eventList) {
                String descrip = eventDescrip;
                jTextArea_eventMessage.append(descrip+"\n");
                jTextArea_eventMessage.setCaretPosition(jTextArea_eventMessage.getDocument().getLength());
                updateLife();
            }
        }

        //fill country table
        ((DefaultTableModel) jTable_airport.getModel()).setRowCount(0);
        for (int i = 0; i < engine.getCountries().size(); i++) {
            Country country = (Country) engine.getCountries().get(i);
            String countryName = country.getName().toUpperCase().charAt(0) + country.getName().substring(1);
            double ticketPrice = country.getTicketPrice();
            ((DefaultTableModel) jTable_airport.getModel()).addRow(new Object[]{});
            jTable_airport.setValueAt(countryName, i, 0);
            jTable_airport.setValueAt(ticketPrice, i, 1);
        }
        
        
        //fill hospital table
        ((DefaultTableModel) jTable_hospital.getModel()).setRowCount(0);
        int count = 0;
        for (Medicin medicin: medicinMap.values()) {
            String medicinName = medicin.getName().toUpperCase().charAt(0) + medicin.getName().substring(1);
            double medicinPrice = medicin.getPrice();
            ((DefaultTableModel) jTable_hospital.getModel()).addRow(new Object[]{});
            jTable_hospital.setValueAt(medicinName, count, 0);
            jTable_hospital.setValueAt(medicinPrice, count, 1);
            count++;
        }

        // fill market table
        ((DefaultTableModel) jTable_market.getModel()).setRowCount(0);
        count = 0;
        for (Product product : marketMap.values()) {
            String productType = product.getType();
            String name = product.getName();
            Double price = product.getModifiedPrice();
            avail = product.getModifiedAvail();
            ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
            jTable_market.setValueAt(productType, count, typeColumn);
            jTable_market.setValueAt(name, count, nameColumn);
            jTable_market.setValueAt(avail, count, qtyColumn);
            jTable_market.setValueAt(price, count, priceColumn);
            count++;
        }
    }

    private void changeCharacterIcon() {
        //------------------------------------------------------------------------------------
        //Looks at value of [nextImg]-int and changes [jLabel_characterPic]-label accordingly
        //------------------------------------------------------------------------------------
        switch (nextImg) {
            case 1:
                jLabel_characterPic.setIcon(clemenza1_icon);
                jLabel_selectionLeft.setVisible(false);
                break;
            case 2:
                jLabel_characterPic.setIcon(brasi2_icon);
                jLabel_selectionLeft.setVisible(true);
                break;
            case 3:
                jLabel_characterPic.setIcon(deNiro3_icon);
                break;
            case 4:
                jLabel_characterPic.setIcon(kay4_icon);
                break;
            case 5:
                jLabel_characterPic.setIcon(michael5_icon);
                break;
            case 6:
                jLabel_characterPic.setIcon(sollozo6_icon);
                break;
            case 7:
                jLabel_characterPic.setIcon(talia7_icon);
                break;
            case 8:
                jLabel_characterPic.setIcon(vitelli8_icon);
                jLabel_selectionRight.setVisible(true);
                break;
            case 9:
                jLabel_characterPic.setIcon(vito9_icon);
                jLabel_selectionRight.setVisible(false);
                break;
        }
    }

    private boolean buy() {
        //------------------------------------------------------------------------------------
        //Looks at data in selected row in [jTable_market]-table
        //If player has enough credits, then transfer 1 quantity of market drug to inventory
        //Calculates new avg. price of particular drug in inventory
        //Creates a new row in inventory if player does not own particular drug already
        //Deletes drug from market if qty of particular drug reaches 0
        //------------------------------------------------------------------------------------
        int selectedRow = jTable_market.getSelectedRow();
        String productType = "";
        String selectedDrugName = "";
        int newMarketQty = 0;
        double sufficiantCredits = engine.getCredits() - MarketPrice;;
        MarketPrice = 0;
        int add = 1;
        Product marketDrug;
        try {
            //-------------------------------------------------------------------
            //get drug name + price from market table + calculate new qty
            productType = (String) jTable_market.getValueAt(selectedRow, typeColumn);
            selectedDrugName = (String) jTable_market.getValueAt(selectedRow, nameColumn);
            newMarketQty = (int) jTable_market.getValueAt(selectedRow, qtyColumn) - 1;
            MarketPrice = (double) jTable_market.getValueAt(selectedRow, priceColumn);

            //-------------------------------------------------------------------
        } catch (ArrayIndexOutOfBoundsException ex) {
            sufficiantCredits = -1.00;
        }

        if ((sufficiantCredits >= 0)) { //if we have sufficiant credits
            //-----------------------------------------------------------
            //update market table + create drug + add to player inv
            jTable_market.setValueAt(newMarketQty, selectedRow, qtyColumn);
            Product drug = new Product(productType, selectedDrugName, 0, MarketPrice, 0, add, 0);
            engine.addToInventory(drug);
            int newInvQty = engine.getInventoryDrug(selectedDrugName).getModifiedAvail();
            //-----------------------------------------------------------
            if (jTable_inventory.getRowCount() == 0) { //is table is empty?
                ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                jTable_inventory.setValueAt(productType, 0, typeColumn);
                jTable_inventory.setValueAt(selectedDrugName, 0, nameColumn);
                jTable_inventory.setValueAt(newInvQty, 0, qtyColumn);
                jTable_inventory.setValueAt(MarketPrice, 0, priceColumn);

            } else {
                boolean drugExist = false; //we have drug 
                for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
                    String inventoryDrug = (String) jTable_inventory.getValueAt(i, nameColumn);
                    if (selectedDrugName.equals(inventoryDrug)) { //do we already have the drug in the table?
                        drugExist = true;
                        jTable_inventory.setValueAt(newInvQty, i, qtyColumn);
                        //set new average price
                        double currentInventoryPrice = (double) jTable_inventory.getValueAt(i, priceColumn);
                        int currentQuantity = (int) jTable_inventory.getValueAt(i, qtyColumn);
                        double newAveragePrice = ((currentInventoryPrice * currentQuantity)
                                + (MarketPrice)) / (currentQuantity + add);
                        jTable_inventory.setValueAt(newAveragePrice, i, priceColumn);
                        break;
                    }
                }
                if (drugExist == false) { //we do not have the drug in the table);
                    int rowPosition = jTable_inventory.getRowCount();
                    ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                    jTable_inventory.setValueAt(productType, rowPosition, typeColumn);
                    jTable_inventory.setValueAt(selectedDrugName, rowPosition, nameColumn);
                    jTable_inventory.setValueAt(newInvQty, rowPosition, qtyColumn);
                    jTable_inventory.setValueAt(MarketPrice, rowPosition, priceColumn);
                }
            }
            if (newMarketQty == 0) {
                ((DefaultTableModel) jTable_market.getModel()).removeRow(selectedRow);
            }
            return true;
        }
        return false;
    }

    private boolean sell() {
        //----------------------------------------------------------------
        //Looks at data in selected row in [jTable_inventory]-table
        //Transfer 1 quantity of inventory drug to market
        //Creates a new row in market if particular drug does not exist
        //Deletes drug from inventory if qty of particular drug reaches 0
        //----------------------------------------------------------------
        int row = jTable_inventory.getSelectedRow();
        String productType = "";
        String inventoryDrug = "";
        int newInventoryQty;
        try {
            productType = (String) jTable_inventory.getValueAt(row, typeColumn);
            inventoryDrug = (String) jTable_inventory.getValueAt(row, nameColumn);
            newInventoryQty = (int) jTable_inventory.getValueAt(row, qtyColumn) - 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            newInventoryQty = -1;
            return false;
        }
        Boolean productExists = false;
        if (newInventoryQty >= 0 && jTable_market.getRowCount() > 0) {
            engine.subtractFromInventory(inventoryDrug);
            jTable_inventory.setValueAt(newInventoryQty, row, qtyColumn);
            for (int i = 0; i < jTable_market.getRowCount(); i++) {
                String marketDrug = (String) jTable_market.getValueAt(i, nameColumn);
                if (inventoryDrug.equals(marketDrug)) {
                    int newQty = (int) jTable_market.getValueAt(i, qtyColumn) + 1;
                    MarketPrice = (double) jTable_market.getValueAt(i, priceColumn);
                    jTable_market.setValueAt(newQty, i, qtyColumn);
                    productExists = true;
                    break;
                }
            }
        } 
        if (productExists == false) {
            int rowIndex = jTable_market.getRowCount();
            ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
            jTable_market.setValueAt(productType, rowIndex, typeColumn);
            jTable_market.setValueAt(inventoryDrug, rowIndex, nameColumn);
            jTable_market.setValueAt(1, rowIndex, qtyColumn);
            jTable_market.setValueAt(marketMap.get(inventoryDrug).getModifiedPrice(), rowIndex, priceColumn);
        }

        if (newInventoryQty == 0) {
            ((DefaultTableModel) jTable_inventory.getModel()).removeRow(row);
        }
        return true;
    }

    private void autoSell() {
        //---------------------------------------------------
        //Runs through all rows in [jTable_inventory]-table
        //Sell every drug in each row to market
        //Add profit to credits
        //---------------------------------------------------
        for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
            String inventoryDrug = (String) jTable_inventory.getValueAt(i, 0);
            int inventoryQty = (int) jTable_inventory.getValueAt(i, 1);
            for (int j = 0; j < jTable_market.getRowCount(); j++) {
                String marketDrug = (String) jTable_market.getValueAt(j, 0);
                if (inventoryDrug.equals(marketDrug)) {
                    double marketPrice = (double) jTable_market.getValueAt(j, 2);
                    int newQty = (int) jTable_market.getValueAt(i, 1) + 1;
                    this.MarketPrice = inventoryQty * marketPrice;
                    engine.calculateCredits(this.MarketPrice);
                    break;
                }
            }
        }
    }

    private void formatTables() {
        jTable_market.setSelectionMode(0);
        jTable_inventory.setSelectionMode(0);
    }

    private void performBuy() {
        jTable_inventory.clearSelection();
        if (buy() == true) {
            engine.calculateCredits(-MarketPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
        }
    }

    private void performSell() {
        jTable_market.clearSelection();
        if (sell() == true) {
            engine.calculateCredits(MarketPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
        }
    }

    private void updateLife() {
        int newLife = engine.getPlayer().getLife();
        jProgressBar_life.setValue(newLife);
        jProgressBar_life.setString(newLife + "% health");
    }

    private void updateDays() {
        int newDay = engine.getPlayer().getDays();
        jProgressBar_days.setValue(newDay);
        jProgressBar_days.setString(newDay + " days left");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton_highscore = new javax.swing.JButton();
        jButton_confirm = new javax.swing.JButton();
        jPanel_player = new javax.swing.JPanel();
        jLabel_characterPic = new javax.swing.JLabel();
        jLabel_TEXT_info = new javax.swing.JLabel();
        jLabel_TEXT_name = new javax.swing.JLabel();
        jLabel_name = new javax.swing.JLabel();
        jLabel_TEXT_money = new javax.swing.JLabel();
        jLabel_money = new javax.swing.JLabel();
        jLabel_selectionRight = new javax.swing.JLabel();
        jLabel_selectionLeft = new javax.swing.JLabel();
        jTextField_inputName = new javax.swing.JTextField();
        jProgressBar_life = new javax.swing.JProgressBar();
        jLabel_location = new javax.swing.JLabel();
        jProgressBar_days = new javax.swing.JProgressBar();
        jLabel_TEXT_loan = new javax.swing.JLabel();
        jLabel_loan = new javax.swing.JLabel();
        jButton_newGame = new javax.swing.JButton();
        jPanel_market = new javax.swing.JPanel();
        jScrollPane_market = new javax.swing.JScrollPane();
        jTable_market = new javax.swing.JTable();
        jLabel_TEXT_market1 = new javax.swing.JLabel();
        jPanel_inventory = new javax.swing.JPanel();
        jScrollPane_inventory = new javax.swing.JScrollPane();
        jTable_inventory = new javax.swing.JTable();
        jLabel_TEXT_market = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton_buy = new javax.swing.JButton();
        jButton_sell = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton_bulkSell = new javax.swing.JButton();
        jButton_bulkBuy = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_eventMessage = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_location = new javax.swing.JPanel();
        jButton_travel = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_airport = new javax.swing.JTable();
        jPanel_Hospital = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_hospital = new javax.swing.JTable();
        jButton_buyMedicin = new javax.swing.JButton();
        jPanel_bank = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextfield_loanAmount = new javax.swing.JTextField();
        jButton_loan1Day = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton_loan1Week = new javax.swing.JButton();
        jButton_loanRefund = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thug Life 0.1");
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_highscore.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_highscore.setText("Highscore");
        jButton_highscore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_highscoreActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_highscore, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jButton_confirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_confirm.setText("Confirm");
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        jPanel_player.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_player.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_characterPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/characters/1_clemenza.png"))); // NOI18N
        jLabel_characterPic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_characterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_characterPic.setMaximumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setMinimumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel_player.add(jLabel_characterPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 110, 160));

        jLabel_TEXT_info.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_TEXT_info.setText("Info");
        jPanel_player.add(jLabel_TEXT_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel_TEXT_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_TEXT_name.setText("Name: ");
        jPanel_player.add(jLabel_TEXT_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_name.setText("Hardboilr");
        jLabel_name.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jLabel_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 130, 30));

        jLabel_TEXT_money.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_TEXT_money.setText("Money:");
        jLabel_TEXT_money.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_money, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        jLabel_money.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_money.setText("5000");
        jLabel_money.setToolTipText("");
        jPanel_player.add(jLabel_money, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 20));

        jLabel_selectionRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/gui/selctionArrow_right.png"))); // NOI18N
        jLabel_selectionRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_selectionRightMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_selectionRightMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel_selectionRightMouseReleased(evt);
            }
        });
        jPanel_player.add(jLabel_selectionRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, -1, -1));

        jLabel_selectionLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/gui/selctionArrow_left.png"))); // NOI18N
        jLabel_selectionLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_selectionLeftMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_selectionLeftMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel_selectionLeftMouseReleased(evt);
            }
        });
        jPanel_player.add(jLabel_selectionLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));

        jTextField_inputName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_inputName.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jTextField_inputName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 130, 30));

        jProgressBar_life.setBackground(new java.awt.Color(204, 0, 0));
        jProgressBar_life.setName(""); // NOI18N
        jProgressBar_life.setStringPainted(true);
        jPanel_player.add(jProgressBar_life, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        jLabel_location.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_location.setText("Location: current location");
        jPanel_player.add(jLabel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jProgressBar_days.setStringPainted(true);
        jPanel_player.add(jProgressBar_days, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        jLabel_TEXT_loan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_TEXT_loan.setText("Loan");
        jLabel_TEXT_loan.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_loan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        jLabel_loan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_loan.setText("0");
        jLabel_loan.setToolTipText("");
        jPanel_player.add(jLabel_loan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, 20));

        getContentPane().add(jPanel_player, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 480, 180));

        jButton_newGame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_newGame.setText("New game");
        jButton_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newGameActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_newGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel_market.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_market.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_market.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_market.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_market.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable_market.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_marketMouseClicked(evt);
            }
        });
        jScrollPane_market.setViewportView(jTable_market);
        if (jTable_market.getColumnModel().getColumnCount() > 0) {
            jTable_market.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable_market.getColumnModel().getColumn(2).setResizable(false);
            jTable_market.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable_market.getColumnModel().getColumn(3).setResizable(false);
            jTable_market.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel_market.add(jScrollPane_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 220));

        jLabel_TEXT_market1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market1.setText("Market");
        jPanel_market.add(jLabel_TEXT_market1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 320, 270));

        jPanel_inventory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_inventory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_inventory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_inventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Qty", "Avg. price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_inventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_inventoryMouseClicked(evt);
            }
        });
        jScrollPane_inventory.setViewportView(jTable_inventory);
        if (jTable_inventory.getColumnModel().getColumnCount() > 0) {
            jTable_inventory.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable_inventory.getColumnModel().getColumn(2).setResizable(false);
            jTable_inventory.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable_inventory.getColumnModel().getColumn(3).setResizable(false);
            jTable_inventory.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel_inventory.add(jScrollPane_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 310, 220));

        jLabel_TEXT_market.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market.setText("Inventory");
        jPanel_inventory.add(jLabel_TEXT_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, 330, 270));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_buy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_buy.setText("Buy >>");
        jButton_buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buyActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_buy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        jButton_sell.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_sell.setText("<< Sell ");
        jButton_sell.setMaximumSize(new java.awt.Dimension(79, 30));
        jButton_sell.setMinimumSize(new java.awt.Dimension(79, 30));
        jButton_sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sellActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_sell, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, 100, 110));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_bulkSell.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_bulkSell.setText("<< x10");
        jButton_bulkSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_bulkSellActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_bulkSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        jButton_bulkBuy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_bulkBuy.setText("x10 >>");
        jButton_bulkBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_bulkBuyActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_bulkBuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 100, 110));

        jTextArea_eventMessage.setColumns(20);
        jTextArea_eventMessage.setRows(5);
        jScrollPane2.setViewportView(jTextArea_eventMessage);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 480, 60));

        jPanel_location.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton_travel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_travel.setText("Travel");
        jButton_travel.setToolTipText("");
        jButton_travel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_travelActionPerformed(evt);
            }
        });

        jTable_airport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Country", "Ticketprice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable_airport);
        if (jTable_airport.getColumnModel().getColumnCount() > 0) {
            jTable_airport.getColumnModel().getColumn(0).setResizable(false);
            jTable_airport.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel_locationLayout = new javax.swing.GroupLayout(jPanel_location);
        jPanel_location.setLayout(jPanel_locationLayout);
        jPanel_locationLayout.setHorizontalGroup(
            jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_locationLayout.createSequentialGroup()
                        .addComponent(jButton_travel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_locationLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel_locationLayout.setVerticalGroup(
            jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_travel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Airport", jPanel_location);

        jPanel_Hospital.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable_hospital.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicin", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable_hospital);
        if (jTable_hospital.getColumnModel().getColumnCount() > 0) {
            jTable_hospital.getColumnModel().getColumn(0).setResizable(false);
            jTable_hospital.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton_buyMedicin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_buyMedicin.setText("Buy");
        jButton_buyMedicin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buyMedicinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_HospitalLayout = new javax.swing.GroupLayout(jPanel_Hospital);
        jPanel_Hospital.setLayout(jPanel_HospitalLayout);
        jPanel_HospitalLayout.setHorizontalGroup(
            jPanel_HospitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_HospitalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_HospitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel_HospitalLayout.createSequentialGroup()
                        .addComponent(jButton_buyMedicin)
                        .addGap(0, 168, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_HospitalLayout.setVerticalGroup(
            jPanel_HospitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_HospitalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_buyMedicin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hospital", jPanel_Hospital);

        jPanel_bank.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Borrow some cash max 10.000$");

        jButton_loan1Day.setText("1 Day ");
        jButton_loan1Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loan1DayActionPerformed(evt);
            }
        });

        jLabel3.setText("35% interest rate");

        jButton_loan1Week.setText("1 Week");
        jButton_loan1Week.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loan1WeekActionPerformed(evt);
            }
        });

        jButton_loanRefund.setText("Refund");
        jButton_loanRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loanRefundActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_bankLayout = new javax.swing.GroupLayout(jPanel_bank);
        jPanel_bank.setLayout(jPanel_bankLayout);
        jPanel_bankLayout.setHorizontalGroup(
            jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_bankLayout.createSequentialGroup()
                .addGroup(jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextfield_loanAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_bankLayout.createSequentialGroup()
                        .addComponent(jButton_loan1Day, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_loan1Week, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_loanRefund, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_bankLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel_bankLayout.setVerticalGroup(
            jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_bankLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextfield_loanAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_loan1Day, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_loan1Week, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_loanRefund, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Loan Shark", jPanel_bank);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 280, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        String name = jTextField_inputName.getText();
        name = name.replaceAll(" ", "").toLowerCase();

        if (!name.isEmpty()) {

            //engine.createPlayer(name, engine.getStartCredits());
            engine.getPlayer().setName(name);
            engine.getPlayer().setCredits(50000.00);
            prepareRound();
            jButton_buy.setEnabled(true);
            jButton_sell.setEnabled(true);
            jButton_bulkBuy.setEnabled(true);
            jButton_bulkSell.setEnabled(true);
            jButton_travel.setEnabled(true);
            jButton_buyMedicin.setEnabled(true);
            jLabel_name.setText(name);
            jLabel_name.setVisible(true);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_money.setVisible(true);
            jLabel_loan.setVisible(true);
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            jTextField_inputName.setVisible(false);
            jLabel_selectionLeft.setVisible(false);
            jLabel_selectionRight.setVisible(false);
            jButton_confirm.setVisible(false);
            jButton_newGame.setVisible(false);
            jProgressBar_days.setMinimum(0);
            jProgressBar_days.setMaximum(engine.getPlayer().getDays());
            updateLife();
            updateDays();
            jProgressBar_days.setVisible(true);
            jProgressBar_life.setVisible(true);
        }
    }//GEN-LAST:event_jButton_confirmActionPerformed

    private void jButton_newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newGameActionPerformed
        jTextField_inputName.setText("");
        jLabel_name.setText("");
        jLabel_money.setText("");
        jTextField_inputName.setVisible(true);
        jLabel_TEXT_info.setText("Input name:");
        jLabel_selectionLeft.setVisible(true);
        jLabel_selectionRight.setVisible(true);
        jLabel_characterPic.setIcon(clemenza1_icon);
        jLabel_characterPic.setVisible(true);
        jButton_confirm.setVisible(true);
        jLabel_selectionLeft.setVisible(false);
        nextImg = 1;
    }//GEN-LAST:event_jButton_newGameActionPerformed

    private void jButton_travelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_travelActionPerformed
        int currentDay = engine.getPlayer().getDays();
        double ticketPrice = (double) jTable_airport.getValueAt(jTable_airport.getSelectedRow(), 1);
        if (currentDay >= 0 && ticketPrice <= engine.getCredits()) {
            engine.getPlayer().setDays(-1);
            engine.getPlayer().setLoanDays(-1);
            updateDays();
            engine.setActiveCountry((String) jTable_airport.getValueAt(jTable_airport.getSelectedRow(), 0));
            setLocationText();
            prepareRound();
            engine.calculateCredits(-ticketPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
        }
        if (currentDay == 0) {
            jButton_travel.setText("Cash in!");
        }
        if (currentDay == -1) {
            autoSell(); //sell everything in inventory
            engine.savePlayerToHighscore("players.txt"); //save player
            GUI_Highscore highscore = new GUI_Highscore((Engine) engine);
            highscore.setVisible(true);
            highscore.setAgainVisibility(true);
            highscore.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_jButton_travelActionPerformed

    private void jButton_buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buyActionPerformed
        performBuy();
    }//GEN-LAST:event_jButton_buyActionPerformed

    private void jButton_sellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sellActionPerformed
        performSell();
    }//GEN-LAST:event_jButton_sellActionPerformed

    private void jTable_marketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_marketMouseClicked
        jTable_inventory.clearSelection();
        for (int i = 0; i < jTable_market.getRowCount(); i++) {
            if (jTable_market.isRowSelected(i) == true) {
                jButton_sell.setEnabled(false);
                jButton_buy.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jTable_marketMouseClicked

    private void jTable_inventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_inventoryMouseClicked
        jTable_market.clearSelection();
        for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
            if (jTable_inventory.isRowSelected(i) == true) {
                jButton_buy.setEnabled(false);
                jButton_sell.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jTable_inventoryMouseClicked

    private void jLabel_selectionRightMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionRightMousePressed
        ++nextImg;
        jLabel_selectionRight.setIcon(selectionArrow_right_pressed_icon);


    }//GEN-LAST:event_jLabel_selectionRightMousePressed

    private void jLabel_selectionRightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionRightMouseReleased
        changeCharacterIcon();
        jLabel_selectionRight.setIcon(selectionArrow_right_icon);
    }//GEN-LAST:event_jLabel_selectionRightMouseReleased

    private void jLabel_selectionLeftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionLeftMousePressed
        --nextImg;
        jLabel_selectionLeft.setIcon(selectionArrow_left_pressed_icon);
    }//GEN-LAST:event_jLabel_selectionLeftMousePressed

    private void jLabel_selectionLeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionLeftMouseReleased
        changeCharacterIcon();
        jLabel_selectionLeft.setIcon(selectionArrow_left_icon);
    }//GEN-LAST:event_jLabel_selectionLeftMouseReleased

    private void jLabel_selectionRightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionRightMouseClicked


    }//GEN-LAST:event_jLabel_selectionRightMouseClicked

    private void jLabel_selectionLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionLeftMouseClicked

    }//GEN-LAST:event_jLabel_selectionLeftMouseClicked

    private void jButton_highscoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_highscoreActionPerformed
        GUI_Highscore highscore = new GUI_Highscore((Engine) engine);
        highscore.setVisible(true);
        highscore.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton_highscoreActionPerformed

    private void jButton_bulkSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_bulkSellActionPerformed
        for (int i = 0; i < 10; i++) {
            performSell();
        }
    }//GEN-LAST:event_jButton_bulkSellActionPerformed

    private void jButton_bulkBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_bulkBuyActionPerformed
        for (int i = 0; i < 10; i++) {
            performBuy();
        }
    }//GEN-LAST:event_jButton_bulkBuyActionPerformed

    private void jButton_buyMedicinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buyMedicinActionPerformed
        String medicinName = (String) jTable_hospital.getValueAt(jTable_hospital.getSelectedRow(), 0);
        double medicinPrice = (double) jTable_hospital.getValueAt(jTable_hospital.getSelectedRow(), 1);
        if (medicinPrice <= engine.getCredits()) {
            engine.calculateCredits(-medicinPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            if (!purchasedMedicin.containsKey(medicinName)) {
                purchasedMedicin.put(medicinName, medicinName);
                int lifemodifier = medicinMap.get(medicinName).getHealing();
                engine.getPlayer().setLife(lifemodifier);
                updateLife();
                jTextArea_eventMessage.append("You bought some " + medicinName + " to eliviate your pains...\n");
            } else {
                jTextArea_eventMessage.append("You bought some more " + medicinName + " but it didnt seem to help ....\n");
            }
        }
    }//GEN-LAST:event_jButton_buyMedicinActionPerformed

    private void jButton_loan1DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loan1DayActionPerformed
        double loanAmount = Double.parseDouble(jTextfield_loanAmount.getText());
        double maxLoan = 10001.00;//System.out.println(loanAmount);
        double existingLoan = engine.getPlayer().getLoan();
        int existingLoanDays = engine.getPlayer().getLoanDays();
       if(loanAmount > -1 && loanAmount < maxLoan && (existingLoan + loanAmount) <= maxLoan){
            engine.getPlayer().setLoan(existingLoan + loanAmount);
            engine.getPlayer().setLoanDays(existingLoanDays + 1);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
        }
        
        
    }//GEN-LAST:event_jButton_loan1DayActionPerformed

    private void jButton_loan1WeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loan1WeekActionPerformed
        double loanAmount = Double.parseDouble(jTextfield_loanAmount.getText());
        double maxLoan = 10001.00;//System.out.println(loanAmount);
        double existingLoan = engine.getPlayer().getLoan();
        int existingLoanDays = engine.getPlayer().getLoanDays();
        if(loanAmount > -1 && loanAmount < maxLoan && (existingLoan + loanAmount) <= maxLoan){
            engine.getPlayer().setLoan(existingLoan + loanAmount);
            engine.getPlayer().setLoanDays(existingLoanDays + 7);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
        } // TODO add your handling code here:
    }//GEN-LAST:event_jButton_loan1WeekActionPerformed

    private void jButton_loanRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loanRefundActionPerformed
        double refundAmount = Double.parseDouble(jTextfield_loanAmount.getText());
        double credits = engine.getCredits();
        double loan = engine.getPlayer().getLoan();
        if(refundAmount <= credits && refundAmount > - 1.0){
            System.out.println("heje");
            if(refundAmount > loan){
                System.out.println("hejeeeee");
                engine.getPlayer().setLoan(loan - loan);
                engine.calculateCredits(-loan);
                jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
                jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            } else{
                engine.getPlayer().setLoan(loan-refundAmount);
                engine.calculateCredits(-refundAmount);
                jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
                jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            }
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton_loanRefundActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_bulkBuy;
    private javax.swing.JButton jButton_bulkSell;
    private javax.swing.JButton jButton_buy;
    private javax.swing.JButton jButton_buyMedicin;
    private javax.swing.JButton jButton_confirm;
    private javax.swing.JButton jButton_highscore;
    private javax.swing.JButton jButton_loan1Day;
    private javax.swing.JButton jButton_loan1Week;
    private javax.swing.JButton jButton_loanRefund;
    private javax.swing.JButton jButton_newGame;
    private javax.swing.JButton jButton_sell;
    private javax.swing.JButton jButton_travel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_TEXT_info;
    private javax.swing.JLabel jLabel_TEXT_loan;
    private javax.swing.JLabel jLabel_TEXT_market;
    private javax.swing.JLabel jLabel_TEXT_market1;
    private javax.swing.JLabel jLabel_TEXT_money;
    private javax.swing.JLabel jLabel_TEXT_name;
    private javax.swing.JLabel jLabel_characterPic;
    private javax.swing.JLabel jLabel_loan;
    private javax.swing.JLabel jLabel_location;
    private javax.swing.JLabel jLabel_money;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JLabel jLabel_selectionLeft;
    private javax.swing.JLabel jLabel_selectionRight;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Hospital;
    private javax.swing.JPanel jPanel_bank;
    private javax.swing.JPanel jPanel_inventory;
    private javax.swing.JPanel jPanel_location;
    private javax.swing.JPanel jPanel_market;
    private javax.swing.JPanel jPanel_player;
    private javax.swing.JProgressBar jProgressBar_days;
    private javax.swing.JProgressBar jProgressBar_life;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane_inventory;
    private javax.swing.JScrollPane jScrollPane_market;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_airport;
    private javax.swing.JTable jTable_hospital;
    private javax.swing.JTable jTable_inventory;
    private javax.swing.JTable jTable_market;
    private javax.swing.JTextArea jTextArea_eventMessage;
    private javax.swing.JTextField jTextField_inputName;
    private javax.swing.JTextField jTextfield_loanAmount;
    // End of variables declaration//GEN-END:variables
}
