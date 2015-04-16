/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.UtilCal;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Annette
 */
public class Invoice {

    private int nr;
    private Calendar cal;
    private ArrayList<InvoiceLine> lines;
    private Employee employee;
    private boolean discount;
    private boolean currency;

    public Invoice(Calendar cal, ArrayList<InvoiceLine> lines, Employee employee, boolean discount, boolean currency) {
        this.cal = cal;
        this.lines = lines;
        this.employee = employee;
        this.discount = discount;
        this.currency = currency;
    }

    public Invoice(Calendar cal, Employee employee, boolean discount, boolean currency) {
        this.cal = cal;
        this.lines = new ArrayList<>();
        this.employee = employee;
        this.discount = discount;
        this.currency = currency;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public String getValuta() {
        String valuta;
        if (currency) {
            valuta = "EUR";
        } else {
            valuta = "DKK";
        }
        return valuta;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    private String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    @Override
    public String toString() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Museum Sydøstdanmark\n\n");
        double sum = 0;
        String currencyStr = currency ? "€ " : "DKK ";
        for (InvoiceLine invoiceLine : lines) {
            String product = invoiceLine.getAmount() + " " + invoiceLine.getProduct().getName();
            double price = invoiceLine.getPrice(currency, discount);
            invoice.append(product)
                    .append(padLeft(String.format("%.2f", price), 60 - product.length()))
                    .append("\n");
            sum += price;
        }
        invoice.append("\n")
                .append("Betale:")
                .append(padLeft(currencyStr + String.format("%.2f", sum), 60 - "Betale:".length()))
                .append("\n\n")
                .append("Betjent af:")
                .append(padLeft(employee.getFname(), 60 - "Bejtent af:".length()))
                .append("\n\n")
                .append("Dato:")
                .append(padLeft(UtilCal.getDateString(cal), 60 - "Dato:".length()));
        return invoice.toString();
    }

    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void addInvoiceLine(InvoiceLine invl) {
        lines.add(invl);
    }

    public double getTotal() {
        double price = 0;
        if (!lines.isEmpty()) {
            for (InvoiceLine invoiceLine : lines) {
                price += invoiceLine.getPrice(currency, discount);
            }
        }
        return price;
    }
}
