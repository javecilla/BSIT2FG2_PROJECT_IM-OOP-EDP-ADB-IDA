package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import config.DBConnection;
import enums.UserRoles;
import models.Admin;
import models.Customer;
import core.Session;
import helpers.Text;

public class UserService {
    public boolean login(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            
            String query = """
                SELECT USER.User_ID, USER.Username, USER.Password, USER.User_Role, USER_INFO.UserInfo_ID, USER_INFO.First_Name, USER_INFO.Last_Name, USER_INFO.Barangay, USER_INFO.Street, USER_INFO.House_Number, USER_INFO.Region, USER_INFO.Province, USER_INFO.Municipality         
            """;
  
            if(user.getUserRole().equalsIgnoreCase(UserRoles.CLIENT.name())) {
                query += ", CUSTOMER.* ";
            } else if(user.getUserRole().equalsIgnoreCase(UserRoles.ADMIN.name())) {
                query += ", ADMIN.* ";
            }
            
            query += "FROM (USER_INFO INNER JOIN [USER] ON USER_INFO.UserInfo_ID = USER.UserInfo_ID) ";
            
            // Join based on user role
            if(user.getUserRole().equalsIgnoreCase(UserRoles.CLIENT.name())) {
                query += "INNER JOIN CUSTOMER ON USER.User_ID = CUSTOMER.Customer_ID ";
            } else if(user.getUserRole().equalsIgnoreCase(UserRoles.ADMIN.name())) {
                query += "INNER JOIN ADMIN ON USER.User_ID = ADMIN.Admin_ID ";
            }
            
            query += "WHERE USER.Username = ? AND USER.User_Role = ?; ";
            
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, Text.capitalizeFirstLetterInString(user.getUserRole()));

            rs = pst.executeQuery();
            
           
            //check if user exists
            if(!rs.next()) {
                return false;
            }
            
            //check input password if match sa db records
            if(!rs.getString("Password").equals(user.getPassword())) {
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
            Session.setLoggedInUser(user); // Store logged-in user
            
            //populate role-specific ifnormation
            if(user.getUserRole().equalsIgnoreCase(UserRoles.CLIENT.name())) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerStatus(rs.getString("Customer_Status"));
                Session.setLoggedInCustomer(customer); // Store customer in session
            }
            else if(user.getUserRole().equalsIgnoreCase(UserRoles.ADMIN.name())) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("Admin_ID"));
                admin.setAdminType(rs.getString("Admin_Type"));
                Session.setLoggedInAdmin(admin); // Store admin in session
            }

            return true;

        } catch (SQLException e) {
            throw new SQLException("Login failed due to database error.");
        } finally {
            DBConnection.closeResources(rs, pst); 
        }
    }
    
    public boolean isAdminExistsById(int adminId) throws SQLException {
        Connection conn = null;
        String query = """
            SELECT COUNT(*) FROM ADMIN WHERE Admin_ID = ?
        """;
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, adminId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}