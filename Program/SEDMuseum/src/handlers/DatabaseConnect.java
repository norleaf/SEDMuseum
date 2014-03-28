/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hans
 */
public class DatabaseConnect {

    private Statement stmt;
    private Connection conn;
    private ResultSet rs;
    private String sql;
    private String url;
    private String user;
    private String password;

    public DatabaseConnect() {
        try {
            Properties p = new Properties(); //bruger Properties klassen til at læse .ini filen
            p.load(new FileInputStream("./src/database settings/settings.ini")); //henter filen ind

            url = "jdbc:mysql://" + p.getProperty("host","localhost") + ":" + p.getProperty("port","3306") + "/" + p.getProperty("db","sedmuseum"); //sammensætter url ud fra properties. har angivet defaults
            System.out.println(url);
            user = p.getProperty("user","root");
            password = p.getProperty("pass","root");
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("error in sql");
        } catch (ClassNotFoundException ex) {
            System.out.println("jdbc driver not found");
        } catch (IOException ex) {
            System.out.println("settings.ini not found");
        }
    }
}
