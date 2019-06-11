package dao.impl;

import dao.Dao;
import objects.CreditCard;
import dao.DaoManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import java.lang.*;

public class CreditCardDaoImpl implements Dao<CreditCard> {
    private Connection conn;

    public CreditCardDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public CreditCard getById(String code) {
        CreditCard card = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM CreditCard WHERE cardNum=?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            Set<CreditCard> cards = unpackResultSet(resultSet);
            card = (CreditCard) cards.toArray()[0];
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
        return card;
    }

    public Set<CreditCard> getAll() {
        Set<CreditCard> cards = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM CreditCard");
            resultSet = preparedStatement.executeQuery();
            cards = unpackResultSet(resultSet);
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
        return cards;
    }

    public Set<CreditCard> getAllWhere(String query) {
        Set<CreditCard> cards = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM CreditCard WHERE " + query);
            resultSet = preparedStatement.executeQuery();
            cards = unpackResultSet(resultSet);
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
        return cards;
    }

    public Boolean insert(CreditCard obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO CreditCard (customerId, cardNum, creditLim, balance, active) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, obj.getCustomerId());
            preparedStatement.setString(2, obj.getCardNum());
            preparedStatement.setDouble(3, obj.getCreditLim());
            preparedStatement.setDouble(4, obj.getBalance());
            preparedStatement.setBoolean(5, obj.isActive());

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

    public Boolean update(CreditCard obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE CreditCard SET customerId=?, cardNum=?, creditLim=?, balance=?, active=?");
            preparedStatement.setString(1, obj.getCustomerId());
            preparedStatement.setString(2, obj.getCardNum());
            preparedStatement.setDouble(3, obj.getCreditLim());
            preparedStatement.setDouble(4, obj.getBalance());
            preparedStatement.setBoolean(5, obj.isActive());

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

    public Boolean delete(CreditCard obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "DELETE FROM CreditCard WHERE cardNum=?");
            preparedStatement.setString(1, obj.getCardNum());
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

    private Set<CreditCard> unpackResultSet(ResultSet rs) throws SQLException {
        Set<CreditCard> cards = new HashSet<CreditCard>();

        while(rs.next()) {
            CreditCard card = new CreditCard(
                    rs.getString("customerId"),
                    rs.getString("cardNum"),
                    rs.getDouble("creditLim"),
                    rs.getDouble("balance"),
                    rs.getBoolean("active")
            );


            cards.add(card);
        }
        return cards;
    }

    public static void main(String[] args) {
        try {
            DaoManager dm = DaoManager.getInstance();
            Dao<CreditCard> creditCardDao = dm.getCreditCardDao();
            //Date.valueOf("2010-01-31")

            CreditCard newCard
                    = new CreditCard("aander65", "1", 4000, 0, true);
            creditCardDao.insert(newCard);

            newCard
                    = new CreditCard("aander65", "2", 4000, 0, false);
            creditCardDao.insert(newCard);

            newCard
                    = new CreditCard("kkrein", "3", 4000, 400, true);
            creditCardDao.insert(newCard);
            dm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}