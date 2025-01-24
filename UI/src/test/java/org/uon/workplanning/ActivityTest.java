package org.uon.workplanning;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityTest {
    private List<Activity> activityList;
    private ConsoleAppActivity app;

    @BeforeEach
    void setUp() {
        activityList = new ArrayList<>();
        activityList.add(new Activity(1, "TestType", "TestActivity"));
        app = new ConsoleAppActivity(activityList);
    }


    @Test
    void testHandleCreate() {
        // Redirect System.in to simulate user input
        String input = "NewType\nNewActivity\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        app.handleCreate();

        // Verify the new activity was added
        List<Activity> updatedList = app.readActivityList();
        assertEquals("NewType", updatedList.get(1).getType());
        assertEquals("NewActivity", updatedList.get(1).getActivity());
    }

    @Test
    void testReadActivityList() {
        List<Activity> activityList = app.readActivityList();
        assertNotNull(activityList);
    }
}
