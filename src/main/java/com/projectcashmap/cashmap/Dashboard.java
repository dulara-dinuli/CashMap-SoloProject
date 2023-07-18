/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.projectcashmap.cashmap;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author dulara dinuli
 */
public class Dashboard extends javax.swing.JFrame {

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
        jLabel1 = new javax.swing.JLabel();
        cashOnHand = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        stockMarket = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        bank = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        binance = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        personalLoan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fixedDeposit = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(47, 47, 57));

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
        userName.setText("Dulmin");
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
                .addGap(22, 22, 22)
                .addComponent(logOut, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dashboard.setBackground(new java.awt.Color(47, 47, 57));

        jLabel1.setBackground(new java.awt.Color(47, 47, 57));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dashboard");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cashOnHand.setBackground(new java.awt.Color(47, 47, 57));

        jLabel2.setBackground(new java.awt.Color(47, 47, 57));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cash On Hand");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout cashOnHandLayout = new javax.swing.GroupLayout(cashOnHand);
        cashOnHand.setLayout(cashOnHandLayout);
        cashOnHandLayout.setHorizontalGroup(
            cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashOnHandLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(302, Short.MAX_VALUE))
        );
        cashOnHandLayout.setVerticalGroup(
            cashOnHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cashOnHandLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel2)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        stockMarket.setBackground(new java.awt.Color(47, 47, 57));

        jLabel3.setBackground(new java.awt.Color(47, 47, 57));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Stock Market");
        jLabel3.setOpaque(true);

        javax.swing.GroupLayout stockMarketLayout = new javax.swing.GroupLayout(stockMarket);
        stockMarket.setLayout(stockMarketLayout);
        stockMarketLayout.setHorizontalGroup(
            stockMarketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockMarketLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(301, Short.MAX_VALUE))
        );
        stockMarketLayout.setVerticalGroup(
            stockMarketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockMarketLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel3)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        bank.setBackground(new java.awt.Color(47, 47, 57));

        jLabel4.setBackground(new java.awt.Color(47, 47, 57));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bank");
        jLabel4.setOpaque(true);

        javax.swing.GroupLayout bankLayout = new javax.swing.GroupLayout(bank);
        bank.setLayout(bankLayout);
        bankLayout.setHorizontalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        bankLayout.setVerticalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel4)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        binance.setBackground(new java.awt.Color(47, 47, 57));

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

        personalLoan.setBackground(new java.awt.Color(47, 47, 57));

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
                .addContainerGap(297, Short.MAX_VALUE))
        );
        personalLoanLayout.setVerticalGroup(
            personalLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalLoanLayout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel6)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        fixedDeposit.setBackground(new java.awt.Color(47, 47, 57));

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
                    .addGap(0, 197, Short.MAX_VALUE)
                    .addComponent(cashOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 198, Short.MAX_VALUE)
                    .addComponent(stockMarket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 201, Short.MAX_VALUE)
                    .addComponent(bank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(binance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 202, Short.MAX_VALUE)
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
    private javax.swing.JPanel bank;
    private javax.swing.JPanel binance;
    private javax.swing.JPanel cashOnHand;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel fixedDeposit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel logOut;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel navBank;
    private javax.swing.JLabel navBinance;
    private javax.swing.JLabel navCashOnHand;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navFixedDeposit;
    private javax.swing.JLabel navPersonalLoan;
    private javax.swing.JLabel navStockMarket;
    private javax.swing.JPanel navbar;
    private javax.swing.JPanel personalLoan;
    private javax.swing.JPanel stockMarket;
    private javax.swing.JLabel userIcon;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
