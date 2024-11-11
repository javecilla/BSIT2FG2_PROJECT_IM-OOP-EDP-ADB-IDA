package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IDatabaseOperators<T> {
    // Create
    boolean create(T entity) throws SQLException;
    
    // Read
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    
    // Update
    boolean update(T entity) throws SQLException;
    
    // Delete
    boolean delete(int id) throws SQLException; 
}
