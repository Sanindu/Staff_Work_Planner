package org.example;

import java.io.*;
import java.util.List;
import java.util.Scanner;

class StaffManager {
    private static StaffManager instance = null;
    User loggedInUser = null;

    private StaffManager() {
    }

    public static StaffManager getInstance() {
        if (instance == null) {
            instance = new StaffManager();
        }
        return instance;
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        User user = validateUser(username, password);
        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private User validateUser(String username, String password) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser"))) {
            List<User> users = (List<User>) ois.readObject();

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addStaffMember() {
        if (loggedInUser == null || !"admin".equals(loggedInUser.getRole())) {
            System.out.println("You do not have permission to add new staff members.");
            return;
        }
        String id = generateNewID();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Employment Type (1.0 for Full-time, 0.5 for Part-time): ");
        double employmentType = scanner.nextDouble();
        scanner.nextLine(); // Consume the remaining newline
        System.out.print("Enter Subject Area: ");
        String subjectArea = scanner.nextLine();
        System.out.print("Enter Line Manager: ");
        String lineManager = scanner.nextLine();
        StaffMember newMember = new StaffMember(name, id, employmentType, subjectArea, lineManager);
        //newMember.displayInfo();
        saveStaffMember(newMember);
    }

    private String generateNewID() {
        StaffMember lastMember = getLastSavedStaffMember();
        if (lastMember != null) {
            String lastID = lastMember.getId();
            int lastNumber = Integer.parseInt(lastID.replaceAll("[^0-9]", ""));
            return "ID" + (lastNumber + 1);
        } else {
            return "ID1"; // Default ID if no previous members exist
        }
    }

    private StaffMember getLastSavedStaffMember() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff_member.ser"))) {
            return (StaffMember) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous staff member data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveStaffMember(StaffMember member) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("staff_member.ser"))) {
            oos.writeObject(member);
            System.out.println("Staff member details have been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStaffMember() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff_member.ser"))) {
            StaffMember member = (StaffMember) ois.readObject();
            member.displayInfo();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}