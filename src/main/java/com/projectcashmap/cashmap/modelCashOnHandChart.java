/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectcashmap.cashmap;

import java.sql.Date;

/**
 *
 * @author dular
 */
public class modelCashOnHandChart {
    
    private Date date;
    private int amount;
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public modelCashOnHandChart(Date date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public modelCashOnHandChart() {
    }
}
