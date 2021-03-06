/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.TillControl;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author MaleneLykke
 */
public class TillReport extends javax.swing.JPanel implements Printable {

    private final ScreenFormat sf;
    private final MainFrame mf;
    private final Dimension screenSize;
    private double profitDKK;
    private double profitEUR;
    private double startBalanceDKK;
    private double startBalanceEUR;
    private Calendar selectedCalendar;

    public TillReport(MainFrame mf) {
        initComponents();
        this.mf = mf;
        sf = mf.getScreenFormat();
        int smallTopGap = sf.getTopGapSmall();
        screenSize = sf.getScreenSize();
        setBounds(new Rectangle(screenSize));
        jCalendarCombo1.setFont(sf.getFont());
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("da", "DK"));
        jCalendarCombo1.setDateFormat(df);
        jCalendarCombo1.setSize((int) (0.12 * screenSize.width), (int) (0.06 * screenSize.height));
        jCalendarCombo1.setLocation(sf.getDefaultgap() * 2 + jButtonHome.getWidth(), smallTopGap);
        jTextAreaTillRep.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        jLabelUdfyldtAf.setFont(sf.getFont());
        jLabelUdfyldtAf.setLocation(jCalendarCombo1.getX() + jCalendarCombo1.getWidth() + sf.getDefaultgap(), smallTopGap);
        jLabelUdfyldtAf.setSize(jLabelUdfyldtAf.getMinimumSize());
        jLabelUser.setFont(sf.getFont());
        jLabelUser.setLocation(jLabelUdfyldtAf.getX() + jLabelUdfyldtAf.getWidth() + sf.getDefaultgap() / 4, smallTopGap);
        jLabelUser.setSize(1000, jLabelUdfyldtAf.getHeight());
        jButtonTillCount.setFont(sf.getFont());
        jButtonPrintReport.setFont(sf.getFont());
        jButtonTillCount.setSize(jButtonTillCount.getMinimumSize());
        jButtonPrintReport.setSize(jButtonPrintReport.getMinimumSize());
        jButtonTillCount.setLocation((int) (0.5 * screenSize.width), smallTopGap);
        jButtonPrintReport.setLocation((int) (0.5 * screenSize.width + jButtonTillCount.getWidth() + 5), smallTopGap);
        jButtonHome.setLocation(sf.getDefaultgap(), smallTopGap);
        jScrollPane1.setLocation(jCalendarCombo1.getX(), jCalendarCombo1.getY() + jCalendarCombo1.getHeight() + smallTopGap);
        jScrollPane2.setLocation(jButtonTillCount.getX(), jScrollPane1.getY());
        selectedCalendar = jCalendarCombo1.getCalendar();
    }

    public JLabel getUserJLabel() {
        return jLabelUser;
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
        jTextAreaTillRep = new javax.swing.JTextArea();
        jLabelUdfyldtAf = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTillTable = new javax.swing.JTable();
        jCalendarCombo1 = new org.freixas.jcalendar.JCalendarCombo();
        jButtonHome = new javax.swing.JButton();
        jButtonTillCount = new javax.swing.JButton();
        jButtonPrintReport = new javax.swing.JButton();
        jLabelUser = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setPreferredSize(new java.awt.Dimension(1266, 678));
        setLayout(null);

        jTextAreaTillRep.setEditable(false);
        jTextAreaTillRep.setColumns(20);
        jTextAreaTillRep.setRows(5);
        jScrollPane1.setViewportView(jTextAreaTillRep);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 110, 485, 627);

        jLabelUdfyldtAf.setForeground(java.awt.Color.white);
        jLabelUdfyldtAf.setText("Udfyldt af");
        jLabelUdfyldtAf.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabelUdfyldtAf);
        jLabelUdfyldtAf.setBounds(180, 10, 290, 90);

        jTillTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Kasse startbeholdning", "", null},
                {"Kasse optælling", null, null},
                {"Dagens Omsætning", null, null},
                {null, null, null},
                {"Evt. difference +-", null, null},
                {null, null, null},
                {"Billetter i alt", null, null},
                {"Børn", null, null},
                {"Voksne", null, null},
                {"Voksne uden betaling", null, null}
            },
            new String [] {
                "Title", "DKK", "Euro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTillTable);
        if (jTillTable.getColumnModel().getColumnCount() > 0) {
            jTillTable.getColumnModel().getColumn(0).setResizable(false);
            jTillTable.getColumnModel().getColumn(1).setResizable(false);
            jTillTable.getColumnModel().getColumn(2).setResizable(false);
        }

        add(jScrollPane2);
        jScrollPane2.setBounds(510, 110, 438, 627);

        jCalendarCombo1.addDateListener(new org.freixas.jcalendar.DateListener() {
            public void dateChanged(org.freixas.jcalendar.DateEvent evt) {
                jCalendarCombo1DateChanged(evt);
            }
        });
        add(jCalendarCombo1);
        jCalendarCombo1.setBounds(10, 10, 160, 50);

        jButtonHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/home-icon.png"))); // NOI18N
        jButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHomeActionPerformed(evt);
            }
        });
        add(jButtonHome);
        jButtonHome.setBounds(880, 30, 50, 50);

        jButtonTillCount.setText("Kasseoptælling");
        jButtonTillCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTillCountActionPerformed(evt);
            }
        });
        add(jButtonTillCount);
        jButtonTillCount.setBounds(510, 30, 105, 23);

        jButtonPrintReport.setText("Udskriv rapport");
        jButtonPrintReport.setEnabled(false);
        jButtonPrintReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintReportActionPerformed(evt);
            }
        });
        add(jButtonPrintReport);
        jButtonPrintReport.setBounds(670, 30, 107, 23);

        jLabelUser.setForeground(java.awt.Color.white);
        add(jLabelUser);
        jLabelUser.setBounds(503, 14, 104, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHomeActionPerformed
        mf.showCard("Forside");
    }//GEN-LAST:event_jButtonHomeActionPerformed

    private void jCalendarCombo1DateChanged(org.freixas.jcalendar.DateEvent evt) {//GEN-FIRST:event_jCalendarCombo1DateChanged
        genReport((Calendar) evt.getSelectedDate().clone());
    }//GEN-LAST:event_jCalendarCombo1DateChanged

    private void jButtonTillCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTillCountActionPerformed
        TillCount tillCount = new TillCount(mf, true, startBalanceDKK, startBalanceEUR, profitDKK, profitEUR);
    }//GEN-LAST:event_jButtonTillCountActionPerformed

    private void jButtonPrintReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintReportActionPerformed
        if (jTillTable.getValueAt(1, 1) != null && !jTillTable.getValueAt(1, 1).toString().isEmpty()) {
            PrinterJob job = PrinterJob.getPrinterJob();
            Printable doc = this;
            job.setPrintable(doc);
            boolean accept = job.printDialog();
            if (accept) {
                try {
                    job.print();

                } catch (PrinterException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    new ErrorPopup("Printeren kunne ikke udskrive.<br/><br/>Tjek om den er tændt/tilsluttet.");
                }
            }
        }
    }//GEN-LAST:event_jButtonPrintReportActionPerformed

    public void fillTable(double profitDKK, double profitEUR, int childTickets, int adultTickets, int freeTickets, int amountOfTickets, double startBalanceDKK, double startBalanceEUR) {
        this.profitDKK = profitDKK;
        this.profitEUR = profitEUR;
        this.startBalanceDKK = startBalanceDKK;
        this.startBalanceEUR = startBalanceEUR;
        jTillTable.setValueAt(String.format("%.2f", startBalanceDKK), 0, 1);
        jTillTable.setValueAt(String.format("%.2f", startBalanceEUR), 0, 2);
        jTillTable.setValueAt(String.format("%.2f", profitDKK), 2, 1);
        jTillTable.setValueAt(String.format("%.2f", profitEUR), 2, 2);
        jTillTable.setValueAt(amountOfTickets, 6, 1);
        jTillTable.setValueAt(childTickets, 7, 1);
        jTillTable.setValueAt(adultTickets, 8, 1);
        jTillTable.setValueAt(freeTickets, 9, 1);
        jTillTable.setValueAt("", 1, 1);
        jTillTable.setValueAt("", 1, 2);
        jTillTable.setValueAt("", 4, 1);
        jTillTable.setValueAt("", 4, 2);
    }

    public void genReport(Calendar cal) {
        selectedCalendar = (Calendar) cal.clone();
        TillControl tc = mf.getShopControl().getTillControl();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        try {
            jTextAreaTillRep.setText(tc.generateTillReport(cal, this));
        } catch (Exception ex) {
            new ErrorPopup(ex.getMessage());
        }

    }

    public JButton getPrintReport() {
        return jButtonPrintReport;
    }

    public JTable getTillTable() {
        return jTillTable;
    }

    public Calendar getSelectedCalendar() {
        return selectedCalendar;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHome;
    private javax.swing.JButton jButtonPrintReport;
    private javax.swing.JButton jButtonTillCount;
    private org.freixas.jcalendar.JCalendarCombo jCalendarCombo1;
    private javax.swing.JLabel jLabelUdfyldtAf;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaTillRep;
    private javax.swing.JTable jTillTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        int printResult;
        if (page > 1) {
            printResult = NO_SUCH_PAGE;
        } else {
            if (page == 0) {
                Graphics2D g2d = (Graphics2D) g;
                double x0 = pf.getImageableX() + 75;
                double y0 = pf.getImageableY() + 50;
                g2d.translate(x0, y0);
                jTextAreaTillRep.printAll(g);
            } else if (page == 1) {
                Graphics2D g2d = (Graphics2D) g;
                double x0 = pf.getImageableX() + 75;
                double y0 = pf.getImageableY() + 50;
                g2d.translate(x0, y0);
                g2d.drawString(String.format("Title%39s%40s", "DKK", "Euro"), (int) x0, (int) y0);
                g2d.translate(x0, y0 + 10);
                jTillTable.printAll(g);
            }
            // Find øverste venstre hjørne i det printbare område
            // Forskyd g2d, så (0,0) svarer til øverste venstre hjørne

            //     drawLines(g2d, page);
            printResult = PAGE_EXISTS;
        }
        return printResult;
    }
}
