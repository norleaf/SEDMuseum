/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Calendar;
import util.UtilCal;

/**
 *
 * @author Annette
 */
public class Till {

    private Calendar cal;
    private double startBalanceKr;
    private double startBalanceEur;
    private ArrayList<Invoice> invoiceList;
    private boolean closed;

    public Till(Calendar cal, double startBalanceKr, double startBalanceEur, boolean closed) {
        this.cal = cal;
        this.startBalanceKr = startBalanceKr;
        this.startBalanceEur = startBalanceEur;
        this.closed = closed;
        invoiceList = new ArrayList<>();
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public double getStartBalanceKr() {
        return startBalanceKr;
    }

    public void setStartBalanceKr(double startBalanceKr) {
        this.startBalanceKr = startBalanceKr;
    }

    public double getStartBalanceEur() {
        return startBalanceEur;
    }

    public void setStartBalanceEur(double startBalanceEur) {
        this.startBalanceEur = startBalanceEur;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return "Calendar: " + UtilCal.getDateString(cal) + ", startBalanceKr: " + startBalanceKr + ", startBalanceEur: " + startBalanceEur;
    }

    public ArrayList<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(ArrayList<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void addInvoiceToList(Invoice inv) {
        invoiceList.add(inv);
    }

}
