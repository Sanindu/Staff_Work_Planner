package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        UserManager userManager = new UserManager();
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter new user's username: ");
//        String username = scanner.nextLine();
//        System.out.print("Enter new user's password: ");
//        String password = scanner.nextLine();
//        System.out.print("Enter new user's role (admin/user): ");
//        String role = scanner.nextLine();
//        userManager.addUser(username, password, role); // Display all users
//        userManager.displayUsers();


        StaffManager manager = StaffManager.getInstance(); // Assuming user data is already serialized for the demo. Otherwise, you need to serialize user data beforehand.
        manager.loginUser();
        if (manager.loggedInUser != null) {
            manager.addStaffMember(); // Uncomment the next line to load and display the saved staff member
            System.out.println("------------------SAVED DATA-----------------------");
            manager.loadStaffMember();
        }
    }
}
