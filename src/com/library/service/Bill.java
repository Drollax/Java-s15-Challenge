package com.library.service;

public class Bill {
    private long billNo;
    private double amount;
    private boolean isPaid;

    public Bill(long billNo, double amount) {
        this.billNo = billNo;
        this.amount = amount;
        this.isPaid = false;
    }

    public void pay() { this.isPaid = true; }
    public boolean isPaid() { return isPaid; }
    public long getBillNo() { return billNo; }
    public double getAmount() { return amount; }
}