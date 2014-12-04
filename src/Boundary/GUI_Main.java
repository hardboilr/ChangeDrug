/**
 * @author Tobias & Sebastian
 */
package Boundary;

import Control.Engine;
import Entity.Country;
import Entity.Product;
import Entity.Medicin;
import Interface.EngineInterface;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends javax.swing.JFrame {

    private Map<String, Product> marketMap;
    private final Map<String, Medicin> medicinMap;
    private Map<String, String> purchasedMedicin;

    private final int column_type = 0;
    private final int column_name = 1;
    private final int column_qty = 2;
    private final int column_price = 3;

    //Images
    private ImageIcon icon_selectionArrow_right_pressed = null;
    private ImageIcon icon_selectionArrow_right = null;
    private ImageIcon icon_selectionArrow_left_pressed = null;
    private ImageIcon icon_selectionArrow_left = null;
    private ImageIcon icon_clemenza1 = null;
    private ImageIcon icon_brasi2 = null;
    private ImageIcon icon_deNiro3 = null;
    private ImageIcon icon_kay4 = null;
    private ImageIcon icon_michael5 = null;
    private ImageIcon icon_sollozo6 = null;
    private ImageIcon icon_talia7 = null;
    private ImageIcon icon_vitelli8 = null;
    private ImageIcon icon_weapon_empty = null;
    private ImageIcon icon_weapon = null;
    private ImageIcon icon_jesus_empty = null;
    private ImageIcon icon_jesus = null;
    private ImageIcon icon_firstclass_empty = null;
    private ImageIcon icon_firstclass = null;
    private ImageIcon icon_friend_vito_empty = null;
    private ImageIcon icon_friend_vito = null;
    private ImageIcon icon_relationship_empty = null;
    private ImageIcon icon_relationship = null;
    private ImageIcon icon_niceclothes_empty = null;
    private ImageIcon icon_niceclothes = null;

    private Boolean hasWeapon;
    private Boolean hasFriends;
    private Boolean hasClothes;
    private Boolean hasGenerous;
    private Boolean hasFirstclass;
    private Boolean hasRelationship;

    //variables
    private int nextImg;
    private int avail;
    private double marketPrice;

    private final EngineInterface engine;
    private final DecimalFormat doubleCreditFormat;

    public GUI_Main() {
        initComponents();
        hasWeapon = false;
        hasFriends = false;
        hasClothes = false;
        hasGenerous = false;
        hasFirstclass = false;
        hasRelationship = false;

        this.setLocationRelativeTo(null); //place window in center of screen
        engine = new Engine();
        engine.createPlayer("", 0.00);
        medicinMap = engine.getMedicin();
        purchasedMedicin = new HashMap<>();
        setLocationText();
        formatTables();
        engine.loadPlayers("players.txt");
        doubleCreditFormat = new DecimalFormat("0.00");

        nextImg = 0;
        //init icons
        icon_selectionArrow_right_pressed = new ImageIcon("./src/art/gui/selectionArrow_right_pressed.png");
        icon_selectionArrow_right = new ImageIcon("./src/art/gui/selectionArrow_right.png");
        icon_selectionArrow_left_pressed = new ImageIcon("./src/art/gui/selectionArrow_left_pressed.png");
        icon_selectionArrow_left = new ImageIcon("./src/art/gui/selectionArrow_left.png");
        icon_clemenza1 = new ImageIcon("./src/art/characters/1_clemenza.png");
        icon_brasi2 = new ImageIcon("./src/art/characters/2_brasi.png");
        icon_deNiro3 = new ImageIcon("./src/art/characters/3_deNiro.png");
        icon_kay4 = new ImageIcon("./src/art/characters/4_kay.png");
        icon_michael5 = new ImageIcon("./src/art/characters/5_michael.png");
        icon_sollozo6 = new ImageIcon("./src/art/characters/6_sollozo.png");
        icon_talia7 = new ImageIcon("./src/art/characters/7_talia.png");
        icon_vitelli8 = new ImageIcon("./src/art/characters/8_vitelli.png");
        icon_weapon_empty = new ImageIcon("./src/art/items/weapon_beretta92f_empty.png");
        icon_weapon = new ImageIcon("./src/art/items/weapon_beretta92f.png");
        icon_jesus_empty = new ImageIcon("./src/art/items/behaviour_jesus_empty.png");
        icon_jesus = new ImageIcon("./src/art/items/behaviour_jesus.png");
        icon_firstclass_empty = new ImageIcon("./src/art/items/behaviour_firstclass_empty.png");
        icon_firstclass = new ImageIcon("./src/art/items/behaviour_firstclass.png");
        icon_friend_vito_empty = new ImageIcon("./src/art/items/friend_vito_empty.png");
        icon_friend_vito = new ImageIcon("./src/art/items/friend_vito.png");
        icon_relationship_empty = new ImageIcon("./src/art/items/event_relationship_empty.png");
        icon_relationship = new ImageIcon("./src/art/items/event_relationship.png");
        icon_niceclothes_empty = new ImageIcon("./src/art/items/clothes_niceclothes_empty.png");
        icon_niceclothes = new ImageIcon("./src/art/items/clothes_niceclothes.png");
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
        jTabbedPane1.setEnabled(false);

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
                jTextArea_eventMessage.append(descrip + "\n");
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
        for (Medicin medicin : medicinMap.values()) {
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
            Double price = product.getPrice();
            avail = product.getAmount();
            ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
            jTable_market.setValueAt(productType, count, column_type);
            jTable_market.setValueAt(name, count, column_name);
            jTable_market.setValueAt(avail, count, column_qty);
            jTable_market.setValueAt(price, count, column_price);
            count++;
        }
    }

    private void changeCharacterIcon() {
        //------------------------------------------------------------------------------------
        //Looks at value of [nextImg]-int and changes [jLabel_characterPic]-label accordingly
        //------------------------------------------------------------------------------------
        switch (nextImg) {
            case 1:
                jLabel_characterPic.setIcon(icon_clemenza1);
                jLabel_selectionLeft.setVisible(false);
                break;
            case 2:
                jLabel_characterPic.setIcon(icon_brasi2);
                jLabel_selectionLeft.setVisible(true);
                break;
            case 3:
                jLabel_characterPic.setIcon(icon_deNiro3);
                break;
            case 4:
                jLabel_characterPic.setIcon(icon_kay4);
                break;
            case 5:
                jLabel_characterPic.setIcon(icon_michael5);
                break;
            case 6:
                jLabel_characterPic.setIcon(icon_sollozo6);
                break;
            case 7:
                jLabel_characterPic.setIcon(icon_talia7);
                jLabel_selectionRight.setVisible(true);
                break;
            case 8:
                jLabel_characterPic.setIcon(icon_vitelli8);
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
        double sufficiantCredits;
        marketPrice = 0;
        int add = 1;
        Product marketDrug;
        try {
            //-------------------------------------------------------------------
            //get drug name + price from market table + calculate new qty
            productType = (String) jTable_market.getValueAt(selectedRow, column_type);
            selectedDrugName = (String) jTable_market.getValueAt(selectedRow, column_name);
            newMarketQty = (int) jTable_market.getValueAt(selectedRow, column_qty) - 1;
            marketPrice = (double) jTable_market.getValueAt(selectedRow, column_price);
            sufficiantCredits = engine.getCredits() - marketPrice;
            //-------------------------------------------------------------------
        } catch (ArrayIndexOutOfBoundsException ex) {
            sufficiantCredits = -1.00;
        }

        if ((sufficiantCredits >= 0)) { //if we have sufficiant credits
            //-----------------------------------------------------------
            //update market table + create drug + add to player inv
            jTable_market.setValueAt(newMarketQty, selectedRow, column_qty);
            Product product = marketMap.get(selectedDrugName);
            engine.addToInventory(product);
            checkInvForNonDrugs();
            int newInvQty = engine.getInventoryDrug(selectedDrugName).getAmount();
            //-----------------------------------------------------------
            Boolean isEligible = true;
            if (productType.equals("Friend") || productType.equals("Clothes") || productType.equals("Behaviour")) {
                isEligible = false;
            }
            if (isEligible == true) {
                if (jTable_inventory.getRowCount() == 0) { //is table is empty?
                    ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                    jTable_inventory.setValueAt(productType, 0, column_type);
                    jTable_inventory.setValueAt(selectedDrugName, 0, column_name);
                    jTable_inventory.setValueAt(newInvQty, 0, column_qty);
                    jTable_inventory.setValueAt(marketPrice, 0, column_price);
                } else {
                    boolean drugExist = false; //we have drug 
                    for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
                        String inventoryDrug = (String) jTable_inventory.getValueAt(i, column_name);
                        if (selectedDrugName.equals(inventoryDrug)) { //do we already have the drug in the table?
                            drugExist = true;
                            jTable_inventory.setValueAt(newInvQty, i, column_qty);
                            //set new average price
                            double currentInventoryPrice = (double) jTable_inventory.getValueAt(i, column_price);
                            int currentQuantity = (int) jTable_inventory.getValueAt(i, column_qty);
                            double newAveragePrice = ((currentInventoryPrice * currentQuantity)
                                    + (marketPrice)) / (currentQuantity + add);
                            jTable_inventory.setValueAt(newAveragePrice, i, column_price);
                            break;
                        }
                    }
                    if (drugExist == false) { //we do not have the drug in the table);
                        int rowPosition = jTable_inventory.getRowCount();
                        ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                        jTable_inventory.setValueAt(productType, rowPosition, column_type);
                        jTable_inventory.setValueAt(selectedDrugName, rowPosition, column_name);
                        jTable_inventory.setValueAt(newInvQty, rowPosition, column_qty);
                        jTable_inventory.setValueAt(marketPrice, rowPosition, column_price);
                    }
                }
            }
            if (newMarketQty == 0) {
                ((DefaultTableModel) jTable_market.getModel()).removeRow(selectedRow);
            }
            return true;
        }
        return false; //we did not have sufficiant credits
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
            productType = (String) jTable_inventory.getValueAt(row, column_type);
            inventoryDrug = (String) jTable_inventory.getValueAt(row, column_name);
            newInventoryQty = (int) jTable_inventory.getValueAt(row, column_qty) - 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            newInventoryQty = -1;
            return false;
        }
        if (newInventoryQty >= 0 && jTable_market.getRowCount() > 0) {
            Boolean productExists = false;
            engine.subtractFromInventory(inventoryDrug);
            checkInvForNonDrugs();
            jTable_inventory.setValueAt(newInventoryQty, row, column_qty);
            for (int i = 0; i < jTable_market.getRowCount(); i++) {
                String marketDrug = (String) jTable_market.getValueAt(i, column_name);
                if (inventoryDrug.equals(marketDrug)) {
                    int newQty = (int) jTable_market.getValueAt(i, column_qty) + 1;
                    marketPrice = (double) jTable_market.getValueAt(i, column_price);
                    jTable_market.setValueAt(newQty, i, column_qty);
                    productExists = true;
                    break;
                }
            }
            if (productExists == false) {
                int rowIndex = jTable_market.getRowCount();
                ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
                jTable_market.setValueAt(productType, rowIndex, column_type);
                jTable_market.setValueAt(inventoryDrug, rowIndex, column_name);
                jTable_market.setValueAt(1, rowIndex, column_qty);
                jTable_market.setValueAt(marketMap.get(inventoryDrug).getPrice(), rowIndex, column_price);
            }
        }
        if (newInventoryQty == 0) {
            ((DefaultTableModel) jTable_inventory.getModel()).removeRow(row);
        }
        return true;
    }

    private void autoSell() {
        //---------------------------------------------------
        //Runs through all rows in [jTable_inventory]-table
        //Sell all drugs
        //Add profit to credits
        //---------------------------------------------------
        for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
            String inventoryDrug = (String) jTable_inventory.getValueAt(i, column_name);
            int inventoryQty = (int) jTable_inventory.getValueAt(i, column_qty);
            for (int j = 0; j < jTable_market.getRowCount(); j++) {
                String marketDrug = marketMap.get(inventoryDrug).getName();
                if (inventoryDrug.equals(marketDrug)) {
                    double marketPrice = marketMap.get(inventoryDrug).getPrice();
                    marketPrice = inventoryQty * marketPrice;
                    engine.calculateCredits(marketPrice);
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
            engine.calculateCredits(-marketPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
        }
    }

    private void performSell() {
        jTable_market.clearSelection();
        if (sell() == true) {
            engine.calculateCredits(marketPrice);
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

    private void checkInvForNonDrugs() {
        Product weapon = engine.getInventoryDrug("Beretta92F");
        Product friend = engine.getInventoryDrug("High friends");
        Product generous = engine.getInventoryDrug("Generous");
        Product niceclothes = engine.getInventoryDrug("Nice clothes");
        Product firstclass = engine.getInventoryDrug("Travel 1.Class");

        String affects = "Affects: ";
        String captureAuthority = "% prob to get captured by customs\n";
        String assaultPusher = "% prob to get assaulted by pusher\n";
        String mafiaTerritory = "% prob to trespass on mafia territory\n";
        String minionPusher = "% prob to get minion pusher to work for you\n";
        String relationship = "% prob to get a local boy or girlfriend\n";

        if (weapon != null) {
            if (weapon.getAmount() == 1 && hasWeapon == false) {
                jLabel_weapon.setIcon(icon_weapon);
                jTextArea_eventMessage.append("You bought a weapon for protection\n");
                jTextArea_eventMessage.append(affects + "-2" + assaultPusher);
                jTextArea_eventMessage.append(affects + "-1" + mafiaTerritory);
                hasWeapon = true;
            }
        } else {
            jLabel_weapon.setIcon(icon_weapon_empty);
            hasWeapon = false;
        }
        if (friend != null) {
            if (friend.getAmount() == 1 && hasFriends == false) {
                jLabel_friend.setIcon(icon_friend_vito);
                jTextArea_eventMessage.append("You bribed some friends in high places\n");
                jTextArea_eventMessage.append(affects + "-2" + captureAuthority);
                jTextArea_eventMessage.append(affects + "-1" + assaultPusher);
                jTextArea_eventMessage.append(affects + "-2" + mafiaTerritory);
                hasFriends = true;
            }
        }
        if (generous != null) {
            if (generous.getAmount() == 1 && hasGenerous == false) {
                jLabel_generous.setIcon(icon_jesus);
                jTextArea_eventMessage.append("You decided to be generous and spent some money partying with your friends\n");
                jTextArea_eventMessage.append(affects + "-1" + assaultPusher);
                jTextArea_eventMessage.append(affects + "2" + minionPusher);
                jTextArea_eventMessage.append(affects + "4" + relationship);
                hasGenerous = true;
            }
        }
        if (niceclothes != null) {
            if (niceclothes.getAmount() == 1 && hasClothes == false) {
                jLabel_niceclothes.setIcon(icon_niceclothes);
                jTextArea_eventMessage.append("You bought some nice clothes\n");
                jTextArea_eventMessage.append(affects + "-1" + captureAuthority);
                jTextArea_eventMessage.append(affects + "2" + minionPusher);
                jTextArea_eventMessage.append(affects + "3" + relationship);
                hasClothes = true;
            }
        }
        if (firstclass != null) {
            if (firstclass.getAmount() == 1 && hasFirstclass == false) {
                jLabel_firstclass.setIcon(icon_firstclass);
                jTextArea_eventMessage.append("You bought a one-way ticket for first class\n");
                jTextArea_eventMessage.append(affects + "-1" + captureAuthority);
                jTextArea_eventMessage.append(affects + "2" + relationship);
                hasFirstclass = true;
            }
        } else {
            jLabel_firstclass.setIcon(icon_firstclass_empty);
            hasFirstclass = false;
        }
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
        jLabel_money = new javax.swing.JLabel();
        jLabel_selectionRight = new javax.swing.JLabel();
        jLabel_selectionLeft = new javax.swing.JLabel();
        jTextField_inputName = new javax.swing.JTextField();
        jProgressBar_life = new javax.swing.JProgressBar();
        jProgressBar_days = new javax.swing.JProgressBar();
        jLabel_weapon = new javax.swing.JLabel();
        jLabel_friend = new javax.swing.JLabel();
        jLabel_generous = new javax.swing.JLabel();
        jLabel_firstclass = new javax.swing.JLabel();
        jLabel_niceclothes = new javax.swing.JLabel();
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
        jLabel_location = new javax.swing.JLabel();
        jPanel_Hospital = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_hospital = new javax.swing.JTable();
        jButton_buyMedicin = new javax.swing.JButton();
        jPanel_bank = new javax.swing.JPanel();
        jLabel_TEXT_borrow = new javax.swing.JLabel();
        jTextfield_loanAmount = new javax.swing.JTextField();
        jButton_loan1Day = new javax.swing.JButton();
        jLabel_TEXT_interestRate = new javax.swing.JLabel();
        jButton_loan1Week = new javax.swing.JButton();
        jButton_loanRefund = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thug Life 0.1");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_highscore.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton_highscore.setText("Highscore");
        jButton_highscore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_highscoreActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_highscore, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 3, -1, -1));

        jButton_confirm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton_confirm.setText("Confirm");
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 3, -1, -1));

        jPanel_player.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_player.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_characterPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/characters/1_clemenza.png"))); // NOI18N
        jLabel_characterPic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_characterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_characterPic.setMaximumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setMinimumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel_player.add(jLabel_characterPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 110, 160));

        jLabel_TEXT_info.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_TEXT_info.setText("Info");
        jPanel_player.add(jLabel_TEXT_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, -1, -1));

        jLabel_TEXT_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_TEXT_name.setText("Name: ");
        jPanel_player.add(jLabel_TEXT_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_name.setText("Hardboilr");
        jLabel_name.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jLabel_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 23, 100, 30));

        jLabel_money.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_money.setText("5000");
        jLabel_money.setToolTipText("");
        jPanel_player.add(jLabel_money, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 30));

        jLabel_selectionRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/gui/selectionArrow_right.png"))); // NOI18N
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
        jPanel_player.add(jLabel_selectionRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        jLabel_selectionLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/gui/selectionArrow_left.png"))); // NOI18N
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
        jPanel_player.add(jLabel_selectionLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));

        jTextField_inputName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField_inputName.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jTextField_inputName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 25, 100, 30));

        jProgressBar_life.setBackground(new java.awt.Color(204, 0, 0));
        jProgressBar_life.setName(""); // NOI18N
        jProgressBar_life.setStringPainted(true);
        jPanel_player.add(jProgressBar_life, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 30));

        jProgressBar_days.setStringPainted(true);
        jPanel_player.add(jProgressBar_days, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 150, 30));

        jLabel_weapon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/items/weapon_beretta92f_empty.png"))); // NOI18N
        jPanel_player.add(jLabel_weapon, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        jLabel_friend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/items/friend_vito_empty.png"))); // NOI18N
        jPanel_player.add(jLabel_friend, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 54, -1, -1));

        jLabel_generous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/items/behaviour_jesus_empty.png"))); // NOI18N
        jLabel_generous.setToolTipText("This is a test");
        jPanel_player.add(jLabel_generous, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 0, -1, -1));

        jLabel_firstclass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/items/behaviour_firstclass_empty.png"))); // NOI18N
        jPanel_player.add(jLabel_firstclass, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 58, -1, -1));

        jLabel_niceclothes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/items/clothes_niceclothes_empty.png"))); // NOI18N
        jPanel_player.add(jLabel_niceclothes, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 114, -1, -1));

        jLabel_loan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_loan.setText("debt");
        jPanel_player.add(jLabel_loan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        getContentPane().add(jPanel_player, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 180));

        jButton_newGame.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton_newGame.setText("New game");
        jButton_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newGameActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_newGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 3, -1, -1));

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
        jPanel2.add(jButton_bulkSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 40));

        jButton_bulkBuy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_bulkBuy.setText("x10 >>");
        jButton_bulkBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_bulkBuyActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_bulkBuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 100, 110));

        jTextArea_eventMessage.setColumns(20);
        jTextArea_eventMessage.setRows(5);
        jScrollPane2.setViewportView(jTextArea_eventMessage);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 480, 90));

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

        jLabel_location.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_location.setText("Location: current loc");

        javax.swing.GroupLayout jPanel_locationLayout = new javax.swing.GroupLayout(jPanel_location);
        jPanel_location.setLayout(jPanel_locationLayout);
        jPanel_locationLayout.setHorizontalGroup(
            jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_locationLayout.createSequentialGroup()
                        .addComponent(jButton_travel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_location, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_locationLayout.setVerticalGroup(
            jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_locationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_locationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_travel)
                    .addComponent(jLabel_location))
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

        jLabel_TEXT_borrow.setText("Borrow some cash max 10.000$");

        jButton_loan1Day.setText("1 Day ");
        jButton_loan1Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loan1DayActionPerformed(evt);
            }
        });

        jLabel_TEXT_interestRate.setText("35% interest rate");

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
                            .addComponent(jLabel_TEXT_borrow)
                            .addComponent(jLabel_TEXT_interestRate))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel_bankLayout.setVerticalGroup(
            jPanel_bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_bankLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_TEXT_borrow, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_TEXT_interestRate)
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

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 280, 200));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        String name = jTextField_inputName.getText();
        name = name.replaceAll(" ", "").toLowerCase();

        if (!name.isEmpty()) {
            jTabbedPane1.setEnabled(true);
            jLabel_TEXT_info.setText("Info");
            engine.getPlayer().setName(name);
            engine.getPlayer().setCredits(5000.00);
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
        jLabel_characterPic.setIcon(icon_clemenza1);
        jLabel_characterPic.setVisible(true);
        jButton_confirm.setVisible(true);
        jLabel_selectionLeft.setVisible(false);
        nextImg = 1;
    }//GEN-LAST:event_jButton_newGameActionPerformed

    private void jButton_travelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_travelActionPerformed
        int currentDay = engine.getPlayer().getDays();
        double ticketPrice = 0;
        try {
        ticketPrice = (double) jTable_airport.getValueAt(jTable_airport.getSelectedRow(), 1);
        } catch (ArrayIndexOutOfBoundsException ai) {
            //Probably nothing selected...
            ticketPrice = engine.getCredits()+1;
        }
        if (currentDay >= 0 && ticketPrice <= engine.getCredits()) {
            engine.getPlayer().setDays(-1);
            engine.getPlayer().setLoanDays(-1);
            updateDays();
            engine.setActiveCountry((String) jTable_airport.getValueAt(jTable_airport.getSelectedRow(), 0));
            setLocationText();
            engine.calculateCredits(-ticketPrice);
            prepareRound();
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            currentDay = engine.getPlayer().getDays();
        }
        if (currentDay == 0) {
            engine.getPlayer().setDays(-1);
            jButton_travel.setText("Cash in!");
        }
        if (currentDay == -1) {
            System.out.println("We sell everything!");
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
        jLabel_selectionRight.setIcon(icon_selectionArrow_right_pressed);
    }//GEN-LAST:event_jLabel_selectionRightMousePressed

    private void jLabel_selectionRightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionRightMouseReleased
        changeCharacterIcon();
        jLabel_selectionRight.setIcon(icon_selectionArrow_right);
    }//GEN-LAST:event_jLabel_selectionRightMouseReleased

    private void jLabel_selectionLeftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionLeftMousePressed
        --nextImg;
        jLabel_selectionLeft.setIcon(icon_selectionArrow_left_pressed);
    }//GEN-LAST:event_jLabel_selectionLeftMousePressed

    private void jLabel_selectionLeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_selectionLeftMouseReleased
        changeCharacterIcon();
        jLabel_selectionLeft.setIcon(icon_selectionArrow_left);
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
        double maxLoan = 10001.00;
        double existingLoan = engine.getPlayer().getLoan();
        int existingLoanDays = engine.getPlayer().getLoanDays();
        if (loanAmount > -1 && loanAmount < maxLoan && (existingLoan + loanAmount) <= maxLoan) {
            engine.getPlayer().setLoan(existingLoan + loanAmount);
            engine.getPlayer().setLoanDays(existingLoanDays + 1);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
        }


    }//GEN-LAST:event_jButton_loan1DayActionPerformed

    private void jButton_loan1WeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loan1WeekActionPerformed
        double loanAmount = Double.parseDouble(jTextfield_loanAmount.getText());
        double maxLoan = 10001.00;
        double existingLoan = engine.getPlayer().getLoan();
        int existingLoanDays = engine.getPlayer().getLoanDays();
        if (loanAmount > -1 && loanAmount < maxLoan && (existingLoan + loanAmount) <= maxLoan) {
            engine.getPlayer().setLoan(existingLoan + loanAmount);
            engine.getPlayer().setLoanDays(existingLoanDays + 7);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
        }
    }//GEN-LAST:event_jButton_loan1WeekActionPerformed

    private void jButton_loanRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loanRefundActionPerformed
        double refundAmount = Double.parseDouble(jTextfield_loanAmount.getText());
        double credits = engine.getCredits();
        double loan = engine.getPlayer().getLoan();
        if (refundAmount <= credits && refundAmount > -1.0) {
            if (refundAmount > loan) {
                engine.getPlayer().setLoan(loan - loan);
                engine.calculateCredits(-loan);
                jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
                jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            } else {
                engine.getPlayer().setLoan(loan - refundAmount);
                engine.calculateCredits(-refundAmount);
                jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
                jLabel_loan.setText(doubleCreditFormat.format(engine.getPlayer().getLoan()) + " $");
            }
        }
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
    private javax.swing.JLabel jLabel_TEXT_borrow;
    private javax.swing.JLabel jLabel_TEXT_info;
    private javax.swing.JLabel jLabel_TEXT_interestRate;
    private javax.swing.JLabel jLabel_TEXT_market;
    private javax.swing.JLabel jLabel_TEXT_market1;
    private javax.swing.JLabel jLabel_TEXT_name;
    private javax.swing.JLabel jLabel_characterPic;
    private javax.swing.JLabel jLabel_firstclass;
    private javax.swing.JLabel jLabel_friend;
    private javax.swing.JLabel jLabel_generous;
    private javax.swing.JLabel jLabel_loan;
    private javax.swing.JLabel jLabel_location;
    private javax.swing.JLabel jLabel_money;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JLabel jLabel_niceclothes;
    private javax.swing.JLabel jLabel_selectionLeft;
    private javax.swing.JLabel jLabel_selectionRight;
    private javax.swing.JLabel jLabel_weapon;
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
