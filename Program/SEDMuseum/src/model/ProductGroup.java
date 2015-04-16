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
public class ProductGroup {

    private int nr;
    private String category;

    public ProductGroup(int nr, String category) {
        this.nr = nr;
        this.category = category;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "nr: " + nr + /*" Category: " + */ " " + category;
    }

}
