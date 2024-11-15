
package models;

public class UserInfo {
    private int userInfoId;
    private String firstName;
    private String lastName;
    private String barangay;
    private String street;
    private String houseNumber;
    private String region;
    private String province;
    private String municipality;
    
    public UserInfo() {}
    
    public UserInfo(
       int userInfoId,
       String firstName,
       String lastName,
       String barangay,
       String street,
       String houseNumber,
       String region,
       String province,
       String municipality
    ) {
       this.userInfoId = userInfoId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.barangay = barangay;
       this.street = street;
       this.houseNumber = houseNumber;
       this.region = region;
       this.province = province;
       this.municipality = municipality;
    }
    
    // Getters and setters for all fields
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    //Get full name
    public String getFullName() {
        //Jerome Avecilla
        return "" + firstName + " " + lastName;
    }
    
    public String getBarangay() {
        return barangay;
    }
    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
    
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
    
    //Get full address
    public String getFullAddress() {
        //2331, St.Piltover, Brgy. Mapulang Lupa, Pandi, Bulacan.
        return "" + houseNumber + ", St." + street + ", Brgy." + barangay + ", " + municipality + ", " + province + ", " + region + ".";
    }
    
    public void display() {
        System.out.println("UserInfo ID: " + getUserInfoId());
        System.out.println("Full Name: " + getFullName());
        System.out.println("Address: " + getFullAddress());
    }
}
 