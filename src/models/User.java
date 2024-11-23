package models;
/**
 * Represents the `User` table in the database.
 * Extends the `UserInfo` class to include additional fields specific to user accounts.
 * Includes attributes for user ID, username, password, and role.
 */
public class User extends UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;  // Role of the user (e.g., Admin, Client).
    
    // Constructors
    public User() {
      super();
    }
    
    public User(String username, String password, String userRole) {
        super();
        this.username = username;
        this.password = password;
        this.role = userRole;
    }
    
    public User(int userId, String username, String password, String userRole) {
        super();
        this.id = userId;
        this.username = username;
        this.password = password;
        this.role = userRole;
    }
    
    public User(int userId, String username, String password, UserInfo userInfo) {
        super(
            userInfo.getUserInfoId(),
            userInfo.getFirstName(),
            userInfo.getLastName(),
            userInfo.getBarangay(),
            userInfo.getStreet(),
            userInfo.getHouseNumber(),
            userInfo.getRegion(),
            userInfo.getProvince(),
            userInfo.getMunicipality()
        );
        this.id = userId;
        this.username = username;
        this.password = password;
    }
    
    public User(int userId, String username, String password, String userRole, UserInfo userInfo) {
        super(
            userInfo.getUserInfoId(),
            userInfo.getFirstName(),
            userInfo.getLastName(),
            userInfo.getBarangay(),
            userInfo.getStreet(),
            userInfo.getHouseNumber(),
            userInfo.getRegion(),
            userInfo.getProvince(),
            userInfo.getMunicipality()
        );
        this.id = userId;
        this.username = username;
        this.password = password;
        this.role = userRole;
    }
    
    // Getters and setters for all fields
    public int getUserId() {
        return id;
    }
    
    public void setUserId(int userId) {
        this.id = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserRole() {
        return role;
    }
    
    public void setUserRole(String userRole) {
        this.role = userRole;
    }
    
    @Override
    public String display() {
        return "User ID: " + getUserId() +  "\nUsername: " + getUsername() + "\nPassword: " + getPassword() + "User Info: \n" + super.display();
    }
}