package dao;

import dto.User;
import utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     *
     *
     */
    public User login(String userID, String password)
            throws ClassNotFoundException, SQLException {
        // Debug đầu vào
        System.out.println("UserDAO.login(): userID=" + userID + ", password=" + password);

        String sql = "SELECT fullName, roleID FROM tblUsers WHERE userID = ? AND password = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, password);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    System.out.println("Login successful for " + userID + ", fullName=" + fullName + ", roleID=" + roleID);
                    // *** LƯU Ý: tham số cuối "***" chỉ dùng tạm, không quan trọng
                    return new User(userID, fullName, roleID, "***");
                } else {
                    System.out.println("Login failed: no matching record");
                }
            }
        }
        return null;
    }
    // Kiểm tra userID đã tồn tại chưa

    public boolean checkDuplicate(String userID) throws Exception {
        String sql = "SELECT userID FROM tblUsers WHERE userID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

// Thêm user mới
    public boolean insert(User user) throws Exception {
        String sql = "INSERT INTO tblUsers(userID, fullName, roleID, password) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRoleID());
            ps.setString(4, user.getPassword());
            return ps.executeUpdate() > 0;
        }
    }

// Lấy danh sách user (có lọc)
    public List<User> getAll(String search) throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID FROM tblUsers WHERE userID LIKE ? OR fullName LIKE ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    list.add(new User(userID, fullName, roleID, "***"));
                }
            }
        }
        return list;
    }
// Lấy toàn bộ danh sách người dùng (không có điều kiện lọc)

    public List<User> getAllUsers() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, roleID FROM tblUsers";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String userID = rs.getString("userID");
                String fullName = rs.getString("fullName");
                String roleID = rs.getString("roleID");
                list.add(new User(userID, fullName, roleID, "***"));
            }
        }
        return list;
    }

// Cập nhật user
    public boolean update(User user) throws Exception {
        String sql = "UPDATE tblUsers SET fullName = ?, roleID = ?, password = ? WHERE userID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getRoleID());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserID());
            return ps.executeUpdate() > 0;
        }
    }

// Xoá user
    public boolean delete(String userID) throws Exception {
        String sql = "DELETE FROM tblUsers WHERE userID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        }
    }
}

