/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ShopControl;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.plaf.ColorUIResource;
import model.InvoiceLine;

/**
 *
 * @author Hans
 */
public class ShopPanel extends javax.swing.JPanel implements ActionListener {

    private final ScreenFormat sf;
    private final int screenWidth;
    private final int topGap;
    private final int paneHeight;
    private final int buttonWidth;
    private final int rPaneWidth;
    private final Dimension screenSize;
    private final ShopControl sc;
    private final MainFrame mf;

    public ShopPanel(MainFrame mf) {
        initComponents();
        this.mf = mf;
        sc = mf.getShopControl();
        sf = mf.getScreenFormat();
        screenSize = sf.getScreenSize();
        topGap = sf.getTopGap();
        paneHeight = sf.getPaneHeight();
        screenWidth = sf.getScreenWidth();
        buttonWidth = sf.getButtonWidth();
        rPaneWidth = sf.getRPaneWidth();
        int smallTopGap = sf.getTopGapSmall();
        int defaultXGap = sf.getDefaultgap();
        jButtonHome.setLocation(defaultXGap, smallTopGap);
        setBounds(new Rectangle(screenSize));
        jPanelCatHolder.setLayout((LayoutManager) new WrapLayout());
        jPanelProductHolder.setLayout((LayoutManager) new WrapLayout());
        jPanelBasket.setLayout((LayoutManager) new WrapLayout());
        jScrollPane1.setBounds((int) (0.025 * screenWidth), topGap, buttonWidth, paneHeight);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane2.setBounds((int) (0.30 * screenWidth), topGap, buttonWidth, paneHeight);
        jScrollPane3.setBounds((int) (0.575 * screenWidth), topGap, rPaneWidth, paneHeight);
        jButtonFinshSale.setBounds((int) (0.85 * screenWidth), (int) (topGap + paneHeight + 0.02 * screenSize.height), (int) (0.975 * screenWidth - 0.85 * screenWidth), (int) (0.05 * screenSize.height));
        jButtonFinshSale.setFont(sf.getFont());
        jButToggleCurrency.setBounds((int) (0.575 * screenWidth), (int) (0.10 * screenSize.height), (int) (0.32 * screenWidth - 0.25 * screenWidth), (int) (0.04 * screenSize.height));
        jButToggleCurrency.setFont(sf.getFont());
        jButToggleCurrency.setText("EUR");
        jButtonRabat.setText("Medlemspris");
        jButtonRabat.setFont(sf.getFont());
        jButtonRabat.setSize(jButtonRabat.getMinimumSize().width, (int) (0.04 * screenSize.height));
        jButtonRabat.setLocation(jScrollPane3.getX() + rPaneWidth - jButtonRabat.getWidth(), jButToggleCurrency.getY());
        jLTotal.setFont(sf.getFont());
        jLTotal.setForeground(java.awt.Color.WHITE);
        jLTotal.setLocation(jScrollPane3.getX(), jButtonFinshSale.getY());
        jLTotal.setSize(500, 50);
        jLTotal.setText("Total: " + String.format("%.2f", 0.0));
        jLabelStartBalance.setFont(sf.getFont());
        jLabelStartBalance.setSize(1000, 35);
        jLabelStartBalance.setLocation(jScrollPane2.getX(), smallTopGap);
        activateListener();
    }

    public final void activateListener() {
        sc.addListener(this);
    }

    public void fillJLabel() {
        jLabelStartBalance.setText("Kassebeholdning: "
                + String.format("%.2f", sc.getCurrentTillBalanceDKK(sc.getTillControl().getTill())) + " DKK, "
                + String.format("%.2f", sc.getCurrentTillBalanceEUR(sc.getTillControl().getTill())) + " EUR");
    }

    public void addCat(CategoryPanel cp) {
        jPanelCatHolder.add(cp);
        if (!cp.getProductPanelList().isEmpty()) {
            cp.setVisible(true);
        } else {
            cp.setVisible(false);
        }
    }

    public void changeCategoryColor() {
        for (Component component : jPanelCatHolder.getComponents()) {
            JButton jb = (JButton) component;
            jb.setContentAreaFilled(true);
            jb.setOpaque(false);
            jb.setBackground(new ColorUIResource(238, 238, 238));
        }
    }

    public void updateCategory() {
        jPanelCatHolder.repaint();
        jPanelCatHolder.revalidate();
    }

    /*addProduct kaldes med et ProductPanel som tilføjes til det panel som viser
     dem og det sættes synligt. Denne metode kunne være mere effektiv hvis den blev
     kaldt med en liste af ProductPanel og så bare kaldte repaint og revalidate til
     sidst, men der er ikke noget performance problem at se umiddelbart.    */
    public void addProduct(ProductPanel pp) {
        jPanelProductHolder.add(pp);
        pp.setVisible(true);
        jPanelProductHolder.repaint();
        jPanelProductHolder.revalidate();
    }

