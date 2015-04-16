/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.DatabaseConnect;
import handlers.EmployeeHandler;
import java.sql.SQLException;
import model.Employee;

/**
 *
 * @author Annette
 */
public class LoginControl {

    private final DatabaseConnect db;
    private final EmployeeHandler emph;
    private Employee employee;

    public LoginControl() {
        this.db = new DatabaseConnect();
        emph = new EmployeeHandler(db);
    }

    public boolean login(String username, String password) throws SQLException {
        employee = emph.checkEmployeePassword(username, password);
        return (employee != null);
    }

    public Employee getEmployee() {
        return employee;
    }

    public DatabaseConnect getDatabaseConnect() {
        return db;
    }

    public EmployeeHandler getEmployeeHandler() {
        return emph;
    }

}
