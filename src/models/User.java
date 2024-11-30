package models;
/**
 * Represents the `User` table in the database.
 * Building on UserInfo, the User class extends it by adding account-specific attributes such as username, 
 * password, email, and role. This design allows us to differentiate between users at the account level 
 * while inheriting general user details. User serves as a bridge for further specialization into distinct user 
 * roles.
 */
public class User extends UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;  // Role of the user (e.g., Admin, Client).
    private String email;
    private String contactNumber;
    
    // Constructors
    public User() {
      super();
    }
    
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, String userRole, String email, String contactNumber) {
        super();
        this.username = username;
        this.password = password;
        this.role = userRole;
        this.email = email;
        this.contactNumber = contactNumber;
    }
    
    public User(int userId, String username, String password, String userRole, String email, String contactNumber) {
        super();
        this.id = userId;
        this.username = username;
        this.password = password;
        this.role = userRole;
        this.email = email;
        this.contactNumber = contactNumber;
    }
    
    public User(int userId, String username, String password, String email, String contactNumber, UserInfo userInfo) {
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
        this.email = email;
        this.contactNumber = contactNumber;
    }
    
    public User(int userId, String username, String password, String userRole, String email, String contactNumber, UserInfo userInfo) {
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
        this.email = email;
        this.contactNumber = contactNumber;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    @Override
    public String display() {
        return "User ID: " + getUserId() +  "\nUsername: " + getUsername() + "\nPassword: " + getPassword() + "User Info: \n" + super.display();
    }
}