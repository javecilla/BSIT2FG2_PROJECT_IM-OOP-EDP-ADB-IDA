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
            if (conn != null) conn.close();
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
               SELECT * FROM COURIRER
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
            if (conn != null) conn.close();
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
            if (conn != null) conn.close();
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
            if (conn != null) conn.close();
        }
    }

    @Override
    //updates only the changed fields and leaves the others as they
    public boolean update(Courier courier) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean isUpdated = false;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            //retrieve the current record
            Courier courierDB = getById(courier.getRiderId());
            if(courierDB == null) {
                throw new SQLException("Courier with Rider_ID " + courier.getRiderId() + " not found.");
            }

            //compare fields and update only yung mga feilds na nabago
            String updateQuery = "UPDATE COURIER SET ";
            StringBuilder setClause = new StringBuilder();
            List<Object> params = new ArrayList<>();

            if(Input.fieldChanged(courierDB.getFirstName(), courier.getFirstName())) {
                setClause.append("First_Name = ?, ");
                params.add(courier.getFirstName());
            }
            if(Input.fieldChanged(courierDB.getLastName(), courier.getLastName())) {
                setClause.append("Last_Name = ?, ");
                params.add(courier.getLastName());
            }
            if(Input.fieldChanged(courierDB.getCompany(), courier.getCompany())) {
                setClause.append("Courier_Company = ?, ");
                params.add(courier.getCompany());
            }
            if(Input.fieldChanged(courierDB.getContactNumber(), courier.getContactNumber())) {
                setClause.append("Contact_Number = ?, ");
                params.add(courier.getContactNumber());
            }
            if(Input.fieldChanged(courierDB.getStatus(), courier.getStatus())) {
                setClause.append("Status = ?, ");
                params.add(courier.getStatus());
            }

            if(setClause.length() == 0) {
                throw new SQLException("No changes occured.");
                //return false;
            }

            //rmove trailing comma and add WHERE clause
            updateQuery += setClause.substring(0, setClause.length() - 2) + " WHERE Rider_ID = ?";
            params.add(courier.getRiderId());

            pst = conn.prepareStatement(updateQuery);

            for(int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

            isUpdated = pst.executeUpdate() > 0;

            if(isUpdated) {
                conn.commit();
            } else {
                conn.rollback();
            }

            return isUpdated;

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Updation of courier failed due to database error: " + e.getMessage());
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
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
            if (conn != null) conn.close();
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}
