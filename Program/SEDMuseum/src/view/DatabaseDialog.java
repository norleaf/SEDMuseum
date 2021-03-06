/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.LoginControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 *
 * @author Joseph
 */
public class DatabaseDialog extends javax.swing.JDialog {

    /**
     * Creates new form DatabaseDialog
     *
     * @param lc
     * @param message
     * @param sf
     */
    public DatabaseDialog(final LoginControl lc, String message, ScreenFormat sf) {
        super(null, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();

        jFieldHostIP.setText(lc.getDatabaseConnect().getProperties().getProperty("host/ip"));
        jFieldPort.setText(lc.getDatabaseConnect().getProperties().getProperty("port"));
        jFieldDatabase.setText(lc.getDatabaseConnect().getProperties().getProperty("database"));
        jFieldUsername.setText(lc.getDatabaseConnect().getProperties().getProperty("username"));
        jFieldPassword.setText(lc.getDatabaseConnect().getProperties().getProperty("password"));
        if (!message.isEmpty()) {
            jMessage.setText("<html><body style='width:320px'>" + message + "</body></html>");
        } else {
            jMessage.setText("");
        }
        setLocation(sf.getScreenSize().width / 2 - getWidth() / 2, sf.getScreenSize().height / 2 - getHeight() / 2);

        jButtonConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lc.getDatabaseConnect().getProperties().setProperty("host/ip", jFieldHostIP.getText());
                lc.getDatabaseConnect().getProperties().setProperty("port", jFieldPort.getText());
                lc.getDatabaseConnect().getProperties().setProperty("database", jFieldDatabase.getText());
                lc.getDatabaseConnect().getProperties().setProperty("username", jFieldUsername.getText());
                lc.getDatabaseConnect().getProperties().setProperty("password", jFieldPassword.getText());
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ewt) {
                dispose();
                try {
                    lc.getDatabaseConnect().close();
                    System.out.println("Lukker programmet");
                    System.exit(0);
                } catch (SQLException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getLocalizedMessage());
                    System.out.println("Forbindelsen er allerede lukket");
                    System.out.println("Lukker programmet");
                    System.exit(0);
                }
            }
        });
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jMessage = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelPassword = new javax.swing.JLabel();
        jFieldPassword = new javax.swing.JTextField();
        jButtonConnect = new javax.swing.JButton();
        jLabelHostIP = new javax.swing.JLabel();
        jFieldHostIP = new javax.swing.JTextField();
        jLabelUsername = new javax.swing.JLabel();
        jFieldUsername = new javax.swing.JTextField();
        jLabelDatabase = new javax.swing.JLabel();
        jFieldDatabase = new javax.swing.JTextField();
        jLabelPort = new javax.swing.JLabel();
        jFieldPort = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(java.awt.Color.darkGray);

        jPanel2.setBackground(java.awt.Color.darkGray);
        jPanel2.setPreferredSize(new java.awt.Dimension(46, 100));

        jMessage.setBackground(java.awt.Color.darkGray);
        jMessage.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jMessage.setForeground(new java.awt.Color(255, 255, 255));
        jMessage.setText("hello");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jMessage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jMessage)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jPanel1.setBackground(java.awt.Color.darkGray);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 500));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelPassword.setBackground(java.awt.Color.darkGray);
        jLabelPassword.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelPassword.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPassword.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabelPassword, gridBagConstraints);

        jFieldPassword.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jFieldPassword.setForeground(new java.awt.Color(0, 0, 0));
        jFieldPassword.setText("lakr");
        jFieldPassword.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jFieldPassword, gridBagConstraints);

        jButtonConnect.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButtonConnect.setText("Connect");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jButtonConnect, gridBagConstraints);

        jLabelHostIP.setBackground(java.awt.Color.darkGray);
        jLabelHostIP.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelHostIP.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHostIP.setText("Host/IP:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabelHostIP, gridBagConstraints);

        jFieldHostIP.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jFieldHostIP.setForeground(new java.awt.Color(0, 0, 0));
        jFieldHostIP.setText("lakr");
        jFieldHostIP.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jFieldHostIP, gridBagConstraints);

        jLabelUsername.setBackground(java.awt.Color.darkGray);
        jLabelUsername.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelUsername.setForeground(new java.awt.Color(255, 255, 255));
        jLabelUsername.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabelUsername, gridBagConstraints);

        jFieldUsername.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jFieldUsername.setForeground(new java.awt.Color(0, 0, 0));
        jFieldUsername.setText("lakr");
        jFieldUsername.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jFieldUsername, gridBagConstraints);

        jLabelDatabase.setBackground(java.awt.Color.darkGray);
        jLabelDatabase.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelDatabase.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDatabase.setText("Database:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabelDatabase, gridBagConstraints);

        jFieldDatabase.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jFieldDatabase.setForeground(new java.awt.Color(0, 0, 0));
        jFieldDatabase.setText("lakr");
        jFieldDatabase.setEnabled(false);
        jFieldDatabase.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jFieldDatabase, gridBagConstraints);

        jLabelPort.setBackground(java.awt.Color.darkGray);
        jLabelPort.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelPort.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPort.setText("Port:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabelPort, gridBagConstraints);

        jFieldPort.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jFieldPort.setForeground(new java.awt.Color(0, 0, 0));
        jFieldPort.setText("lakr");
        jFieldPort.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jFieldPort, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JTextField jFieldDatabase;
    private javax.swing.JTextField jFieldHostIP;
    private javax.swing.JTextField jFieldPassword;
    private javax.swing.JTextField jFieldPort;
    private javax.swing.JTextField jFieldUsername;
    private javax.swing.JLabel jLabelDatabase;
    private javax.swing.JLabel jLabelHostIP;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPort;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JLabel jMessage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
