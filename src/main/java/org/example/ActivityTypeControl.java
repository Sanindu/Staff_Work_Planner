package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActivityTypeControl {
    private List<ActivityType> activityTypes;
    private final String filePath;

    public ActivityTypeControl(String filePath) {
        this.filePath = filePath;
        this.activityTypes = loadFromFile();
    }

    public void addActivityType(String name) {
        activityTypes.add(new ActivityType(name));
    }

    public void removeActivityType(String name) {
        activityTypes.removeIf(activityType -> activityType.getName().equalsIgnoreCase(name));
    }

    public ActivityType getActivityType(String name) {
        for (ActivityType activityType : activityTypes) {
            if (activityType.getName().equalsIgnoreCase(name)) {
                return activityType;
            }
        }
        return null;
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(activityTypes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<ActivityType> loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<ActivityType>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void displayActivityTypes() {
        for (ActivityType activityType : activityTypes) {
            System.out.println(activityType);
        }
    }
}
