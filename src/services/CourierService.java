package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import interfaces.IDatabaseOperators;
import models.Courier;
import enums.CourierStatus;
import helpers.Text;
import helpers.Input;

public class CourierService implements IDatabaseOperators<Courier> {

    @Override
    public boolean create(Courier courier) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean isCreated = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO COURIER (First_Name, Last_Name, Courier_Company, Contact_Number, Status) 
                VALUES (?, ?, ?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, courier.getFirstName());
            pst.setString(2, courier.getLastName());
            pst.setString(3, courier.getCompany());
            pst.setString(4, courier.getContactNumber());
            //creation of new courier as default status is set as available
            pst.setString(5, Text.capitalizeFirstLetterInString(CourierStatus.AVAILABLE.name()));
       
            
            isCreated = pst.executeUpdate() > 0;
            
            if(isCreated) {
                conn.commit();
            } else {
                conn.rollback();
            }
            
            return isCreated;
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Creation of courirer failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(null, pst);
            //if (conn != null) conn.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }
    

    @Override
    public Courier getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = """
               SELECT * FROM COURIER
               WHERE Rider_ID = ?
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
                    rs.getString("Contact_Number"), 
                    rs.getString("Status")
                     
                );
            }
            
            return null;
        } finally {
            DBConnection.closeResources(rs, pst);
            //if (conn != null) conn.close();
        }
    }

    @Override
    public List<Courier> getAll() throws SQLException {
        List<Courier> couriers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        
        try {
            conn = DBConnection.getConnection();  
            String query = """
                SELECT * FROM COURIER
            """;

            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {            
                Courier courier = new Courier(
                    rs.getInt("Rider_ID"), 
                    rs.getString("First_Name"), 
                    rs.getString("Last_Name"), 
                    rs.getString("Courier_Company"), 
                    rs.getString("Contact_Number"), 
                    rs.getString("Status")
                     
                );
                
                couriers.add(courier);
            }
            return couriers;
        } finally {
            DBConnection.closeResources(rs, pst);
            //if (conn != null) conn.close();
        }
    }
    
    public List<Courier> getByStatus(String status) throws SQLException {
        List<Courier> couriers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        
        try {
            conn = DBConnection.getConnection();  
            String query = """
                SELECT * FROM COURIER 
                WHERE Status = ?
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
                    rs.getString("Contact_Number"), 
                    rs.getString("Status")
                     
                );
                
                couriers.add(courier);
            }
            return couriers;
        } finally {
            DBConnection.closeResources(rs, pst);
            //if (conn != null) conn.close();
        }
    }
    
    public boolean updateStatus(int id, String newStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                UPDATE COURIER
                SET Status = ?
                WHERE Rider_ID = ?
            """;

            pst = conn.prepareStatement(query);
            pst.setString(1, newStatus);  
            pst.setInt(2, id);  

            return pst.executeUpdate() > 0;  
        } finally {
            DBConnection.closeResources(null, pst);
            //if (conn != null) conn.close();
        }
    }
    
    @Override
    public boolean update(Courier courier) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean isDeleted = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = """
                DELETE FROM COURIER WHERE Rider_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
                        
            isDeleted = pst.executeUpdate() > 0;
            
            if(isDeleted) {
                conn.commit();
            } else {
                conn.rollback();
            }
            
            return isDeleted;
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Deletion of courirer failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(null, pst);
            //if (conn != null) conn.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}
