/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ShopControl;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.ColorUIResource;
import model.Product;
import model.ProductGroup;

/**
 *
 * @author Hans
 */
public class CategoryPanel extends JButton {

    private final ArrayList<ProductPanel> productPanelList;

    public CategoryPanel(final ProductGroup pg, final ShopPanel sp) {
        final ScreenFormat sf = sp.getScreenFormat();
        final ShopControl sc = sp.getShopControl();
        productPanelList = new ArrayList<>();
        try {
            for (Product product : sc.getProductsFromGroup(pg)) {
                productPanelList.add(new ProductPanel(product, sp));
            }
        } catch (Exception ex) {
            new ErrorPopup(ex.getMessage());
        }
        setText(pg.getCategory());
        setFont(sf.getFont());
        setHorizontalAlignment(SwingConstants.LEFT);
        setPreferredSize(new Dimension(sf.getButtonWidth() - 25, sf.getButtonWidth() / 6));
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    sp.clearProducts();
                    sc.getProductsFromGroup(pg);
                    for (ProductPanel productPanel : productPanelList) {
                        sp.addProduct(productPanel);
                    }
                } catch (Exception ex) {
                    new ErrorPopup(ex.getMessage());
                }
                sp.changeCategoryColor();
                setContentAreaFilled(false);
                setOpaque(true);
                setBackground(new ColorUIResource(255, 255, 128));
            }
        });
    }

    public ArrayList<ProductPanel> getProductPanelList() {
        return productPanelList;
    }

}
