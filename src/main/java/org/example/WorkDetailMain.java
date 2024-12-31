package org.example;

import java.util.List;
import java.util.Scanner;

public class WorkDetailMain {
    public static void main(String[] args) {
        // File paths
        String activityTypesFilePath = "activity_types.ser";
        String workDetailsFilePath = "work_details.ser";

        // Managers
        WorkDetailControl workDetailsManager = new WorkDetailControl(workDetailsFilePath);
        ActivityTypeControl activityTypeControl = new ActivityTypeControl(activityTypesFilePath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Work Detail");
            System.out.println("2. Remove Work Detail");
            System.out.println("3. Display Work Details");
            System.out.println("4. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Ask for the work details (type, activity, etc.)
                    System.out.print("Enter Type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter Activity: ");
                    String activity = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Activity Duration: ");
                    String duration = scanner.nextLine();
                    System.out.print("Enter Number of Instances: ");
                    int instances = scanner.nextInt();
                    System.out.print("Enter Hours: ");
                    double hours = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    // Collect subtype values
                   // activityTypeControl.collectSubtypeValuesForWorkDetails();
                    // Now save the work detail (you can process the subtype values as needed here)
                    WorkDetail workDetail = new WorkDetail(type, activity, description, name, year, duration, instances, hours);
                    workDetailsManager.addWorkDetail(workDetail);
                    break;

                case 2:
                    // Handle removing a work detail
                    System.out.print("Enter index to remove: ");
                    int index = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    workDetailsManager.removeWorkDetail(index - 1);
                    break;

                case 3:
                    // Display the work details
                    workDetailsManager.displayWorkDetails();
                    break;

                case 4:
                    // Save and exit
                    workDetailsManager.saveToFile();
                    activityTypeControl.saveToFile();
                    System.out.println("Data saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
