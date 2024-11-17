
import models.User;

public class Admin extends User {
    private int id;
    private String type;
    
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
            user.getUserRole()
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
    
    public void setAdminStatus(String type) {
        this.type = type;
    }
    
    @Override
    public void display() {
        System.out.println("Admin ID: " + getAdminId());
        System.out.println("Admin Type: " + getAdminType());
        System.out.println("Admin Info: \n");
        super.display();
    }   
}
