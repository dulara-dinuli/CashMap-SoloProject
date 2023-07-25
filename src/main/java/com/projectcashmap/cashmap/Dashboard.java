/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.projectcashmap.cashmap;

//for the navigation bar
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

//for the line chart
import java.awt.Color;
import chart.ModelChart;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.sql.Date;

//for the table
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dulara dinuli
 */

interface TotalDividendsObserver {
    void updateTotalDividends(double totalDividends);
}

public class Dashboard extends javax.swing.JFrame implements CashOnHandUpdateObserver,TotalDividendsObserver{

    private CashOnHandUpdate cashOnHandUpdateForm;
    /**
     * Creates new form Dashboard
     */

    //default border for the nav items
    Border defaultNavItemBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#1d1d2f"));
    
    // border for the nav itmes
    Border navItemBorder = BorderFactory.createMatteBorder(0, 20, 0, 0, Color.decode("#2f2f39"));

    // create an array of JLabels of navbar items
    JLabel[] navbarItem = new JLabel[7];
    int navItemActive = 0;
    
    // create an array of JPanels of content
    JPanel[] panels = new JPanel[7];
    
    public Dashboard() {
        initComponents();
        
        // center the form
        this.setLocationRelativeTo(null);
        
        // set icons
        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/user.png")));
        logOutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/logOut.png")));
    
        // set borders
        Border navBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#282830"));
        navbar.setBorder(navBorder);
        
        //populate the navbarItem array
        navbarItem[0]= navDashboard;
        navbarItem[1]= navCashOnHand;
        navbarItem[2]= navStockMarket;
        navbarItem[3]= navBank;
        navbarItem[4]= navBinance;
        navbarItem[5]= navPersonalLoan;
        navbarItem[6]= navFixedDeposit;
        
        //populate the panels array
        panels[0]= dashboard;
        panels[1]= cashOnHand;
        panels[2]= stockMarket;
        panels[3]= bank;
        panels[4]= binance;
        panels[5]= personalLoan;
        panels[6]= fixedDeposit;
        
        // set first dashboard as already selected navItem
        setNavItemBackground(navDashboard);
        
        addActionToNavbarItems();
        
        showPanel(dashboard);
        
        // create the line chart
        assetsGrowthChart.setTitle("Assets Growth");
        assetsGrowthChart.addLegend("Amount (LKR)", Color.decode("#00b5a0"), Color.decode("#004d44"));
        lineChart();
        
        
        // Add a DocumentListener to the cost,marketvalue fields document
        cost.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { 
                stockMarketComponentShown(null);
                saveStockMarketDataToPreferences();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                stockMarketComponentShown(null);
                saveStockMarketDataToPreferences();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });    
        marketValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { 
                stockMarketComponentShown(null);
                saveStockMarketDataToPreferences();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                stockMarketComponentShown(null);
                saveStockMarketDataToPreferences();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        buyingPower.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {saveStockMarketDataToPreferences();}

            @Override
            public void removeUpdate(DocumentEvent e) {saveStockMarketDataToPreferences();}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });   
    
        loadStockMarketDataFromPreferences();
    }
    
    // Method to save data from text fields
    private void saveStockMarketDataToPreferences() {
        // Get the data from text fields
        String costVariable = cost.getText().trim();
        String marketValueVariable= marketValue.getText().trim();
        String buyingPowerVariable = buyingPower.getText().trim();

        // Concatenate the data into a single string with a delimiter (e.g., comma)
        String dataToSave = costVariable + "," + marketValueVariable + "," + buyingPowerVariable;

        // Save the data using the DataStorage class
        DataStorage.saveData(dataToSave);
    }
    
    @Override
    public void updateTotalDividends(double totalDividends) {
        // Update the totalDividends label in Dashboard with the new value
        totalDividendsLabel.setText(String.valueOf(totalDividends));
//        saveStockMarketDataToPreferences();
    }

    // Method to load data from preferences and set the values to text fields
    private void loadStockMarketDataFromPreferences() {
        // Load the data using the DataStorage class
        String data = DataStorage.loadData();
        String dividendsData = NewDataStorage.newLoadData();

        // Check if data is not null and not empty
        if (data != null && !data.isEmpty()) {
            // Split the data using the delimiter (e.g., comma) to get individual values
            String[] values = data.split(",");

            // Check if the split result has the expected number of values (3 in this case)
            if (values.length == 3) {
                // Set the values to the text fields
                cost.setText(values[0]);
                marketValue.setText(values[1]);
                buyingPower.setText(values[2]);
            }
        }
        if (dividendsData != null && !dividendsData.isEmpty()) {
            totalDividendsLabel.setText(dividendsData);
        }
    }
    
    @Override
    public void updateCashOnHandTable() {
        // Update the cash on hand table in the dashboard
        // You can re-fetch the data from the database or update the existing data model and refresh the table.
        // For simplicity, let's assume you have a method called "refreshTableData()" that updates the table data.
        refreshTableData();
    }

    private void refreshTableData() {
        // Refresh the cash on hand table data here
        // For example, you can query the database to fetch the latest data and update the table model
        // For demonstration, let's assume you have a method called "fetchCashOnHandData()" that fetches data from the database
        formWindowOpened(null);
    }
    
    // Helper method to get the index of a given JLabel in the navbarItem array
    private int getIndex(JLabel label) {
        for (int i = 0; i < navbarItem.length; i++) {
            if (navbarItem[i] == label) {
                return i;
            }
        }
        return -1;
    }
    
    public void setNavItemBackground(JLabel label){
        
        for(JLabel navbarItem : navbarItem){
            //set the border color of nav itmes
            navbarItem.setBorder(defaultNavItemBorder);

            //change the nav item background and foreground color
            navbarItem.setBackground(Color.decode("#1d1d2f"));
            navbarItem.setForeground(Color.decode("#b3b3d8"));
        }
        
        //set the border color of nav itme
        label.setBorder(navItemBorder);

        //change the nav item background and foreground color
        label.setBackground(Color.decode("#ffc400"));
        label.setForeground(Color.decode("#282830"));
    }
    
    // create a function to show the selected panel
    public void showPanel(JPanel panel){
        for(JPanel panels: panels){
            panels.setVisible(false);
        }
        
        // show only the selected panel
        panel.setVisible(true);
    }
    
    public void addActionToNavbarItems(){
        // get labels in the navbar
        Component[] components = navbar.getComponents();
        for(Component component : components){
            if(component instanceof JLabel){
                JLabel label = (JLabel) component;
                label.addMouseListener(new MouseListener(){
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        navItemActive = getIndex(label);
                        
                        //change the nav item border, background and foreground color
                        setNavItemBackground(label);
                        
                        // display the selected panel
                        switch (label.getText().trim()){
                            case "Dashboard":
                                showPanel(dashboard);
                                break;
                            case "Cash On Hand":
                                showPanel(cashOnHand);
                                break;
                            case "Stock Market":
                                showPanel(stockMarket);
                                break;
                            case "Bank":
                                showPanel(bank);
                                break;
                            case "Binance":
                                showPanel(binance);
                                break;
                            case "Personal Loan":
                                showPanel(personalLoan);
                                break;
                            case "Fixed Deposit":
                                showPanel(fixedDeposit);
                                break;                                
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
     
                        if(navItemActive != getIndex(label)){
                            //set the hover border color of nav itmes
                            label.setBorder(defaultNavItemBorder);

                            // Set the background and foreground colors of navDashboard label
                            label.setBackground(Color.decode("#2f2f39"));
                            label.setForeground(Color.decode("#b3b3d8")); 
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                  
                        if(navItemActive != getIndex(label)){
                            //set the default border color of nav itmes
                            label.setBorder(defaultNavItemBorder);

                            // Set the background and foreground colors of navDashboard label
                            label.setBackground(Color.decode("#1d1d2f"));
                            label.setForeground(Color.decode("#b3b3d8")); 
                        }
                    }
                });
            }
        }
    }
    
    private void lineChart() {

        try {
            List<modelAssetGrowthChart> lists = new ArrayList<>();
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "select assetgrowth_year as `Year`, assetgrowth_money as Amount from assetgrowth order by assetgrowth_year DESC";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                int year = r.getInt("year");
                double amount = r.getDouble("amount");
                lists.add(new modelAssetGrowthChart(year, amount));
            }
        
            //  Add Data to chart
            for (int i = lists.size() - 1; i >= 0; i--) {
                modelAssetGrowthChart d = lists.get(i);
                assetsGrowthChart.addData(new ModelChart(String.valueOf(d.getYear()), new double[]{d.getAmount()}));
            }
            //  Start to show data with animation
            assetsGrowthChart.start();
        } catch (Exception e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        navbar = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navCashOnHand = new javax.swing.JLabel();
        navStockMarket = new javax.swing.JLabel();
        navBank = new javax.swing.JLabel();
        navBinance = new javax.swing.JLabel();
        navFixedDeposit = new javax.swing.JLabel();
        navPersonalLoan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        userName = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        logOut = new javax.swing.JLabel();
        logOutIcon = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        Dashboard_Upper = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        netAsset = new javax.swing.JLabel();
        currentAsset = new javax.swing.JLabel();
        currentLiabilities = new javax.swing.JLabel();
        Dashboard_Left = new javax.swing.JPanel();
        assetsGrowthChart = new chart.CurveLineChart();
        Dashboard_Right = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        assetsGrowthTable = new javax.swing.JTable();
        update = new javax.swing.JButton();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        cashOnHand = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cashOnHandTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        totalCashOnHand = new javax.swing.JLabel();
        updateCashOnHand = new javax.swing.JButton();
        stockMarket = new javax.swing.JPanel();
        panelShadow1 = new panel.PanelShadow();
        costLabel = new javax.swing.JLabel();
        marketValueLabel = new javax.swing.JLabel();
        dividendsLabel = new javax.swing.JLabel();
        buyingPowerLabel = new javax.swing.JLabel();
        gainLabel = new javax.swing.JLabel();
        gainPercentageLabel = new javax.swing.JLabel();
        cost = new javax.swing.JTextField();
        marketValue = new javax.swing.JTextField();
        buyingPower = new javax.swing.JTextField();
        gain = new javax.swing.JLabel();
        totalDividendsLabel = new javax.swing.JLabel();
        gainPercentage = new javax.swing.JLabel();
        bank = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bankTable = new javax.swing.JTable();
        bankAdd = new javax.swing.JButton();
        bankUpdate = new javax.swing.JButton();
        bankDelete = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        bankAmount = new javax.swing.JLabel();
        binance = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        personalLoan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fixedDeposit = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CashMap");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        navbar.setBackground(new java.awt.Color(29, 29, 47));

        navDashboard.setBackground(new java.awt.Color(29, 29, 47));
        navDashboard.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navDashboard.setForeground(new java.awt.Color(179, 179, 216));
        navDashboard.setText("    Dashboard");
        navDashboard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navDashboard.setOpaque(true);

        navCashOnHand.setBackground(new java.awt.Color(29, 29, 47));
        navCashOnHand.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navCashOnHand.setForeground(new java.awt.Color(179, 179, 216));
        navCashOnHand.setText("    Cash On Hand");
        navCashOnHand.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navCashOnHand.setOpaque(true);

        navStockMarket.setBackground(new java.awt.Color(29, 29, 47));
        navStockMarket.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navStockMarket.setForeground(new java.awt.Color(179, 179, 216));
        navStockMarket.setText("    Stock Market");
        navStockMarket.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navStockMarket.setOpaque(true);

        navBank.setBackground(new java.awt.Color(29, 29, 47));
        navBank.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navBank.setForeground(new java.awt.Color(179, 179, 216));
        navBank.setText("    Bank");
        navBank.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navBank.setOpaque(true);

        navBinance.setBackground(new java.awt.Color(29, 29, 47));
        navBinance.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navBinance.setForeground(new java.awt.Color(179, 179, 216));
        navBinance.setText("    Binance");
        navBinance.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navBinance.setOpaque(true);

        navFixedDeposit.setBackground(new java.awt.Color(29, 29, 47));
        navFixedDeposit.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navFixedDeposit.setForeground(new java.awt.Color(179, 179, 216));
        navFixedDeposit.setText("    Fixed Deposit");
        navFixedDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navFixedDeposit.setOpaque(true);

        navPersonalLoan.setBackground(new java.awt.Color(29, 29, 47));
        navPersonalLoan.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 18)); // NOI18N
        navPersonalLoan.setForeground(new java.awt.Color(179, 179, 216));
        navPersonalLoan.setText("    Personal Loan");
        navPersonalLoan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navPersonalLoan.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(34, 34, 61));

        userName.setFont(new java.awt.Font("SimSun", 1, 20)); // NOI18N
        userName.setForeground(new java.awt.Color(255, 255, 255));
        userName.setText("Admin");
        userName.setIconTextGap(5);

        userIcon.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(userName))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(34, 34, 61));

        logOut.setFont(new java.awt.Font("SimSun", 1, 18)); // NOI18N
        logOut.setForeground(new java.awt.Color(255, 255, 255));
        logOut.setText("Log Out");
        logOut.setIconTextGap(5);

        logOutIcon.setBackground(new java.awt.Color(255, 255, 255));
        logOutIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(logOut, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(logOutIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logOut, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logOutIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navCashOnHand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navStockMarket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navBank, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navBinance, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navPersonalLoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navFixedDeposit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(navDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navCashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navStockMarket, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navBank, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navBinance, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navPersonalLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(navFixedDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dashboard.setBackground(new java.awt.Color(255, 255, 255));
        dashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dashboard.setPreferredSize(new java.awt.Dimension(1069, 630));

        Dashboard_Upper.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(96, 96, 96));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Current Liabilities");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(96, 96, 96));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Net Assets");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(96, 96, 96));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Current Assets");

        netAsset.setBackground(new java.awt.Color(247, 247, 247));
        netAsset.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        netAsset.setForeground(new java.awt.Color(81, 81, 81));
        netAsset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        netAsset.setText("1,000,000.00 ");
        netAsset.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(191, 191, 191), new java.awt.Color(255, 255, 255)));
        netAsset.setOpaque(true);

        currentAsset.setBackground(new java.awt.Color(247, 247, 247));
        currentAsset.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        currentAsset.setForeground(new java.awt.Color(81, 81, 81));
        currentAsset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentAsset.setText("1,000,000.00 ");
        currentAsset.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(191, 191, 191), new java.awt.Color(255, 255, 255)));
        currentAsset.setOpaque(true);

        currentLiabilities.setBackground(new java.awt.Color(247, 247, 247));
        currentLiabilities.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        currentLiabilities.setForeground(new java.awt.Color(81, 81, 81));
        currentLiabilities.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentLiabilities.setText("1,000,000.00 ");
        currentLiabilities.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(191, 191, 191), new java.awt.Color(255, 255, 255)));
        currentLiabilities.setOpaque(true);

        javax.swing.GroupLayout Dashboard_UpperLayout = new javax.swing.GroupLayout(Dashboard_Upper);
        Dashboard_Upper.setLayout(Dashboard_UpperLayout);
        Dashboard_UpperLayout.setHorizontalGroup(
            Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard_UpperLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(netAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentLiabilities, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        Dashboard_UpperLayout.setVerticalGroup(
            Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard_UpperLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Dashboard_UpperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentLiabilities, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        Dashboard_Left.setBackground(new java.awt.Color(255, 255, 255));
        Dashboard_Left.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 10, 10, 10));

        assetsGrowthChart.setForeground(new java.awt.Color(102, 102, 102));
        assetsGrowthChart.setFillColor(true);

        javax.swing.GroupLayout Dashboard_LeftLayout = new javax.swing.GroupLayout(Dashboard_Left);
        Dashboard_Left.setLayout(Dashboard_LeftLayout);
        Dashboard_LeftLayout.setHorizontalGroup(
            Dashboard_LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(assetsGrowthChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Dashboard_LeftLayout.setVerticalGroup(
            Dashboard_LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(assetsGrowthChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        Dashboard_Right.setBackground(new java.awt.Color(255, 255, 255));
        Dashboard_Right.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 0, 10, 10));

        assetsGrowthTable.setBackground(new java.awt.Color(255, 255, 255));
        assetsGrowthTable.setForeground(new java.awt.Color(51, 51, 51));
        assetsGrowthTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        assetsGrowthTable.setFillsViewportHeight(true);
        assetsGrowthTable.setGridColor(new java.awt.Color(255, 255, 255));
        assetsGrowthTable.setRowHeight(40);
        assetsGrowthTable.setSelectionBackground(new java.awt.Color(118, 152, 154));
        assetsGrowthTable.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(assetsGrowthTable);

        update.setBackground(new java.awt.Color(0, 67, 61));
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        add.setBackground(new java.awt.Color(0, 67, 61));
        add.setForeground(new java.awt.Color(255, 255, 255));
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(0, 67, 61));
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Dashboard_RightLayout = new javax.swing.GroupLayout(Dashboard_Right);
        Dashboard_Right.setLayout(Dashboard_RightLayout);
        Dashboard_RightLayout.setHorizontalGroup(
            Dashboard_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard_RightLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(add)
                .addGap(42, 42, 42)
                .addComponent(update)
                .addGap(40, 40, 40)
                .addComponent(delete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        Dashboard_RightLayout.setVerticalGroup(
            Dashboard_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard_RightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Dashboard_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add)
                    .addComponent(update)
                    .addComponent(delete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Dashboard_Upper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addComponent(Dashboard_Left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dashboard_Right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addComponent(Dashboard_Upper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Dashboard_Left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Dashboard_Right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cashOnHand.setBackground(new java.awt.Color(255, 255, 255));
        cashOnHand.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 20, 20));

        cashOnHandTable.setBackground(new java.awt.Color(255, 255, 255));
        cashOnHandTable.setForeground(new java.awt.Color(51, 51, 51));
        cashOnHandTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        cashOnHandTable.setFillsViewportHeight(true);
        cashOnHandTable.setGridColor(new java.awt.Color(166, 198, 198));
        cashOnHandTable.setRowHeight(65);
        cashOnHandTable.setSelectionBackground(new java.awt.Color(118, 152, 154));
        cashOnHandTable.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane3.setViewportView(cashOnHandTable);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(96, 96, 96));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("LKR");

        totalCashOnHand.setBackground(new java.awt.Color(247, 247, 247));
        totalCashOnHand.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        totalCashOnHand.setForeground(new java.awt.Color(81, 81, 81));
        totalCashOnHand.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalCashOnHand.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(191, 191, 191), new java.awt.Color(255, 255, 255)));
        totalCashOnHand.setOpaque(true);

        updateCashOnHand.setBackground(new java.awt.Color(0, 67, 61));
        updateCashOnHand.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        updateCashOnHand.setForeground(new java.awt.Color(255, 255, 255));
        updateCashOnHand.setText("Update");
        updateCashOnHand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCashOnHandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cashOnHandLayout = new javax.swing.GroupLayout(cashOnHand);
        cashOnHand.setLayout(cashOnHandLayout);
        cashOnHandLayout.setHorizontalGroup(
            cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashOnHandLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cashOnHandLayout.createSequentialGroup()
                        .addComponent(updateCashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1021, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cashOnHandLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalCashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        cashOnHandLayout.setVerticalGroup(
            cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashOnHandLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalCashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(updateCashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        stockMarket.setBackground(new java.awt.Color(255, 255, 255));
        stockMarket.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                stockMarketComponentShown(evt);
            }
        });

        panelShadow1.setBackground(new java.awt.Color(233, 226, 232));
        panelShadow1.setGradientType(panel.PanelShadow.GradientType.VERTICAL);
        panelShadow1.setRadius(30);

        costLabel.setBackground(new java.awt.Color(255, 255, 255));
        costLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        costLabel.setForeground(new java.awt.Color(73, 73, 73));
        costLabel.setText("Cost");

        marketValueLabel.setBackground(new java.awt.Color(255, 255, 255));
        marketValueLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        marketValueLabel.setForeground(new java.awt.Color(73, 73, 73));
        marketValueLabel.setText("Market Value");

        dividendsLabel.setBackground(new java.awt.Color(246, 223, 255));
        dividendsLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        dividendsLabel.setForeground(new java.awt.Color(73, 73, 73));
        dividendsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dividendsLabel.setText("Dividends");
        dividendsLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        dividendsLabel.setOpaque(true);
        dividendsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dividendsLabelMouseClicked(evt);
            }
        });

        buyingPowerLabel.setBackground(new java.awt.Color(255, 255, 255));
        buyingPowerLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        buyingPowerLabel.setForeground(new java.awt.Color(73, 73, 73));
        buyingPowerLabel.setText("Buying Power");

        gainLabel.setBackground(new java.awt.Color(255, 255, 255));
        gainLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        gainLabel.setForeground(new java.awt.Color(73, 73, 73));
        gainLabel.setText("Gain");

        gainPercentageLabel.setBackground(new java.awt.Color(255, 255, 255));
        gainPercentageLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 20)); // NOI18N
        gainPercentageLabel.setForeground(new java.awt.Color(73, 73, 73));
        gainPercentageLabel.setText("Gain %");

        cost.setBackground(new java.awt.Color(211, 211, 211));
        cost.setColumns(1);
        cost.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cost.setForeground(new java.awt.Color(51, 51, 51));
        cost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cost.setToolTipText("");
        cost.setActionCommand("<Not Set>");
        cost.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        cost.setSelectionColor(new java.awt.Color(169, 220, 255));
        cost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                costFocusGained(evt);
            }
        });
        cost.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                costComponentShown(evt);
            }
        });
        cost.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                costInputMethodTextChanged(evt);
            }
        });
        cost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costActionPerformed(evt);
            }
        });

        marketValue.setBackground(new java.awt.Color(211, 211, 211));
        marketValue.setColumns(1);
        marketValue.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        marketValue.setForeground(new java.awt.Color(51, 51, 51));
        marketValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        marketValue.setToolTipText("");
        marketValue.setActionCommand("<Not Set>");
        marketValue.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        marketValue.setSelectionColor(new java.awt.Color(169, 220, 255));

        buyingPower.setBackground(new java.awt.Color(211, 211, 211));
        buyingPower.setColumns(1);
        buyingPower.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        buyingPower.setForeground(new java.awt.Color(51, 51, 51));
        buyingPower.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        buyingPower.setToolTipText("");
        buyingPower.setActionCommand("<Not Set>");
        buyingPower.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        buyingPower.setSelectionColor(new java.awt.Color(169, 220, 255));

        gain.setBackground(new java.awt.Color(255, 255, 255));
        gain.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        gain.setForeground(new java.awt.Color(51, 51, 51));
        gain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gain.setOpaque(true);

        totalDividendsLabel.setBackground(new java.awt.Color(198, 198, 198));
        totalDividendsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        totalDividendsLabel.setForeground(new java.awt.Color(51, 51, 51));
        totalDividendsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalDividendsLabel.setOpaque(true);

        gainPercentage.setBackground(new java.awt.Color(255, 255, 255));
        gainPercentage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        gainPercentage.setForeground(new java.awt.Color(51, 51, 51));
        gainPercentage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gainPercentage.setOpaque(true);

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                        .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cost, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                        .addComponent(marketValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(marketValue, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelShadow1Layout.createSequentialGroup()
                                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buyingPowerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gainPercentageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShadow1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(dividendsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(154, 154, 154)))
                        .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buyingPower, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalDividendsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gainPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(55, 55, 55))
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marketValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(marketValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyingPowerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buyingPower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gain, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gainPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gainPercentageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalDividendsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dividendsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout stockMarketLayout = new javax.swing.GroupLayout(stockMarket);
        stockMarket.setLayout(stockMarketLayout);
        stockMarketLayout.setHorizontalGroup(
            stockMarketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockMarketLayout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        stockMarketLayout.setVerticalGroup(
            stockMarketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockMarketLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        bank.setBackground(new java.awt.Color(255, 255, 255));
        bank.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bankTable.setBackground(new java.awt.Color(255, 255, 255));
        bankTable.setForeground(new java.awt.Color(51, 51, 51));
        bankTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        bankTable.setFillsViewportHeight(true);
        bankTable.setGridColor(new java.awt.Color(255, 255, 255));
        bankTable.setRowHeight(40);
        bankTable.setSelectionBackground(new java.awt.Color(255, 231, 179));
        bankTable.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setViewportView(bankTable);

        bankAdd.setBackground(new java.awt.Color(255, 183, 0));
        bankAdd.setForeground(new java.awt.Color(51, 51, 51));
        bankAdd.setText("Add");
        bankAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankAddActionPerformed(evt);
            }
        });

        bankUpdate.setBackground(new java.awt.Color(255, 183, 0));
        bankUpdate.setForeground(new java.awt.Color(51, 51, 51));
        bankUpdate.setText("Update");
        bankUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankUpdateActionPerformed(evt);
            }
        });

        bankDelete.setBackground(new java.awt.Color(255, 183, 0));
        bankDelete.setForeground(new java.awt.Color(51, 51, 51));
        bankDelete.setText("Delete");
        bankDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankDeleteActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(96, 96, 96));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Amount");

        bankAmount.setBackground(new java.awt.Color(247, 247, 247));
        bankAmount.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        bankAmount.setForeground(new java.awt.Color(81, 81, 81));
        bankAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bankAmount.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(191, 191, 191), new java.awt.Color(255, 255, 255)));
        bankAmount.setOpaque(true);

        javax.swing.GroupLayout bankLayout = new javax.swing.GroupLayout(bank);
        bank.setLayout(bankLayout);
        bankLayout.setHorizontalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bankLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bankAdd)
                        .addGap(18, 18, 18)
                        .addComponent(bankUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bankDelete))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                    .addGroup(bankLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bankAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        bankLayout.setVerticalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankLayout.createSequentialGroup()
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bankLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bankLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bankAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bankAdd)
                    .addComponent(bankDelete)
                    .addComponent(bankUpdate))
                .addContainerGap())
        );

        binance.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setBackground(new java.awt.Color(47, 47, 57));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Binance");
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout binanceLayout = new javax.swing.GroupLayout(binance);
        binance.setLayout(binanceLayout);
        binanceLayout.setHorizontalGroup(
            binanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(binanceLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );
        binanceLayout.setVerticalGroup(
            binanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(binanceLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel5)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        personalLoan.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(47, 47, 57));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Personal Loan");
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout personalLoanLayout = new javax.swing.GroupLayout(personalLoan);
        personalLoan.setLayout(personalLoanLayout);
        personalLoanLayout.setHorizontalGroup(
            personalLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalLoanLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(529, Short.MAX_VALUE))
        );
        personalLoanLayout.setVerticalGroup(
            personalLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalLoanLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel6)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        fixedDeposit.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(47, 47, 57));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Fixed Deposit");
        jLabel7.setOpaque(true);

        javax.swing.GroupLayout fixedDepositLayout = new javax.swing.GroupLayout(fixedDeposit);
        fixedDeposit.setLayout(fixedDepositLayout);
        fixedDepositLayout.setHorizontalGroup(
            fixedDepositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedDepositLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        fixedDepositLayout.setVerticalGroup(
            fixedDepositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fixedDepositLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel7)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 206, Short.MAX_VALUE)
                    .addComponent(cashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 206, Short.MAX_VALUE)
                    .addComponent(stockMarket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(bank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(binance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 193, Short.MAX_VALUE)
                    .addComponent(personalLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 201, Short.MAX_VALUE)
                    .addComponent(fixedDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cashOnHand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(stockMarket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bank, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(binance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(personalLoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fixedDeposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOutIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutIconMouseClicked
        // log out and display the login
        this.dispose();
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_logOutIconMouseClicked

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // Insert data to the database
        
        int selectedRow = assetsGrowthTable.getSelectedRow();

        int oldYear = Integer.parseInt(assetsGrowthTable.getValueAt(selectedRow, 0).toString());
        int newYear = Integer.parseInt(assetsGrowthTable.getValueAt(selectedRow, 0).toString());

        
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            
            // Check if the new year matches any existing year in the table
            String sqlCheck = "SELECT COUNT(*) FROM assetgrowth WHERE assetgrowth_year=?";
            PreparedStatement pCheck = DatabaseConnection.getInstance().getConnection().prepareStatement(sqlCheck);
            pCheck.setInt(1, newYear);
            ResultSet rCheck = pCheck.executeQuery();
            rCheck.next();
            int count = rCheck.getInt(1);
            pCheck.close();

            if (count > 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "The year already exists.", "Warning", JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "insert into assetgrowth(assetgrowth_year, assetgrowth_money, assetgrowth_change)values('"
                    +assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),0)+"','"
                    +assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),1)+"','"
                    +assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),2)+"')";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            boolean r = p.execute();
            if(!r){
                JOptionPane.showMessageDialog(this, "Inserted.");
                formWindowOpened(null);
                assetsGrowthChart.clear();
                lineChart();
            }else{
                JOptionPane.showMessageDialog(this, "Error! Try Again.");
            }
            
            p.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_addActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // add data to the Assets Groth Table
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "SELECT * FROM assetgrowth";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();

            // Create a list to hold the data rows with the additional column
            List<String[]> rowData = new ArrayList<>();
            
            double moneyPrevious=0;

            while (r.next()) {
                int year = r.getInt("assetgrowth_year");
                double change = r.getDouble("assetgrowth_change");
                double percentage = 0;
                if(moneyPrevious!=0){
                    percentage = (change*100)/moneyPrevious;
                }
                double money = r.getDouble("assetgrowth_money");
                moneyPrevious = money;
                
                // Create a DecimalFormat instance with the desired format pattern
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedPercentage = df.format(percentage)+" %";
                
                rowData.add(new String[]{String.valueOf(year), String.valueOf(money), String.valueOf(change), String.valueOf(formattedPercentage)});
            }

            String[] columnName = {"Year", "Money (LKR)", "Change (LKR)", "Percentage"}; 
            DefaultTableModel model = (DefaultTableModel) assetsGrowthTable.getModel();
            model.setDataVector(rowData.toArray(new String[0][]), columnName);
            
            DefaultTableModel newModel = new DefaultTableModel(rowData.toArray(new String[0][]), columnName) {
                // Override isCellEditable to make the "Percentage" column non-editable
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Return false for the "Percentage" column (column 3)
                    return column != 3;
                }
            };
            assetsGrowthTable.setModel(newModel);
            
            // Add an empty row
            String[] emptyRow = {"", "", ""};
            //rowData.add(emptyRow);
            newModel.addRow(emptyRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // add data to the Cash On Hand Table
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "SELECT '5000' AS Note, GREATEST(SUM(cashonhand_5000), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '2000' AS Note, GREATEST(SUM(cashonhand_2000), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '1000' AS Note, GREATEST(SUM(cashonhand_1000), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '500' AS Note, GREATEST(SUM(cashonhand_500), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '100' AS Note, GREATEST(SUM(cashonhand_100), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '50' AS Note, GREATEST(SUM(cashonhand_50), 0) AS Quantity FROM cashonhandupdate\n" +
                        "UNION\n" +
                        "SELECT '20' AS Note, GREATEST(SUM(cashonhand_20), 0) AS Quantity FROM cashonhandupdate;";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();

            // Create a list to hold the data rows with the additional column
            List<String[]> rowData = new ArrayList<>();
            
            double cashOnHandTotal =0;

            while (r.next()) {
                int notes = r.getInt("Note");
                int quantity = r.getInt("Quantity");
                double total = notes*quantity;
                
                cashOnHandTotal+=total;
                
                // Create a DecimalFormat instance with the desired format pattern
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedTotal = df.format(total);
                
                rowData.add(new String[]{String.valueOf(notes), String.valueOf(quantity), String.valueOf(formattedTotal)});
            }

            String[] columnName = {"Notes", "Quantity", "Total (LKR)"}; 
            DefaultTableModel model = (DefaultTableModel) cashOnHandTable.getModel();
            model.setDataVector(rowData.toArray(new String[0][]), columnName);
            
            DefaultTableModel newModel = new DefaultTableModel(rowData.toArray(new String[0][]), columnName) {
                // Override isCellEditable to make the "Percentage" column non-editable
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Return false for all columns (0, 1, 2, 3)
                    return false;
                }
            };
            cashOnHandTable.setModel(newModel);
            
            totalCashOnHand.setText(String.valueOf(cashOnHandTotal));

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // add data to the Bank Table
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "SELECT * FROM bank";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();

            // Create a list to hold the data rows with the additional column
            List<String[]> rowData = new ArrayList<>();
            
            double balancePrevious=0;

            while (r.next()) {
                int id = r.getInt("bank_id");
                Date date = r.getDate("bank_date");
                String creditDescription = r.getString("bank_creditDescription");
                double credit = r.getDouble("bank_credit");
                String debitDescription = r.getString("bank_debitDescription");
                double debit = r.getDouble("bank_debit");
                
                double balance = 0;
                balance = balancePrevious + credit - debit;
                bankAmount.setText(String.valueOf(balance));
                
                balancePrevious = balance;
                
                // Create a DecimalFormat instance with the desired format pattern
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedBalance = df.format(balance);
                
                rowData.add(new String[]{String.valueOf(id), String.valueOf(date), String.valueOf(creditDescription), 
                    String.valueOf(credit), String.valueOf(debitDescription), String.valueOf(debit), 
                    String.valueOf(formattedBalance)});
            }

            String[] columnName = {"ID", "Date", "Description (Credit)", "Credit (LKR)", "Description (Debit)", "Debit", "Balance"}; 
            DefaultTableModel model = (DefaultTableModel) bankTable.getModel();
            model.setDataVector(rowData.toArray(new String[0][]), columnName);
            
            DefaultTableModel newModel = new DefaultTableModel(rowData.toArray(new String[0][]), columnName) {
                // Override isCellEditable to make the "balance" column non-editable
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Return false for the "Percentage" column (column 3)
                    return column != 7;
                }
            };
            bankTable.setModel(newModel);
            
            // Hide the "ID" column by setting its width to 0
            TableColumn idColumn = bankTable.getColumnModel().getColumn(0);
            idColumn.setMinWidth(0);
            idColumn.setMaxWidth(0);
            idColumn.setPreferredWidth(0);
            idColumn.setResizable(false);
            
            // Add an empty row
            String[] emptyRow = {"", "", "", "", "", ""};
            //rowData.add(emptyRow);
            newModel.addRow(emptyRow);
            
            bankTable.getColumnModel().getColumn(1).setCellEditor(new JDateChooserEditor(new JCheckBox()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_formWindowOpened

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // update selected data

        int year= Integer.parseInt(assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),0).toString());
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "update assetgrowth set assetgrowth_year='"+assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),0)+"',"
                    +"assetgrowth_money='"+assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),1)+"',"
                    +"assetgrowth_change='"+assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),2)+"' where assetgrowth_year='"+year+"'"; 
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            
            
            int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to change?","",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(c==0){
                boolean r = p.execute();
                if(!r){
                    JOptionPane.showMessageDialog(this, "Updated");
                    formWindowOpened(null);
                    assetsGrowthChart.clear();
                    lineChart();
                }else{
                    JOptionPane.showMessageDialog(this, "Error! Try Again.");
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // delete the selected data
        int year= Integer.parseInt(assetsGrowthTable.getValueAt(assetsGrowthTable.getSelectedRow(),0).toString());
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "delete FROM assetgrowth where assetgrowth_year="+"'"+year+"'"; 
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            
            int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?","Warning",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(c==0){
                if(!p.execute()){
                    formWindowOpened(null);
                    assetsGrowthChart.clear();
                    lineChart();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void updateCashOnHandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCashOnHandActionPerformed
    // Popup the cash on hand update window
    CashOnHandUpdate cashOnHandUpdate = new CashOnHandUpdate();

    // Set the default close operation to DISPOSE_ON_CLOSE
    cashOnHandUpdate.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    cashOnHandUpdate.setVisible(true);
    cashOnHandUpdate.addObserver(this); // Register the dashboard as an observer
    }//GEN-LAST:event_updateCashOnHandActionPerformed

    private void costActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_costActionPerformed

    private void costInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_costInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_costInputMethodTextChanged

    private void stockMarketComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_stockMarketComponentShown
        // get the gain and gain percentage values
        double inputCost =(cost.getText().trim()).isEmpty() ? 0 : Double.parseDouble(cost.getText().trim());
        double inputMarketValue = (marketValue.getText().trim()).isEmpty() ? 0 : Double.parseDouble(marketValue.getText().trim());
        double inputBuyingPower = (buyingPower.getText().trim()).isEmpty() ? 0 : Double.parseDouble(buyingPower.getText().trim());
        
        double gainValue = inputMarketValue - inputCost;
        double gainPercentageValue;
        if (inputCost == 0) {
            gainPercentageValue = 0; 
        } else {
            gainPercentageValue = (gainValue/ inputCost)*100;
        }
        
        // Create a DecimalFormat instance with the desired format pattern
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedGainValue = df.format(gainValue); 
        String formattedGainPercentageValue = df.format(gainPercentageValue); 
        
        gain.setText(formattedGainValue);
        gainPercentage.setText(formattedGainPercentageValue +" %");
        
        // Set the text color based on the values
        if (gainValue < 0) {
            gain.setForeground(Color.RED);
        } else {
            gain.setForeground(Color.decode("#008227"));
        }

        if (gainPercentageValue < 0) {
            gainPercentage.setForeground(Color.RED);
        } else {
            gainPercentage.setForeground(Color.decode("#008227"));
        }
    }//GEN-LAST:event_stockMarketComponentShown

    private void costFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_costFocusGained

    private void costComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_costComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_costComponentShown

    private void dividendsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dividendsLabelMouseClicked
    // Popup the cash on hand update window
    Dividends dividends = new Dividends();

    // Set the default close operation to DISPOSE_ON_CLOSE
    dividends.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    dividends.setVisible(true);
    dividends.addObserver(this); // Register the dashboard as an observer
    }//GEN-LAST:event_dividendsLabelMouseClicked

    private void bankAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankAddActionPerformed
        // Insert data to the database
        int selectedRow = bankTable.getSelectedRow();

        try {
            DatabaseConnection.getInstance().connectToDatabase();

            // Get values from the table row and handle null values
            String dateString = bankTable.getValueAt(selectedRow, 1).toString();
            String creditDescription = bankTable.getValueAt(selectedRow, 2).toString();
            double credit = getDoubleValue(bankTable.getValueAt(selectedRow, 3));
            String debitDescription = bankTable.getValueAt(selectedRow, 4).toString();
            double debit = getDoubleValue(bankTable.getValueAt(selectedRow, 5));
  

            // Convert the date String to java.sql.Date using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            // Prepare and execute the SQL insert statement
            String sql = "INSERT INTO bank (bank_date, bank_creditDescription, bank_credit, "
            + "bank_debitDescription,bank_debit) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            p.setDate(1, sqlDate);
            p.setString(2, creditDescription);
            p.setDouble(3, credit);
            p.setString(4, debitDescription);
            p.setDouble(5, debit);
            
            boolean r = p.execute();
            if (!r) {
                JOptionPane.showMessageDialog(this, "Inserted.");
                formWindowOpened(null);
            } else {
                JOptionPane.showMessageDialog(this, "Error! Try Again.");
            }

            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to handle null values and convert to int
    private double getDoubleValue(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }//GEN-LAST:event_bankAddActionPerformed

    private void bankUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankUpdateActionPerformed
        // update selected data

        int id= Integer.parseInt(bankTable.getValueAt(bankTable.getSelectedRow(),0).toString());
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "update bank set bank_date='"+bankTable.getValueAt(bankTable.getSelectedRow(),1)+"',"
            +"bank_creditDscription='"+bankTable.getValueAt(bankTable.getSelectedRow(),2)+"',"
            +"bank_credit='"+bankTable.getValueAt(bankTable.getSelectedRow(),3)+"',"
            +"bank_debitDscription='"+bankTable.getValueAt(bankTable.getSelectedRow(),4)+"',"
            +"bank_debit='"+bankTable.getValueAt(bankTable.getSelectedRow(),5)+"' where bank_id='"+id+"'";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);

            int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to change?","",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(c==0){
                boolean r = p.execute();
                if(!r){
                    JOptionPane.showMessageDialog(this, "Updated");
                    formWindowOpened(null);
                }else{
                    JOptionPane.showMessageDialog(this, "Error! Try Again.");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bankUpdateActionPerformed

    private void bankDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankDeleteActionPerformed
        // delete the selected data
        int id= Integer.parseInt(bankTable.getValueAt(bankTable.getSelectedRow(),0).toString());
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            String sql = "delete FROM bank where bank_id="+"'"+id+"'";
            PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);

            int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?","Warning",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(c==0){
                if(!p.execute()){
                    formWindowOpened(null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bankDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard_Left;
    private javax.swing.JPanel Dashboard_Right;
    private javax.swing.JPanel Dashboard_Upper;
    private javax.swing.JButton add;
    private chart.CurveLineChart assetsGrowthChart;
    private javax.swing.JTable assetsGrowthTable;
    private javax.swing.JPanel bank;
    private javax.swing.JButton bankAdd;
    private javax.swing.JLabel bankAmount;
    private javax.swing.JButton bankDelete;
    private javax.swing.JTable bankTable;
    private javax.swing.JButton bankUpdate;
    private javax.swing.JPanel binance;
    private javax.swing.JTextField buyingPower;
    private javax.swing.JLabel buyingPowerLabel;
    private javax.swing.JPanel cashOnHand;
    private javax.swing.JTable cashOnHandTable;
    private javax.swing.JTextField cost;
    private javax.swing.JLabel costLabel;
    private javax.swing.JLabel currentAsset;
    private javax.swing.JLabel currentLiabilities;
    private javax.swing.JPanel dashboard;
    private javax.swing.JButton delete;
    private javax.swing.JLabel dividendsLabel;
    private javax.swing.JPanel fixedDeposit;
    private javax.swing.JLabel gain;
    private javax.swing.JLabel gainLabel;
    private javax.swing.JLabel gainPercentage;
    private javax.swing.JLabel gainPercentageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel logOut;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JTextField marketValue;
    private javax.swing.JLabel marketValueLabel;
    private javax.swing.JLabel navBank;
    private javax.swing.JLabel navBinance;
    private javax.swing.JLabel navCashOnHand;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navFixedDeposit;
    private javax.swing.JLabel navPersonalLoan;
    private javax.swing.JLabel navStockMarket;
    private javax.swing.JPanel navbar;
    private javax.swing.JLabel netAsset;
    private panel.PanelShadow panelShadow1;
    private javax.swing.JPanel personalLoan;
    private javax.swing.JPanel stockMarket;
    private javax.swing.JLabel totalCashOnHand;
    private javax.swing.JLabel totalDividendsLabel;
    private javax.swing.JButton update;
    private javax.swing.JButton updateCashOnHand;
    private javax.swing.JLabel userIcon;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

}

class DataStorage {
    private static final String PREFERENCE_KEY = "stockMarketValues";

    public static void saveData(String data) {
        Preferences preferences = Preferences.userRoot().node("com.projectcashmap.cashmap");
        preferences.put(PREFERENCE_KEY, data);
    }

    public static String loadData() {
        Preferences preferences = Preferences.userRoot().node("com.projectcashmap.cashmap");
        return preferences.get(PREFERENCE_KEY, null);
    }
}

class JDateChooserEditor extends DefaultCellEditor{
  
  private JDateChooser dateChooser;
  public JDateChooserEditor(JCheckBox checkBox)
  {
    super(checkBox);
    dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    
  }

  JDateChooser date;
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
  {
        if (value instanceof String) {
            try {
                dateChooser.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").parse((String) value));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return dateChooser;
  }

  public Object getCellEditorValue() 
  {
            if (dateChooser.getDate() != null) {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
        } else {
            return super.getCellEditorValue();
        }
  }

    public boolean stopCellEditing() {
        if (dateChooser.getDate() != null) {
            return super.stopCellEditing();
        } else {
            return false;
        }
    }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}

class CustomJDateChooser extends JDateChooser {

    public CustomJDateChooser() {
        super();
    }

    public CustomJDateChooser(String dateFormat) {
        super();
        this.setDateFormatString(dateFormat);
    }

    public Dimension getCalendarPreferredSize() {
        return new Dimension(300, 200); // Customize the size as per your requirement
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);

        // Modify the preferred size of the popup calendar
        try {
            Field popupField = JDateChooser.class.getDeclaredField("datePopupMenu");
            popupField.setAccessible(true);
            JPopupMenu popupMenu = (JPopupMenu) popupField.get(this);
            if (popupMenu != null) {
                popupMenu.setPreferredSize(preferredSize);
                popupMenu.setMaximumSize(preferredSize);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
