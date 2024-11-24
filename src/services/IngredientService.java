package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Ingredient;
import models.Supplier;
import models.Admin;
import models.User;
import config.DBConnection;
import interfaces.IDatabaseOperators;

public class IngredientService implements IDatabaseOperators<Ingredient> { 
    @Override
    public boolean create(Ingredient ingredient) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
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
            pst.setInt(5, ingredient.getAdmin().getAdminId());

            success = pst.executeUpdate() > 0;

            if(success) {
                conn.commit();
            } else {
                conn.rollback();
            }

            return success;
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public Ingredient getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                SELECT TOP 1 *            
                FROM USER_INFO
                INNER JOIN ([USER]
                    INNER JOIN (ADMIN   
                        INNER JOIN (SUPPLIER
                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
                    ) ON [USER].User_ID = ADMIN.Admin_ID
                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
                WHERE INGREDIENT.Ingredient_ID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("Admin_ID"),
                    rs.getString("Admin_Type"),
                    new User(
                        rs.getInt("User_ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("User_Role")
                    )
                );

                Ingredient ingredient = new Ingredient(
                    rs.getInt("Ingredient_ID"),
                    rs.getString("Ingredient_Name"),
                    rs.getInt("Ingredient_Quantity"),
                    rs.getInt("Reorder_Point"),
                    new Supplier(
                        rs.getInt("Supplier_ID"),
                        rs.getString("Supplier_Name"),
                        rs.getString("Supplier_Address"),
                        rs.getString("Contact_Number")
                    )
                );
                
                ingredient.setAdmin(admin);

                return ingredient;
            }

            return null; //no record found
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }

    
    @Override
    public List<Ingredient> getAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                SELECT *            
                FROM USER_INFO
                INNER JOIN ([USER]
                    INNER JOIN (ADMIN   
                        INNER JOIN (SUPPLIER
                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
                    ) ON [USER].User_ID = ADMIN.Admin_ID
                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
                ORDER BY INGREDIENT.Ingredient_ID ASC
            """; 
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("Admin_ID"),
                    rs.getString("Admin_Type"),
                    new User(
                        rs.getInt("User_ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("User_Role")
                    )
                );

                Ingredient ingredient = new Ingredient(
                    rs.getInt("Ingredient_ID"),
                    rs.getString("Ingredient_Name"),
                    rs.getInt("Ingredient_Quantity"),
                    rs.getInt("Reorder_Point"),
                    new Supplier(
                        rs.getInt("Supplier_ID"),
                        rs.getString("Supplier_Name"),
                        rs.getString("Supplier_Address"),
                        rs.getString("Contact_Number")
                    )
                );
                
                ingredient.setAdmin(admin);
                ingredients.add(ingredient);
            }

            return ingredients;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    public List<Ingredient> getAllLowStock() throws SQLException { 
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                SELECT *            
                FROM USER_INFO
                INNER JOIN ([USER]
                    INNER JOIN (ADMIN   
                        INNER JOIN (SUPPLIER
                            INNER JOIN INGREDIENT ON SUPPLIER.Supplier_ID = INGREDIENT.Supplier_ID
                        ) ON ADMIN.Admin_ID = INGREDIENT.Admin_ID
                    ) ON [USER].User_ID = ADMIN.Admin_ID
                ) ON USER_INFO.UserInfo_ID = [USER].UserInfo_ID
                WHERE INGREDIENT.Ingredient_Quantity <= INGREDIENT.Reorder_Point
                ORDER BY INGREDIENT.Ingredient_ID ASC
            """; 
 
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("Admin_ID"),
                    rs.getString("Admin_Type"),
                    new User(
                        rs.getInt("User_ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("User_Role")
                    )
                );

                Ingredient ingredient = new Ingredient(
                    rs.getInt("Ingredient_ID"),
                    rs.getString("Ingredient_Name"),
                    rs.getInt("Ingredient_Quantity"),
                    rs.getInt("Reorder_Point"),
                    new Supplier(
                        rs.getInt("Supplier_ID"),
                        rs.getString("Supplier_Name"),
                        rs.getString("Supplier_Address"),
                        rs.getString("Contact_Number")
                    )
                );
                
                ingredient.setAdmin(admin);
                ingredients.add(ingredient);
            }
            
            return ingredients;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean update(Ingredient ingredient) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBConnection.getConnection();
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
            pst.setInt(5, ingredient.getAdmin().getAdminId());
            pst.setInt(6, ingredient.getIngredientId());

            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    public boolean updateQuantity(int id, int newQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                UPDATE INGREDIENT
                SET Ingredient_Quantity = ?
                WHERE Ingredient_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, newQuantity); 
            pst.setInt(2, id);  

            return pst.executeUpdate() > 0;  
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    public boolean updateReorderPoint(int id, int newReorderPoint) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
                UPDATE INGREDIENT
                SET Reorder_Point = ?
                WHERE Ingredient_ID = ?
            """;

            pst = conn.prepareStatement(query);
            pst.setInt(1, newReorderPoint);  
            pst.setInt(2, id);  

            return pst.executeUpdate() > 0;  
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }


    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBConnection.getConnection();
            String query = """
               DELETE FROM INGREDIENT WHERE Ingredient_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);

            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }

    public boolean isIngredientExistsByName(String ingredientName) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String query = """
                SELECT COUNT(*) FROM INGREDIENT WHERE UPPER(Ingredient_Name) = UPPER(?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, ingredientName);
            rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
}