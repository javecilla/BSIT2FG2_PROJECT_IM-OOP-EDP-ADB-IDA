package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Ingredient;
import models.Supplier;
//import models.Admin;
import models.User;
//import config.MSACCESSConnection;
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import java.util.Arrays;

public class IngredientService implements IDatabaseOperators<Ingredient> { 
    @Override
    public boolean create(Ingredient ingredient) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = """
                INSERT INTO INGREDIENT (Ingredient_Name, Ingredient_Quantity, Reorder_Point, Supplier_ID, Admin_ID)
                VALUES (?, ?, ?, ?, ?)
            """;

            pst = conn.prepareStatement(query);
            pst.setString(1, ingredient.getIngredientName());
            pst.setInt(2, ingredient.getQuantity());
            pst.setInt(3, ingredient.getReorderPoint());
            pst.setInt(4, ingredient.getSupplier().getSupplierId());
            pst.setInt(5, ingredient.getUserAdmin().getUserId());
            
            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during ingredient creation.");
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
    public Ingredient getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT TOP 1 *            
//                FROM USER_INFO
//                INNER JOIN ([USER]
//                    INNER JOIN (ADMIN   
//                        INNER JOIN (SUPPLIER
//                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
//                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
//                    ) ON [USER].User_ID = ADMIN.Admin_ID
//                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
//                WHERE INGREDIENT.Ingredient_ID = ?
//            """;   

            String query = """
                SELECT TOP 1 * FROM INGREDIENT_DETAILS WHERE Ingredient_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                Ingredient ingredient = new Ingredient();
//                Admin admin = new Admin(
//                    rs.getInt("Admin_ID"),
//                    rs.getString("Admin_Type"),
//                    new User(
//                        rs.getInt("User_ID"),
//                        rs.getString("Username"),
//                        rs.getString("Password"),
//                        rs.getString("User_Role"),
//                        rs.getString("Email"),   
//                        rs.getString("Contact_Number")
//                    )
//                );
//
//                Ingredient ingredient = new Ingredient(
//                    rs.getInt("Ingredient_ID"),
//                    rs.getString("Ingredient_Name"),
//                    rs.getInt("Ingredient_Quantity"),
//                    rs.getInt("Reorder_Point")
//                );
//                ingredient.setSupplier(new Supplier(
//                    rs.getInt("Supplier_ID"),
//                    rs.getString("Supplier_Name"),
//                    rs.getString("Supplier_Address"),
//                    rs.getString("Contact_Number")
//                ));
//                ingredient.setAdmin(admin);

                ingredient.setIngredientId(rs.getInt("Ingredient_ID"));
                ingredient.setIngredientName(rs.getString("Ingredient_Name"));
                ingredient.setQuantity(rs.getInt("Ingredient_Quantity"));
                ingredient.setReorderPoint(rs.getInt("Reorder_Point"));
                
                User admin = new User();
                admin.setUserId(rs.getInt("Admin_ID"));
                admin.setContactNumber(rs.getString("Admin_Contact"));
                ingredient.setUserAdmin(admin);
                
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("Supplier_ID"));
                supplier.setSupplierName(rs.getString("Supplier_Name"));
                supplier.setContactNumber(rs.getString("Supplier_Contact"));
                supplier.setAddress(rs.getString("Supplier_Address"));
                ingredient.setSupplier(supplier);

                return ingredient;
            }

            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved ingredient an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }  
    }

    
    @Override
    public List<Ingredient> getAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT *            
//                FROM USER_INFO
//                INNER JOIN ([USER]
//                    INNER JOIN (ADMIN   
//                        INNER JOIN (SUPPLIER
//                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
//                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
//                    ) ON [USER].User_ID = ADMIN.Admin_ID
//                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
//                ORDER BY INGREDIENT.Ingredient_ID ASC
//            """; 
            String query = """
                SELECT * FROM INGREDIENT_DETAILS ORDER BY Ingredient_ID DESC
            """;
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
//                Admin admin = new Admin(
//                    rs.getInt("Admin_ID"),
//                    rs.getString("Admin_Type"),
//                    new User(
//                        rs.getInt("User_ID"),
//                        rs.getString("Username"),
//                        rs.getString("Password"),
//                        rs.getString("User_Role"),
//                        rs.getString("Email"),   
//                        rs.getString("Contact_Number")
//                    )
//                );
//
//                Ingredient ingredient = new Ingredient(
//                    rs.getInt("Ingredient_ID"),
//                    rs.getString("Ingredient_Name"),
//                    rs.getInt("Ingredient_Quantity"),
//                    rs.getInt("Reorder_Point")
//                );
//                ingredient.setSupplier(new Supplier(
//                    rs.getInt("Supplier_ID"),
//                    rs.getString("Supplier_Name"),
//                    rs.getString("Supplier_Address"),
//                    rs.getString("Contact_Number")
//                ));
//                ingredient.setAdmin(admin);

                Ingredient ingredient = new Ingredient();
                
                ingredient.setIngredientId(rs.getInt("Ingredient_ID"));
                ingredient.setIngredientName(rs.getString("Ingredient_Name"));
                ingredient.setQuantity(rs.getInt("Ingredient_Quantity"));
                ingredient.setReorderPoint(rs.getInt("Reorder_Point"));
                
