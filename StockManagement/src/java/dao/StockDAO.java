package dao;

import dto.Stock;
import dto.User;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    // login
    public User login(String userID, String password)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT fullName, roleID FROM tblUsers WHERE userID=? AND password=?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setString(2, password);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            userID,
                            rs.getString("fullName"),
                            rs.getString("roleID"),
                            "" // mask password
                    );
                }
            }
        }
        return null;
    }

    // check exist
    public boolean isTickerExist(String ticker)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM tblStocks WHERE ticker=?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticker);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // create
    public boolean create(Stock s)
            throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblStocks(ticker,name,sector,price,status) VALUES(?,?,?,?,?)";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getTicker());
            ps.setString(2, s.getName());
            ps.setString(3, s.getSector());
            ps.setFloat(4, s.getPrice());
            ps.setBoolean(5, s.isStatus());
            return ps.executeUpdate() > 0;
        }
    }

    // delete
    public boolean delete(String ticker)
            throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblStocks WHERE ticker=?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticker);
            return ps.executeUpdate() > 0;
        }
    }

    // search by keyword
    public List<Stock> search(String kw)
            throws SQLException, ClassNotFoundException {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStocks WHERE ticker LIKE ? OR name LIKE ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + kw + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // search by price
    public List<Stock> search(double min, double max)
            throws SQLException, ClassNotFoundException {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStocks WHERE price BETWEEN ? AND ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // get all
    public List<Stock> findAll()
            throws SQLException, ClassNotFoundException {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStocks";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // sort asc
    public List<Stock> findAllOrderByPriceAsc()
            throws SQLException, ClassNotFoundException {
        return findByOrder("ASC");
    }

    // sort desc
    public List<Stock> findAllOrderByPriceDesc()
            throws SQLException, ClassNotFoundException {
        return findByOrder("DESC");
    }

    private List<Stock> findByOrder(String dir)
            throws SQLException, ClassNotFoundException {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStocks ORDER BY price " + dir;
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // update
    public boolean update(Stock s)
            throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblStocks SET name=?, sector=?, price=?, status=? WHERE ticker=?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getSector());
            ps.setFloat(3, s.getPrice());
            ps.setBoolean(4, s.isStatus());
            ps.setString(5, s.getTicker());
            return ps.executeUpdate() > 0;
        }
    }

    // helper
    private Stock mapRow(ResultSet rs) throws SQLException {
        return new Stock(
                rs.getString("ticker"),
                rs.getString("name"),
                rs.getString("sector"),
                rs.getFloat("price"),
                rs.getBoolean("status")
        );
    }
}
