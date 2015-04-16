/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Annette
 */
public class Product {

    private int nr;
    private String name;
    private double purchaseprice;
    private String supplier;
    private double priceDKK;
    private double priceEUR;
    private double discountDKK;
    private double discountEUR;
    private int amount;
    private ProductGroup pg;

    public Product(int nr, String pname, double purchaseprice, String supplyer, double priceDKK, double priceEUR, double discountDKK, double discountEUR, int amount, ProductGroup pg) {
        this.nr = nr;
        this.name = pname;
        this.purchaseprice = purchaseprice;
        this.supplier = supplyer;
        this.priceDKK = priceDKK;
        this.priceEUR = priceEUR;
        this.discountDKK = discountDKK;
        this.discountEUR = discountEUR;
        this.amount = amount;
        this.pg = pg;
    }

    public ProductGroup getPg() {
        return pg;
    }

    public void setPg(ProductGroup pg) {
        this.pg = pg;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public String getSupplyer() {
        return supplier;
    }

    public void setSupplyer(String supplyer) {
        this.supplier = supplyer;
    }

    public double getPriceDKK() {
        return priceDKK;
    }

    public void setPriceDKK(double pricedkk) {
        this.priceDKK = pricedkk;
    }

    public double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(double priceeur) {
        this.priceEUR = priceeur;
    }

    public double getDiscountDKK() {
        return discountDKK;

    }

    public void setDiscountDKK(double discountdkk) {
        this.discountDKK = discountdkk;
    }

    public double getDiscountEUR() {
        return discountEUR;
    }

    public void setDiscountEUR(double discounteur) {
        this.discountEUR = discounteur;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Nr: " + nr + "\nProduktnavn: " + name + "\nPris i KR: " + priceDKK + "\nPris i EUR: " + priceEUR + "\nTilbudspris i KR: " + discountDKK + "\nTilbudspris i EUR: " + discountEUR;
    }

}
