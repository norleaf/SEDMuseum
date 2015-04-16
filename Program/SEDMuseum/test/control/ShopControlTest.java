/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Invoice;
import model.InvoiceLine;
import model.Product;
import model.Till;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hans
 */
public class ShopControlTest {

    private Till till;
    private ShopControl sc;

    public ShopControlTest() {
        till = new Till(Calendar.getInstance(), 100, 100, false);
        till.addInvoiceToList(new Invoice(Calendar.getInstance(), null, false, false));
        till.addInvoiceToList(new Invoice(Calendar.getInstance(), null, false, true));
        Product product1 = new Product(1, "testvare", 20.00, null, 25.00, 3.00, 22.00, 2.50, 100, null);
        Product product2 = new Product(2, "testvare", 20.00, null, 100.00, 14.00, 90.00, 13.00, 100, null);
        InvoiceLine invl1 = new InvoiceLine(1, product1);
        InvoiceLine invl2 = new InvoiceLine(3, product2);
        till.getInvoiceList().get(0).addInvoiceLine(invl1);
        till.getInvoiceList().get(0).addInvoiceLine(invl2);
        till.getInvoiceList().get(1).addInvoiceLine(invl1);
        till.getInvoiceList().get(1).addInvoiceLine(invl2);
        sc = null;
        try {
            sc = new ShopControl(null);
        } catch (Exception ex) {
            Logger.getLogger(ShopControlTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of ... getcurrentTillBalanceDKK which is an awsome amazing method
     */
    @Test
    public void testGetCurrentTillBalanceDKK() {
        System.out.println("getCurrentTillBalanceDKK");

        double expResult = 425.00;
        double result = sc.getCurrentTillBalanceDKK(till);
        System.out.println(till);
        InvoiceLine invl1 = till.getInvoiceList().get(0).getLines().get(0);
        InvoiceLine invl2 = till.getInvoiceList().get(0).getLines().get(1);
        System.out.println(invl1.getAmount() + " stk af " + invl1.getProduct().getPriceDKK() + " kr");
        System.out.println(invl2.getAmount() + " stk af " + invl2.getProduct().getPriceDKK() + " kr");
        System.out.println("forventet: " + expResult + " og faktisk: " + result);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getCurrentTillBalanceEUR method, of class ShopControl.
     */
    @Test
    public void testGetCurrentTillBalanceEUR() {
        System.out.println("getCurrentTillBalanceEUR");
        System.out.println(till);
        double expResult = 145.00;
        double result = sc.getCurrentTillBalanceEUR(till);
        InvoiceLine invl1 = till.getInvoiceList().get(0).getLines().get(0);
        InvoiceLine invl2 = till.getInvoiceList().get(0).getLines().get(1);
        System.out.println(invl1.getAmount() + " stk af " + invl1.getProduct().getPriceEUR() + " euro");
        System.out.println(invl2.getAmount() + " stk af " + invl2.getProduct().getPriceEUR() + " euro");
        System.out.println("forventet: " + expResult + " og faktisk: " + result);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.

    }

}
