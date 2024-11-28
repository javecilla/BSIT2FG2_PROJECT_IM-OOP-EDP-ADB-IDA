package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import config.DBConnection;
import interfaces.IDatabaseOperators;
import models.User;
import models.Admin;
import models.Customer;
import core.Session;
import enums.CustomerStatus;
import enums.UserRoles;
import helpers.Text;

public class UserService implements IDatabaseOperators<User> {

    public boolean login(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            
            String query = """
                SELECT USER.User_ID, USER.Username, USER.Password, USER.User_Role, USER.Email, USER.Contact_Number,
                    USER_INFO.UserInfo_ID, USER_INFO.First_Name, USER_INFO.Last_Name, 
                    USER_INFO.Barangay, USER_INFO.Street, USER_INFO.House_Number, 
                    USER_INFO.Region, USER_INFO.Province, USER_INFO.Municipality,
                    CUSTOMER.Customer_ID, CUSTOMER.Customer_Status,
                    ADMIN.Admin_ID, ADMIN.Admin_Type
                FROM [USER]
                INNER JOIN USER_INFO ON USER_INFO.UserInfo_ID = USER.UserInfo_ID
                LEFT JOIN CUSTOMER ON USER.User_ID = CUSTOMER.Customer_ID
                LEFT JOIN ADMIN ON USER.User_ID = ADMIN.Admin_ID
                WHERE USER.Username = ? OR USER.Email = ? OR USER.Contact_Number = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getUsername());
            
            rs = pst.executeQuery();
            
            // Check if user exists
            if (!rs.next()) {
                return false;
            }
            
            // Check input password
            String storedPassword = rs.getString("Password");
            if (!storedPassword.equals(user.getPassword())) {
                return false;
            }
            
            // Populate user object
            user.setUserId(rs.getInt("User_ID"));
            user.setUsername(rs.getString("Username"));
            user.setEmail(rs.getString("Email"));
            user.setContactNumber(rs.getString("Contact_Number"));
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
            // Populate role-specific information
            if (user.getUserRole().equalsIgnoreCase(UserRoles.CLIENT.name())) {
                Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Status"),
                    user
                );
                Session.setLoggedInCustomer(customer);
            } else if (user.getUserRole().equalsIgnoreCase(UserRoles.ADMIN.name())) {
                Admin admin = new Admin(
                    rs.getInt("Admin_ID"),
                    rs.getString("Admin_Type"),
                    user
                );
                Session.setLoggedInAdmin(admin);
            }
            
            return true;
        } catch (SQLException e) {
            throw new SQLException("Login failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rs, pst); 
        }
    }
    
    //Check if username already exists in the database
    public boolean isUsernameTaken(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = """
                SELECT COUNT(*) FROM [USER] WHERE Username = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, username);
            rs = pst.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new SQLException("Checking username availability failed: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }

    //Check if email already exists in the database
    public boolean isEmailTaken(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = """
                SELECT COUNT(*) FROM [USER] WHERE Email = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new SQLException("Checking email availability failed: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
    
    public boolean isContactNumberTaken(String contactNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = """
                SELECT COUNT(*) FROM [USER] WHERE Contact_Number = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, contactNumber);
            rs = pst.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new SQLException("Checking contact number availability failed: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }

    //Check if admin exists by its id in the database
    public boolean isAdminExistsById(int adminId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String query = """
                SELECT COUNT(*) FROM ADMIN WHERE Admin_ID = ?
            """;
            conn.prepareStatement(query);
            pst.setInt(1, adminId);
            pst.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new SQLException("Checking admin id failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rs, pst); 
        }
    }
    
    //Get last record customer/admin/userInfo id, this use to track kung ano
    //yung pinaka last id sa table customer/admin/userInfo id (since ang primary id ng customer/admin/userInfo id
    //table is hindi naka auto number/increment) and with that may possbile
    //mag karoon ng duplication ng id sa customer/admin/userInfo id
    protected int getLastCustomerId() throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = "SELECT MAX(Customer_ID) AS last_customer_id FROM CUSTOMER";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            //Return 0 if no records are found meaning eto yung pinaka unang record na 
            return rs.next() ? rs.getInt("last_customer_id") : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving last customer ID: " + e.getMessage(), e);
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }

    protected int getLastAdminId() throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = "SELECT MAX(Admin_ID) AS last_admin_id FROM ADMIN";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            return rs.next() ? rs.getInt("last_admin_id") : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving last admin ID: " + e.getMessage(), e);
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
    
    private int getLastUserInfoId() throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = "SELECT MAX(UserInfo_ID) AS last_user_info_id FROM USER_INFO";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            return rs.next() ? rs.getInt("last_user_info_id") : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving last userInfo ID: " + e.getMessage(), e);
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }

    @Override
    public boolean create(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstUserInfo = null;
        PreparedStatement pstUser = null;
        ResultSet rsUserInfo = null;
        ResultSet rsUser = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            int userInfoId = getLastUserInfoId() + 1;
            user.setUserInfoId(userInfoId);
                
            //User Info Insertion
            String userInfoQuery = """
                INSERT INTO USER_INFO (UserInfo_ID, First_Name, Last_Name, Barangay, Street, House_Number, Region, Province, Municipality)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
            pstUserInfo = conn.prepareStatement(userInfoQuery);
            pstUserInfo.setInt(1, user.getUserInfoId());
            pstUserInfo.setString(2, user.getFirstName());
            pstUserInfo.setString(3, user.getLastName());
            pstUserInfo.setString(4, user.getBarangay());
            pstUserInfo.setString(5, user.getStreet());
            pstUserInfo.setString(6, user.getHouseNumber());
            pstUserInfo.setString(7, user.getRegion());
            pstUserInfo.setString(8, user.getProvince());
            pstUserInfo.setString(9, user.getMunicipality());
            
            if(pstUserInfo.executeUpdate() > 0) {
                
                
                //User Insertion
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
        } catch(SQLException e) {
            //if meron anomaly or exception sa transaction then rollback the transcation
            if (conn != null) conn.rollback();
            throw new SQLException("Registration failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(rsUserInfo, pstUserInfo);
            DBConnection.closeResources(null, pstUser);
            //if (conn != null) conn.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
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