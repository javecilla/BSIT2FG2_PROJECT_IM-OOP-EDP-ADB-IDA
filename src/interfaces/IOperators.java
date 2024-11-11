package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IOperators<T> {
    // Create
    boolean create(T entity) throws SQLException;
    
    // Read
    T getById(String id) throws SQLException;
    List<T> getAll() throws SQLException;
    
    // Update
    boolean update(T entity) throws SQLException;
    
    // Delete
    boolean delete(String id) throws SQLException; 
}
