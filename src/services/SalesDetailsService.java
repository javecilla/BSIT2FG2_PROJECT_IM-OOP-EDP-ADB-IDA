package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import models.*;
import interfaces.IDatabaseOperators;
import helpers.Date;

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
            //if (conn != null) conn.close();
        }
    }
    
    public List<SalesDetails> getSalesDetails() throws SQLException { 
        List<SalesDetails> salesDetails = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            int salesId = new SaleService().getLastSaleId();
                    
            conn = DBConnection.getConnection();  
            String query = """
                SELECT SALES_DETAIL.Item_Quantity, SALES_DETAIL.Food_ID, SALES_DETAIL.Sales_ID, 
                    FOOD.Food_Name, FOOD.Price, FOOD.Category_ID, CATEGORY.Category_Name, 
                    SALE.Sales_Date, SALE.Net_Total, SALE.Payment_Amount, SALE.Customer_ID, 
                    CUSTOMER.Customer_Status, 
                    [USER].User_ID, [USER].Username, [USER].Password, [USER].User_Role, [USER].Email, [USER].Contact_Number, 
                    USER_INFO.UserInfo_ID, USER_INFO.First_Name, USER_INFO.Last_Name, USER_INFO.Barangay, USER_INFO.Street, USER_INFO.House_Number, USER_INFO.Region, USER_INFO.Province, USER_INFO.Municipality, 
                    COURIER.Rider_ID, COURIER.First_Name AS Courier_First_Name, COURIER.Last_Name AS Courier_Last_Name, COURIER.Courier_Company, COURIER.Contact_Number AS Courier_Contact_Number, COURIER.Status
                FROM COURIER INNER JOIN ((USER_INFO INNER JOIN [USER] ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID) 
                    INNER JOIN ((CUSTOMER INNER JOIN SALE ON CUSTOMER.Customer_ID = SALE.Customer_ID) 
                    INNER JOIN ((CATEGORY INNER JOIN FOOD ON CATEGORY.Category_ID = FOOD.Category_ID) 
                    INNER JOIN SALES_DETAIL ON FOOD.Food_ID = SALES_DETAIL.Food_ID) 
                    ON SALE.Sales_ID = SALES_DETAIL.Sales_ID) 
                    ON [USER].User_ID = CUSTOMER.Customer_ID) 
                    ON COURIER.Rider_ID = SALE.Rider_ID
                WHERE 
                    SALE.Sales_Date = #%s# 
                    AND SALE.Sales_ID = ?
                ORDER BY 
                    SALE.Sales_ID;
            """;

            String currentDate = Date.getCurrentDate(); 
            String formattedQuery = String.format(query, currentDate);
            
            pst = conn.prepareStatement(formattedQuery);
            pst.setInt(1, salesId);
            rs = pst.executeQuery();
            
            while (rs.next()) {  
                Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Status"),
                    new User(
                        rs.getInt("User_ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("User_Role"),
                        rs.getString("Email"),   
                        rs.getString("Contact_Number"),
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
                    ) 
                );

                Courier courier = new Courier(
                    rs.getInt("Rider_ID"),
                    rs.getString("Courier_First_Name"),
                    rs.getString("Courier_Last_Name"),
                    rs.getString("Courier_Company"),
                    rs.getString("Courier_Contact_Number"),
                    rs.getString("Status")
                );
                
                Sale sale = new Sale(
                    rs.getInt("Sales_ID"), 
                    rs.getString("Sales_Date"), 
                    rs.getDouble("Net_Total"),
                    rs.getDouble("Payment_Amount")
                );
                sale.setCustomer(customer);
                sale.setCourier(courier);
                
                SalesDetails salesDetail = new SalesDetails(rs.getInt("Item_Quantity"));
                salesDetail.setSale(sale);
                salesDetail.setFood(new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"),
                    new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Category_Name")
                    )
                ));
                
                salesDetails.add(salesDetail);
            }
            return salesDetails;
        } finally { 
            DBConnection.closeResources(rs, pst);
            //if (conn != null) conn.close();
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
