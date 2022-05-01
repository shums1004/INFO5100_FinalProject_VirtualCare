/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Pharmacy;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.DeliveryHeadDetails;
import model.PharmacyOrders;
import ui.Hospital.AdminHospital;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminPharmacy extends javax.swing.JPanel {

    /**
     * Creates new form AdminPharmacy
     */
    public AdminPharmacy(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        DefaultTableModel PhMod = (DefaultTableModel) tblMedicineRequests.getModel();
        this.PhMod = PhMod;
        jLabel1.setText("Pharmacy " + a.getOrganization());
        this.SplitPane = SplitPane;
        CardLayout Card = (CardLayout) CardLayout.getLayout();
        this.Card = Card;
        CardLayout.setVisible(false);
    }

    CardLayout Card;
    JSplitPane SplitPane;
    AdminDetails a;
    ArrayList<PharmacyOrders> PhReq;
    DefaultTableModel PhMod;
    ArrayList<DeliveryHeadDetails> DHlist;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    DeliveryHeadDetails makeHead(){
        DeliveryHeadDetails DH = new DeliveryHeadDetails();
        DH.setFirstName(jTextField1.getText().trim());
        DH.setLastName(jTextField4.getText().trim());
        DH.setOrganization(a.getOrganization());
        DH.setEmail(jTextField2.getText().trim());
        DH.setPassword(Arrays.toString(jPasswordField1.getPassword()));
        return DH;
    }
    
    void clearfields(){
        jTextField1.setText("");
        jTextField4.setText("");
        jTextField2.setText("");
        jPasswordField1.setText("");
    }
    
    boolean checkfields(){
        if(jTextField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(jTextField4.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(jTextField2.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(jPasswordField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else{
            return true;
        }
    }
    
    public static void AddDelHeadtoDB(DeliveryHeadDetails DH){     
        try {
            UserSystem.DelHeaddb.store(DH);
            System.out.println("Stored " + DH.getFirstName());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Database Error");
        }
    }
    
    void PullDHtoList(){
        ArrayList<DeliveryHeadDetails> DHlist = new ArrayList<>();

        DeliveryHeadDetails DH;
        try {
            List<DeliveryHeadDetails> DHresult = UserSystem.DelHeaddb.query(DeliveryHeadDetails.class);
            if(DHresult.isEmpty())
                return;
            Iterator DHitr = DHresult.iterator();
            while(DHitr.hasNext()){
                DH = (DeliveryHeadDetails)DHitr.next();
                if(a.getOrganization().equalsIgnoreCase(DH.getOrganization()))
                    DHlist.add(DH);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.DHlist = DHlist;
    }
    
    void PullPhOrderstoList(){
        ArrayList<PharmacyOrders> PhReq = new ArrayList<>();

        PharmacyOrders P;
        try {
            List<PharmacyOrders> Pharmacyresult = UserSystem.Phardb.query(PharmacyOrders.class);
            if(Pharmacyresult.isEmpty())
                return;
            Iterator Phitr = Pharmacyresult.iterator();
            while(Phitr.hasNext()){
                P = (PharmacyOrders)Phitr.next();
                if(a.getOrganization().equalsIgnoreCase(P.getToOrg()))
                    PhReq.add(P);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.PhReq = PhReq;
    }
    
    void populateOrderstable(){
        PhMod.setRowCount(0);
        Iterator itr = PhReq.iterator();
        while(itr.hasNext()){
            PharmacyOrders P = (PharmacyOrders)itr.next();

            if(P.getStatus().equalsIgnoreCase("Pending")){
                String data[] = {P.getFromHospital(), P.getMedicine(), String.valueOf(P.getQuantity())};
                PhMod.addRow(data);
            }
        }
    }
    
    boolean DHExists(DeliveryHeadDetails DH){
        PullDHtoList();
        if(DHlist == null){
            return false;
        }
        else if(DHlist.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnAddDelivery = new javax.swing.JButton();
        btnAssignDelivey = new javax.swing.JButton();
        CardLayout = new javax.swing.JPanel();
        AssignDelivery = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicineRequests = new javax.swing.JTable();
        btnDelivered = new javax.swing.JButton();
        AddDeliveryHead = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        btnAddDH = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnAddDelivery.setText("Add Delivery Head");
        btnAddDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDeliveryActionPerformed(evt);
            }
        });

        btnAssignDelivey.setText("Assign Delivery");
        btnAssignDelivey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignDeliveyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddDelivery)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnAssignDelivey)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(btnAssignDelivey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddDelivery)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        CardLayout.setLayout(new java.awt.CardLayout());

        AssignDelivery.setBackground(new java.awt.Color(51, 153, 255));
        AssignDelivery.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Pharmacy");
        AssignDelivery.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 245, 49));

        tblMedicineRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Hospital", "Medicine Name", "Quatinty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMedicineRequests);

        AssignDelivery.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 98, 630, 137));

        btnDelivered.setText("Assign Delivery");
        btnDelivered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeliveredActionPerformed(evt);
            }
        });
        AssignDelivery.add(btnDelivered, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 283, -1, -1));

        CardLayout.add(AssignDelivery, "card2");

        jLabel2.setText("Adding Delivery Head");

        jLabel3.setText("First Name:");

        jLabel4.setText("Email Id:");

        jLabel5.setText("Password:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        btnAddDH.setText("ADD");
        btnAddDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDHActionPerformed(evt);
            }
        });

        jLabel6.setText("Last Name:");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddDeliveryHeadLayout = new javax.swing.GroupLayout(AddDeliveryHead);
        AddDeliveryHead.setLayout(AddDeliveryHeadLayout);
        AddDeliveryHeadLayout.setHorizontalGroup(
            AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                            .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(34, 34, 34)
                            .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                .addComponent(jPasswordField1)))
                        .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addComponent(btnAddDH)))
                    .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                            .addGap(267, 267, 267)
                            .addComponent(jLabel3)
                            .addGap(34, 34, 34)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                            .addGap(356, 356, 356)
                            .addComponent(jLabel2)))
                    .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(274, Short.MAX_VALUE))
        );
        AddDeliveryHeadLayout.setVerticalGroup(
            AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDeliveryHeadLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(59, 59, 59)
                .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(AddDeliveryHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnAddDH)
                .addContainerGap(301, Short.MAX_VALUE))
        );

        CardLayout.add(AddDeliveryHead, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(791, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(166, Short.MAX_VALUE)
                    .addComponent(CardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CardLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeliveredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeliveredActionPerformed
        // TODO add your handling code here:
        if(tblMedicineRequests.getSelectedRow() < 0)
            return;
        int Row = tblMedicineRequests.getSelectedRow();
        String Medicine = tblMedicineRequests.getValueAt(Row, 1).toString();
        String Hospital = tblMedicineRequests.getValueAt(Row, 0).toString();
        int Quantity = Integer.parseInt(tblMedicineRequests.getValueAt(Row, 2).toString());
        
        PharmacyOrders P;
        
        Iterator itr = PhReq.iterator();
        while(itr.hasNext()){
            P = (PharmacyOrders)itr.next();
            if(P.getFromHospital().equalsIgnoreCase(Hospital) && P.getMedicine().equalsIgnoreCase(Medicine) && P.getQuantity() == Quantity){
                
                P.setStatus("Delivery Review");
                AdminHospital.AddPhOrderstoDB(P);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Sent for Delivery.");
        populateOrderstable();
    }//GEN-LAST:event_btnDeliveredActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void btnAssignDeliveyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignDeliveyActionPerformed
        // TODO add your handling code here:
        PullPhOrderstoList();
        populateOrderstable();
        Card.show(CardLayout, "card2");
        CardLayout.setVisible(true);
    }//GEN-LAST:event_btnAssignDeliveyActionPerformed

    private void btnAddDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDeliveryActionPerformed
        // TODO add your handling code here:
        clearfields();
        Card.show(CardLayout, "card3");
        CardLayout.setVisible(true);
    }//GEN-LAST:event_btnAddDeliveryActionPerformed

    private void btnAddDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDHActionPerformed
        // TODO add your handling code here:
        if(checkfields()){
            DeliveryHeadDetails DH = makeHead();
            if(!DHExists(DH)){
                AddDelHeadtoDB(DH);
                JOptionPane.showMessageDialog(this, "Delivery Head Added");
            }
            else{
                JOptionPane.showMessageDialog(this, "Delivery Head " + DHlist.get(0).getFirstName() + "already exists for " + a.getOrganization());
            }
        }
    }//GEN-LAST:event_btnAddDHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddDeliveryHead;
    private javax.swing.JPanel AssignDelivery;
    private javax.swing.JPanel CardLayout;
    private javax.swing.JButton btnAddDH;
    private javax.swing.JButton btnAddDelivery;
    private javax.swing.JButton btnAssignDelivey;
    private javax.swing.JButton btnDelivered;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable tblMedicineRequests;
    // End of variables declaration//GEN-END:variables
}
