package org.uon.workplanning;

import java.io.Serializable;

public class Activity  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int activityId;
    private String type;
    private String activity;

    public Activity(int activityId, String type,String activity){
        this.activityId = activityId;
        this.type = type;
        this.activity = activity;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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
}


