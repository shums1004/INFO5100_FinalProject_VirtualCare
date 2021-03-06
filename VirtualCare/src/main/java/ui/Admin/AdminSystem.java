/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.Admin;

import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import model.AdminDetails;
import model.UserDetails;
import org.apache.commons.validator.routines.EmailValidator;
import ui.User.UserSystem;

/**
 *
 * @author shubhampatil
 */
public class AdminSystem extends javax.swing.JPanel {

    /**
     * Creates new form AdminSystem
     */
    public AdminSystem(JSplitPane SplitPane, AdminDetails a) {
        initComponents();
        this.a = a;
        Card = (CardLayout) cardLayout.getLayout();
        cardLayout.setVisible(true);
        DefaultTableModel OrgMod = (DefaultTableModel) tableViewOrganisations.getModel();
        DefaultTableModel UserMod = (DefaultTableModel) UsersTable.getModel();
        this.UserMod = UserMod;
        this.OrgMod = OrgMod;
        this.SplitPane = SplitPane;
    }
    
    JSplitPane SplitPane;
    CardLayout Card;
    AdminDetails a;
    HashMap<String, UserDetails> UserMap;
    HashMap<String, AdminDetails> AdminMap;
    DefaultTableModel UserMod;
    DefaultTableModel OrgMod;
    

    
    void PullUserstoHashMap(){
        HashMap<String, UserDetails> UserMap = new HashMap<>();
        
        UserDetails u;
        try {
            List<UserDetails> userresult = UserSystem.Userdb.query(UserDetails.class);
            if(userresult.isEmpty())
                return;
            Iterator useritr = userresult.iterator();
            while(useritr.hasNext()){
                u = (UserDetails)useritr.next();
                UserMap.put(u.getEmail(), u);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.UserMap = UserMap;
    }
    
    void PullAdminstoHashMap(){
        HashMap<String, AdminDetails> AdminMap = new HashMap<>();

        AdminDetails a;
        try {
            List<AdminDetails> adminresult = UserSystem.Admindb.query(AdminDetails.class);
            if(adminresult.isEmpty()){
                this.AdminMap = AdminMap;
                return;
            }
            Iterator adminitr = adminresult.iterator();
            while(adminitr.hasNext()){
                a = (AdminDetails)adminitr.next();
                AdminMap.put(a.getEmail(), a);
            }
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
        this.AdminMap = AdminMap;
    }
    
    AdminDetails makeAdmin(){
        AdminDetails a = new AdminDetails();
        a.setEnterprise(cmbBoxSelectEnterpriseAS.getSelectedItem().toString());
        a.setOrganization(txtNameOfOrganisationAS.getText());
        a.setLocation(txtLocationAS.getText());
        a.setRatings(Integer.parseInt(txtRatingsAS.getText()));
        a.setEmail(txtEmailIdAS.getText());
        a.setPassword(Arrays.toString(txtPasswordAS.getPassword()));
        return a;
    }
    
    public static void AddAdmintoDB(AdminDetails a){
        try {
            UserSystem.Admindb.store(a);
            System.out.println("Stored: " + a.getEmail());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            System.out.println("Error in storing:  " + a.getEmail());
        }
    }
    
    void clearorgfields(){
        txtNameOfOrganisationAS.setText("");
        txtLocationAS.setText("");
        txtRatingsAS.setText("");
        txtEmailIdAS.setText("");
        txtPasswordAS.setText("");
    }
    
    void displayusers(){
        UserMod.setRowCount(0);
        try{
        Collection<UserDetails> values = UserMap.values();
        ArrayList<UserDetails> Users = new ArrayList<>(values);
        Iterator itr = Users.iterator();
        while(itr.hasNext()){
            UserDetails u = (UserDetails) itr.next();
            String data[] = {u.getFirstName(), u.getDOB(), u.getCity(), u.getEmail()};
            UserMod.addRow(data);
            }
        }
        catch(NullPointerException E){
            return;
        }
    }
    void RemoveuserfromDB(UserDetails u){
        try {
            UserSystem.Userdb.delete(u);
            System.out.println("Deleted: " + u.getFirstName());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
    }
    
    void RemoveAdminfromDB(AdminDetails a){
        try {
            UserSystem.Admindb.delete(a);
            System.out.println("Deleted: " + a.getEmail());
        }
        catch(DatabaseClosedException | Db4oIOException E){
            JOptionPane.showMessageDialog(this, "Database Error.");
        }
    }
    
    void displayOrgs(String Enterprise){
        OrgMod.setRowCount(0);
        try{
        Collection<AdminDetails> values = AdminMap.values();
        ArrayList<AdminDetails> Users = new ArrayList<>(values);
        Iterator itr = Users.iterator();
        while(itr.hasNext()){
            AdminDetails a = (AdminDetails) itr.next();
            if(Enterprise.equalsIgnoreCase(a.getEnterprise())){
            String data[] = {a.getOrganization(), a.getLocation(), a.getEmail(), String.valueOf(a.getRatings())};
            OrgMod.addRow(data);
            }
        }
        }
        catch(NullPointerException E){
            return;
        }
    }
    
    boolean CheckBlankFields(){
        if(txtNameOfOrganisationAS.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtLocationAS.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(txtRatingsAS.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Fields can't be blank");
            return false;
        }
        else if(!EmailValidator.getInstance().isValid(txtEmailIdAS.getText().trim())){
            JOptionPane.showMessageDialog(this, "Invalid Email ID");
            return false;
        }
        else if(!isValidPassword(txtPasswordAS.getText().trim())){
            JOptionPane.showMessageDialog(this, "Password must have one numeric, one lowercase, one uppercase, one symbol (@#$%) and length should be between 8 to 20");
            return false;
        }
        else
            return true;
    }
    
    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
    boolean checkduplicateentry(){
        String Email = txtEmailIdAS.getText().trim();
        PullAdminstoHashMap();
        try{
            if(AdminMap.get(Email) == null){
                return false;
            }
            else{
                return true;
            }
        }
        catch(NullPointerException E){
            return false;
        }
    }
    
    boolean checkduplicatetableentry(){
        int Row = tableViewOrganisations.getSelectedRow();
        String Email = tableViewOrganisations.getValueAt(Row, 2).toString().trim();
        PullAdminstoHashMap();
        if(AdminMap.get(Email) == null){
            return false;
        }
        else{
            return true;
        }
    }
    
    boolean checkblanktablefields(){
        int Row = tableViewOrganisations.getSelectedRow();
        try{
            String Name = tableViewOrganisations.getValueAt(Row, 0).toString();
            String Location = tableViewOrganisations.getValueAt(Row, 1).toString();
            String Email = tableViewOrganisations.getValueAt(Row, 2).toString();
            int Ratings = Integer.parseInt(tableViewOrganisations.getValueAt(Row, 3).toString());
            if(Name.equals("")){
                JOptionPane.showMessageDialog(this, "Fields can't be blank");
                return false;
            }
            else if(Location.equals("")){
                JOptionPane.showMessageDialog(this, "Fields can't be blank");
                return false;
            }
            else if(Email.equals("")){
                JOptionPane.showMessageDialog(this, "Fields can't be blank");
                return false;
            }
            else if(Ratings > 5 || Ratings < 0){
                JOptionPane.showMessageDialog(this, "Invalid Ratings");
                return false;
            }
            else{
                return true;
            }
        }
        catch(NumberFormatException E){
            JOptionPane.showMessageDialog(this, "Ratings should be a number");
            return false;
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

        sidePanelAS = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnViewOrganisations = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        btnAddOrg = new javax.swing.JButton();
        cardLayout = new javax.swing.JPanel();
        AdminDashboard = new javax.swing.JPanel();
        lblVirtualCare = new javax.swing.JLabel();
        cmbBoxSelectEnterpriseAS = new javax.swing.JComboBox<>();
        lblSelectEnterpriseAS = new javax.swing.JLabel();
        lblNameOfOrganisation = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        lblEmailId = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtNameOfOrganisationAS = new javax.swing.JTextField();
        txtLocationAS = new javax.swing.JTextField();
        txtEmailIdAS = new javax.swing.JTextField();
        jButton1AS = new javax.swing.JButton();
        lblADDORGANISTION = new javax.swing.JLabel();
        lblRatings = new javax.swing.JLabel();
        txtRatingsAS = new javax.swing.JTextField();
        txtPasswordAS = new javax.swing.JPasswordField();
        ViewOrganisation = new javax.swing.JPanel();
        lblVirtualCare1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbBoxSelectEnterpriseAS1 = new javax.swing.JComboBox<>();
        lblSelectEnterpriseAS1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableViewOrganisations = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        ViewUsers = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        btnDeleteUser = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 153, 255));
        setPreferredSize(new java.awt.Dimension(1200, 1200));

        sidePanelAS.setBackground(new java.awt.Color(0, 102, 204));

        jButton1.setText("View Users");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnViewOrganisations.setText("View Organsations");
        btnViewOrganisations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOrganisationsActionPerformed(evt);
            }
        });

        btnLogOut.setText("LogOut");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnAddOrg.setText("Add organisation");
        btnAddOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidePanelASLayout = new javax.swing.GroupLayout(sidePanelAS);
        sidePanelAS.setLayout(sidePanelASLayout);
        sidePanelASLayout.setHorizontalGroup(
            sidePanelASLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddOrg, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnViewOrganisations, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(sidePanelASLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btnLogOut))
        );
        sidePanelASLayout.setVerticalGroup(
            sidePanelASLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelASLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(btnAddOrg)
                .addGap(18, 18, 18)
                .addComponent(btnViewOrganisations)
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addGap(12, 12, 12)
                .addComponent(btnLogOut))
        );

