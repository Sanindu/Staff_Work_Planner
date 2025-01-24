package org.uon.workplanning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private List<Staff> staffList;
    private ConsoleAppLogin consoleAppLogin;

    @BeforeEach
    void setUp() throws Exception {
        // Create mock staff data
        staffList = new ArrayList<>();
        staffList.add(new Staff(1, "admin123", "Admin", "0", "add", "admin", 1.0, "SE", "LM", "Admin"));
        staffList.add(new Staff(2, "user123", "User", "0", "add", "user", 1.0, "SE", "LM", "User"));

        // Serialize mock data to a file for testing loadLoginData
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("staff.ser"))) {
            oos.writeObject(staffList);
        }

        // Create the ConsoleAppLogin instance
        consoleAppLogin = new ConsoleAppLogin(staffList);
    }

    @Test
    void testHandleLoginWithValidAdminCredentials() throws Exception {
        // Simulate user input for valid admin credentials
        String input = "1\nadmin\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the method
        consoleAppLogin.handleLogin();

        // Validate the output
        String expectedOutput = "Enter your Staff ID: Enter your password: Welcome, Admin! Redirecting to Admin Dashboard...";
        assertTrue(outContent.toString().trim().contains(expectedOutput));
    }

    @Test
    void testHandleLoginWithValidUserCredentials() throws Exception {
        // Simulate user input for valid user credentials
        String input = "2\nuser\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the method
        consoleAppLogin.handleLogin();

        // Validate the output
        String expectedOutput = "Enter your Staff ID: Enter your password: Welcome, User! Redirecting to User Dashboard...";
        assertTrue(outContent.toString().trim().contains(expectedOutput));
    }

    @Test
    void testHandleLoginWithInvalidCredentials() throws Exception {
        // Simulate user input for invalid credentials
        String input = "3\nwrongpassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the method
        consoleAppLogin.handleLogin();

        // Validate the output
        String expectedOutput = "Enter your Staff ID: Enter your password: Login Failed: Invalid staff ID or password. Please try again.";
        assertTrue(outContent.toString().trim().contains(expectedOutput));
    }

    @Test
    void testLoadLoginData() {
        // Verify that staffList is loaded correctly
        assertNotNull(ConsoleAppLogin.staffList);
        assertEquals(2, ConsoleAppLogin.staffList.size());

        Staff admin = ConsoleAppLogin.staffList.get(0);
        assertEquals(1, admin.getStaffId());
        assertEquals("admin", admin.getPassword());
        assertEquals("Admin", admin.getRole());

        Staff user = ConsoleAppLogin.staffList.get(1);
        assertEquals(2, user.getStaffId());
        assertEquals("user", user.getPassword());
        assertEquals("User", user.getRole());
    }
}
