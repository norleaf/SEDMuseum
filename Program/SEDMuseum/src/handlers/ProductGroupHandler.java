/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ProductGroup;

/**
 *
 * @author Hans
 */
public class ProductGroupHandler {

    private final DatabaseConnect db;

    public ProductGroupHandler(DatabaseConnect db) {
        this.db = db;
    }

    public ArrayList<ProductGroup> getAllProductGroups() throws SQLException {

        return getAllProductGroups("category");
    }

    public ArrayList<ProductGroup> getAllProductGroups(String order) throws SQLException {
        ArrayList<ProductGroup> list = new ArrayList<>();
        String sql = "SELECT * FROM p_group ORDER BY " + order + " ASC";
        ResultSet rs = db.readFromDatabase(sql);
        while (rs.next()) {
            int nr = rs.getInt("nr");
            String cat = rs.getString("category");
            ProductGroup pg = new ProductGroup(nr, cat);
            list.add(pg);
        }
        return list;
    }

    public ProductGroup getProductGroup(int number) throws SQLException {
        ProductGroup pg = null;
        String sql = "select * from p_group where nr = " + number;
        ResultSet rs = db.readFromDatabase(sql);
        if (rs.next()) {
            pg = new ProductGroup(number, rs.getString("category"));
        }
        return pg;
    }

}
