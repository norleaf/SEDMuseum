/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.TillHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Invoice;
import model.InvoiceLine;
import model.Product;
import model.Till;
import view.Statistics;

/**
 *
 * @author MaleneLykke
 */
public class StatControl {

    private TillHandler th;

    public StatControl(TillHandler th) {
        this.th = th;
    }

    public void generateStatistics(Calendar date, Statistics statistics) throws SQLException {
        int store = 0;
        int adults = 0;
        int children = 0;
        int adultsgroup = 0;
        int childrengroup = 0;
        int freebies = 0;
        int visitorstotal;

        ArrayList<Till> tillList = th.getTillFromDBForAWeek(date);
        if (tillList == null) {
            System.out.println("No visitors this week");
        } else {
            for (Till till : tillList) {
                for (Invoice invoice : till.getInvoiceList()) {
                    store++;
                    for (InvoiceLine invoiceLine : invoice.getLines()) {
                        Product product = invoiceLine.getProduct();
                        if (product.getNr() == 1) {
                            adults += invoiceLine.getAmount();
                            if (invoice.isDiscount()) {
                                adults -= invoiceLine.getAmount();
                                freebies += invoiceLine.getAmount();
                            }
                        } else if (product.getNr() == 2) {
                            children += invoiceLine.getAmount();
                        } else if (product.getNr() == 3) {
                            adultsgroup += invoiceLine.getAmount();
                        } else if (product.getNr() == 7) {
                            adultsgroup += invoiceLine.getAmount();
                        } else if (product.getNr() == 8) {
                            adultsgroup += invoiceLine.getAmount();
                        } else if (product.getNr() == 4) {
                            childrengroup += invoiceLine.getAmount();
                        } else if (product.getNr() == 9) {
                            childrengroup += invoiceLine.getAmount();
                        } else if (product.getNr() == 5) {
                            freebies += invoiceLine.getAmount();
                        }
                    }
                }
            }
        }
        visitorstotal = store + adults + children + adultsgroup + childrengroup + freebies;
        statistics.fillSTable(store, adults, children, adultsgroup, childrengroup, freebies, visitorstotal);
    }
}
