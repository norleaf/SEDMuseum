/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ShopControl;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.InvoiceLine;
import model.Product;

/**
 *
 * @author Hans
 */
public class BasketLine extends JPanel {

    private final Product product;
    private final InvoiceLine invoiceLine;
    private final Dimension screenSize;
    private final JButton jButtonAdd, jButtonSubt, jButtonDelete;
    private final JLabel productName, price, amount;

    public BasketLine(ScreenFormat sf, InvoiceLine invoiceLine, final ShopControl sc) {
        this.invoiceLine = invoiceLine;
        product = invoiceLine.getProduct();
        Font font = new Font("Garland", Font.BOLD, sf.getSmallFontSize());
        screenSize = sf.getScreenSize();
        setLayout(null);

        //Her sættes defaultstørrelsen på panelet. Derefter beregnes en skalering baseret på skærmopløsningen og størrelsen sættes med setPreferredSize()
        Dimension defaultDimension = new Dimension(750, 45);
        float horScale = (float) screenSize.width / 1920;
        float vertScale = (float) screenSize.height / 1080;
        Dimension size = new Dimension((int) (defaultDimension.width * horScale), (int) (defaultDimension.height * vertScale));
        setPreferredSize(size);

        //Knapperne til add, subtract og delete får fastsat deres størrelse udfra skærmopløsning, de oprettes, får tildelt koordinater og tilføjes til panelet.
        int buttonWidth = sf.getDefaultButtonWidth();
        int buttonHeight = sf.getDefaultButtonHeight();
        int gap = buttonWidth / 5;
        jButtonAdd = new JButton("+");
        jButtonSubt = new JButton("-");
        jButtonDelete = new JButton("X");
        Dimension buttonDim = new Dimension(buttonWidth, buttonHeight);
        jButtonAdd.setSize(buttonDim);
        jButtonSubt.setSize(buttonDim);
        jButtonDelete.setSize(buttonDim);
        jButtonAdd.setFont(font);
        jButtonSubt.setFont(font);
        jButtonDelete.setFont(font);
        jButtonAdd.setLocation(0, 0);
        jButtonSubt.setLocation(buttonWidth + gap, 0);
        jButtonDelete.setLocation(getPreferredSize().width - buttonWidth - gap, 0);
        add(jButtonAdd);
        add(jButtonSubt);
        add(jButtonDelete);

        //ActionPerformed
        jButtonAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sc.addToInvoiceLine(product);
            }
        });

        jButtonSubt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sc.subtractFromInvoiceLine(product);
            }
        });

        jButtonDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sc.removeProductFromBasket(product);
            }
        });

        //De tre JLabels oprettes og indsættes.
        String currencyStr = sc.isCurrency() ? " €" : " DKK";
        amount = new JLabel(invoiceLine.getAmount() + "");
        productName = new JLabel(product.getName());
        price = new JLabel(String.format("%.2f", this.invoiceLine.getPrice(sc.isCurrency(), sc.isDiscount())) + currencyStr);
        amount.setFont(font);
        amount.setVisible(true);
        amount.setSize(buttonWidth / 2, buttonHeight);
        amount.setLocation(buttonWidth * 2 + gap * 2, 0);
        productName.setSize(productName.getText().length() * gap, amount.getHeight());
        productName.setFont(font);
        productName.setVisible(true);
        productName.setLocation(amount.getX() + amount.getWidth() + gap, 0);
        price.setFont(font);
        price.setVisible(true);
        price.setSize(gap * price.getText().length(), amount.getHeight());
        price.setLocation(jButtonDelete.getX() - (gap * 10), 0);
        add(amount);
        add(productName);
        add(price);
        revalidate();
        repaint();
    }
}
