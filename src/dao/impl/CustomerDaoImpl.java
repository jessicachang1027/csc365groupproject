package dao.impl;

import dao.Dao;
import objects.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CustomerDaoImpl implements Dao<Customer> {
    private Connection conn;

    public CustomerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Customer getById(String username) {
        Customer customer = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customers WHERE username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            Set<Customer> customers = unpackResultSet(resultSet);
            customer = (Customer)customers.toArray()[0];
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    public Customer login(String username, String password) {
        Customer customer = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customers WHERE username=? AND password=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            Set<Customer> customers = unpackResultSet(resultSet);
            customer = (Customer)customers.toArray()[0];
        } catch (SQLException e) {
            //e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    public Set<Customer> getAll() {
        Set<Customer> customers = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customers");
            resultSet = preparedStatement.executeQuery();
            customers = unpackResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }

    public Boolean insert(Customer obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Customers (firstName, lastName, username, password) VALUES (?, ?, ?, ?)");

            //preparedStatement.setString(1, obj.getCustomerId());
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getLastName());
            preparedStatement.setString(3, obj.getUsername());
            preparedStatement.setString(4, obj.getPassword());
            successful = !preparedStatement.execute();
        } catch (SQLException e) {
            //e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    public Boolean update(Customer obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Customer SET firstName=?, lastName=?, password=? WHERE username=?");
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getLastName());
            preparedStatement.setString(4, obj.getPassword());
            preparedStatement.setString(3, obj.getUsername());
            successful = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    public Boolean delete(Customer obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "DELETE FROM Customer WHERE username=?");
            preparedStatement.setString(1, obj.getUsername());
            successful = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    private Set<Customer> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Customer> customers = new HashSet<Customer>();

        while(rs.next()) {
            Customer customer = new Customer(
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("password"));


            customers.add(customer);
        }
        return customers;
    }

    public static void main(String[] args) {
//        try {
            //Connection conn = Dao.ConnectionFactory.getConnection();
            //Dao.Impl.CustomerDaoImpl customerDao = new Dao.Impl.CustomerDaoImpl(conn);
//            Dao.Dao.DaoManager dm = Dao.Dao.DaoManager.getInstance();
//            Dao.Dao<Customer> customerDao = dm.getCustomerDao();
//            Customer customer = customerDao.getById(2);
//            System.out.println(customer.getFirstName());
//            Set<Customer> customers = customerDao.getAll();
//            System.out.println(customers);
//            Customer newCustomer
//                    = new Customer("Jessica", "Chang", "jessicachang38", "password1027");
            //customerDao.insert(newCustomer);
//            customers = customerDao.getAll();
//            System.out.println(customers);
//            customer.setPhone("8052223456");
//            customerDao.update(customer);
//            customers = customerDao.getAll();
//            System.out.println(customers);
//            Iterator<Customer> customerIterator = customers.iterator();
//            while (customerIterator.hasNext()) {
//                Customer delCustomer = customerIterator.next();
//                if (delCustomer.getName().equals("Tom")) {
//                    customerDao.delete(delCustomer);
//                }
//            }
//            customers = customerDao.getAll();
//            System.out.println(customers);
//            dm.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
