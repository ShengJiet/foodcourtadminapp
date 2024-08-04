package my.edu.utar.foodcourtadmin;

public class MainModelProfile {
    private String Name;
    private String Role;
    private String Email;
    private String ContactNo;
    private String Age;
    private String Gender;
    private String Nationality;
    private String Races;
    private String Address;

    // Default constructor required for Firebase
    public MainModelProfile() {
    }

    public MainModelProfile(String name, String role, String email, String contactNo, String age, String gender, String nationality, String races, String address) {
        Name = name;
        Role = role;
        Email = email;
        ContactNo = contactNo;
        Age = age;
        Gender = gender;
        Nationality = nationality;
        Races = races;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getRaces() {
        return Races;
    }

    public void setRaces(String races) {
        Races = races;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}