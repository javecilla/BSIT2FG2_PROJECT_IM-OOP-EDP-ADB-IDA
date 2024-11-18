
package services;

import models.Category;
import config.DBConnection;
import interfaces.IDatabaseOperators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements IDatabaseOperators<Category> {
    @Override
    public boolean create(Category category) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = "INSERT INTO CATEGORY (Category_Name) VALUES (?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getCategoryName());
        
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

    @Override
    public Category getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM CATEGORY WHERE Category_ID = ?";
 
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return new Category(
                    rs.getInt("Category_ID"), 
                    rs.getString("Category_Name")
                );
            }
            return null;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM CATEGORY";
            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("Category_ID"),
                    rs.getString("Category_Name")
                );

                
                categories.add(category);
            }
            return categories;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }

    @Override
    public boolean update(Category category) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "UPDATE CATEGORY SET Category_Name = ? WHERE Category_ID = ? ";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getCategoryId());
            
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
            String query = "DELETE FROM CATEGORY WHERE Category_ID = ? ";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    public boolean isCategoryExists(String categoryName) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT COUNT(*) FROM CATEGORY WHERE UPPER(Category_Name) = UPPER(?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, categoryName);
            rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
}
