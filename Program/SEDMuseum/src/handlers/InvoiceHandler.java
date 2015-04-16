/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import control.LoginControl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Invoice;
import model.InvoiceLine;
import model.Product;
import model.ProductGroup;
import java.sql.Date;
import model.Employee;

/**
 *
 * @author MaleneLykke
 */
public class InvoiceHandler {

    private final DatabaseConnect db;
    private final LoginControl lc;

    public InvoiceHandler(LoginControl lc) {
        this.db = lc.getDatabaseConnect();
        this.lc = lc;
    }

    public void saveInvoiceToDb(Invoice inv) throws SQLException {
        String sql = "insert into invoice(i_date, employee, valuta, discount) values(now()," + lc.getEmployee().getNr() + ", '" + inv.getValuta() + "', " + inv.isDiscount() + ");";
        db.writeToDatabase(sql);
        sql = "select MAX(nr) as nr from invoice";
        ResultSet rs = db.readFromDatabase(sql);
        if (rs.next()) {
            int id = rs.getInt(1);
            inv.setNr(id);
            for (InvoiceLine invL : inv.getLines()) {
                sql = "insert into invoicelines values (" + id + ", " + invL.getProduct().getNr() + ", " + invL.getAmount() + ")";
                db.writeToDatabase(sql);
                int value = invL.getProduct().getAmount() - invL.getAmount();
                sql = "update product set amount = " + value + " where nr = " + invL.getProduct().getNr();
                db.writeToDatabase(sql);
                invL.getProduct().setAmount(value);
            }
        }
    }

    public ArrayList<InvoiceLine> loadInvoiceLines(int invoiceNumber) throws SQLException {
        ArrayList<InvoiceLine> invL = new ArrayList<>();

        String sql = "select * from invoicelines join product on invoicelines.product_nr = product.nr join p_group ON product.p_group = p_group.nr where invoice_nr = " + invoiceNumber;
        ResultSet rs = db.readFromDatabase(sql);
        while (rs.next()) {
            ProductGroup productGroup = new ProductGroup(rs.getInt("p_group.nr"), rs.getString("category"));
            Product product = new Product(rs.getInt("nr"), rs.getString("p_name"), rs.getDouble("purchase_price"), rs.getString("supplyer"), rs.getDouble("price_kr"), rs.getDouble("price_euro"), rs.getDouble("discount_kr"), rs.getDouble("discount_eur"), rs.getInt("amount"), productGroup);
            invL.add(new InvoiceLine(rs.getInt("amount"), product));
        }
        return invL;
    }

    public ArrayList<Invoice> loadInvoiceFromDb(Calendar date) throws SQLException {
        ArrayList<Invoice> invoices = new ArrayList<>();

        Date sqldate = new Date(date.getTimeInMillis());
        String sql = "select * from invoice join employee on invoice.employee = employee.nr where i_date = '" + sqldate + "'";
        ResultSet rs = db.readFromDatabase(sql);
        while (rs.next()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(rs.getDate("i_date"));
            Invoice inv = new Invoice(cal, null, null, rs.getBoolean("discount"), isEuroCurrency(rs.getString("valuta")));
            inv.setNr(rs.getInt("nr"));
            Employee emp = new Employee(rs.getInt("employee"), rs.getString("fname"), rs.getString("lname"), rs.getString("address"), rs.getString("zipcode"), rs.getString("username"), rs.getString("passwrd"));
            inv.setEmployee(emp);
            invoices.add(inv);
        }
        return invoices;
    }

    private boolean isEuroCurrency(String valuta) {
        boolean boo = false;
        if (valuta.equals("EUR")) {
            boo = true;
        }
        return boo;
    }

}
