package org.uon.workplanning;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleAppActivity {
    private List<Activity> activityList;

    public ConsoleAppActivity(List<Activity> activityList) {
        this.activityList = activityList;
    }

    private int getLastActivityId() {
        return activityList.stream().mapToInt(Activity::getActivityId).max().orElse(0);
    }

    List<Activity> readActivityList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityList;
    }

    void handleCreate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the type of activity: ");
        String type = scanner.nextLine();
        System.out.print("Enter the activity description: ");
        String activity = scanner.nextLine();

        int newActivityId = getLastActivityId() + 1;

        Activity activityObj = new Activity(newActivityId, type, activity);

        List<Activity> activityList = readActivityList();
        activityList.add(activityObj);

        // Serialize the activity list
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList);
            System.out.println("Success: Activity added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Sample data for testing
        List<Activity> activityList = List.of(
                new Activity(1, "TESTACTTYPE123", "TESTACT123")
        );

        ConsoleAppActivity app = new ConsoleAppActivity(activityList);
        app.handleCreate();
    }
}
