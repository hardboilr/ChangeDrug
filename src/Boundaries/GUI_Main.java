/**
 * @author Tobias & Sebastian
 */
package Boundaries;

import Controllere.Engine;
import Entities.Country;
import Entities.Drug;
import Entities.Event;
import Interfaces.EngineInterface;
import java.awt.Color;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends javax.swing.JFrame {

    private Map<String, Drug> marketMap;
    private Map<String, Drug> inventoryMap;

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

    private EngineInterface engine;
    private DefaultListModel listmodel = new DefaultListModel();
    private DecimalFormat doubleCreditFormat;

    public GUI_Main() {
        initComponents();
        this.setLocationRelativeTo(null); //place window in center of screen
        engine = new Engine();
        prepareRound();
        setLocationText();
        formatTables();
        engine.loadHighscore("players.txt");
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
        jButton_confirm.setVisible(false);

        //deactivate buttons
        jButton_travel.setEnabled(false);
        jButton_buy.setEnabled(false);
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
        List<String> eventList = engine.getEvents();
        if (eventList.size() > 0) {
            System.out.println(eventList.size());
            for (String eventDescrip : eventList) {
                String descrip = eventDescrip;
                jTextArea_event.setText(descrip);
                setLife((int)10);
            }
            

        }
        //fill country table
        ((DefaultTableModel) jTableAirport.getModel()).setRowCount(0);
        for (int i = 0; i < engine.getCountries().size(); i++) {
            Country country = (Country) engine.getCountries().get(i);
            String countryName = country.getName().toUpperCase().charAt(0) + country.getName().substring(1);
            double ticketPrice = country.getTicketPrice();
            ((DefaultTableModel) jTableAirport.getModel()).addRow(new Object[]{});
            jTableAirport.setValueAt(countryName, i, 0);
            jTableAirport.setValueAt(ticketPrice, i, 1);

        }

        // fill market table
        ((DefaultTableModel) jTable_market.getModel()).setRowCount(0);
        marketMap = engine.travel();
        int count = 0;
        for (Drug drug : marketMap.values()) {
            String marName = drug.getName();
            double marketDrugPrice = drug.getModifiedPrice();
            avail = drug.getModifiedAvail();

            ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
            jTable_market.setValueAt(marName, count, 0);
            jTable_market.setValueAt(avail, count, 1);
            jTable_market.setValueAt(marketDrugPrice, count, 2);
            count++;
        }
        
        count = 0;
        //fill inventory table
         ((DefaultTableModel) jTable_inventory.getModel()).setRowCount(0);
         inventoryMap = engine.getInventory();
         for(Drug drug : inventoryMap.values()){
             String invName = drug.getName();
             double inventoryDrugPrice = drug.getModifiedPrice();
             int qty = drug.getModifiedAvail();
             
             ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
             jTable_inventory.setValueAt(invName, count, 0);
             jTable_inventory.setValueAt(qty, count, 1);
             jTable_inventory.setValueAt(inventoryDrugPrice, count, 2);
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
        String selectedDrugName = "";
        int newMarketQty = 0;
        double sufficiantCredits;
        MarketPrice = 0;
        int add = 1;
        Drug marketDrug;
        try {
            //-------------------------------------------------------------------
            //get drug name + price from market table + calculate new qty
            selectedDrugName = (String) jTable_market.getValueAt(selectedRow, 0);
            newMarketQty = (int) jTable_market.getValueAt(selectedRow, 1) - 1;
            MarketPrice = (double) jTable_market.getValueAt(selectedRow, 2);
            //-------------------------------------------------------------------
        } catch (ArrayIndexOutOfBoundsException ex) {
            sufficiantCredits = -1.00;
        }
        sufficiantCredits = engine.getCredits() - MarketPrice;
        if ((sufficiantCredits >= 0)) { //if we have sufficiant credits
            //-----------------------------------------------------------
            //update market table + create drug + add to player inv
            jTable_market.setValueAt(newMarketQty, selectedRow, 1);
            Drug drug = new Drug(selectedDrugName, 0, MarketPrice, 0, add, 0);
            engine.addToInventory(drug);
            int newInvQty = engine.getInventoryDrug(selectedDrugName).getModifiedAvail();
            //-----------------------------------------------------------
            if (jTable_inventory.getRowCount() == 0) { //is table is empty?
                ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                jTable_inventory.setValueAt(selectedDrugName, 0, 0);
                jTable_inventory.setValueAt(newInvQty, 0, 1);
                jTable_inventory.setValueAt(MarketPrice, 0, 2);

            } else {
                boolean drugExist = false; //we have drug 
                for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
                    String inventoryDrug = (String) jTable_inventory.getValueAt(i, 0);
                    if (selectedDrugName.equals(inventoryDrug)) { //do we already have the drug in the table?
                        drugExist = true;
                        jTable_inventory.setValueAt(newInvQty, i, 1);
                        //set new average price
                        double currentInventoryPrice = (double) jTable_inventory.getValueAt(i, 2);
                        int currentQuantity = (int) jTable_inventory.getValueAt(i, 1);
                        double newAveragePrice = ((currentInventoryPrice * currentQuantity)
                                + (MarketPrice)) / (currentQuantity + add);
                        jTable_inventory.setValueAt(newAveragePrice, i, 2);
                        break;
                    }
                }
                if (drugExist == false) { //we do not have the drug in the table);
                    int rowPosition = jTable_inventory.getRowCount();
                    ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                    jTable_inventory.setValueAt(selectedDrugName, rowPosition, 0);
                    jTable_inventory.setValueAt(newInvQty, rowPosition, 1);
                    jTable_inventory.setValueAt(MarketPrice, rowPosition, 2);
                }
            }
            if (newMarketQty == 0) {
                ((DefaultTableModel) jTable_market.getModel()).removeRow(selectedRow);
            }
            return true;
        }
        jTextArea_event.setText("not enough money");
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
        String inventoryDrug = "";
        int newInventoryQty;
        try {
            inventoryDrug = (String) jTable_inventory.getValueAt(row, 0);
            newInventoryQty = (int) jTable_inventory.getValueAt(row, 1) - 1;
        } catch (ArrayIndexOutOfBoundsException ex) {
            newInventoryQty = -1;
        }

        if (newInventoryQty >= 0) {
            engine.removeFromInventory(inventoryDrug);
            jTable_inventory.setValueAt(newInventoryQty, row, 1);
            for (int i = 0; i <= jTable_market.getRowCount(); i++) {
                String marketDrug = (String) jTable_market.getValueAt(i, 0);
                if (marketDrug == null) {
                    int add = 1;
                    jTable_market.setValueAt(inventoryDrug, i, 0);
                    jTable_market.setValueAt(add, i, 1);
                    jTable_market.setValueAt(MarketPrice, i, 2);
                    break;
                } else {
                    if (inventoryDrug.equals(marketDrug)) {
                        int newQty = (int) jTable_market.getValueAt(i, 1) + 1;
                        MarketPrice = (double) jTable_market.getValueAt(i, 2);
                        jTable_market.setValueAt(newQty, i, 1);
                        break;
                    }
                
            }
            if (newInventoryQty == 0) {
                ((DefaultTableModel) jTable_inventory.getModel()).removeRow(row);
                engine.removeInventoryDrug(inventoryDrug);
            }
            return true;
        }
        return false;
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

    private void setLife(int input) {
        int life = engine.getPlayer().getLife();
        int damage = input;
        engine.getPlayer().setLife(life - damage);
        int newLife = engine.getPlayer().getLife();
        jProgressBar_life.setValue(newLife);
        jProgressBar_life.setString(newLife + "% health");
    }

    private void setDays(int input) {
        engine.getPlayer().setDays(input);
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
        jLabel_TEXT_location = new javax.swing.JLabel();
        jLabel_location = new javax.swing.JLabel();
        jProgressBar_days = new javax.swing.JProgressBar();
        jButton_newGame = new javax.swing.JButton();
        jPanel_market = new javax.swing.JPanel();
        jScrollPane_market = new javax.swing.JScrollPane();
        jTable_market = new javax.swing.JTable();
        jLabel_TEXT_market1 = new javax.swing.JLabel();
        jPanel_inventory = new javax.swing.JPanel();
        jScrollPane_inventory = new javax.swing.JScrollPane();
        jTable_inventory = new javax.swing.JTable();
        jLabel_TEXT_market = new javax.swing.JLabel();
        jPanel_location = new javax.swing.JPanel();
        jButton_travel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAirport = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton_buy = new javax.swing.JButton();
        jButton_sell = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton_bulkSell = new javax.swing.JButton();
        jButton_bulkBuy = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_event = new javax.swing.JTextArea();

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
        jPanel_player.add(jLabel_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 130, 40));

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
        jPanel_player.add(jTextField_inputName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 130, 30));

        jProgressBar_life.setBackground(new java.awt.Color(204, 0, 0));
        jProgressBar_life.setName(""); // NOI18N
        jProgressBar_life.setStringPainted(true);
        jPanel_player.add(jProgressBar_life, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        jLabel_TEXT_location.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_TEXT_location.setText("Location:");
        jPanel_player.add(jLabel_TEXT_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel_location.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_location.setText("current location");
        jPanel_player.add(jLabel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        jProgressBar_days.setStringPainted(true);
        jPanel_player.add(jProgressBar_days, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        getContentPane().add(jPanel_player, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 420, 180));

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
                "Name", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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
            jTable_market.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        jPanel_market.add(jScrollPane_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 220));

        jLabel_TEXT_market1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market1.setText("Market");
        jPanel_market.add(jLabel_TEXT_market1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 310, 270));

        jPanel_inventory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_inventory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_inventory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_inventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Qty", "Avg. price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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
            jTable_inventory.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        jPanel_inventory.add(jScrollPane_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 220));

        jLabel_TEXT_market.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market.setText("Inventory");
        jPanel_inventory.add(jLabel_TEXT_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 310, 270));

        jPanel_location.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_location.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_travel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_travel.setText("Go!");
        jButton_travel.setToolTipText("");
        jButton_travel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_travelActionPerformed(evt);
            }
        });
        jPanel_location.add(jButton_travel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jTableAirport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Country", "Ticketprice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
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
        jScrollPane1.setViewportView(jTableAirport);

        jPanel_location.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 240, 120));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Airport");
        jPanel_location.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 260, 180));

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

        jTextArea_event.setColumns(20);
        jTextArea_event.setRows(5);
        jScrollPane2.setViewportView(jTextArea_event);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 420, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        String name = jTextField_inputName.getText();
        name = name.replaceAll(" ", "").toLowerCase();

        if (!name.isEmpty()) {

            engine.createPlayer(name, engine.getStartCredits());
            jButton_buy.setEnabled(true);
            jButton_sell.setEnabled(true);
            jButton_bulkBuy.setEnabled(true);
            jButton_bulkSell.setEnabled(true);
            jButton_travel.setEnabled(true);
            jLabel_name.setText(name);
            jLabel_name.setVisible(true);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_money.setVisible(true);
            jTextField_inputName.setVisible(false);
            jLabel_selectionLeft.setVisible(false);
            jLabel_selectionRight.setVisible(false);
            jButton_confirm.setVisible(false);
            jButton_newGame.setVisible(false);
            jProgressBar_days.setMinimum(0);
            jProgressBar_days.setMaximum(engine.getPlayer().getDays());
            setLife(0);
            setDays(0);
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
        setDays(1);
        int currentDay = engine.getPlayer().getDays();
        double ticketPrice = (double) jTableAirport.getValueAt(jTableAirport.getSelectedRow(),1);
        if (currentDay >= 0 && ticketPrice <= engine.getCredits()) {
            engine.setActiveCountry((String)jTableAirport.getValueAt(jTableAirport.getSelectedRow(),0));
            setLocationText();
            prepareRound();
            engine.calculateCredits(-ticketPrice);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
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
            java.util.logging.Logger.getLogger(GUI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButton_confirm;
    private javax.swing.JButton jButton_highscore;
    private javax.swing.JButton jButton_newGame;
    private javax.swing.JButton jButton_sell;
    private javax.swing.JButton jButton_travel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_TEXT_info;
    private javax.swing.JLabel jLabel_TEXT_location;
    private javax.swing.JLabel jLabel_TEXT_market;
    private javax.swing.JLabel jLabel_TEXT_market1;
    private javax.swing.JLabel jLabel_TEXT_money;
    private javax.swing.JLabel jLabel_TEXT_name;
    private javax.swing.JLabel jLabel_characterPic;
    private javax.swing.JLabel jLabel_location;
    private javax.swing.JLabel jLabel_money;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JLabel jLabel_selectionLeft;
    private javax.swing.JLabel jLabel_selectionRight;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_inventory;
    private javax.swing.JPanel jPanel_location;
    private javax.swing.JPanel jPanel_market;
    private javax.swing.JPanel jPanel_player;
    private javax.swing.JProgressBar jProgressBar_days;
    private javax.swing.JProgressBar jProgressBar_life;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane_inventory;
    private javax.swing.JScrollPane jScrollPane_market;
    private javax.swing.JTable jTableAirport;
    private javax.swing.JTable jTable_inventory;
    private javax.swing.JTable jTable_market;
    private javax.swing.JTextArea jTextArea_event;
    private javax.swing.JTextField jTextField_inputName;
    // End of variables declaration//GEN-END:variables
}
