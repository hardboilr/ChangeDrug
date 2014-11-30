/**
 * @author Tobias & Sebastian
 */
package Boundaries;

import Controllere.Engine;
import Entities.Drug;
import Interfaces.EngineInterface;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends javax.swing.JFrame {

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
    private double price;
    
    private EngineInterface engine;
    private DefaultListModel listmodel = new DefaultListModel();
    private DecimalFormat doubleCreditFormat;    
    
    public GUI_Main() {
        initComponents();
        this.setLocationRelativeTo(null); //place window in center of screen
        engine = new Engine();
        jList_countries.setModel(listmodel);
        prepareGame();
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
        jLabel_life.setVisible(false);
        jLabel_daysLeft.setVisible(false);
        jLabel_money.setVisible(false);
        jButton_confirm.setVisible(false);
        

        //deactivate buttons
        jButton_travel.setEnabled(false);
        jButton_buy.setEnabled(false);
        jButton_sell.setEnabled(false);
        jButton_bulkBuy.setEnabled(false);
        jButton_bulkSell.setEnabled(false);
    }
    
    private void setLocationText() {
    //---------------------------------------------------------
        //Updates the [jLabel_location]-label with current country
        //---------------------------------------------------------
        jLabel_location.setText("Location: " + engine.getActiveCountry().toUpperCase().charAt(0)
                + engine.getActiveCountry().substring(1));
    }
    
    private void prepareGame() {
    //---------------------------------------------
        //Fill [jList_countries]-list with countries 
        //fill [jTable_market]-table with drugs
        //---------------------------------------------
        listmodel.clear();
        for (int i = 0; i < engine.getCountries().size(); i++) {
            String country = (String) engine.getCountries().get(i);
            listmodel.addElement(country.toUpperCase().charAt(0) + country.substring(1));
        }

        // fill market table
        ((DefaultTableModel) jTable_market.getModel()).setRowCount(0);
        List<Drug> tempList = engine.travel();
        for (int i = 0; i < tempList.size(); i++) {
            Drug drug = tempList.get(i);
            String name = drug.getName();
            Double price = drug.getModifiedPrice();
            avail = drug.getModifiedAvail();
            ((DefaultTableModel) jTable_market.getModel()).addRow(new Object[]{});
            jTable_market.setValueAt(name, i, 0);
            jTable_market.setValueAt(avail, i, 1);
            jTable_market.setValueAt(price, i, 2);
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
        int row = jTable_market.getSelectedRow();
        String marketDrug = "";
        int subtract = 0;
        double sufficiantCredits;
        price = 0;
        int add = 1;
        try {
        marketDrug = (String) jTable_market.getValueAt(row, 0);
        int qty = (int) jTable_market.getValueAt(row, 1);
        price = (double) jTable_market.getValueAt(row, 2);
        subtract = qty - 1;
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            sufficiantCredits = -1.00;
        }
        
        sufficiantCredits = engine.getCredits() - price;
        if ((engine.getCredits() - price >= 0)) { //sufficiant credits
            jTable_market.setValueAt(subtract, row, 1); //withdraw 1 from markedet qty 
            if (jTable_inventory.getRowCount() == 0) { //hvis tabel er tom i inv, tilføjes en række.
                ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                jTable_inventory.setValueAt(marketDrug, 0, 0);
                jTable_inventory.setValueAt(add, 0, 1);
                jTable_inventory.setValueAt(price, 0, 2);
            } else {
                boolean drugExist = false;
                for (int i = 0; i < jTable_inventory.getRowCount(); i++) {
                    String inventoryDrug = (String) jTable_inventory.getValueAt(i, 0);
                    if (marketDrug.equals(inventoryDrug)) { //do we already have the drug?
                        drugExist = true;
                        int newQty = (int) jTable_inventory.getValueAt(i, 1) + 1;
                        jTable_inventory.setValueAt(newQty, i, 1);
                        //set new average price
                        double currentInventoryPrice = (double) jTable_inventory.getValueAt(i, 2);
                        int currentQuantity = (int) jTable_inventory.getValueAt(i, 1);
                        double newAveragePrice = ((currentInventoryPrice * currentQuantity)
                                + (price * 1)) / (currentQuantity + 1);
                        jTable_inventory.setValueAt(newAveragePrice, i, 2);
                        break;
                    }
                }
                if (drugExist == false) { //Vi har IKKE drugget i forvejen");

                    int rowPosition = jTable_inventory.getRowCount();
                    ((DefaultTableModel) jTable_inventory.getModel()).addRow(new Object[]{});
                    jTable_inventory.setValueAt(marketDrug, rowPosition, 0);
                    jTable_inventory.setValueAt(add, rowPosition, 1);
                    jTable_inventory.setValueAt(price, rowPosition, 2);
                }
            }
            if (subtract == 0) {
                System.out.println("Slettes den tomme række");
                ((DefaultTableModel) jTable_market.getModel()).removeRow(row);
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
        String inventoryDrug = "";
        int subtract;
        try {
        inventoryDrug = (String) jTable_inventory.getValueAt(row, 0);
        int qty = (int) jTable_inventory.getValueAt(row, 1);
        price = (double) jTable_inventory.getValueAt(row, 2);
        subtract = qty - 1;
        }
        catch (ArrayIndexOutOfBoundsException ex) {
        subtract = -1;    
        }
        
        if (subtract >= 0) {
            jTable_inventory.setValueAt(subtract, row, 1);
            for (int i = 0; i <= jTable_market.getRowCount(); i++) {
                String marketDrug = (String) jTable_market.getValueAt(i, 0);
                if (marketDrug == null) {
                    int add = 1;
                    jTable_market.setValueAt(inventoryDrug, i, 0);
                    jTable_market.setValueAt(add, i, 1);
                    jTable_market.setValueAt(price, i, 2);
                    break;
                } else {
                    if (inventoryDrug.equals(marketDrug)) {
                        int newQty = (int) jTable_market.getValueAt(i, 1) + 1;
                        price = (double) jTable_market.getValueAt(i, 2);
                        jTable_market.setValueAt(newQty, i, 1);
                        break;
                    }
                }
            }
            if (subtract == 0) {
                ((DefaultTableModel) jTable_inventory.getModel()).removeRow(row);
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
                    price = inventoryQty * marketPrice;
                    engine.calculateCredits(price);
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
            engine.calculateCredits(-price);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
        }
    }
    
    private void performSell() {
        jTable_market.clearSelection();
        if (sell() == true) {
            engine.calculateCredits(price);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
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
        jLabel_TEXT_Life = new javax.swing.JLabel();
        jLabel_life = new javax.swing.JLabel();
        jLabel_TEXT_money = new javax.swing.JLabel();
        jLabel_money = new javax.swing.JLabel();
        jLabel_selectionRight = new javax.swing.JLabel();
        jLabel_selectionLeft = new javax.swing.JLabel();
        jLabel_TEXT_DaysLeft = new javax.swing.JLabel();
        jLabel_daysLeft = new javax.swing.JLabel();
        jTextField_inputName = new javax.swing.JTextField();
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
        jLabel_location = new javax.swing.JLabel();
        jScrollPane_countries = new javax.swing.JScrollPane();
        jList_countries = new javax.swing.JList();
        jButton_travel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton_buy = new javax.swing.JButton();
        jButton_sell = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton_bulkSell = new javax.swing.JButton();
        jButton_bulkBuy = new javax.swing.JButton();

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
        getContentPane().add(jButton_highscore, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        jButton_confirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_confirm.setText("Confirm");
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jPanel_player.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_player.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_characterPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/characters/1_clemenza.png"))); // NOI18N
        jLabel_characterPic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_characterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_characterPic.setMaximumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setMinimumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel_player.add(jLabel_characterPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 160));

        jLabel_TEXT_info.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_info.setText("Info");
        jPanel_player.add(jLabel_TEXT_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel_TEXT_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_name.setText("Name: ");
        jPanel_player.add(jLabel_TEXT_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_name.setText("Hardboilr");
        jLabel_name.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jLabel_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 130, 30));

        jLabel_TEXT_Life.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_Life.setText("Life:");
        jLabel_TEXT_Life.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_Life, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel_life.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_life.setText("55%");
        jPanel_player.add(jLabel_life, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel_TEXT_money.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_money.setText("Money:");
        jLabel_TEXT_money.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_money, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        jLabel_money.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_money.setText("5000");
        jLabel_money.setToolTipText("");
        jPanel_player.add(jLabel_money, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, 20));

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
        jPanel_player.add(jLabel_selectionRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, -1, -1));

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
        jPanel_player.add(jLabel_selectionLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        jLabel_TEXT_DaysLeft.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_DaysLeft.setText("Days left: ");
        jLabel_TEXT_DaysLeft.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_DaysLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 20));

        jLabel_daysLeft.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_daysLeft.setText("14");
        jLabel_daysLeft.setToolTipText("");
        jPanel_player.add(jLabel_daysLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, 20));

        jTextField_inputName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_inputName.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel_player.add(jTextField_inputName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 130, 30));

        getContentPane().add(jPanel_player, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 420, 180));

        jButton_newGame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_newGame.setText("New game");
        jButton_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newGameActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_newGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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

        jPanel_market.add(jScrollPane_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 260));

        jLabel_TEXT_market1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market1.setText("Market");
        jPanel_market.add(jLabel_TEXT_market1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 310, 310));

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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

        jPanel_inventory.add(jScrollPane_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 260));

        jLabel_TEXT_market.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market.setText("Inventory");
        jPanel_inventory.add(jLabel_TEXT_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 310, 310));

        jPanel_location.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_location.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_location.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_location.setText("Location");
        jPanel_location.add(jLabel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jList_countries.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList_countries.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Denmark", "Columbia" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane_countries.setViewportView(jList_countries);

        jPanel_location.add(jScrollPane_countries, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 120));

        jButton_travel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_travel.setText("Go!");
        jButton_travel.setToolTipText("");
        jButton_travel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_travelActionPerformed(evt);
            }
        });
        jPanel_location.add(jButton_travel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        getContentPane().add(jPanel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 310, 220));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 100, 110));

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

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 400, 100, 110));

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
            jLabel_daysLeft.setText(engine.getPlayer().getDays() + "");
            jLabel_daysLeft.setVisible(true);
            jLabel_life.setText(engine.getPlayer().getLife() + "");
            jLabel_life.setVisible(true);
            jLabel_money.setText(doubleCreditFormat.format(engine.getCredits()) + " $");
            jLabel_money.setVisible(true);
            jTextField_inputName.setVisible(false);
            jLabel_selectionLeft.setVisible(false);
            jLabel_selectionRight.setVisible(false);
            jButton_confirm.setVisible(false);
            jButton_newGame.setVisible(false);
        }
    }//GEN-LAST:event_jButton_confirmActionPerformed

    private void jButton_newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newGameActionPerformed
        jTextField_inputName.setText("");
        jLabel_name.setText("");
        jLabel_life.setText("");
        jLabel_money.setText("");
        jLabel_daysLeft.setText("");
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
        engine.getPlayer().setDays(-1);
        int currentDay = engine.getPlayer().getDays();
        if (currentDay >= 0) {
            jLabel_daysLeft.setText(engine.getPlayer().getDays() + "");
            int index = jList_countries.getSelectedIndex();
            engine.setActiveCountry((String) listmodel.getElementAt(index));
            setLocationText();
            engine.travel();
            prepareGame();
        }
        if (currentDay == 0) {
            jButton_travel.setText("Cash in!");
        }
        if (currentDay == -1) {
            autoSell(); //sell everything in inventory
            engine.savePlayer("players.txt"); //save player
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
            System.out.println("Selling in bulk");
            performSell();
    }
    }//GEN-LAST:event_jButton_bulkSellActionPerformed

    private void jButton_bulkBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_bulkBuyActionPerformed
        for (int i = 0; i < 10; i++) {
            System.out.println("Selling in bulk");
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
    private javax.swing.JLabel jLabel_TEXT_DaysLeft;
    private javax.swing.JLabel jLabel_TEXT_Life;
    private javax.swing.JLabel jLabel_TEXT_info;
    private javax.swing.JLabel jLabel_TEXT_market;
    private javax.swing.JLabel jLabel_TEXT_market1;
    private javax.swing.JLabel jLabel_TEXT_money;
    private javax.swing.JLabel jLabel_TEXT_name;
    private javax.swing.JLabel jLabel_characterPic;
    private javax.swing.JLabel jLabel_daysLeft;
    private javax.swing.JLabel jLabel_life;
    private javax.swing.JLabel jLabel_location;
    private javax.swing.JLabel jLabel_money;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JLabel jLabel_selectionLeft;
    private javax.swing.JLabel jLabel_selectionRight;
    private javax.swing.JList jList_countries;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_inventory;
    private javax.swing.JPanel jPanel_location;
    private javax.swing.JPanel jPanel_market;
    private javax.swing.JPanel jPanel_player;
    private javax.swing.JScrollPane jScrollPane_countries;
    private javax.swing.JScrollPane jScrollPane_inventory;
    private javax.swing.JScrollPane jScrollPane_market;
    private javax.swing.JTable jTable_inventory;
    private javax.swing.JTable jTable_market;
    private javax.swing.JTextField jTextField_inputName;
    // End of variables declaration//GEN-END:variables
}
