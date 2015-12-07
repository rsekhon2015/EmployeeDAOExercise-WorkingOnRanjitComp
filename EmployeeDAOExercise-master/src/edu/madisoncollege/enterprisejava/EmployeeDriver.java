package edu.madisoncollege.enterprisejava;

import edu.madisoncollege.enterprisejava.entity.Employee;
import edu.madisoncollege.enterprisejava.persistence.Database;
import edu.madisoncollege.enterprisejava.persistence.EmployeeDao;

import java.sql.SQLException;

/**
 * @author Ranjit Sekhon
 * @version 1.0 10/20/15.
 */
public class EmployeeDriver {
    /** The main method is used to run the various
     *   Dao methods.
     *
     * @param args
     */
    public static void main(String[] args) {
        EmployeeDao dao = new EmployeeDao();
        try {
            Database.getInstance().connect();
            System.out.println(dao.getAllEmployees());

            System.out.println(dao.getEmployee(110));

            System.out.println();
            System.out.println(dao.getAllEmployees());

            Employee employee = new Employee(118, "John", "Michaels", "9999990345", "IT", "310","555-6784");
            dao.insertEmployee(employee);

            System.out.println();
            System.out.println("After the insert");
            System.out.println(dao.getAllEmployees());

            Employee updateEmployee = new Employee(118, "Ranjit", "Sekhon", "9999990345", "IT", "310", "555-6784");
            dao.updateEmployee(updateEmployee);

            System.out.println();
            System.out.println("After the update");
            System.out.println(dao.getAllEmployees());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
