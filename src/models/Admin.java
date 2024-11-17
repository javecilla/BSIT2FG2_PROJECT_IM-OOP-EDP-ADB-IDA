package models;

import javax.swing.JOptionPane;

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
    public String display() {
        /*System.out.println("Admin ID: " + getAdminId());
        System.out.println("Admin Type: " + getAdminType());
        System.out.println("Admin Info: \n");*/
        /*        JOptionPane.showMessageDialog(null, "Admin ID: " + getAdminId() +
        "\nAdmin Type: " + getAdminType() + "\n"
        + "Admin Info: " + super.display(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);*/
        
        return "Admin ID: " + getAdminId() + "\nAdmin Type: " + getAdminType() + "\n" + "Admin Info: \n\n" + super.display();
    }   
}