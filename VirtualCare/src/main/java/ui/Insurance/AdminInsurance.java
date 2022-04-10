/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Insurance;

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
import model.InsuranceAgentDetails;
import model.InsuranceRequests;
import model.PharmacyOrders;
import ui.Hospital.AdminHospital;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminInsurance extends javax.swing.JPanel {

    /**
     * Creates new form AdminInsurance
     */
    public AdminInsurance(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        DefaultTableModel InsMod = (DefaultTableModel) tblViewInsuranceRequest.getModel();
        this.InsMod = InsMod;
        PullInsuranceRequeststoList();
        populateInsurancetable();
        this.SplitPane = SplitPane;
        CardLayout Card = (CardLayout) CardLayout.getLayout();
        this.Card = Card;
        CardLayout.setVisible(false);
    }

    CardLayout Card;
    JSplitPane SplitPane;
    AdminDetails a;
    ArrayList<InsuranceRequests> InsReq;
    DefaultTableModel InsMod;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    void PullInsuranceRequeststoList(){
        ArrayList<InsuranceRequests> InsReq = new ArrayList<>();

        InsuranceRequests I;
        try {
            List<InsuranceRequests> Insuranceresult = UserSystem.Insudb.query(InsuranceRequests.class);
            if(Insuranceresult.isEmpty())
                return;
            Iterator Insuitr = Insuranceresult.iterator();
            while(Insuitr.hasNext()){
                I = (InsuranceRequests)Insuitr.next();
                if(a.getOrganization().equalsIgnoreCase(I.getToOrg()))
                    InsReq.add(I);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.InsReq = InsReq;
    }
    
    void populateInsurancetable(){
        InsMod.setRowCount(0);
        try{
            Iterator itr = InsReq.iterator();
            while(itr.hasNext()){
                InsuranceRequests I = (InsuranceRequests)itr.next();

                if(I.getStatus().equalsIgnoreCase("Pending")){
                    String data[] = {I.getFromHospital(), I.getPatientEmail(), String.valueOf(I.getAmount()), I.getStatus()};
                    InsMod.addRow(data);
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Requests Exist.");
        }
    }
    
    
    
    boolean checkAgent(){
        if(txtFirstNameAI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fill all the fields");
            return false;
        }
        else if(txtLastNameAI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fill all the fields");
            return false;
        }
        else if(txtEmailAI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fill all the fields");
            return false;
        }
        else if(txtIAPassword.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Fill all the fields");
            return false;
        }
        else{
            return true;
        }
    }
    
    InsuranceAgentDetails makeAgent(){
        InsuranceAgentDetails IA = new InsuranceAgentDetails();
        IA.setFirstName(txtFirstNameAI.getText().trim());
        IA.setLastName(txtLastNameAI.getText().trim());
        IA.setOrganization(a.getOrganization());
        IA.setEmail(txtEmailAI.getText().trim());
        IA.setPassword(Arrays.toString(txtIAPassword.getPassword()));
        return IA;
    }
    
    void clearfields(){
        txtFirstNameAI.setText("");
        txtLastNameAI.setText("");
        txtEmailAI.setText("");
        txtIAPassword.setText("");
    }
    
    public static void AddInsAgenttoDB(InsuranceAgentDetails IA){     
        try {
            UserSystem.InsAgentdb.store(IA);
            System.out.println("Stored " + IA.getFirstName());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Database Error");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddAgent = new javax.swing.JButton();
        btnViewRequests = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        CardLayout = new javax.swing.JPanel();
        AddAgent = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblFirstNameAI = new javax.swing.JLabel();
        lblLastNameAI = new javax.swing.JLabel();
        lblEmailAI = new javax.swing.JLabel();
        lblPasswordAI = new javax.swing.JLabel();
        txtFirstNameAI = new javax.swing.JTextField();
        txtEmailAI = new javax.swing.JTextField();
        txtLastNameAI = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txtIAPassword = new javax.swing.JPasswordField();
        ViewRequests = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewInsuranceRequest = new javax.swing.JTable();
        btnfwdtoAgent = new javax.swing.JButton();
        btnDecline = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        btnAddAgent.setText("Add Agent");
        btnAddAgent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAgentActionPerformed(evt);
            }
        });

        btnViewRequests.setText("View Request");
        btnViewRequests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewRequestsActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddAgent)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnViewRequests)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogout)
                        .addGap(68, 68, 68))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(btnAddAgent)
                .addGap(18, 18, 18)
                .addComponent(btnViewRequests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        CardLayout.setLayout(new java.awt.CardLayout());

        AddAgent.setBackground(new java.awt.Color(51, 153, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Adding Agent");

        lblFirstNameAI.setText("First Name:");

        lblLastNameAI.setText("Last Name:");

        lblEmailAI.setText("email-id:");

        lblPasswordAI.setText("Password:");

        txtFirstNameAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameAIActionPerformed(evt);
            }
        });

        txtEmailAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailAIActionPerformed(evt);
            }
        });

        txtLastNameAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameAIActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setText("Add Agent");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddAgentLayout = new javax.swing.GroupLayout(AddAgent);
        AddAgent.setLayout(AddAgentLayout);
        AddAgentLayout.setHorizontalGroup(
            AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAgentLayout.createSequentialGroup()
                .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAgentLayout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(jLabel2))
                    .addGroup(AddAgentLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFirstNameAI)
                            .addComponent(lblLastNameAI, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmailAI, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPasswordAI, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(64, 64, 64)
                        .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLastNameAI, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(txtEmailAI, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(txtFirstNameAI, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(txtIAPassword))))
                .addContainerGap(183, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddAgentLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(281, 281, 281))
        );
        AddAgentLayout.setVerticalGroup(
            AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAgentLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(60, 60, 60)
                .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstNameAI)
                    .addComponent(txtFirstNameAI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastNameAI)
                    .addComponent(txtLastNameAI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailAI)
                    .addComponent(txtEmailAI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(AddAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPasswordAI)
                    .addComponent(txtIAPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jButton2)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        CardLayout.add(AddAgent, "card2");

        tblViewInsuranceRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Hospital", "Patient Id", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblViewInsuranceRequest);

        btnfwdtoAgent.setText("Forward to Agent");
        btnfwdtoAgent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfwdtoAgentActionPerformed(evt);
            }
        });

        btnDecline.setText("Decline");
        btnDecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewRequestsLayout = new javax.swing.GroupLayout(ViewRequests);
        ViewRequests.setLayout(ViewRequestsLayout);
        ViewRequestsLayout.setHorizontalGroup(
            ViewRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewRequestsLayout.createSequentialGroup()
                .addGroup(ViewRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewRequestsLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(btnfwdtoAgent)
                        .addGap(121, 121, 121)
                        .addComponent(btnDecline)
                        .addGap(0, 197, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                .addContainerGap())
        );
        ViewRequestsLayout.setVerticalGroup(
            ViewRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewRequestsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(ViewRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDecline)
                    .addComponent(btnfwdtoAgent))
                .addContainerGap(308, Short.MAX_VALUE))
        );

        CardLayout.add(ViewRequests, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(CardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1423, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(2436, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(190, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnfwdtoAgentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfwdtoAgentActionPerformed
        // TODO add your handling code here:
        if(tblViewInsuranceRequest.getSelectedRow() < 0)
            return;
        int Row = tblViewInsuranceRequest.getSelectedRow();
        String PatientEmail = tblViewInsuranceRequest.getValueAt(Row, 1).toString();
        
        InsuranceRequests I;
        
        Iterator itr = InsReq.iterator();
        while(itr.hasNext()){
            I = (InsuranceRequests)itr.next();
            if(I.getPatientEmail().equalsIgnoreCase(PatientEmail)){
                I.setStatus("Agent Review");
                AdminHospital.AddInsRequeststoDB(I);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Forwarded to Agent.");
        populateInsurancetable();
    }//GEN-LAST:event_btnfwdtoAgentActionPerformed

    private void btnDeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineActionPerformed
        // TODO add your handling code here:
        if(tblViewInsuranceRequest.getSelectedRow() < 0)
            return;
        int Row = tblViewInsuranceRequest.getSelectedRow();
        String PatientEmail = tblViewInsuranceRequest.getValueAt(Row, 1).toString();
        
        InsuranceRequests I;
        
        Iterator itr = InsReq.iterator();
        while(itr.hasNext()){
            I = (InsuranceRequests)itr.next();
            if(I.getPatientEmail().equalsIgnoreCase(PatientEmail)){
                I.setStatus("Declined");
                AdminHospital.AddInsRequeststoDB(I);
                break;
            }
        }
        JOptionPane.showMessageDialog(this, "Request Declined.");
        populateInsurancetable();
    }//GEN-LAST:event_btnDeclineActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void txtFirstNameAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameAIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameAIActionPerformed

    private void txtEmailAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailAIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailAIActionPerformed

    private void txtLastNameAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameAIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameAIActionPerformed

    private void btnAddAgentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAgentActionPerformed
        // TODO add your handling code here:
        Card.show(CardLayout, "card2");
        CardLayout.setVisible(true);
    }//GEN-LAST:event_btnAddAgentActionPerformed

    private void btnViewRequestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewRequestsActionPerformed
        // TODO add your handling code here:
        PullInsuranceRequeststoList();
        populateInsurancetable();
        Card.show(CardLayout, "card3");
        CardLayout.setVisible(true);
    }//GEN-LAST:event_btnViewRequestsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(checkAgent()){
            InsuranceAgentDetails IA = makeAgent();
            AddInsAgenttoDB(IA);
            clearfields();
            JOptionPane.showMessageDialog(this, "Agent Added");
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddAgent;
    private javax.swing.JPanel CardLayout;
    private javax.swing.JPanel ViewRequests;
    private javax.swing.JButton btnAddAgent;
    private javax.swing.JButton btnDecline;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnViewRequests;
    private javax.swing.JButton btnfwdtoAgent;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmailAI;
    private javax.swing.JLabel lblFirstNameAI;
    private javax.swing.JLabel lblLastNameAI;
    private javax.swing.JLabel lblPasswordAI;
    private javax.swing.JTable tblViewInsuranceRequest;
    private javax.swing.JTextField txtEmailAI;
    private javax.swing.JTextField txtFirstNameAI;
    private javax.swing.JPasswordField txtIAPassword;
    private javax.swing.JTextField txtLastNameAI;
    // End of variables declaration//GEN-END:variables
}
