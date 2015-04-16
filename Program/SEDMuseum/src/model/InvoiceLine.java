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
public class InvoiceLine {

    private int amount;
    private Product product;

    public InvoiceLine(int amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addInvoiceAmount() {
        amount++;
    }

    public void subtractInvoiceAmount() {
        amount--;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice(boolean currency, boolean discount) {
        double price;
        if (currency) {
            price = discount
                    ? product.getDiscountEUR()
                    : product.getPriceEUR();
        } else {
            price = discount
                    ? product.getDiscountDKK()
                    : product.getPriceDKK();
        }
        price = price * amount;
        return price;
    }

}