        cardLayout.setLayout(new java.awt.CardLayout());

        AdminDashboard.setBackground(new java.awt.Color(51, 153, 255));
        AdminDashboard.setPreferredSize(new java.awt.Dimension(1200, 1200));

        lblVirtualCare.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare.setText("VIRTUAL CARE");

        cmbBoxSelectEnterpriseAS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hospital", "Pharmacy", "NGO", "Insurance" }));

        lblSelectEnterpriseAS.setText("Select Enterprise ");

        lblNameOfOrganisation.setText("Name of Organisation:");

        lblLocation.setText("Location :");

        lblEmailId.setText("Email Id:");

        lblPassword.setText("Password:");

        txtNameOfOrganisationAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameOfOrganisationASActionPerformed(evt);
            }
        });

        txtLocationAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocationASActionPerformed(evt);
            }
        });

        txtEmailIdAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailIdASActionPerformed(evt);
            }
        });

        jButton1AS.setText("ADD Organisation");
        jButton1AS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ASActionPerformed(evt);
            }
        });

        lblADDORGANISTION.setText("ADD ORGANISATION");

        lblRatings.setText("Ratings");

        txtRatingsAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRatingsASActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminDashboardLayout = new javax.swing.GroupLayout(AdminDashboard);
        AdminDashboard.setLayout(AdminDashboardLayout);
        AdminDashboardLayout.setHorizontalGroup(
            AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardLayout.createSequentialGroup()
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminDashboardLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmailId)
                            .addComponent(lblPassword)
                            .addComponent(lblRatings)
                            .addComponent(lblLocation)
                            .addComponent(lblNameOfOrganisation))
                        .addGap(27, 27, 27)
                        .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRatingsAS, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLocationAS, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmailIdAS, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPasswordAS, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNameOfOrganisationAS, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AdminDashboardLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jButton1AS)))
                .addGap(293, 293, 293))
            .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AdminDashboardLayout.createSequentialGroup()
                    .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AdminDashboardLayout.createSequentialGroup()
                            .addGap(221, 221, 221)
                            .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AdminDashboardLayout.createSequentialGroup()
                            .addGap(213, 213, 213)
                            .addComponent(lblADDORGANISTION))
                        .addGroup(AdminDashboardLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblSelectEnterpriseAS)
                                .addComponent(cmbBoxSelectEnterpriseAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(575, Short.MAX_VALUE)))
        );
        AdminDashboardLayout.setVerticalGroup(
            AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameOfOrganisationAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNameOfOrganisation))
                .addGap(22, 22, 22)
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLocationAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRatings)
                    .addComponent(txtRatingsAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmailId)
                    .addComponent(txtEmailIdAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPasswordAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jButton1AS)
                .addGap(764, 764, 764))
            .addGroup(AdminDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AdminDashboardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblVirtualCare, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(lblADDORGANISTION)
                    .addGap(17, 17, 17)
                    .addComponent(lblSelectEnterpriseAS)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(cmbBoxSelectEnterpriseAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1060, Short.MAX_VALUE)))
        );

        cardLayout.add(AdminDashboard, "card2");

        ViewOrganisation.setBackground(new java.awt.Color(51, 153, 255));

        lblVirtualCare1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lblVirtualCare1.setText("VIRTUAL CARE");

        jLabel1.setText("VIEW ORGANISATIONS");

        cmbBoxSelectEnterpriseAS1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hospital", "Insurance", "NGO", "Pharmacy" }));
        cmbBoxSelectEnterpriseAS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBoxSelectEnterpriseAS1ActionPerformed(evt);
            }
        });

        lblSelectEnterpriseAS1.setText("Select Enterprise ");

        tableViewOrganisations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Location", "Email ", "Ratings"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableViewOrganisations);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewOrganisationLayout = new javax.swing.GroupLayout(ViewOrganisation);
        ViewOrganisation.setLayout(ViewOrganisationLayout);
        ViewOrganisationLayout.setHorizontalGroup(
            ViewOrganisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewOrganisationLayout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addGroup(ViewOrganisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewOrganisationLayout.createSequentialGroup()
                        .addComponent(lblVirtualCare1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(323, 323, 323))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewOrganisationLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(311, 311, 311))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewOrganisationLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(ViewOrganisationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(ViewOrganisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbBoxSelectEnterpriseAS1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelectEnterpriseAS1)))
            .addGroup(ViewOrganisationLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(btnEdit)
                .addGap(27, 27, 27)
                .addComponent(btnDelete))
        );
        ViewOrganisationLayout.setVerticalGroup(
            ViewOrganisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewOrganisationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVirtualCare1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(lblSelectEnterpriseAS1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbBoxSelectEnterpriseAS1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(ViewOrganisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addContainerGap(134, Short.MAX_VALUE))
        );

        cardLayout.add(ViewOrganisation, "card3");

        ViewUsers.setBackground(new java.awt.Color(51, 153, 255));

        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "DoB", "City", "Email ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(UsersTable);

        btnDeleteUser.setText("Delete User");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewUsersLayout = new javax.swing.GroupLayout(ViewUsers);
        ViewUsers.setLayout(ViewUsersLayout);
        ViewUsersLayout.setHorizontalGroup(
            ViewUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewUsersLayout.createSequentialGroup()
                .addGroup(ViewUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewUsersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE))
                    .addGroup(ViewUsersLayout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(btnDeleteUser)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ViewUsersLayout.setVerticalGroup(
            ViewUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewUsersLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteUser)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        cardLayout.add(ViewUsers, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sidePanelAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 919, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sidePanelAS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameOfOrganisationASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameOfOrganisationASActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameOfOrganisationASActionPerformed

    private void txtLocationASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocationASActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocationASActionPerformed

    private void txtEmailIdASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailIdASActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailIdASActionPerformed

    private void txtRatingsASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRatingsASActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRatingsASActionPerformed

    private void jButton1ASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ASActionPerformed
        // TODO add your handling code here:
        if(CheckBlankFields()){
            if(!checkduplicateentry()){
                try{
                    AdminDetails a = makeAdmin();
                    AddAdmintoDB(a);
                    JOptionPane.showMessageDialog(this, "Organization Added");
                }
                catch(NumberFormatException E){
                    JOptionPane.showMessageDialog(this, "Ratings should be a number");
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "An Organisation with same email already exists");
            }
        }
        clearorgfields();
    }//GEN-LAST:event_jButton1ASActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        UserSystem LoginPanel = new UserSystem();
        SplitPane.removeAll();
        SplitPane.add(LoginPanel.SplitPane);
        SplitPane.repaint();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnViewOrganisationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewOrganisationsActionPerformed
        // TODO add your handling code here:
        Card.show(cardLayout, "card3");
        cardLayout.setVisible(true);
        PullAdminstoHashMap();
        displayOrgs(cmbBoxSelectEnterpriseAS1.getSelectedItem().toString());
    }//GEN-LAST:event_btnViewOrganisationsActionPerformed

    private void btnAddOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrgActionPerformed
        // TODO add your handling code here:
        Card.show(cardLayout, "card2");
        cardLayout.setVisible(true);
    }//GEN-LAST:event_btnAddOrgActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Card.show(cardLayout, "card4");
        PullUserstoHashMap();
        displayusers();
        cardLayout.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
        int Row = UsersTable.getSelectedRow();
        if(Row<0)
            return;
        UserDetails u = UserMap.get(UsersTable.getValueAt(Row, 3).toString());
        RemoveuserfromDB(u);
        UserMap.remove(u.getEmail());
        displayusers();
        JOptionPane.showMessageDialog(this, "User Deleted.");
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int Row = tableViewOrganisations.getSelectedRow();
        if(Row<0)
            return;
        displayOrgs(cmbBoxSelectEnterpriseAS1.getSelectedItem().toString());
        AdminDetails a = AdminMap.get(tableViewOrganisations.getValueAt(Row, 2).toString());
        RemoveAdminfromDB(a);
        AdminMap.remove(a.getEmail());
        PullAdminstoHashMap();
        displayOrgs(cmbBoxSelectEnterpriseAS1.getSelectedItem().toString());
        JOptionPane.showMessageDialog(this, "Admin Deleted.");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int Row = tableViewOrganisations.getSelectedRow();
        if(Row < 0)
            return;
        if(checkblanktablefields()){
            AdminDetails a = AdminMap.get(tableViewOrganisations.getValueAt(Row, 2).toString().trim());
            a.setOrganization(tableViewOrganisations.getValueAt(Row, 0).toString().trim());
            a.setLocation(tableViewOrganisations.getValueAt(Row, 1).toString().trim());
//            a.setEmail(tableViewOrganisations.getValueAt(Row, 2).toString().trim());
            a.setRatings(Integer.parseInt(tableViewOrganisations.getValueAt(Row, 3).toString().trim()));
            AddAdmintoDB(a);
            JOptionPane.showMessageDialog(this, "Admin Edited.");
            PullAdminstoHashMap();
            displayOrgs(cmbBoxSelectEnterpriseAS1.getSelectedItem().toString());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void cmbBoxSelectEnterpriseAS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBoxSelectEnterpriseAS1ActionPerformed
        // TODO add your handling code here:
        PullAdminstoHashMap();
        displayOrgs(cmbBoxSelectEnterpriseAS1.getSelectedItem().toString());
    }//GEN-LAST:event_cmbBoxSelectEnterpriseAS1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminDashboard;
    private javax.swing.JTable UsersTable;
    private javax.swing.JPanel ViewOrganisation;
    private javax.swing.JPanel ViewUsers;
    private javax.swing.JButton btnAddOrg;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnViewOrganisations;
    private javax.swing.JPanel cardLayout;
    private javax.swing.JComboBox<String> cmbBoxSelectEnterpriseAS;
    private javax.swing.JComboBox<String> cmbBoxSelectEnterpriseAS1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton1AS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblADDORGANISTION;
    private javax.swing.JLabel lblEmailId;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblNameOfOrganisation;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRatings;
    private javax.swing.JLabel lblSelectEnterpriseAS;
    private javax.swing.JLabel lblSelectEnterpriseAS1;
    private javax.swing.JLabel lblVirtualCare;
    private javax.swing.JLabel lblVirtualCare1;
    private javax.swing.JPanel sidePanelAS;
    private javax.swing.JTable tableViewOrganisations;
    private javax.swing.JTextField txtEmailIdAS;
    private javax.swing.JTextField txtLocationAS;
    private javax.swing.JTextField txtNameOfOrganisationAS;
    private javax.swing.JPasswordField txtPasswordAS;
    private javax.swing.JTextField txtRatingsAS;
    // End of variables declaration//GEN-END:variables
}
