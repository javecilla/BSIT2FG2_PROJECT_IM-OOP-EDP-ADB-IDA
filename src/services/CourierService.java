package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MSACCESSConnection;
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import models.Courier;
import enums.CourierStatus;
import helpers.Text;
import helpers.Input;
import java.util.Arrays;

public class CourierService implements IDatabaseOperators<Courier> {

    @Override
    public boolean create(Courier courier) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO COURIER (First_Name, Last_Name, Courier_Company, Courier_Contact, Rider_Status) 
                VALUES (?, ?, ?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, courier.getFirstName());
            pst.setString(2, courier.getLastName());
            pst.setString(3, courier.getCompany());
            pst.setString(4, courier.getContactNumber());
            //creation of new courier as default status is set as available
            pst.setString(5, courier.getStatus());

            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during courier creation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to registered an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    

    @Override
    public Courier getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT * FROM COURIER WHERE Rider_ID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if(rs.next()) {             
                return new Courier(
                    rs.getInt("Rider_ID"), 
                    rs.getString("First_Name"), 
                    rs.getString("Last_Name"), 
                    rs.getString("Courier_Company"), 
                    rs.getString("Courier_Contact"), 
                    rs.getString("Rider_Status")
                     
                );
            }
            
            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved courier an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        } 
    }

    @Override
    public List<Courier> getAll() throws SQLException {
        List<Courier> couriers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection(); 
            String query = """
                SELECT * FROM COURIER ORDER BY Rider_ID DESC
            """;

            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {            
                Courier courier = new Courier(
                    rs.getInt("Rider_ID"), 
                    rs.getString("First_Name"), 
                    rs.getString("Last_Name"), 
                    rs.getString("Courier_Company"), 
                    rs.getString("Courier_Contact"), 
                    rs.getString("Rider_Status")
                     
                );
                
                couriers.add(courier);
            }
            return couriers;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved couriers an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public List<Courier> getByStatus(String status) throws SQLException {
        List<Courier> couriers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();   
            String query = """
                SELECT * FROM COURIER WHERE UPPER(Rider_Status) = UPPER(?)
            """;

            pst = conn.prepareStatement(query);
            pst.setString(1, status);
            rs = pst.executeQuery();
            
            while (rs.next()) {            
                Courier courier = new Courier(
                    rs.getInt("Rider_ID"), 
                    rs.getString("First_Name"), 
                    rs.getString("Last_Name"), 
                    rs.getString("Courier_Company"), 
                    rs.getString("Courier_Contact"), 
                    rs.getString("Rider_Status")
                );
                
                couriers.add(courier);
            }
            return couriers;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved couriers an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean updateStatus(int id, String newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection(); 
            conn.setAutoCommit(false);
            
            String query = """
                UPDATE COURIER SET Rider_Status = ? WHERE Rider_ID = ?
            """;

            pst = conn.prepareStatement(query);
            pst.setString(1, newStatus);  
            pst.setInt(2, id);  

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during status courier updation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update status courier an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    @Override
        public boolean update(Courier courier) throws SQLException {
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                //conn = MSACCESSConnection.getConnection();
                conn = MSSQLConnection.getConnection(); 
                conn.setAutoCommit(false);
                
                String query = """
                    UPDATE COURIER
                    SET First_Name = ?, 
                        Last_Name = ?, 
                        Courier_Company = ?,    
                        Courier_Contact= ?
                    WHERE Rider_ID = ?
                """;

                pst = conn.prepareStatement(query);
                pst.setString(1, courier.getFirstName());
                pst.setString(2, courier.getLastName());
                pst.setString(3, courier.getCompany());   
                pst.setString(4, courier.getContactNumber());
                pst.setInt(5, courier.getRiderId());

                boolean updated = pst.executeUpdate() > 0;
                if(!updated) {
                    throw new SQLException("An error occured during courier updation.");
                }

                conn.commit();
                return true;  
            } catch(SQLException e) {
                if (conn != null) conn.rollback();
                System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
                System.out.println("Message: " + e.getMessage());
                throw new SQLException("Failed to update courier an error occured in our end.");
            } finally {
                if (conn != null) conn.setAutoCommit(true);
                //if (conn != null) conn.close();
                //MSACCESSConnection.closeResources(rs, pst);
                MSSQLConnection.closeResources(rs, pst);
            }
        }

    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = """
                DELETE FROM COURIER WHERE Rider_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
                        
            boolean deleted = pst.executeUpdate() > 0;
            if(!deleted) {
                throw new SQLException("An error occured during ingredient deletion.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to delete courier an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
}