                User admin = new User();
                admin.setUserId(rs.getInt("Admin_ID"));
                admin.setContactNumber(rs.getString("Admin_Contact"));
                ingredient.setUserAdmin(admin);
                
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("Supplier_ID"));
                supplier.setSupplierName(rs.getString("Supplier_Name"));
                supplier.setContactNumber(rs.getString("Supplier_Contact"));
                supplier.setAddress(rs.getString("Supplier_Address"));
                ingredient.setSupplier(supplier);
                
                ingredients.add(ingredient);
            }

            return ingredients;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved ingredients an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }  
    }
    
    public List<Ingredient> getAllLowStock() throws SQLException { 
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT *            
//                FROM USER_INFO
//                INNER JOIN ([USER]
//                    INNER JOIN (ADMIN   
//                        INNER JOIN (SUPPLIER
//                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
//                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
//                    ) ON [USER].User_ID = ADMIN.Admin_ID
//                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
//                WHERE INGREDIENT.Ingredient_Quantity <= INGREDIENT.Reorder_Point
//                ORDER BY INGREDIENT.Ingredient_ID ASC
//            """; 

            String query = """
                SELECT * FROM INGREDIENT_DETAILS WHERE Ingredient_Quantity <= Reorder_Point
            """;
 
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
//                Admin admin = new Admin(
//                    rs.getInt("Admin_ID"),
//                    rs.getString("Admin_Type"),
//                    new User(
//                        rs.getInt("User_ID"),
//                        rs.getString("Username"),
//                        rs.getString("Password"),
//                        rs.getString("User_Role"),
//                        rs.getString("Email"),   
//                        rs.getString("Contact_Number")
//                    )
//                );
//
//                Ingredient ingredient = new Ingredient(
//                    rs.getInt("Ingredient_ID"),
//                    rs.getString("Ingredient_Name"),
//                    rs.getInt("Ingredient_Quantity"),
//                    rs.getInt("Reorder_Point")
//                );
//                ingredient.setSupplier(new Supplier(
//                    rs.getInt("Supplier_ID"),
//                    rs.getString("Supplier_Name"),
//                    rs.getString("Supplier_Address"),
//                    rs.getString("Contact_Number")
//                ));
//                ingredient.setAdmin(admin);

                Ingredient ingredient = new Ingredient();
                
                ingredient.setIngredientId(rs.getInt("Ingredient_ID"));
                ingredient.setIngredientName(rs.getString("Ingredient_Name"));
                ingredient.setQuantity(rs.getInt("Ingredient_Quantity"));
                ingredient.setReorderPoint(rs.getInt("Reorder_Point"));
                
                User admin = new User();
                admin.setUserId(rs.getInt("Admin_ID"));
                admin.setContactNumber(rs.getString("Admin_Contact"));
                ingredient.setUserAdmin(admin);
                
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("Supplier_ID"));
                supplier.setSupplierName(rs.getString("Supplier_Name"));
                supplier.setContactNumber(rs.getString("Supplier_Contact"));
                supplier.setAddress(rs.getString("Supplier_Address"));
                ingredient.setSupplier(supplier);
                
                ingredients.add(ingredient);
            }
            
            return ingredients;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved low stock ingredients an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    @Override
    public boolean update(Ingredient ingredient) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
                UPDATE INGREDIENT 
                SET Ingredient_Name = ?,
                    Ingredient_Quantity = ?,
                    Reorder_Point = ?,
                    Supplier_ID = ?,
                    Admin_ID = ? 
                WHERE Ingredient_ID = ?          
            """;                       
            pst = conn.prepareStatement(query);
            pst.setString(1, ingredient.getIngredientName());
            pst.setInt(2, ingredient.getQuantity());
            pst.setInt(3, ingredient.getReorderPoint());
            pst.setInt(4, ingredient.getSupplier().getSupplierId());
            pst.setInt(5, ingredient.getUserAdmin().getUserId());
            pst.setInt(6, ingredient.getIngredientId());

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during ingredient updation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update ingredient an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean updateQuantity(int id, int newQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
                UPDATE INGREDIENT SET Ingredient_Quantity = ? WHERE Ingredient_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, newQuantity); 
            pst.setInt(2, id);  

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during ingredient updation of quantity.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update ingredient quantity an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean updateReorderPoint(int id, int newReorderPoint) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
                UPDATE INGREDIENT SET Reorder_Point = ? WHERE Ingredient_ID = ?
            """;

            pst = conn.prepareStatement(query);
            pst.setInt(1, newReorderPoint);  
            pst.setInt(2, id);  

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during ingredient updation of reorder point.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update ingredient reorder point an error occured in our end.");
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
            String query = """
               DELETE FROM INGREDIENT WHERE Ingredient_ID = ?
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
            throw new SQLException("Failed to delete ingredient an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    public boolean isIngredientExistsByName(String ingredientName) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
                SELECT TOP 1 Ingredient_Name FROM INGREDIENT_DETAILS WHERE UPPER(Ingredient_Name) = UPPER(?);
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, ingredientName);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to checked ingredient name availability an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean isIngredientExistsById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """  
                SELECT TOP 1 Ingredient_ID FROM INGREDIENT WHERE Ingredient_ID = 2;
            """;
            conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to checked ingredient existance an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        }
    }
}