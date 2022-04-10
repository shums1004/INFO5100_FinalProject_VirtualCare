/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Hospital;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.DoctorDetails;
import model.UserDetails;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class DoctorsDashboard extends javax.swing.JPanel {

    /**
     * Creates new form DoctorsDashboard
     */
    public DoctorsDashboard(JSplitPane SplitPane, DoctorDetails d) {
        initComponents();
        this.d = d;
        DefaultTableModel AptMod = (DefaultTableModel) tblViewPatientsDD.getModel();
        this.AptMod = AptMod;
        Appointments = d.getAppointments();
        CardLayout Card = (CardLayout) cardLayout.getLayout();
        this.Card = Card;
        cardLayout.setVisible(false);
        this.SplitPane = SplitPane;
    }
    
    JSplitPane SplitPane;
    DoctorDetails d;
    DefaultTableModel AptMod;
    ArrayList<UserDetails> Appointments;
    CardLayout Card;

    void populateappointmentstable(){
        try{
            AptMod.setRowCount(0);

            if(Appointments.isEmpty()){
                JOptionPane.showMessageDialog(this, "No Appointments Available.");
                return;
            }
            
            Iterator itr = Appointments.iterator();
            while(itr.hasNext()){
                UserDetails u = (UserDetails)itr.next();
                String data[] = {u.getFirstName() + u.getLastName(), u.getDOB(), d.getDepartment(), u.getEmail()};
                AptMod.addRow(data);
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Appointments Available.");
        }
    }
    
    void deleteAppointment(){
        int Row = tblViewPatientsDD.getSelectedRow();
        String PatientEmail = tblViewPatientsDD.getValueAt(Row, 3).toString();
        
        Iterator itr = Appointments.iterator();
        while(itr.hasNext()){
            UserDetails u = (UserDetails)itr.next();
            
            if(PatientEmail.equalsIgnoreCase(u.getEmail())){
                Appointments.remove(u);
                u.getAppointments().remove(d);
                UserSystem.Doctordb.store(d);
                UserSystem.Userdb.store(u);
                break;
            }
        }   
    }
    
    void AddPrescription(){
        try{
            int row = tblViewPatientsDD.getSelectedRow();
            Iterator itr = Appointments.iterator();
            while(itr.hasNext()){
                UserDetails u = (UserDetails)itr.next();
                if(u.getEmail().equals(tblViewPatientsDD.getValueAt(row,3).toString())){
                    d.AddPrescription(u, txtAddPrescribtion.getText().trim());
                }
            }
        }
        catch(NullPointerException E){
            JOptionPane.showMessageDialog(this, "No Appointments Available.");
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

        jLabel1 = new javax.swing.JLabel();
        sidePanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        cardLayout = new javax.swing.JPanel();
        viewPatients = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewPatientsDD = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        btnAddPrescribtion = new javax.swing.JButton();
        addPrescribtion = new javax.swing.JPanel();
        txtAddPrescribtion = new javax.swing.JTextField();

        setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setText("Doctors Dashboard");

        sidePanel.setBackground(new java.awt.Color(0, 51, 51));

        jButton1.setText("View Patients");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(60, 60, 60))
        );

        cardLayout.setLayout(new java.awt.CardLayout());

        viewPatients.setBackground(new java.awt.Color(0, 0, 102));

        tblViewPatientsDD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Date of Birth", "Treatment", "Email-Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblViewPatientsDD);

        jButton2.setText("Delete Appointment");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnAddPrescribtion.setText("Add Prescribtion");
        btnAddPrescribtion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPrescribtionActionPerformed(evt);
            }
        });

        txtAddPrescribtion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddPrescribtionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addPrescribtionLayout = new javax.swing.GroupLayout(addPrescribtion);
        addPrescribtion.setLayout(addPrescribtionLayout);
        addPrescribtionLayout.setHorizontalGroup(
            addPrescribtionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addPrescribtionLayout.createSequentialGroup()
                .addComponent(txtAddPrescribtion, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );
        addPrescribtionLayout.setVerticalGroup(
            addPrescribtionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtAddPrescribtion, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout viewPatientsLayout = new javax.swing.GroupLayout(viewPatients);
        viewPatients.setLayout(viewPatientsLayout);
        viewPatientsLayout.setHorizontalGroup(
            viewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPatientsLayout.createSequentialGroup()
                .addGroup(viewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewPatientsLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(viewPatientsLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jButton2)
                        .addGap(67, 67, 67)
                        .addGroup(viewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddPrescribtion)
                            .addComponent(addPrescribtion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        viewPatientsLayout.setVerticalGroup(
            viewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPatientsLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(viewPatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnAddPrescribtion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPrescribtion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        cardLayout.add(viewPatients, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(318, 318, 318))
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cardLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddPrescribtionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddPrescribtionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddPrescribtionActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        populateappointmentstable();
        cardLayout.setVisible(true);
        Card.show(cardLayout, "card2");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(tblViewPatientsDD.getSelectedRow() == 0)
            return;
        deleteAppointment();
        JOptionPane.showMessageDialog(this, "Appointment Deleted.");
        populateappointmentstable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAddPrescribtionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPrescribtionActionPerformed
        // TODO add your handling code here:
        if(tblViewPatientsDD.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "Select a Patient");
            return;
        }
        AddPrescription();
        JOptionPane.showMessageDialog(this, "Prescription Added.");
    }//GEN-LAST:event_btnAddPrescribtionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addPrescribtion;
    private javax.swing.JButton btnAddPrescribtion;
    private javax.swing.JButton btnLogout;
    private javax.swing.JPanel cardLayout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JTable tblViewPatientsDD;
    private javax.swing.JTextField txtAddPrescribtion;
    private javax.swing.JPanel viewPatients;
    // End of variables declaration//GEN-END:variables
}
