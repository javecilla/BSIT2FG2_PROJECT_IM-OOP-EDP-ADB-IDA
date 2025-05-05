package services;

import config.MSACCESSConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import config.MSACCESSConnection;
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import models.User;
//import models.Admin;
//import models.Customer;
import core.Session;
import java.util.ArrayList;
//import enums.CustomerStatus;
//import enums.UserRoles;
//import exceptions.DatabaseException;
//import helpers.Text;
import java.util.Arrays;
import models.Courier;

public class UserService implements IDatabaseOperators<User> {

    public boolean login(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = MSSQLConnection.getConnection();
//            conn = MSACCESSConnection.getConnection();
            
//            String query = """
//                SELECT USER.User_ID, USER.Username, USER.Password, USER.User_Role, USER.Email, USER.Contact_Number,
//                    USER_INFO.UserInfo_ID, USER_INFO.First_Name, USER_INFO.Last_Name, 
//                    USER_INFO.Barangay, USER_INFO.Street, USER_INFO.House_Number, 
//                    USER_INFO.Region, USER_INFO.Province, USER_INFO.Municipality,
//                    CUSTOMER.Customer_ID, CUSTOMER.Customer_Status,
//                    ADMIN.Admin_ID, ADMIN.Admin_Type
//                FROM [USER]
//                INNER JOIN USER_INFO ON USER_INFO.UserInfo_ID = USER.UserInfo_ID
//                LEFT JOIN CUSTOMER ON USER.User_ID = CUSTOMER.Customer_ID
//                LEFT JOIN ADMIN ON USER.User_ID = ADMIN.Admin_ID
//                WHERE USER.Username = ? OR USER.Email = ? OR USER.Contact_Number = ?
//            """;

            String query = """
                SELECT TOP 1 * FROM USER_WITH_PROFILE WHERE Username = ? OR Email = ? OR User_Contact = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getUsername());
            
            rs = pst.executeQuery();
            if (!rs.next()) {
                return false; //user not exist
            }
            
            //String storedPassword = rs.getString("Password");
            String storedPassword = rs.getString("User_Password");
            if (!storedPassword.equals(user.getPassword())) {
                return false;
            }
            
            //user.setUserId(rs.getInt("User_ID"));
            user.setUserId(rs.getInt("UserID"));
            user.setUsername(rs.getString("Username"));
            user.setEmail(rs.getString("Email"));
            //user.setContactNumber(rs.getString("Contact_Number"));
            user.setContactNumber(rs.getString("User_Contact"));
            user.setUserRole(rs.getString("User_Role"));
            user.setUserInfoId(rs.getInt("UserInfo_ID"));
            user.setFirstName(rs.getString("First_Name"));
            user.setLastName(rs.getString("Last_Name"));
            user.setBarangay(rs.getString("Barangay"));
            user.setStreet(rs.getString("Street"));
            user.setHouseNumber(rs.getString("House_Number"));
            user.setRegion(rs.getString("Region"));
            user.setProvince(rs.getString("Province"));
            user.setMunicipality(rs.getString("Municipality"));
            
            Session.setLoggedInUser(user);
     
//            if (user.getUserRole().equalsIgnoreCase(UserRoles.CLIENT.name())) {
//                Customer customer = new Customer(
//                    rs.getInt("Customer_ID"),
//                    rs.getString("Customer_Status"),
//                    user
//                );
//                Session.setLoggedInCustomer(customer);
//            } else if (user.getUserRole().equalsIgnoreCase(UserRoles.ADMIN.name())) {
//                Admin admin = new Admin(
//                    rs.getInt("Admin_ID"),
//                    rs.getString("Admin_Type"),
//                    user
//                );
//                Session.setLoggedInAdmin(admin);
//            }
           
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to login an error occured in our end.");
            throw new SQLException("Failed to login an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst); 
            MSSQLConnection.closeResources(rs, pst); 
        }
    }
    
    public boolean isUsernameTaken(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT COUNT(UserID) FROM USER_ WHERE Username = ?;
//            """;
            String query = """  
                SELECT TOP 1 Username FROM USER_ WHERE Username = ?
             """;
            pst = conn.prepareStatement(query);
            pst.setString(1, username);
            rs = pst.executeQuery();

            //return rs.next() && rs.getInt(1) > 0;
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to checked username availability an error occured in our end.");
            throw new SQLException("Failed to checked username availability an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst); 
        }
    }

