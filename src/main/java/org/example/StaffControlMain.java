package org.example;

public class StaffControlMain {
    public static void main(String[] args) {
        StaffManager manager = StaffManager.getInstance(); // Assuming user data is already serialized for the demo. Otherwise, you need to serialize user data beforehand.
        manager.loginUser();
        if (manager.loggedInUser != null) {
            manager.addStaffMember(); // Uncomment the next line to load and display the saved staff member
            System.out.println("------------------SAVED DATA-----------------------");
            manager.loadStaffMember();
        }
    }
}
