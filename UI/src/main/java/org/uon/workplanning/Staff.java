package org.uon.workplanning;

import java.io.Serializable;

public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;

    private int staffId;
    private String fullName;
    private String email;
    private String contactNumber;
    private String address;
    private String password;
    private double employmentType;
    private String subjectArea;
    private String lineManager;
    private String role;

    public Staff(String fullName, String email, String contactNumber, String address, String password, double employmentType, String subjectArea, String lineManager, String role) {
        this.staffId = ++idCounter;
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
        this.password = password;
        this.employmentType = employmentType;
        this.subjectArea = subjectArea;
        this.lineManager = lineManager;
        this.role = role;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(double employmentType) {
        this.employmentType = employmentType;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getLineManager() {
        return lineManager;
    }

    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
