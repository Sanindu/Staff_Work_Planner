package org.example;

import java.io.Serializable;

class StaffMember implements Serializable {
    private String name;
    private String id;
    private double employmentType; // 1.0 for full-time, 0.5 for part-time
    private String subjectArea;
    private String lineManager;

    // Constructor
    public StaffMember(String name, String id, double employmentType, String subjectArea, String lineManager) {
        this.name = name;
        this.id = id;
        this.employmentType = employmentType;
        this.subjectArea = subjectArea;
        this.lineManager = lineManager;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getEmploymentType() {
        return employmentType;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public String getLineManager() {
        return lineManager;
    }

    // Display method
    public void displayInfo() {
        System.out.println("Staff Member Details:");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Employment Type: " + (employmentType == 1.0 ? "Full-time" : "Part-time"));
        System.out.println("Subject Area: " + subjectArea);
        System.out.println("Line Manager: " + lineManager);
    }
}


