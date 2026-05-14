package com.library.service;

import java.time.LocalDate;

public class Bill {
    private long billNo;
    private double amount;
    private boolean isPaid;
    private LocalDate date;

    public Bill(long billNo, double amount) {
        this.billNo = billNo;
        this.amount = amount;
        this.isPaid = false;
        this.date = LocalDate.now();
    }

    public void pay() { this.isPaid = true; }
    public boolean isPaid() { return isPaid; }
    public long getBillNo() { return billNo; }
    public double getAmount() { return amount; }
}