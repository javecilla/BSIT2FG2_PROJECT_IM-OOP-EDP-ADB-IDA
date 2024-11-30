package models;
/**
 * Represents the `Admin` table in the database.
 * The Admin class extends User to represent administrative users, adding attributes like type to 
 * distinguish different types of administrators (e.g., super admin, manager). By doing so, we encapsulate 
 * admin-specific responsibilities while leveraging the shared functionality of the User class.
 */
public class Admin extends User {
    private int id;
    private String type;        // Type of admin (e.g., super admin, manager).
    ///private User user;          // Associated user account details.
    //private UserInfo userInfo;  // Detailed user information for the admin.
    
    public Admin() {
        super();
    }
    
    public Admin(int id, String type) {
        super();
        this.id = id;
        this.type = type;
    }
    
    public Admin(int id, String type, User user) {
        super(
            user.getUserId(), 
            user.getUsername(),
            user.getPassword(),
            user.getUserRole(),
            user.getEmail(),
            user.getContactNumber()
        );
        this.id = id;
        this.type = type;
    }
    
    public int getAdminId() {
        return id;
    }
    
    public void setAdminId(int id) {
        this.id = id;
    }
    
    public String getAdminType() {
        return type;
    }
    
    public void setAdminType(String type) {
        this.type = type;
    }
//    
//    public UserInfo getUserInfo() {
//        return userInfo;
//    }
//     
//     public void setUserInfo(UserInfo userInfo) {
//        this.userInfo = userInfo;
//    }
    
    @Override
    public String display() {
        return "Admin ID: " + getAdminId() + "\nAdmin Type: " + getAdminType() + "\n" + "Admin Info: \n\n" + super.display();
    }   
}