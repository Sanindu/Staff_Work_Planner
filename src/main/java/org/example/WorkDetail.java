package org.example;

import java.io.Serializable;

public class WorkDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String activity;
    private String description;
    private String name;
    private int year;
    private String activityDuration;
    private int numberOfInstances;
    private double hours;

    public WorkDetail(String type, String activity, String description, String name, int year,
                      String activityDuration, int numberOfInstances, double hours) {
        this.type = type;
        this.activity = activity;
        this.description = description;
        this.name = name;
        this.year = year;
        this.activityDuration = activityDuration;
        this.numberOfInstances = numberOfInstances;
        this.hours = hours;
    }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getActivityDuration() { return activityDuration; }
    public void setActivityDuration(String activityDuration) { this.activityDuration = activityDuration; }

    public int getNumberOfInstances() { return numberOfInstances; }
    public void setNumberOfInstances(int numberOfInstances) { this.numberOfInstances = numberOfInstances; }

    public double getHours() { return hours; }
    public void setHours(double hours) { this.hours = hours; }

    @Override
    public String toString() {
        return "WorkDetail{" +
                "Type='" + type + '\'' +
                ", Activity='" + activity + '\'' +
                ", Description='" + description + '\'' +
                ", Name='" + name + '\'' +
                ", Year=" + year +
                ", Activity Duration='" + activityDuration + '\'' +
                ", No of Instances=" + numberOfInstances +
                ", Hours=" + hours +
                '}';
    }
}
