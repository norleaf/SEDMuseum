/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Product;
import model.ProductGroup;

/**
 *
 * @author Hans
 */
public class ProductHandler {

    private final DatabaseConnect db;

    public ProductHandler(DatabaseConnect db) {
        this.db = db;
    }

    public ArrayList<Product> getProductsFromCategory(ProductGroup pg) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE p_group = " + pg.getNr() + " ORDER BY p_name ASC";
        if (pg.getNr() == 1) {
            sql = "SELECT * FROM product WHERE p_group = " + pg.getNr();
        }

        ResultSet rs = db.readFromDatabase(sql);
        while (rs.next()) {
            int nr = rs.getInt("nr");
            String name = rs.getString("p_name");
            double purchasePrice = rs.getDouble("purchase_price");
            String supplier = rs.getString("supplyer");
            double priceDKK = rs.getDouble("price_kr");
            double priceEUR = rs.getDouble("price_euro");
            double discountDKK = rs.getDouble("discount_kr");
            double discountEUR = rs.getDouble("discount_eur");
            int amount = rs.getInt("amount");

            list.add(new Product(nr, name, purchasePrice, supplier, priceDKK, priceEUR, discountDKK, discountEUR, amount, pg));
        }
        return list;
    }
}
