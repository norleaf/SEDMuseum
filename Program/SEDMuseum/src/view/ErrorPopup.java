/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author MaleneLykke
 */
public class ErrorPopup {

    public ErrorPopup(String message) {
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(200, 130));
        jp.setLayout(new GridLayout(1, 1));
        JLabel jl = new JLabel("<html>" + message + "</html>");
        jp.add(jl);
        Object[] options = {"Ok"};
        JOptionPane.showOptionDialog(null, jp, "Fejlbesked", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
