/**
 * @author Tobias
 */
package Boundaries;

import Controllere.Engine;
import Entities.Drug;
import Entities.Player;
import Interfaces.EngineInterface;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GUI_Main extends javax.swing.JFrame {

    private EngineInterface engine;
    private DefaultListModel listmodel = new DefaultListModel();
    

    private int avail;
    private double price;
    public GUI_Main() {
        initComponents();
        this.setLocationRelativeTo(null);
        engine = new Engine();
        jList_countries.setModel(listmodel);
        fillCountryList();
        setLocationText();
        jLabel_money.setText(Double.toString(engine.getCredits()));
        jButton_buy.setEnabled(false);
        jButton_sell.setEnabled(false);
    }

    private void setLocationText() {
        //char char1 = engine.getActiveCountry().charAt(0);
        jLabel_location.setText("Location: " + engine.getActiveCountry().toUpperCase().charAt(0)
                + engine.getActiveCountry().substring(1));
    }

    private void fillCountryList() {
        listmodel.clear();
        // fill countrylist
        for (int i = 0; i < engine.getCountries().size(); i++) {
            String country = (String) engine.getCountries().get(i);
            listmodel.addElement(country.toUpperCase().charAt(0) + country.substring(1));
        }

        // fill market table
        List<Drug> tempList = engine.travel();

        for (int i = 0; i < tempList.size(); i++) {
            Drug drug = tempList.get(i);
            String name = drug.getName();
            Double price = drug.getModifiedPrice();
            avail = drug.getModifiedAvail();
            jTable_market.setValueAt(name, i, 0);
            jTable_market.setValueAt(avail, i, 1);
            jTable_market.setValueAt(price, i, 2);
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

        jButton_sell = new javax.swing.JButton();
        jButton_buy = new javax.swing.JButton();
        jButton_highscore = new javax.swing.JButton();
        jButton_newGame = new javax.swing.JButton();
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
        jButton_newGame1 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thug Life 0.1");
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_sell.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_sell.setText("<< Sell ");
        jButton_sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sellActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sell, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 80, -1));

        jButton_buy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_buy.setText("Buy >>");
        jButton_buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buyActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_buy, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 80, -1));

        jButton_highscore.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_highscore.setText("Highscore");
        getContentPane().add(jButton_highscore, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        jButton_newGame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_newGame.setText("Confirm");
        jButton_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newGameActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_newGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jPanel_player.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_player.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_characterPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/characters/clemenza.png"))); // NOI18N
        jLabel_characterPic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_characterPic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_characterPic.setMaximumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setMinimumSize(new java.awt.Dimension(800, 600));
        jLabel_characterPic.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel_player.add(jLabel_characterPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 110, 160));

        jLabel_TEXT_info.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_info.setText("Info");
        jPanel_player.add(jLabel_TEXT_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel_TEXT_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_name.setText("Name: ");
        jPanel_player.add(jLabel_TEXT_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_name.setText("Hardboilr");
        jPanel_player.add(jLabel_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

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
        jPanel_player.add(jLabel_selectionRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, -1, -1));

        jLabel_selectionLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/art/gui/selctionArrow_left.png"))); // NOI18N
        jPanel_player.add(jLabel_selectionLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        jLabel_TEXT_DaysLeft.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_TEXT_DaysLeft.setText("Days left: ");
        jLabel_TEXT_DaysLeft.setToolTipText("");
        jPanel_player.add(jLabel_TEXT_DaysLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 20));

        jLabel_daysLeft.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_daysLeft.setText("14");
        jLabel_daysLeft.setToolTipText("");
        jLabel_daysLeft.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel_daysLeftPropertyChange(evt);
            }
        });
        jPanel_player.add(jLabel_daysLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, 20));

        getContentPane().add(jPanel_player, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 360, 180));

        jButton_newGame1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_newGame1.setText("New game");
        jButton_newGame1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newGame1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_newGame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel_market.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_market.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_market.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_market.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
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
        jTable_market.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_marketMouseClicked(evt);
            }
        });
        jScrollPane_market.setViewportView(jTable_market);
        if (jTable_market.getColumnModel().getColumnCount() > 0) {
            jTable_market.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        jPanel_market.add(jScrollPane_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 260));

        jLabel_TEXT_market1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_TEXT_market1.setText("Market");
        jPanel_market.add(jLabel_TEXT_market1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel_market, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 320, 310));

        jPanel_inventory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_inventory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_inventory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable_inventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
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

        getContentPane().add(jPanel_inventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 330, 310));

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

        getContentPane().add(jPanel_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 330, 220));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newGameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_newGameActionPerformed

    private void jButton_newGame1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newGame1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_newGame1ActionPerformed

    private void jButton_travelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_travelActionPerformed
        int index = jList_countries.getSelectedIndex();
        //System.out.println(listmodel.getElementAt(index));
        engine.setActiveCountry((String) listmodel.getElementAt(index));
        setLocationText();
        engine.travel();
        fillCountryList();


    }//GEN-LAST:event_jButton_travelActionPerformed

    private void jButton_buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buyActionPerformed
        if(transaction(jTable_market, jTable_inventory) == true){
        engine.calculateCredits(-price);
        jLabel_money.setText(Double.toString(engine.getCredits()));
        }
    }//GEN-LAST:event_jButton_buyActionPerformed

    private void jButton_sellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sellActionPerformed
        if(transaction(jTable_inventory, jTable_market) == true){
        engine.calculateCredits(price);
        jLabel_money.setText(Double.toString(engine.getCredits()));
        }
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

    private void jLabel_daysLeftPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel_daysLeftPropertyChange
        if (jLabel_daysLeft.getText().equals("0")) {
            //game is over
            //close frame and open highscore frame
        }
    }//GEN-LAST:event_jLabel_daysLeftPropertyChange

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

    public boolean transaction(javax.swing.JTable input1, javax.swing.JTable input2) {
        int row = input1.getSelectedRow();
        String marketName = (String) input1.getValueAt(row, 0);
        int qty = (int) input1.getValueAt(row, 1);
        price = (double) input1.getValueAt(row, 2);
        int subtract = qty - 1;

        if (subtract >= 0 && engine.getCredits() - price >= 0) {
            input1.setValueAt(subtract, row, 1);
            for (int i = 0; i <= input2.getRowCount(); i++) {
                String temp = (String) input2.getValueAt(i, 0);
                String inventoryName = (String) input2.getValueAt(i, 0);
                if (temp == null) {
                    int add = 1;
                    System.out.println("temp is null");
                    input2.setValueAt(marketName, i, 0);
                    input2.setValueAt(add, i, 1);
                    input2.setValueAt(price, i, 2);

                    break;
                } else if (inventoryName == marketName) {
                    System.out.println("Eksisterer i forvejen!");
                    int newQty = (int) input2.getValueAt(i, 1) + 1;
                    input2.setValueAt(newQty, i, 1);
                    break;
                }

            }
            if (subtract == 0) {
                System.out.println("Nu fjerner jeg!");
                ((DefaultTableModel) input1.getModel()).removeRow(row);
                ((DefaultTableModel) input1.getModel()).addRow(new Object[]{});
            }
            return true;
        }

        return false;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_buy;
    private javax.swing.JButton jButton_highscore;
    private javax.swing.JButton jButton_newGame;
    private javax.swing.JButton jButton_newGame1;
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
    private javax.swing.JPanel jPanel_inventory;
    private javax.swing.JPanel jPanel_location;
    private javax.swing.JPanel jPanel_market;
    private javax.swing.JPanel jPanel_player;
    private javax.swing.JScrollPane jScrollPane_countries;
    private javax.swing.JScrollPane jScrollPane_inventory;
    private javax.swing.JScrollPane jScrollPane_market;
    private javax.swing.JTable jTable_inventory;
    private javax.swing.JTable jTable_market;
    // End of variables declaration//GEN-END:variables
}
