package com.projectcashmap.cashmap;

/**
 *
 * @author dulara
 */
public class modelAssetGrowthChart {
    
    private int year;
    private double amount;
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public modelAssetGrowthChart(int year, double amount) {
        this.year = year;
        this.amount = amount;
    }

    public modelAssetGrowthChart() {
    }
}
