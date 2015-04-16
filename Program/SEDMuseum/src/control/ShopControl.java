/// *
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package control;

import handlers.DatabaseConnect;
import handlers.InvoiceHandler;
import handlers.ProductGroupHandler;
import handlers.ProductHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import model.Invoice;
import model.InvoiceLine;
import model.Product;
import model.ProductGroup;
import model.Till;

/**
 *
 * @author Hans
 */
public class ShopControl {

    private final LinkedHashMap<ProductGroup, ArrayList<Product>> productList;
    private final LinkedHashMap<Product, InvoiceLine> shoppingBasket;
    private final ArrayList<ActionListener> listeners;
    private ArrayList<ProductGroup> productGroupList;
    private boolean currency;
    private boolean discount;
    private final LoginControl lc;
    private final ProductHandler ph;
    private final ProductGroupHandler pgh;
    private final InvoiceHandler ih;
    private final TillControl tc;
    private Invoice inv;

    public ShopControl(LoginControl lc) throws Exception {
        if (lc != null) {
            this.lc = lc;
            this.pgh = new ProductGroupHandler(lc.getDatabaseConnect());
            this.ih = new InvoiceHandler(lc);
            this.ph = new ProductHandler(lc.getDatabaseConnect());
            productList = new LinkedHashMap<>();
            productGroupList = getCategories();

            shoppingBasket = new LinkedHashMap<>();
            listeners = new ArrayList<>();
            inv = null;
            tc = new TillControl(this, pgh);
        } else {
            productGroupList = null;
            pgh = null;
            ih = null;
            ph = null;
            productList = null;
            shoppingBasket = null;
            listeners = null;
            this.lc = null;
            tc = null;
        }
    }

    public DatabaseConnect getDatabaseConnect() {
        return lc.getDatabaseConnect();
    }

    public InvoiceHandler getInvoiceHandler() {
        return ih;
    }

    public Invoice getInv() {
        return inv;
    }

    public void addListener(ActionListener al) {
        listeners.add(al);
    }

