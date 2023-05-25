package ithub.com.blogposting.Models;

public class RegistrationModel {

    String id;
    String name;
    String email;
    String mobileno;
    String password;

    public RegistrationModel() {
    }

    public RegistrationModel(String id, String name, String email, String mobileno, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
