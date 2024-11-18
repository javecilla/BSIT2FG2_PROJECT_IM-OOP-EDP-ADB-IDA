package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Recipe;
import models.Food;
import models.Ingredient;
import config.DBConnection;
import interfaces.IDatabaseOperators;
import models.Admin;
import models.Category;
import models.Supplier;
import models.User;
import models.UserInfo;

public class RecipeService implements IDatabaseOperators<Recipe> {
    @Override
    public List<Recipe> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public boolean create(Recipe entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Recipe getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean update(Recipe entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
