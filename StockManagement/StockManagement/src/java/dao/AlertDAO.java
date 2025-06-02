/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Alert;
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
public class AlertDAO {

    public ArrayList<Alert> search(String search) throws SQLException {
        ArrayList<Alert> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM tblAlerts WHERE userID LIKE ? OR ticker LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, '%' + search + '%');
                ps.setString(2, '%' + search + '%');
                rs = ps.executeQuery();
                while (rs.next()) {
                    int alertID = rs.getInt("alertID");
                    String userID = rs.getString("userID");
                    String ticker = rs.getString("ticker");
                    float threshold = rs.getFloat("threshold");
                    String direction = rs.getString("direction");
                    String status = rs.getString("status");
                    list.add(new Alert(alertID, userID, ticker, threshold, direction, status));
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

    public boolean create(Alert alert) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isCreated = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblAlerts(userID, ticker, threshold, direction) VALUES(?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, alert.getUserID());
                ps.setString(2, alert.getTicker());
                ps.setFloat(3, alert.getThreshold());
                ps.setString(4, alert.getDirection());
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

    public boolean update(int alertID, String userID, float threshold, String status) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isUpdated = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblAlerts SET threshold = ?, status = ? WHERE alertID = ? AND userID = ?";
                ps = conn.prepareStatement(sql);
                ps.setFloat(1, threshold);
                ps.setString(2, status);
                ps.setInt(3, alertID);
                ps.setString(4, userID);
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

    public boolean delete(int alertID, String userID) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isDeleted = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "DELETE FROM tblAlerts WHERE alertID = ? AND status = 'inactive'";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, alertID);
//                ps.setString(2, userID);
                isDeleted = ps.executeUpdate() > 0;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return isDeleted;
    }
}
