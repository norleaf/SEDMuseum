/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ShopControl;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Annette
 */
public class SalesReceipt extends javax.swing.JPanel implements Printable {

    private final ShopControl sc;
    private final MainFrame mf;

    /**
     * Creates new form SalgKvittering
     */
    public SalesReceipt(ShopControl shopControl, MainFrame mf) {
        initComponents();
        this.sc = shopControl;
        this.mf = mf;
        ScreenFormat sf = this.mf.getScreenFormat();
        jTextArea1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        int smallTopGap = sf.getTopGapSmall();
        int defaultXGap = sf.getDefaultgap();
        int screenwidth = sf.getScreenWidth();
        jButtonHome.setLocation(defaultXGap, smallTopGap);
        jButtonBack.setLocation(jButtonHome.getWidth() + jButtonHome.getX() + (int) (screenwidth * 0.01), smallTopGap);
        jScrollPane1.setBounds(0, 0, 500, 640);
        jScrollPane1.setLocation(screenwidth / 2 - jScrollPane1.getWidth() / 2, smallTopGap);
        jButtonPrint.setFont(sf.getFont());
        jButtonPrint.setSize(jButtonPrint.getMinimumSize());
        jButtonPrint.setLocation(jScrollPane1.getX() + jScrollPane1.getWidth() + (int) (screenwidth * 0.01), smallTopGap);
        jLabelTotalText.setFont(sf.getFont());
        jLabelTotalText.setSize(jLabelTotalText.getMinimumSize());
        jLabelTotalText.setLocation(jButtonPrint.getX(), jButtonPrint.getY() + jButtonPrint.getHeight() + smallTopGap);
        jLabelTotal.setFont(sf.getFont());
        jLabelRecieved.setFont(sf.getFont());
        jLabelRecieved.setSize(jLabelRecieved.getMinimumSize());
        jLabelRecieved.setLocation(jButtonPrint.getX(), jLabelTotalText.getY() + jLabelTotalText.getHeight() + smallTopGap);
        jFormattedTextFeildRecieved.setFont(sf.getFont());
        jFormattedTextFeildRecieved.setLocation(jLabelRecieved.getX() + jLabelRecieved.getWidth() + defaultXGap, jLabelRecieved.getY() + 5);
        jFormattedTextFeildRecieved.setSize(150, sf.getFont().getSize());
        jLabelPaybackText.setFont(sf.getFont());
        jLabelPaybackText.setSize(jLabelPaybackText.getMinimumSize());
        jLabelPaybackText.setLocation(jButtonPrint.getX(), jLabelRecieved.getY() + jLabelRecieved.getHeight() + smallTopGap);
        jLabelPayback.setFont(sf.getFont());
        jLabelPayback.setSize(jLabelPayback.getMinimumSize());
        jLabelTotal.setLocation(jFormattedTextFeildRecieved.getX(), jLabelTotalText.getY());
        jLabelPayback.setLocation(jFormattedTextFeildRecieved.getX(), jLabelPaybackText.getY());
        NumberFormat format = new java.text.DecimalFormat("#,##0.00");
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.00);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        jLabelPayback.setSize(1000, sf.getFontSize());
        jFormattedTextFeildRecieved.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatter));
        jFormattedTextFeildRecieved.setValue(0.0);
        jFormattedTextFeildRecieved.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        jFormattedTextFeildRecieved.selectAll();
                    }
                });
            }
        });
    }

    public void createReceipt() {
        if (sc.getInv() != null) {
            jTextArea1.setText(sc.getInv().toString());
            String currencyStr = sc.getInv().isCurrency() ? " €" : " DKK";
            jLabelTotal.setText(String.format("%.2f", sc.getInv().getTotal()) + currencyStr);
            jLabelTotal.setSize(jLabelTotal.getMinimumSize());
            jFormattedTextFeildRecieved.setValue(0.0);
            double payback = (double) jFormattedTextFeildRecieved.getValue() - sc.getInv().getTotal();
            jLabelPayback.setText(String.format("%.2f", payback) + currencyStr);
            jFormattedTextFeildRecieved.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String currencyStr = sc.getInv().isCurrency() ? " €" : " DKK";
                    double payback = (double) evt.getNewValue() - sc.getInv().getTotal();
                    jLabelPayback.setText(String.format("%.2f", payback) + currencyStr);
                }
            });
        }
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
        jTextArea1 = new javax.swing.JTextArea();
        jButtonPrint = new javax.swing.JButton();
        jButtonHome = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jLabelPaybackText = new javax.swing.JLabel();
        jLabelPayback = new javax.swing.JLabel();
        jLabelRecieved = new javax.swing.JLabel();
        jLabelTotalText = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jFormattedTextFeildRecieved = new javax.swing.JFormattedTextField();

        setBackground(java.awt.Color.darkGray);
        setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);
        jScrollPane1.setBounds(436, 0, 393, 640);

        jButtonPrint.setText("Udskriv");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });
        add(jButtonPrint);
        jButtonPrint.setBounds(600, 646, 55, 25);

        jButtonHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/home-icon.png"))); // NOI18N
        jButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHomeActionPerformed(evt);
            }
        });
        add(jButtonHome);
        jButtonHome.setBounds(1205, 11, 50, 50);

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/Back-icon.png"))); // NOI18N
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });
        add(jButtonBack);
        jButtonBack.setBounds(1150, 11, 50, 50);

        jLabelPaybackText.setForeground(java.awt.Color.white);
        jLabelPaybackText.setText("Byttepenge");
        add(jLabelPaybackText);
        jLabelPaybackText.setBounds(30, 140, 72, 15);

        jLabelPayback.setForeground(java.awt.Color.white);
        jLabelPayback.setText("0 DKK");
        add(jLabelPayback);
        jLabelPayback.setBounds(90, 140, 35, 15);

        jLabelRecieved.setForeground(java.awt.Color.white);
        jLabelRecieved.setText("Modtaget beløb");
        add(jLabelRecieved);
        jLabelRecieved.setBounds(30, 170, 130, 15);

        jLabelTotalText.setForeground(java.awt.Color.white);
        jLabelTotalText.setText("At betale");
        add(jLabelTotalText);
        jLabelTotalText.setBounds(30, 210, 80, 15);

        jLabelTotal.setForeground(java.awt.Color.white);
        jLabelTotal.setText("0 DKK");
        add(jLabelTotal);
        jLabelTotal.setBounds(120, 210, 35, 15);

        jFormattedTextFeildRecieved.setText("0.0");
        add(jFormattedTextFeildRecieved);
        jFormattedTextFeildRecieved.setBounds(140, 170, 30, 25);
    }// </editor-fold>//GEN-END:initComponents

    public void leave(String leavePage) {
        sc.emptyBasket();
        mf.showCard(leavePage);
        for (PropertyChangeListener propertyChangeListener : jFormattedTextFeildRecieved.getPropertyChangeListeners()) {
            jFormattedTextFeildRecieved.removePropertyChangeListener(propertyChangeListener);
        }
    }

    private void jButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHomeActionPerformed
        leave("Forside");
    }//GEN-LAST:event_jButtonHomeActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        leave("Salg");
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        PrinterJob job = PrinterJob.getPrinterJob();
        Printable doc = this;
        job.setPrintable(doc);
        boolean accept = job.printDialog();
        if (accept) {
            try {
                job.print();
                mf.showCard("Salg");
                sc.emptyBasket();
            } catch (PrinterException ex) {
                System.out.println(ex.getLocalizedMessage());
                new ErrorPopup("Printeren kunne ikke udskrive.<br/><br/>Tjek om den er tændt/tilsluttet.");
            }
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonHome;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JFormattedTextField jFormattedTextFeildRecieved;
    private javax.swing.JLabel jLabelPayback;
    private javax.swing.JLabel jLabelPaybackText;
    private javax.swing.JLabel jLabelRecieved;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalText;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        int printResult;
        if (page > 0) {
            printResult = NO_SUCH_PAGE;
        } else {
            // Find øverste venstre hjørne i det printbare område
            // Forskyd g2d, så (0,0) svarer til øverste venstre hjørne
            Graphics2D g2d = (Graphics2D) g;
            double x0 = pf.getImageableX() + 50;
            double y0 = pf.getImageableY() + 50;
            g2d.translate(x0, y0);
            jTextArea1.printAll(g);
            //     drawLines(g2d, page);
            printResult = PAGE_EXISTS;
        }
        return printResult;
    }
}
