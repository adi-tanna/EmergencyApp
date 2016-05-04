package org.tanna.inhouse.EmergencyApp.Model;

public class User {
    private long id;
    private String fname;
    private String lname;
    private long contactNo;
    private String password;

    public User() {
    }

    public User(long id, String fname, long contactNo, String lname, String password) {
        this.id = id;
        this.fname = fname;
        this.contactNo = contactNo;
        this.lname = lname;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}