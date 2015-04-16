/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import control.TillControl;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Invoice;
import model.Till;

/**
 *
 * @author MaleneLykke
 */
public class TillHandler {

    private final DatabaseConnect db;
    private final InvoiceHandler ih;
    private final TillControl tc;

    public TillHandler(DatabaseConnect db, InvoiceHandler ih, TillControl tc) {
        this.db = db;
        this.ih = ih;
        this.tc = tc;
    }

    public Till getTillFromDB() throws SQLException {
        return getTillFromDB(Calendar.getInstance());
    }

    public Till getTillFromDB(Calendar date) throws SQLException {
        Date sqldate = new Date(date.getTimeInMillis());
        String sql = "select * from till where t_date = '" + sqldate + "'";
        ResultSet rs = db.readFromDatabase(sql);
        Till till = null;
        if (rs.next()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(rs.getDate(1));
            till = new Till(cal, rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
        }
        if (till != null) {
            till.setInvoiceList(ih.loadInvoiceFromDb(date));
            for (Invoice invoice : till.getInvoiceList()) {
                int number = invoice.getNr();
                invoice.setLines(ih.loadInvoiceLines(number));
            }
        }
        return till;
    }

    public ArrayList<Till> getTillFromDBForAWeek(Calendar cal) throws SQLException {
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ArrayList<Till> tillList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Till till = getTillFromDB(cal);
            if (till != null) {
                tillList.add(till);
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return tillList;
    }

    public void saveTillToDB() throws SQLException {
        Till till = tc.getTill();
        Date date = new Date(till.getCal().getTimeInMillis());
        String sql = "insert into till values('" + date + "'," + till.getStartBalanceKr() + "," + till.getStartBalanceEur() + "," + till.isClosed() + ")";
        db.writeToDatabase(sql);
    }

    public void updateTillStatus() throws SQLException {
        Till till = tc.getTill();
        Date date = new Date(till.getCal().getTimeInMillis());
        String sql = "UPDATE till SET closed = " + till.isClosed() + " WHERE till.t_date = '" + date + "'";
        System.out.println(sql);
        db.writeToDatabase(sql);
    }

}