    /*clearProducts tømmer panelet som viser produkterne for valgte kategori. 
     Metoden er lavet til at bruges når der skiftes kategori*/
    public void clearProducts() {
        jPanelProductHolder.removeAll();
        jPanelProductHolder.repaint();
        jPanelProductHolder.revalidate();
    }
    /*
     * mulige action commands: product added, new product added, product subtracted, 
     * product removed, currency changed, discount changed, cleared basket
     * Kan bruges i tilfædet at der er behov for anden adfærd.
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        updateShoppingBasket();
        if (ae.getActionCommand().matches("new product added")) {
            int height = (int) jPanelBasket.getPreferredSize().getHeight();
            jPanelBasket.scrollRectToVisible(new Rectangle(0, height, 0, 0));
        }
    }

    // updateShoppingBasket fungerer ved at den først tømmer panelet for alt 
    // hvad det indeholder og så løber den ShopControl klassens indkøbskurv (basket)
    // igennem og laver og indsætter BasketLine paneler. Til sidst repainter den 
    // panelet.
    private void updateShoppingBasket() {
        jPanelBasket.removeAll();
        Iterator<InvoiceLine> iter;
        iter = sc.getShoppingBasket().values().iterator();
        while (iter.hasNext()) {
            jPanelBasket.add(new BasketLine(sf, iter.next(), sc));
        }
        String currencyStr = sc.isCurrency() ? " €" : " DKK";
        jLTotal.setText("Total: " + String.format("%.2f", sc.totalSum()) + currencyStr);
        jPanelBasket.revalidate();
        jPanelBasket.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelCatHolder = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelProductHolder = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelBasket = new javax.swing.JPanel();
        jButtonFinshSale = new javax.swing.JButton();
        jButToggleCurrency = new javax.swing.JButton();
        jButtonRabat = new javax.swing.JButton();
        jLTotal = new javax.swing.JLabel();
        jButtonHome = new javax.swing.JButton();
        jLabelStartBalance = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setLayout(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");

        jPanelCatHolder.setBorder(javax.swing.BorderFactory.createTitledBorder("Varekategorier"));
        jPanelCatHolder.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jScrollPane1.setViewportView(jPanelCatHolder);

        add(jScrollPane1);
        jScrollPane1.setBounds(64, 79, 306, 524);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelProductHolder.setBorder(javax.swing.BorderFactory.createTitledBorder("Produkter"));
        jScrollPane2.setViewportView(jPanelProductHolder);

        add(jScrollPane2);
        jScrollPane2.setBounds(420, 80, 320, 520);

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelBasket.setBorder(javax.swing.BorderFactory.createTitledBorder("Indkøbskurv"));
        jScrollPane3.setViewportView(jPanelBasket);

        add(jScrollPane3);
        jScrollPane3.setBounds(780, 80, 540, 520);

        jButtonFinshSale.setText("Afslut Salg");
        jButtonFinshSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinshSaleActionPerformed(evt);
            }
        });
        add(jButtonFinshSale);
        jButtonFinshSale.setBounds(1010, 620, 100, 30);

        jButToggleCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButToggleCurrencyActionPerformed(evt);
            }
        });
        add(jButToggleCurrency);
        jButToggleCurrency.setBounds(780, 20, 73, 23);

        jButtonRabat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRabatActionPerformed(evt);
            }
        });
        add(jButtonRabat);
        jButtonRabat.setBounds(880, 20, 80, 20);

        jLTotal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLTotal.setForeground(new java.awt.Color(255, 255, 255));
        add(jLTotal);
        jLTotal.setBounds(0, 0, 0, 0);

        jButtonHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/home-icon.png"))); // NOI18N
        jButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHomeActionPerformed(evt);
            }
        });
        add(jButtonHome);
        jButtonHome.setBounds(1260, 10, 50, 50);

        jLabelStartBalance.setForeground(java.awt.Color.white);
        add(jLabelStartBalance);
        jLabelStartBalance.setBounds(0, 0, 0, 0);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFinshSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinshSaleActionPerformed
        if (!sc.getShoppingBasket().isEmpty()) {

            try {
                sc.finishSale();
                fillJLabel();
                mf.getSalesReceipt().createReceipt();
                mf.showCard("SalgsKvittering");
            } catch (Exception ex) {
                new ErrorPopup(ex.getMessage());
            }
        } else {
            new ErrorPopup("Indkøbskurven er tom.<br/><br/>Tilføj produkter til kurven for at gennemføre et salg.");
        }
    }//GEN-LAST:event_jButtonFinshSaleActionPerformed

    private void jButToggleCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButToggleCurrencyActionPerformed
        jButToggleCurrency.setText(sc.updateCurrencyButton());
    }//GEN-LAST:event_jButToggleCurrencyActionPerformed

    private void jButtonRabatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRabatActionPerformed
        jButtonRabat.setText(sc.updateDiscount());
    }//GEN-LAST:event_jButtonRabatActionPerformed

    private void jButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHomeActionPerformed
        sc.emptyBasket();
        mf.showCard("Forside");
    }//GEN-LAST:event_jButtonHomeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButToggleCurrency;
    private javax.swing.JButton jButtonFinshSale;
    private javax.swing.JButton jButtonHome;
    private javax.swing.JButton jButtonRabat;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JLabel jLabelStartBalance;
    private javax.swing.JPanel jPanelBasket;
    private javax.swing.JPanel jPanelCatHolder;
    private javax.swing.JPanel jPanelProductHolder;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    public ScreenFormat getScreenFormat() {
        return sf;
    }

    public ShopControl getShopControl() {
        return sc;
    }
}
