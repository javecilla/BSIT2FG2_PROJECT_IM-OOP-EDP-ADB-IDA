
package models;

public class User extends UserInfo {
    private int userId;
    private String username;
    private String password;
    private String userRole;
    
    public User() {
      super();
    }
    
    public User(String username, String password, String userRole) {
        super();
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
    
    public User(int userId, String username, String password, String userRole) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
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
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    
    // Getters and setters for all fields
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
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
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    @Override
    public void display() {
        System.out.println("User ID: " + getUserId());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());
        System.out.println("User Info: \n");
        super.display();
    }
}
