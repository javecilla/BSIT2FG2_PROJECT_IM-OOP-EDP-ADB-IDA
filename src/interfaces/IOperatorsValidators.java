package interfaces;

import helpers.Response;

public interface IOperatorsValidators<T> {
    // Validation for Create operation
    Response<T> validateCreate(T entity);
    
    // Validation for Update operation
    Response<T> validateUpdate(T entity);
    
    // Validation for Delete operation
    Response<Boolean> validateDelete(int id);
}
