package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Sale;
import models.Food;
import models.SalesDetails;
import config.DBConnection;
import helpers.Date;
import interfaces.IDatabaseOperators;
import models.Category;
import models.Customer;
import models.User;
import models.UserInfo;

public class SalesDetailsService implements IDatabaseOperators<SalesDetails>{

    @Override
    public boolean create(SalesDetails salesDetails) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO SALES_DETAIL (Food_ID, Sales_ID, Item_Quantity) VALUES (?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, salesDetails.getFood().getFoodId());
            pst.setInt(2, salesDetails.getSale().getSaleId());
            pst.setInt(3, salesDetails.getItemQuantity());
            
            success = pst.executeUpdate() > 0;
            
            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            
            return success;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    public List<SalesDetails> getSalesDetailsByCustomer(int customerId) throws SQLException { 
        List<SalesDetails> salesDetails = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();  
            String query = """
                SELECT SALES_DETAIL.Item_Quantity, SALES_DETAIL.Food_ID, SALES_DETAIL.Sales_ID, 
                    FOOD.Food_Name, FOOD.Price, FOOD.Category_ID, 
                    CATEGORY.Category_Name, SALE.Sales_Date, SALE.Net_Total, 
                    SALE.Customer_ID, CUSTOMER.Customer_Status, 
                    [USER].User_ID, [USER].Username, [USER].User_Role, [USER].Password, 
                    USER_INFO.*
                FROM CATEGORY 
                INNER JOIN (FOOD 
                INNER JOIN (USER_INFO 
                INNER JOIN ([USER]  
                INNER JOIN (CUSTOMER 
                INNER JOIN (SALE 
                INNER JOIN SALES_DETAIL ON SALE.Sales_ID = SALES_DETAIL.Sales_ID) 
                ON CUSTOMER.Customer_ID = SALE.Customer_ID) 
                ON [USER].User_ID = CUSTOMER.Customer_ID) 
                ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID) 
                ON FOOD.Food_ID = SALES_DETAIL.Food_ID) 
                ON CATEGORY.Category_ID = FOOD.Category_ID
                WHERE SALE.Customer_ID = ? AND SALE.Sales_Date = #%s#
                ORDER BY SALE.Sales_ID ASC
            """;

            String currentDate = Date.getCurrentDate(); 
            String formattedQuery = String.format(query, currentDate);
            
            pst = conn.prepareStatement(formattedQuery);
            pst.setInt(1, customerId);
            rs = pst.executeQuery();
            
            while (rs.next()) {   
                SalesDetails salesDetail = new SalesDetails(
                    new Food(
                        rs.getInt("Food_ID"), 
                        rs.getString("Food_Name"), 
                        rs.getDouble("Price")
                    ), 
                    new Sale(
                        rs.getInt("Sales_ID"), 
                        rs.getString("Sales_Date"), 
                        rs.getDouble("Net_Total")
                    ),
                    rs.getInt("Item_Quantity"),
                    new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Category_Name")
                    ),
                    new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Status")
                    ),
                    new User(
                        rs.getInt("User_ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("User_Role")
                    ),
                    new UserInfo(
                        rs.getInt("UserInfo_ID"),
                        rs.getString("First_Name"),
                        rs.getString("Last_Name"),
                        rs.getString("Barangay"),
                        rs.getString("Street"),
                        rs.getString("House_Number"),
                        rs.getString("Region"),
                        rs.getString("Province"),
                        rs.getString("Municipality")
                    )
                );
                
                salesDetails.add(salesDetail);
            }
            return salesDetails;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public SalesDetails getById(int customerId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SalesDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(SalesDetails entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
