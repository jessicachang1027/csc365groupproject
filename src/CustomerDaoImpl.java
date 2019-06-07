import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CustomerDaoImpl implements Dao<Customer> {
    private Connection conn;

    public CustomerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Customer getById(int id) {
        Customer customer = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customer WHERE id=?");
            preparedStatement.setInt(1, id);
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

    public Set<Customer> getAll() {
        Set<Customer> customers = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customer");
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
                    "INSERT INTO Customer (ssn, name, address, phone) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, obj.getSsn());
            preparedStatement.setString(2, obj.getName());
            preparedStatement.setString(3, obj.getAddress());
            preparedStatement.setString(4, obj.getPhone());
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

    public Boolean update(Customer obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Customer SET ssn=?, name=?, address=?, phone=? WHERE id=?");
            preparedStatement.setString(1, obj.getSsn());
            preparedStatement.setString(2, obj.getName());
            preparedStatement.setString(3, obj.getAddress());
            preparedStatement.setString(4, obj.getPhone());
            preparedStatement.setString(5, obj.getCustomerId());
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
                    "DELETE FROM Customer WHERE id=?");
            preparedStatement.setString(1, obj.getCustomerId());
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
            Customer customer = new Customer();
                    /*
                    rs.getString("id"),
                    rs.getString("ssn"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"));

                     */
            customers.add(customer);
        }
        return customers;
    }

    public static void main(String[] args) {
        try {
            //Connection conn = ConnectionFactory.getConnection();
            //CustomerDaoImpl customerDao = new CustomerDaoImpl(conn);
            DaoManager dm = DaoManager.getInstance();
            Dao<Customer> customerDao = dm.getCustomerDao();
            Customer customer = customerDao.getById(1);
            System.out.println(customer);
            Set<Customer> customers = customerDao.getAll();
            System.out.println(customers);
            Customer newCustomer
                    = new Customer();
                            /*"123456789", "Mary", "There", "8051112222");

                             */
            customerDao.insert(newCustomer);
            customers = customerDao.getAll();
            System.out.println(customers);
            customer.setPhone("8052223456");
            customerDao.update(customer);
            customers = customerDao.getAll();
            System.out.println(customers);
            Iterator<Customer> customerIterator = customers.iterator();
            while (customerIterator.hasNext()) {
                Customer delCustomer = customerIterator.next();
                if (delCustomer.getName().equals("Tom")) {
                    customerDao.delete(delCustomer);
                }
            }
            customers = customerDao.getAll();
            System.out.println(customers);
            dm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}