    public void notifyListeners(String msg) {
        for (ActionListener listener : listeners) {
            ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, msg);
            listener.actionPerformed(event);
        }
    }

    public void finishSale() throws Exception {
        ArrayList<InvoiceLine> lines = new ArrayList<>();
        for (InvoiceLine invoiceLine : shoppingBasket.values()) {
            lines.add(invoiceLine);
        }
        inv = new Invoice(Calendar.getInstance(), lines, lc.getEmployee(), discount, currency);
        try {
            ih.saveInvoiceToDb(inv);
            tc.getTill().addInvoiceToList(inv);
        } catch (SQLException ex) {
            Exception exc = new Exception("Fejl med at gemme faktura i database.<br/>Databasen kan ikke finde de nødvendige tabeller. <br/><br/>Kontakt Carl på tlf +45 2077 2521");
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            System.out.println("Error saving invoice to database.");
            throw exc;
        }
    }

    /*getCategories laver et handler objekt som henter en liste af varekategorier
     fra databasen, lægger dem i klassens productGroupList variabel og returnerer 
     denne. Metoden er indskudt mellem handleren og gui'en for at lette mængden af 
     kode i gui'en, således at exceptionhandling sker før gui laget og istedet for 
     at gui'en skulle ha lister og objekter af de forskellige handlers så skal den 
     blot ha et ShopControl objekt. */
    public final ArrayList<ProductGroup> getCategories() throws Exception {

        try {
            productGroupList = pgh.getAllProductGroups();

        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            System.out.println("Error in getting categories from database");
            Exception exc = new Exception("Kunne ikke hente produkt kategorier.<br/>Databasen kan ikke finde de nødvendige tabeller. <br/><br/>Kontakt Carl på tlf +45 2077 2521");
            throw exc;
        }
        return productGroupList;
    }


    /*getProductsFromGroup kaldes med en ProductGroup og den opretter et object 
     af databasehandlerklassen ProductsHandler. Handler-metoden getProductsFromCategory 
     returnerer en ArrayListe af Product som denne metode ligger i sin egen variabel 
     productList som den efterfølgende returnerer. Metoden er lavet med henblik på at
     udfylde listen af produkter under den valgte kategori i Shop Panelet. Metoden 
     er desuden ansvarlig for exceptionhandling*/
    public ArrayList<Product> getProductsFromGroup(ProductGroup pg) throws Exception {

        ArrayList<Product> list = null;
        try {
            list = ph.getProductsFromCategory(pg);
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
            System.out.println("Error in getting products from database");
            Exception exc = new Exception("Der skete en fejl med at hente produkter fra databasen.<br/>Databasen kan ikke finde de nødvendige tabeller. <br/><br/>Kontakt Carl på tlf +45 2077 2521");
            throw exc;
        }
        if (productList.containsKey(pg)) {
            if (list != null && productList.get(pg) != null && list.size() != productList.get(pg).size()) {
                productList.put(pg, list);
            }
        } else if (list != null) {
            productList.put(pg, list);
        }
        return productList.get(pg);
    }

    // addProductToBasket tilføjer et produkt til indkøbskurven. hvis produktet
    //allerede er i kurven bliver antallet øget med en. Ellers tilføjes det med
    //værdien 1. Her skal tilføjes kode som tjekker at der er det ønskede antal
    //varer tilgængeligt i databasen.
    public void addProductToBasket(Product product) {
        if (shoppingBasket.containsKey(product)) {
            if (shoppingBasket.get(product).getAmount() < product.getAmount() && product.getAmount() > 0
                    || product.getPg().getCategory().equals("Billetter")
                    || product.getPg().getCategory().equals("Café")) {
                shoppingBasket.get(product).addInvoiceAmount();
                notifyListeners("product added");
            }
        } else {
            if (product.getAmount() > 0 || product.getPg().getCategory().equals("Billetter")
                    || product.getPg().getCategory().equals("Café")) {
                if (product.getNr() == 3) {
                    shoppingBasket.put(product, new InvoiceLine(10, product));
                } else {
                    shoppingBasket.put(product, new InvoiceLine(1, product));
                }
                notifyListeners("new product added");
            }
        }
    }

    public void addToInvoiceLine(Product product) {
        if (shoppingBasket.get(product).getAmount() < product.getAmount() && product.getAmount() > 1
                || product.getPg().getCategory().equals("Billetter")
                || product.getPg().getCategory().equals("Café")) {
            shoppingBasket.get(product).addInvoiceAmount();
            notifyListeners("product added");
        }
    }

    public void subtractFromInvoiceLine(Product product) {
        shoppingBasket.get(product).subtractInvoiceAmount();
        if (shoppingBasket.get(product).getAmount() < 1) {
            removeProductFromBasket(product);
        } else {
            notifyListeners("product subtracted");
        }

    }

    public void emptyBasket() {
        shoppingBasket.clear();
        notifyListeners("cleared basket");
    }

    public int getInvoiceLineAmount(Product product) {
        int amount = 0;
        if (shoppingBasket.get(product) != null) {
            amount = shoppingBasket.get(product).getAmount();
        }
        return amount;
    }

    /* getBasketProduct returnerer en arrayListe af Product som er nøglen i det 
     HashMap som hedder shoppingBasket. Årsagen er at vi i mange tilfælde gerne vil
     kunne iterere over varerne i kurven. */
    public ArrayList<Product> getBasketProducts() {
        ArrayList<Product> list = new ArrayList<>();
        for (Product product : shoppingBasket.keySet()) {
            list.add(product);
        }
        return list;
    }

    public LinkedHashMap<Product, InvoiceLine> getShoppingBasket() {
        return shoppingBasket;
    }

    public int getBasketSize() {
        return shoppingBasket.size();
    }

    public ArrayList<Product> getProductList(ProductGroup pg) {
        return productList.get(pg);
    }

    public ArrayList<ProductGroup> getProductGroupList() {
        return productGroupList;
    }
    /* removeProductFromBasket kaldes med et product som skal fjernes fra
     indkøbskurven. Efterfølgende kaldes notifyListeners således at gui'en kan
     blive opdaret*/

    public void removeProductFromBasket(Product product) {
        shoppingBasket.remove(product);
        notifyListeners("product removed");
    }

    public String updateCurrencyButton() {
        String c;
        currency = !currency;
        if (!currency) {
            c = "EUR";
        } else {
            c = "DKK";
        }
        notifyListeners("currency changed");
        return c;
    }

    public String updateDiscount() {
        String str;
        discount = !discount;
        if (!discount) {
            str = "Medlemspris";
        } else {
            str = "Ikke medlem";
        }
        notifyListeners("discount changed");
        return str;
    }

    public boolean isCurrency() {
        return currency;
    }

    public boolean isDiscount() {
        return discount;
    }

    public double totalSum() {
        double value = 0;
        for (InvoiceLine invL : shoppingBasket.values()) {
            value += invL.getPrice(currency, discount);
        }
        return value;
    }

    public TillControl getTillControl() {
        return tc;
    }

    public double getCurrentTillBalanceDKK(Till till) {
        double currTillBalanceDKK = 0.0;
        if (till != null) {
            currTillBalanceDKK = till.getStartBalanceKr();
            if (!till.isClosed() && !till.getInvoiceList().isEmpty()) {
                for (Invoice invoice : till.getInvoiceList()) {
                    if (!invoice.isCurrency()) {
                        currTillBalanceDKK += invoice.getTotal();
                    }
                }
            }
        }
        return currTillBalanceDKK;
    }

    public double getCurrentTillBalanceEUR(Till till) {
        double currTillBalanceEUR = 0.0;
        if (till != null) {
            currTillBalanceEUR = till.getStartBalanceEur();
            if (!till.isClosed() && !till.getInvoiceList().isEmpty()) {
                for (Invoice invoice : till.getInvoiceList()) {
                    if (invoice.isCurrency()) {
                        currTillBalanceEUR += invoice.getTotal();
                    }
                }
            }
        }
        return currTillBalanceEUR;
    }
}
