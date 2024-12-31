package org.example;

import java.util.Scanner;

public class ActivityTypeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ActivityTypeControl manager = new ActivityTypeControl("activity_types.ser");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Activity Type");
            System.out.println("2. Remove Activity Type");
            System.out.println("3. Add Subtype to Activity Type");
            System.out.println("4. Remove Subtype from Activity Type");
            System.out.println("5. Display All Activity Types");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter new activity type name: ");
                    String activityName = scanner.nextLine();
                    manager.addActivityType(activityName);
                    manager.saveToFile();
                    break;
                case 2:
                    System.out.print("Enter activity type name to remove: ");
                    String removeName = scanner.nextLine();
                    manager.removeActivityType(removeName);
                    manager.saveToFile();
                    break;
                case 3:
                    System.out.print("Enter activity type name: ");
                    String typeName = scanner.nextLine();
                    ActivityType type = manager.getActivityType(typeName);
                    if (type != null) {
                        System.out.print("Enter subtype to add: ");
                        String subtypeToAdd = scanner.nextLine();
                        type.addSubtype(subtypeToAdd);
                        manager.saveToFile();
                    } else {
                        System.out.println("Activity type not found!");
                    }
                    break;
                case 4:
                    System.out.print("Enter activity type name: ");
                    String typeNameToRemove = scanner.nextLine();
                    ActivityType typeToRemove = manager.getActivityType(typeNameToRemove);
                    if (typeToRemove != null) {
                        System.out.print("Enter subtype to remove: ");
                        String subtypeToRemove = scanner.nextLine();
                        typeToRemove.removeSubtype(subtypeToRemove);
                        manager.saveToFile();
                    } else {
                        System.out.println("Activity type not found!");
                    }
                    break;
                case 5:
                    manager.displayActivityTypes();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
