package edu.madisoncollege.enterprisejava.persistence;

import edu.madisoncollege.enterprisejava.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Ranjit Sekhon
 * @version 1.0 10/20/15.
 */
public class EmployeeDao {

    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<Employee>();

        Connection connection = Database.getInstance().getConnection();

        String sql = "select * from employees order by emp_id";
        Statement selectStatement = connection.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);

        while (results.next()) {
            int employeeId = results.getInt("emp_id");
            String firstName = results.getString("first_name");
            String lastName = results.getString("last_name");
            String socialSecurityNumber = results.getString("ssn");
            String department = results.getString("dept");
            String room = results.getString("room");
            String phone = results.getString("phone");

            Employee employee = new Employee(employeeId, firstName, lastName, socialSecurityNumber, department, room, phone);
            employees.add(employee);
        }

        results.close();
        selectStatement.close();

        return employees;
    }



    public Employee getEmployee(int employeeId) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        String sql = "select * from employees where emp_id = '" + employeeId + "'";
        Statement selectStatement = connection.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);

        Employee employee = null;

        while (results.next()){
            employee = createEmployeeFromResults(results);
        }
        results.close();
        selectStatement.close();

        return employee;
    }

    /** Insert an employee using a prepared statement
     *
     * @param employee to be inserted
     * @throws SQLException
     */
    public void insertEmployee(Employee employee) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = null;

        String insertString = "INSERT INTO EMPLOYEES (first_name, last_name, ssn, dept)" + "VALUES (?,?,?,?)";

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getSocialSecurityNumber());
        preparedStatement.setString(4, employee.getDepartment());
        preparedStatement.execute();
        connection.commit();
        preparedStatement.close();
    }

    /** Update an employee using a prepared statememt
     *
     * @param employee
     * @throws SQLException
     */
    public void updateEmployee(Employee employee) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = null;

        String updateString = "UPDATE EMPLOYEES SET first_name = ?, last_name = ?,"
                + "ssn = ?, dept = ?, room = ?, phone = ?"
                + "WHERE emp_id = ?";
        System.out.println(updateString);

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(updateString);

        //the ints in the following sets correspond to the ?s in the insertString
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getSocialSecurityNumber());
        preparedStatement.setString(4, employee.getDepartment());
        preparedStatement.setString(5, employee.getRoom());
        preparedStatement.setString(6, employee.getPhone());

        //this is the "WHERE" parameter
        preparedStatement.setInt(7, employee.getEmployeeId());
        preparedStatement.executeUpdate();
        connection.commit();
        preparedStatement.close();

    }

    /** Delete an employee by Id using a prepared statement
     *
     * @param id
     * @throws SQLException
     */
    public void deleteEmployeeById(int id) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = null;

        String deleteString = "DELETE FROM EMPLOYEES WHERE emp_id = ?";

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(deleteString);

        // this is the "WHERE" parameter
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
        connection.commit();
        preparedStatement.close();
    }

    /** Utility method to create an employee from a resultset
     *
     * @param results
     * @return
     * @throws SQLException
     */

    private Employee createEmployeeFromResults(ResultSet results) throws SQLException {
        Employee employee;
        int employeeId = results.getInt("emp_id");
        String firstName = results.getString("first_name");
        String lastName = results.getString("last_name");
        String socialSecurityNumber = results.getString("ssn");
        String department = results.getString("dept");
        String room = results.getString("room");
        String phone = results.getString("phone");

        employee = new Employee(employeeId, firstName,lastName, socialSecurityNumber, department, room, phone);
        return employee;

    }


}