    public boolean isEmailTaken(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();

//            String query = """
//                SELECT COUNT(*) FROM [USER] WHERE Email = ?
//            """;
            String query = """  
                SELECT TOP 1 Email FROM USER_ WHERE Email = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();

            //return rs.next() && rs.getInt(1) > 0;
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to checked email availability an error occured in our end.");
            throw new SQLException("Failed to checked email availability an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean isContactNumberTaken(String contactNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();

//            String query = """
//                SELECT COUNT(*) FROM [USER] WHERE Contact_Number = ?
//            """;
            String query = """  
                SELECT TOP 1 User_Contact FROM USER_ WHERE User_Contact = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, contactNumber);
            rs = pst.executeQuery();

            //return rs.next() && rs.getInt(1) > 0;
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to checked contact number  availability an error occured in our end.");
            throw new SQLException("Failed to checked contact number availability an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean isUserExistsById(int adminId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            
//            String query = """
//                SELECT COUNT(*) FROM ADMIN WHERE Admin_ID = ?
//            """;
            String query = """  
                SELECT TOP 1 UserID FROM USER_ WHERE UserID = ?
            """;
            conn.prepareStatement(query);
            pst.setInt(1, adminId);
            pst.executeQuery();
            
            //return rs.next() && rs.getInt(1) > 0;
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to checked contact number  availability an error occured in our end.");
            throw new SQLException("Failed to checked user existance an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    //Get last record customer/admin/userInfo id, this use to track kung ano
    //yung pinaka last id sa table customer/admin/userInfo id (since ang primary id ng customer/admin/userInfo id
    //table is hindi naka auto number/increment) and with that may possbile
    //mag karoon ng duplication ng id sa customer/admin/userInfo id
//    protected int getLastCustomerId() throws SQLException {
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            conn = MSACCESSConnection.getConnection();
//
//            String query = "SELECT MAX(Customer_ID) AS last_customer_id FROM CUSTOMER";
//            pst = conn.prepareStatement(query);
//            rs = pst.executeQuery();
//            
//            //Return 0 if no records are found meaning eto yung pinaka unang record na 
//            return rs.next() ? rs.getInt("last_customer_id") : 0;
//        } catch (SQLException e) {
//            throw new SQLException("Error retrieving last customer ID: " + e.getMessage(), e);
//        } finally {
//            MSACCESSConnection.closeResources(rs, pst);
//        }
//    }

//    protected int getLastAdminId() throws SQLException {
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            conn = MSACCESSConnection.getConnection();
//
//            String query = "SELECT MAX(Admin_ID) AS last_admin_id FROM ADMIN";
//            pst = conn.prepareStatement(query);
//            rs = pst.executeQuery();
//
//            return rs.next() ? rs.getInt("last_admin_id") : 0;
//        } catch (SQLException e) {
//            throw new SQLException("Error retrieving last admin ID: " + e.getMessage(), e);
//        } finally {
//            MSACCESSConnection.closeResources(rs, pst);
//        }
//    }
    
//    private int getLastUserInfoId() throws SQLException {
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            conn = MSACCESSConnection.getConnection();
//
//            String query = "SELECT MAX(UserInfo_ID) AS last_user_info_id FROM USER_INFO";
//            pst = conn.prepareStatement(query);
//            rs = pst.executeQuery();
//            
//            return rs.next() ? rs.getInt("last_user_info_id") : 0;
//        } catch (SQLException e) {
//            throw new SQLException("Error retrieving last userInfo ID: " + e.getMessage(), e);
//        } finally {
//            MSACCESSConnection.closeResources(rs, pst);
//        }
//    }

    @Override
    public boolean create(User user) throws SQLException {
        Connection conn = null;
//        PreparedStatement pstUserInfo = null;
//        PreparedStatement pstUser = null;
//        ResultSet rsUserInfo = null;
//        ResultSet rsUser = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
       
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
            
//            int userInfoId = getLastUserInfoId() + 1;
//            user.setUserInfoId(userInfoId);
                
//            String userInfoQuery = """
//                INSERT INTO USER_INFO (UserInfo_ID, First_Name, Last_Name, Barangay, Street, House_Number, Region, Province, Municipality)
//                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
//            """;
//            pstUserInfo = conn.prepareStatement(userInfoQuery);
//            //pstUserInfo.setInt(1, user.getUserInfoId());
//            pstUserInfo.setString(2, user.getFirstName());
//            pstUserInfo.setString(3, user.getLastName());
//            pstUserInfo.setString(4, user.getBarangay());
//            pstUserInfo.setString(5, user.getStreet());
//            pstUserInfo.setString(6, user.getHouseNumber());
//            pstUserInfo.setString(7, user.getRegion());
//            pstUserInfo.setString(8, user.getProvince());
//            pstUserInfo.setString(9, user.getMunicipality());

            String query = """
                EXEC SignUp
                    @Username = ?,
                    @User_Password = ?,
                    @User_Role = ?,
                    @Email = ?,
                    @User_Contact = ?,
                    @First_Name = ?,
                    @Last_Name = ?,
                    @Barangay = ?,
                    @Street = ?,
                    @House_Number = ?,
                    @Region = ?,
                    @Province = ?,
                    @Municipality = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserRole());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getContactNumber());
            //user info
            pst.setString(6, user.getFirstName());
            pst.setString(7, user.getLastName());
            pst.setString(8, user.getBarangay());
            pst.setString(9, user.getStreet());
            pst.setString(10, user.getHouseNumber());
            pst.setString(11, user.getRegion());
            pst.setString(12, user.getProvince());
            pst.setString(13, user.getMunicipality());
            
//            System.out.println("Data User: " + user);
//            System.out.println(user.getUsername());
//            System.out.println(user.getPassword());
//            System.out.println(user.getUserRole());
//            System.out.println(user.getEmail());
//            System.out.println(user.getContactNumber());
//            System.out.println(user.getFirstName());
//            System.out.println(user.getLastName());
//            System.out.println(user.getBarangay());
//            System.out.println(user.getStreet());
//            System.out.println(user.getHouseNumber());
//            System.out.println(user.getRegion());
//            System.out.println(user.getProvince());
//            System.out.println(user.getMunicipality());
//           
//            return true;

            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during user creation.");
            }
            
            conn.commit();
            return true;  
            
            /*
            if(pstUserInfo.executeUpdate() > 0) {
                String userQuery = """
                    INSERT INTO [USER] (Username, Password, User_Role, UserInfo_ID, Email, Contact_Number) 
                    VALUES (?, ?, ?, ?, ?, ?)
                """;
                
                pstUser = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                pstUser.setString(1, user.getUsername());
                pstUser.setString(2, user.getPassword());
                pstUser.setString(3, user.getUserRole());
                pstUser.setInt(4, user.getUserInfoId());
                pstUser.setString(5, user.getEmail());
                pstUser.setString(6, user.getContactNumber());
                
                int userRowsAffected = pstUser.executeUpdate();
                if(userRowsAffected == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }
                
                //Retrieve the generated User ID
                rsUser = pstUser.getGeneratedKeys();
                if(rsUser.next()) {
                    int userId = rsUser.getInt(1);
                    user.setUserId(userId);
                    
                    //Aditional User Info Insert in specific user role
                    if (user.getUserRole().equals(Text.capitalizeFirstLetterInString(UserRoles.CLIENT.name()))) {
                        //plus 1 doon sa last id sa recod ng customer example:
                        //if last record id sa customer is 10, then plus 1
                        //para sa new record as serve new id,
                        //so the new record will have id 11
                        int lastCustomerId = getLastCustomerId() + 1;
                        //as default creation of customer the status is active
                        String status = Text.capitalizeFirstLetterInString(CustomerStatus.ACTIVE.name()); 
                        Customer customer = new Customer(lastCustomerId, status);

                        String customerQuery = "INSERT INTO CUSTOMER (Customer_ID, Customer_Status) VALUES (?, ?)";
                        try (PreparedStatement pst = conn.prepareStatement(customerQuery)) {
                            pst.setInt(1, customer.getCustomerId());
                            pst.setString(2, customer.getCustomerStatus()); 
                            pst.executeUpdate();
                        }
                    } else if (user.getUserRole().equals(Text.capitalizeFirstLetterInString(UserRoles.ADMIN.name()))) {
                        int lastAdminId = getLastAdminId() + 1;
                        //beta test palang HAHAHAH
                        //since wala namang creation of new admin currently
                        //and as defult ng system is one admin palang
                        String adminType = "Test"; 
                        Admin admin = new Admin(lastAdminId, adminType);
                        String adminQuery = "INSERT INTO ADMIN (Admin_ID, Admin_Type) VALUES (?, ?)";
                        try (PreparedStatement pst = conn.prepareStatement(adminQuery)) {
                            pst.setInt(1, admin.getAdminId());
                            pst.setString(2, admin.getAdminType()); 
                            pst.executeUpdate();
                        }
                    }
                    
                    //commit the transaction sa database
                    conn.commit();
                    return true;
                } else {
                    throw new SQLException("Creating user failed, no User ID obtained.");
                }
            } else {
                throw new SQLException("An error occured in userInfo insertion.");
            }
            */
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            //throw new DatabaseException("Failed to registered an error occured in our end.");
            throw new SQLException("Failed to registered an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT TOP 1 * FROM USER_WITH_PROFILE WHERE UserID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setContactNumber(rs.getString("User_Contact"));
                user.setUserRole(rs.getString("User_Role"));
                user.setUserInfoId(rs.getInt("UserInfo_ID"));
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setBarangay(rs.getString("Barangay"));
                user.setStreet(rs.getString("Street"));
                user.setHouseNumber(rs.getString("House_Number"));
                user.setRegion(rs.getString("Region"));
                user.setProvince(rs.getString("Province"));
                user.setMunicipality(rs.getString("Municipality"));
                
                return user;
            }
            
            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved user an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        }    
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT * FROM USER_WITH_PROFILE
            """;            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {       
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setContactNumber(rs.getString("User_Contact"));
                user.setUserRole(rs.getString("User_Role"));
                user.setUserInfoId(rs.getInt("UserInfo_ID"));
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setBarangay(rs.getString("Barangay"));
                user.setStreet(rs.getString("Street"));
                user.setHouseNumber(rs.getString("House_Number"));
                user.setRegion(rs.getString("Region"));
                user.setProvince(rs.getString("Province"));
                user.setMunicipality(rs.getString("Municipality"));
                
                users.add(user);
            }
            
            return users;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved users an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        } 
    }

    @Override
    public boolean update(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}