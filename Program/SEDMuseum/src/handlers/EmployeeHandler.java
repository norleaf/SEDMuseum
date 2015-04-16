/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;

/**
 *
 * @author Hans
 */
public class EmployeeHandler {

    private final DatabaseConnect db;

    public EmployeeHandler(DatabaseConnect db) {
        this.db = db;
    }

    public Employee checkEmployeePassword(String username, String password) throws SQLException {
        String sql = "select * from employee WHERE username = '" + username + "' AND passwrd = '" + password + "'";
        ResultSet rs = db.readFromDatabase(sql);
        Employee employee = null;
        if (rs.next()) {
            employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), username, password);
        }
        return employee;
    }

    public Employee getEmployee(int number) throws SQLException {

        String sql = "select * from employee where nr = " + number;
        ResultSet rs = db.readFromDatabase(sql);
        Employee emp = null;
        if (rs.next()) {
            emp = new Employee(number, rs.getString("fname"), rs.getString("lname"), rs.getString("address"), rs.getString("zipcode"), rs.getString("username"), rs.getString("passwrd"));
        }
        return emp;
    }

}
