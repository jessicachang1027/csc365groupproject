package dao.impl;

import dao.Dao;
import dao.DaoManager;
import objects.Payment;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PaymentDaoImpl implements Dao<Payment> {
    private Connection conn;

    public PaymentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Payment getById(String resID) {
        Payment payment = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Payment WHERE resID=?");
            preparedStatement.setString(1, resID);
            resultSet = preparedStatement.executeQuery();
            Set<Payment> payments = unpackResultSet(resultSet);
            payment = (Payment) payments.toArray()[0];
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
        return payment;
    }

    public Set<Payment> getAll() {
        Set<Payment> payments = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Payment");
            resultSet = preparedStatement.executeQuery();
            payments = unpackResultSet(resultSet);
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
        return payments;
    }

    public Boolean insert(Payment obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Payment (resID, cID, cardNum) VALUES (?, ?, ?)");
            preparedStatement.setString(1, obj.getReservationId());
            preparedStatement.setString(2, obj.getCustomerId());
            preparedStatement.setString(3, obj.getCardNum());

            successful = !preparedStatement.execute();

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

    public Boolean update(Payment obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Payment SET cID=?, cardNum=? WHERE resID=?");
            preparedStatement.setString(1, obj.getCustomerId());
            preparedStatement.setString(2, obj.getCardNum());
            preparedStatement.setString(3, obj.getReservationId());

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

    public Boolean delete(Payment obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "DELETE FROM Payment WHERE resID=?");
            preparedStatement.setString(1, obj.getReservationId());
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

    private Set<Payment> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Payment> payments = new HashSet<Payment>();

        while(rs.next()) {
            Payment payment = new Payment(
                    rs.getString("resID"),
                    rs.getString("cID"),
                    rs.getString("cardNum")
            );


            payments.add(payment);
        }
        return payments;
    }

    public static void main(String[] args) {
        try {
            //Connection conn = Dao.ConnectionFactory.getConnection();
            //Dao.Impl.PaymentDaoImpl paymentDao = new Dao.Impl.PaymentDaoImpl(conn);
            DaoManager dm = DaoManager.getInstance();
            Dao<Payment> paymentDao = dm.getPaymentDao();
            //Payment payment = paymentDao.getById(1);
            //System.out.println(payment);
            //Set<Payment> payments = paymentDao.getAll();
            //System.out.println(payments);

            //Date.valueOf("2010-01-31")
            Payment newPayment
                    = new Payment("1","2", "1000002020202020");
            paymentDao.insert(newPayment);
//            payments = paymentDao.getAll();
//            System.out.println(payments);
//            payment.setPhone("8052223456");
//            paymentDao.update(payment);
//            payments = paymentDao.getAll();
//            System.out.println(payments);
//            Iterator<Payment> paymentIterator = payments.iterator();
//            while (paymentIterator.hasNext()) {
//                Payment delPayment = paymentIterator.next();
//                if (delPayment.getName().equals("Tom")) {
//                    paymentDao.delete(delPayment);
//                }
//            }
//            payments = paymentDao.getAll();
//            System.out.println(payments);
            dm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}