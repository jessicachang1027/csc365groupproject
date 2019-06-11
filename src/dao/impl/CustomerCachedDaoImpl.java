package dao.impl;

import objects.Customer;
import dao.CachedDao;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class CustomerCachedDaoImpl implements CachedDao<Customer> {
    private Connection conn;

    public CustomerCachedDaoImpl(Connection conn) {
        this.conn = conn;
        try {
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CachedRowSet getCachedRowSet() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RowSetFactory aFactory = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Customer");
            resultSet = preparedStatement.executeQuery();
            aFactory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = aFactory.createCachedRowSet();
            cachedRowSet.setTableName("Customer");
            cachedRowSet.populate(resultSet);
            int [] keys = {1};
            cachedRowSet.setKeyColumns(keys);
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getById(String id) {
        return null;
    }

    public Set getAll() {
        return null;
    }

    public Boolean insert(Object obj) {
        return null;
    }

    public Boolean update(Object obj) {
        return null;
    }

    public Boolean delete(Object obj) {
        return null;
    }
}