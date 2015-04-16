/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.ProductGroupHandler;
import handlers.TillHandler;
import util.UtilCal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Invoice;
import model.InvoiceLine;
import model.ProductGroup;
import model.Till;
import view.TillReport;

/**
 *
 * @author MaleneLykke
 */
public class TillControl {

    private Till till;
    private final TillHandler th;
    private final ProductGroupHandler pgh;

    public TillControl(ShopControl sc, ProductGroupHandler pgh) {
        this.th = new TillHandler(sc.getDatabaseConnect(), sc.getInvoiceHandler(), this);
        this.pgh = pgh;
    }

    public void addInvoice(Invoice inv) {
        till.getInvoiceList().add(inv);
    }

    public ArrayList<Invoice> getInvoices() {
        return till.getInvoiceList();
    }

    public void saveToDb() throws Exception {
        try {
            th.saveTillToDB();
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            Exception exc = new Exception("Du kan have mistet forbindelsen til databasen.<br/><br/>Startbeholdningen kunne ikke gemmes.");
            throw exc;
        }
    }

    public void closeTill() throws Exception {
        try {
            till.setClosed(true);
            th.updateTillStatus();
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            Exception exc = new Exception("Du kan have mistet forbindelsen til databasen.<br/><br/>Kassen kunne ikke opdateres.");
            throw exc;
        }
    }

    public final boolean openTill() throws Exception {
        Boolean bool;
        if (till == null) {
            try {
                till = th.getTillFromDB();
            } catch (SQLException ex) {
                System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
                Exception exc = new Exception("Du kan have mistet forbindelsen til databasen.<br/><br/>Kassen kunne ikke hentes.");
                throw exc;
            }
        }
        if (till == null) {
            bool = false;
        } else {
            bool = true;
            System.out.println("Till indlæst - DKK: " + till.getStartBalanceKr() + " EUR: " + till.getStartBalanceEur());
        }
        return bool;
    }

    public Till getTill() {
        return till;
    }

    public void setTill(Till till) {
        this.till = till;
    }

    public String generateTillReport(Calendar date, TillReport viewTillReport) throws Exception {
        StringBuilder stb = new StringBuilder();
        Till report = null;
        double profitEUR = 0.0;
        double profitDKK = 0.0;
        int totalTickets = 0;
        int adultTickets = 0;
        int childTickets = 0;
        int freeTickets = 0;
        try {
            report = th.getTillFromDB(date);
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            System.out.println("fejl i generateTillRapportMethod under hentning af till " + UtilCal.getDateString(date));
            Exception exc = new Exception("Du kan have mistet forbindelsen til databasen. <br/><br/>Kasseopgørelsen kunne ikke hentes.");
            throw exc;
        } finally {
            if (report == null) {
                stb.append("Ingen rapport");
            } else {
                try {

                    stb.append("Rapport over dagens omsætning.\n\n");
                    stb.append("fordeling af salg på varegrupper.\n");
                    for (ProductGroup productGroup : pgh.getAllProductGroups("category")) {
                        double totalEUR = 0.0;
                        double totalDKK = 0.0;
                        for (Invoice invoice : report.getInvoiceList()) {
                            int amountOfTickets = 0;
                            for (InvoiceLine invoiceLine : invoice.getLines()) {
                                if (invoiceLine.getProduct().getPg().getNr() == productGroup.getNr()) {
                                    if (invoice.isCurrency()) {
                                        totalEUR += invoiceLine.getPrice(invoice.isCurrency(), invoice.isDiscount());
                                    } else {
                                        totalDKK += invoiceLine.getPrice(invoice.isCurrency(), invoice.isDiscount());
                                    }
                                }
                                if (productGroup.getNr() == 1) {
                                    amountOfTickets += invoiceLine.getAmount();
                                    if (invoiceLine.getProduct().getNr() == 1) {
                                        if (invoice.isDiscount()) {
                                            freeTickets += invoiceLine.getAmount();
                                        } else {
                                            adultTickets += invoiceLine.getAmount();
                                        }
                                    } else if (invoiceLine.getProduct().getNr() == 2) {
                                        childTickets += invoiceLine.getAmount();
                                    }
                                }

                            }
                            totalTickets += amountOfTickets;
                        }
                        profitDKK += totalDKK;
                        profitEUR += totalEUR;
                        stb.append(String.format("\t%-20s %.2f DKK / %.2f Euro\n", productGroup.getCategory() + ":", totalDKK, totalEUR));
                    }
                    viewTillReport.fillTable(profitDKK, profitEUR, childTickets, adultTickets, freeTickets, totalTickets, report.getStartBalanceKr(), report.getStartBalanceEur());
                } catch (SQLException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
                    System.out.println("Fejl at hente produkt grupper");
                    Exception exc = new Exception("Du kan have mistet forbindelsen til databasen.<br/><br/>Produkterne kunne ikke hentes.");
                    throw exc;
                }
            }

        }
        return stb.toString();
    }

    public TillHandler getTillHandler() {
        return th;
    }
}
