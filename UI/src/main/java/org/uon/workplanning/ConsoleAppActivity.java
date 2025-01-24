package org.uon.workplanning;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleAppActivity {
    private List<Activity> activityList;

    // Constructor to initialize the activity list
    public ConsoleAppActivity(List<Activity> activityList) {
        this.activityList = activityList;
    }

    // Method to get the last activity ID in the list
    private int getLastActivityId() {
        return activityList.stream().mapToInt(Activity::getActivityId).max().orElse(0);
    }

    // Method to read the list of activities from the serialized file
    List<Activity> readActivityList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException e) {
            // End of file means the list is empty, so we do nothing
        } catch (FileNotFoundException e) {
            // File not found means the list is empty, so we do nothing
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
        return activityList;
    }

    // Method to handle the creation of a new activity
    void handleCreate() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the type and description of the activity
        System.out.print("Enter the type of activity: ");
        String type = scanner.nextLine();
        System.out.print("Enter the activity description: ");
        String activity = scanner.nextLine();

        // Generate a new activity ID by getting the last activity ID and adding 1
        int newActivityId = getLastActivityId() + 1;

        // Create a new Activity object with the new ID, type, and activity description
        Activity activityObj = new Activity(newActivityId, type, activity);

        // Read the existing list of activities from the serialized file
        List<Activity> activityList = readActivityList();
        activityList.add(activityObj); // Add the new activity to the list

        // Serialize the updated list of activities to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList); // Write the list to the file
            System.out.println("Success: Activity added successfully!"); // Print success message
        } catch (Exception ex) {
            ex.printStackTrace(); // Print any exceptions that occur
        }
    }

    public static void main(String[] args) {
        // Sample data for testing
        List<Activity> activityList = List.of(
                new Activity(1, "TESTACTTYPE123", "TESTACT123")
        );

        // Create an instance of ConsoleAppActivity and handle the creation of a new activity
        ConsoleAppActivity app = new ConsoleAppActivity(activityList);
        app.handleCreate();
    }
}
