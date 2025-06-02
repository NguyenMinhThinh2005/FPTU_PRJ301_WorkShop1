/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Stock;
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
public class StockDAO {

    public ArrayList<Stock> search(String search) throws SQLException {
        ArrayList<Stock> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM tblStocks WHERE ticker LIKE ? OR name LIKE ? OR sector LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, '%' + search + '%');
                ps.setString(2, '%' + search + '%');
                ps.setString(3, '%' + search + '%');
                rs = ps.executeQuery();
                while (rs.next()) {
                    String ticker = rs.getString("ticker");
                    String name = rs.getString("name");
                    String sector = rs.getString("sector");
                    float price = rs.getFloat("price");
                    boolean status = rs.getBoolean("status");
                    list.add(new Stock(ticker, name, sector, price, status));
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

    public boolean isTickerExist(String ticker) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT ticker FROM tblStocks WHERE ticker = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, ticker);
                rs = ps.executeQuery();
                exists = rs.next();
            }
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
        return exists;
    }

    public boolean create(Stock stock) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "INSERT INTO tblStocks(ticker, name, sector, price, status) VALUES(?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stock.getTicker());
            ps.setString(2, stock.getName());
            ps.setString(3, stock.getSector());
            ps.setFloat(4, stock.getPrice());
            ps.setBoolean(5, stock.isStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public boolean update(Stock stock) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE tblStocks SET name = ?, sector = ?, price = ? WHERE ticker = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stock.getName());
            ps.setString(2, stock.getSector());
            ps.setFloat(3, stock.getPrice());
            ps.setString(4, stock.getTicker());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public boolean hasRelatedTransactions(String ticker) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean hasTransactions = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT id FROM tblTransactions WHERE ticker = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, ticker);
                rs = ps.executeQuery();
                hasTransactions = rs.next();
            }
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
        return hasTransactions;
    }
    
    public boolean delete(String ticker) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "DELETE tblStocks WHERE ticker=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, ticker);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
}
