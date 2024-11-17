package services;

import models.User;
import config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public boolean login(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            
            String query = "SELECT u.User_ID, u.Username, u.Password, u.User_Role, "
                + "ui.UserInfo_ID, ui.First_Name, ui.Last_Name, ui.Barangay, ui.Street, "
                + "ui.House_Number, ui.Region, ui.Province, ui.Municipality "
                + "FROM USER u "
                + "INNER JOIN USER_INFO ui ON u.UserInfo_ID = ui.UserInfo_ID "
                + "WHERE u.Username = ? AND u.User_Role = ? ";
            
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getUserRole());
            rs = pst.executeQuery();

            //check if user exists
            if (!rs.next()) {
                return false;
            }

            String dbUsername = rs.getString("Username");
            String dbPassword = rs.getString("Password");
            //check password
            if(!dbUsername.equals(user.getUsername()) || !dbPassword.equals(user.getPassword())) {
                return false;
            }

            //populate the User object with additional UserInfo fields
            user.setUserId(rs.getInt("User_ID"));
            user.setUserInfoId(rs.getInt("UserInfo_ID"));
            user.setFirstName(rs.getString("First_Name"));
            user.setLastName(rs.getString("Last_Name"));
            user.setBarangay(rs.getString("Barangay"));
            user.setStreet(rs.getString("Street"));
            user.setHouseNumber(rs.getString("House_Number"));
            user.setRegion(rs.getString("Region"));
            user.setProvince(rs.getString("Province"));
            user.setMunicipality(rs.getString("Municipality"));

            return true;

        } catch (SQLException e) {
            throw new SQLException("Login failed due to database error.");
        } finally {
            DBConnection.closeResources(rs, pst); 
        }
    }
    
    protected boolean isAdminExists(Connection conn, int adminId) throws SQLException {
        String query = "SELECT COUNT(*) FROM ADMIN WHERE Admin_ID = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, adminId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
