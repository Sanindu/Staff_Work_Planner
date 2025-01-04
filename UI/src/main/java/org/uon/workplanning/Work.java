package org.uon.workplanning;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    private int workId;
    private int staffId;
    private String type;
    private String activity;
    private String description;
    private String week;
    private int duration;
    private int instances;
    private int hours;



    public Work(int workId, int staffId, String type, String activity, String description, String week, int duration, int instances, int hours) {
        this.workId = workId;
        this.staffId = staffId;
        this.type = type;
        this.activity = activity;
        this.description = description;
        this.week = week;
        this.duration = duration;
        this.instances = instances;
        this.hours = hours;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
/*
    private Map<String, String> typeValues = new HashMap<>();
    public String getTypeValue(String type) { return typeValues.get(type); }
    public void setTypeValue(String type, String value) { typeValues.put(type, value); }

 */
}
