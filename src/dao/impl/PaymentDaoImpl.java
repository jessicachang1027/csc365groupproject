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
            preparedStatement.setInt(1, obj.getReservationId());
            preparedStatement.setString(2, obj.getCustomerId());
            preparedStatement.setString(3, obj.getCardNum());

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

    public Boolean updateReservationCode(int resID){
        Boolean successful = true;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Payment SET resID = ? WHERE resID=0");
            preparedStatement.setInt(1, resID);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            successful = false;
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
            preparedStatement.setInt(3, obj.getReservationId());

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
            preparedStatement.setInt(1, obj.getReservationId());
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
                    rs.getInt("resID"),
                    rs.getString("cID"),
                    rs.getString("cardNum")
            );


            payments.add(payment);
        }
        return payments;
    }

}