/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.LoginControl;
import control.ShopControl;
import java.awt.CardLayout;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Hans
 */
public class MainFrame extends javax.swing.JFrame {

    private final CardLayout cardLayout;
    private final Rectangle screenSize;
    private final JPanel jPanelCardHolder;
    private JPanel currentCard;
    private final ShopControl shopControl;
    private final TillReport tillReport;
    private final Frontpage frontpage;
    private final SalesReceipt salesReceipt;
    private final Statistics statistics;
    private final ShopPanel shopPanel;
    private final Stock stock;
    private ScreenFormat screenFormat;
    private final LoginControl lc;

    public MainFrame() {
        setTitle("Museum Sydøstdanmark");
        LoginControl lcLocal = new LoginControl();
        screenFormat = new ScreenFormat();
        String message;

        /*  Popup som kun vises hvis database forbindelse ikke benytter sig af
         standard stillinger eller kan ikke forbinde til den
         Her kan du ændre database indstillinger.*/
        do {
            try {
                lcLocal.getDatabaseConnect().connectToDB();
            } catch (ClassNotFoundException ex) {
                System.out.println("Mangler MySQL driver");
                message = "Mangler MySQL driver får at kunne køre programmet. Kræver at mysql-connector-java-5.1.30-bin.jar ligger i /lib mappen";
                Object[] options = {"OK"};
                int input = JOptionPane.showOptionDialog(null, message, "Ingen MySQL driver til Java", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (input == JOptionPane.CLOSED_OPTION || input == JOptionPane.OK_OPTION) {
                    try {
                        lcLocal.getDatabaseConnect().close();
                        System.out.println("Lukker programmet");
                        System.exit(0);
                    } catch (SQLException ex1) {
                        System.out.println(ex1.getErrorCode() + ": " + ex1.getLocalizedMessage());
                        System.out.println("Forbindelsen er allerede lukket");
                        System.out.println("Lukker programmet");
                        System.exit(0);
                    }
                }
            } catch (SQLException ex) {
                if (ex.getMessage().equals("Database does not exist")) {
                    message = "Databasen er ikke oprettet i serveren";
                } else if (ex.getErrorCode() == 1045) {
                    message = "Adgang nægtet: forkert Username/Password";
                } else if (ex.getMessage().contains("Communications link failure")) {
                    message = "Server svarer ikke eller er ikke startet op. Måske forkert Host/IP eller Port";
                } else {
                    message = ex.getErrorCode() + ": " + ex.getLocalizedMessage();
                }
                System.out.println(ex.getErrorCode() + ": " + ex.getMessage());
                DatabaseDialog databaseDialog = new DatabaseDialog(lcLocal, message, screenFormat);
            }
        } while (!lcLocal.getDatabaseConnect().isConnected());
        lc = lcLocal;

        /*  Logger ind pop som prøvet at logge ind og hvis det ikke lykkes vise
         Den igen indtil brugeren taster rigtig ind
         Eller hvis de trykker luk så lukker programmet*/
        LoginDialog loginDialog = new LoginDialog(lc, screenFormat);
        ShopControl shopControlLocal = null;
        try {
            shopControlLocal = new ShopControl(lc);
        } catch (Exception ex) {
            new ErrorPopup(ex.getMessage());
        }
        shopControl = shopControlLocal;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ewt) {
                try {
                    lc.getDatabaseConnect().close();
                    System.out.println("Lukker programmet");
                } catch (SQLException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
                    System.out.println("Forbindelsen er allerede lukket");
                }
            }
        });
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        this.screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        setBounds(this.screenSize);
        jPanelCardHolder = new JPanel(new CardLayout());
        add(jPanelCardHolder);
        jPanelCardHolder.setVisible(true);
        jPanelCardHolder.setBounds(this.screenSize);
        cardLayout = (CardLayout) (jPanelCardHolder.getLayout());

        shopPanel = new ShopPanel(this);

        if (shopControl != null) {
            for (int i = 0; i < shopControl.getProductGroupList().size(); i++) {
                shopPanel.addCat(new CategoryPanel(shopControl.getProductGroupList().get(i), shopPanel));
            }
        }
        shopPanel.updateCategory();
        tillReport = new TillReport(this);
        salesReceipt = new SalesReceipt(shopControl, this);
        frontpage = new Frontpage(lc, shopControl.getTillControl(), shopPanel.getScreenFormat(), this);
        stock = new Stock(this);
        statistics = new Statistics(this);
        setCard(frontpage, "Forside");
        setCard(shopPanel, "Salg");
        setCard(salesReceipt, "SalgsKvittering");
        setCard(tillReport, "KasseRapport");
        setCard(statistics, "Statistik");
        setCard(stock, "Varebeholdning");
        showCard("Forside");
        revalidate();
        repaint();

    }

    public final void setCard(JPanel panel, String str) {
        jPanelCardHolder.add(panel);
        cardLayout.addLayoutComponent(panel, str);
    }

    public JPanel getCard() {
        return currentCard;
    }

    public final void showCard(String str) {
        cardLayout.show(jPanelCardHolder, str);
        setBounds(screenSize);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        switch (str) {
            case "Salg":
                shopPanel.fillJLabel();
                break;
            case "KasseRapport":
                tillReport.genReport(Calendar.getInstance());
                tillReport.getUserJLabel().setText(lc.getEmployee().getFname() + " " + lc.getEmployee().getLname());
                break;
            case "Statistik":
                statistics.genStat(Calendar.getInstance());
                break;
            case "Varebeholdning":
                stock.updateStock();
                break;
        }
    }

    public TillReport getTillReport() {
        return tillReport;
    }

    public SalesReceipt getSalesReceipt() {
        return salesReceipt;
    }

    public ShopControl getShopControl() {
        return shopControl;
    }

    public ScreenFormat getScreenFormat() {
        return screenFormat;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Frontpage getFrontpage() {
        return frontpage;
    }
}
