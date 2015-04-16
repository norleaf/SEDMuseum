/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Hans
 */
public final class DatabaseConnect {

    private Statement stmt;
    private Connection conn;
    private String url;
    private String user;
    private String password;
    private Properties properties;
    private Properties loadedProperties;
    private boolean connected;

    public DatabaseConnect() {
        properties = new Properties();
        loadedProperties = new Properties();
        loadProperties();
        connected = false;
    }

    public boolean checkDBExists(String dbName) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Oprettelse af en forbindelse til databasen...");
        url = "jdbc:mysql://" + properties.getProperty("host/ip", "localhost") + ":" + properties.getProperty("port", "3306"); //sammensætter url ud fra properties. har angivet defaults
        user = properties.getProperty("username", "root");
        password = properties.getProperty("password", "root");
        conn = DriverManager.getConnection(url, user, password); //Open a connection
        ResultSet resultSet = conn.getMetaData().getCatalogs();
        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            if (databaseName.equals(dbName)) {
                conn.setCatalog(dbName);
                result = true;
            }
        }
        return result;
    }

    public void connectToDB() throws SQLException, ClassNotFoundException {
        if (checkDBExists(properties.getProperty("database", "sedmuseum"))) {
            checkToSave();
            System.out.println("Fandt databasen " + properties.getProperty("database", "sedmuseum"));
            System.out.println(conn.getMetaData().getURL() + "/" + conn.getCatalog());
            stmt = conn.createStatement();
            connected = true;
        } else {
            System.out.println("Databasen findes ikke i MySQL serveren");
            throw new SQLException("Database does not exist");
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void checkToSave() {
        if (!properties.isEmpty() && !loadedProperties.isEmpty()) {
            if (!properties.getProperty("host/ip").equals(loadedProperties.getProperty("host/ip"))
                    || !properties.getProperty("port").equals(loadedProperties.getProperty("port"))
                    || !properties.getProperty("database").equals(loadedProperties.getProperty("database"))
                    || !properties.getProperty("username").equals(loadedProperties.getProperty("username"))
                    || !properties.getProperty("password").equals(loadedProperties.getProperty("password"))) {
                saveProperties();
            }
        }
    }

    public void checkProperties() {
        if (properties.isEmpty()
                || properties.getProperty("host/ip").isEmpty()
                || properties.getProperty("port").isEmpty()
                || properties.getProperty("database").isEmpty()
                || properties.getProperty("username").isEmpty()
                || properties.getProperty("password").isEmpty()) {
            properties.setProperty("host/ip", "localhost");
            properties.setProperty("port", "3306");
            properties.setProperty("database", "sedmuseum");
            properties.setProperty("username", "root");
            properties.setProperty("password", "root");
        }
    }

    public void loadProperties() {
        FileInputStream fileInputStream = null;
        try {
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            String realPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
            fileInputStream = new FileInputStream(realPath + "config.properties");
            System.out.println("Prøver at indlæse den ønskede fil: " + realPath + "config.properties");
            properties.load(fileInputStream); //henter filen ind
            loadedProperties = (Properties) properties.clone();
        } catch (IOException ex) {
            System.out.println("Kan ikke finde den ønskede fil, prøver at oprette den.");
            saveProperties();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void saveProperties() {
        checkProperties();
        FileOutputStream output = null;
        try {
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            String realPath = decodedPath.substring(0, decodedPath.lastIndexOf("/") + 1);
            output = new FileOutputStream(realPath + "config.properties");
            System.out.println("Gemmer indstillinger til database i den ønskede fil: " + realPath + "config.properties");
            properties.store(output, "Config for Mysql Database");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void saveProperties(Properties p) {
        properties = p;
        saveProperties();
    }

    public void writeToDatabase(String sql) throws SQLException {
        stmt.execute(sql);
    }

    public ResultSet readFromDatabase(String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }

    public void close() throws SQLException {
        System.out.println("Lukker forbindelse til databasen");
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
