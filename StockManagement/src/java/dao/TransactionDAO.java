/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBUtils;

/**
 *
 * @author admin
 */
public class TransactionDAO {

    public ArrayList<Transaction> search(String search) throws SQLException {
        ArrayList<Transaction> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM tblTransactions WHERE ticker LIKE ? OR userID LIKE ? OR status LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, '%' + search + '%');
                ps.setString(2, '%' + search + '%');
                ps.setString(3, '%' + search + '%');
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String userID = rs.getString("userID");
                    String ticker = rs.getString("ticker");
                    String type = rs.getString("type");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    String status = rs.getString("status");
                    list.add(new Transaction(id, userID, ticker, type, quantity, price, status));
                }
            }
        } catch (Exception e) {
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }
    
    public Transaction getTransactionById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tblTransactions WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Transaction(
                            rs.getInt("id"),
                            rs.getString("userID"),
                            rs.getString("ticker"),
                            rs.getString("type"),
                            rs.getInt("quantity"), (float) rs.getDouble("price"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving transaction: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean create(Transaction transaction) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isCreated = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblTransactions(userID, ticker, type, quantity, price, status) VALUES(?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, transaction.getUserID());
                ps.setString(2, transaction.getTicker());
                ps.setString(3, transaction.getType());
                ps.setInt(4, transaction.getQuantity());
                ps.setFloat(5, transaction.getPrice());
                ps.setString(6, transaction.getStatus());
                isCreated = ps.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return isCreated;
    }

    public boolean update(int transactionId, int quantity, double price, String type, String status) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isUpdated = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblTransactions SET type = ?, quantity = ?, price = ? WHERE id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, type);
                ps.setInt(2, quantity);
                ps.setDouble(3, price);
                ps.setInt(4, transactionId);
                isUpdated = ps.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return isUpdated;
    }
    
     public boolean delete(int transactionId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isDeleted = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "DELETE FROM tblTransactions WHERE id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, transactionId);
                isDeleted = ps.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) conn.close();
            if (ps != null) ps.close();
        }
        return isDeleted;
    }
}
