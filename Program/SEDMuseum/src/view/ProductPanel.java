/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import model.Product;

/**
 *
 * @author Hans
 */
public class ProductPanel extends JButton {

    public ProductPanel(final Product product, final ShopPanel sp) {
        ScreenFormat sf = sp.getScreenFormat();
        setText(product.getName());
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(sf.getFont());
        setPreferredSize(new Dimension(sf.getButtonWidth() - 25, sf.getButtonWidth() / 6));
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                sp.getShopControl().addProductToBasket(product);
            }
        });
    }

}
