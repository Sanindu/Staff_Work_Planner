package org.uon.workplanning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class WorkDetailsControllerTest {
    private Work work;

    @BeforeEach
    void setUp() {
        work = new Work(1, 101, "Teaching", "Lecture", "Delivering lectures", "Trimester 1", 2, 3, 6, 6, 0, 0, 0, 6.0);
    }

    @Test
    void testGetWorkId() {
        assertEquals(1, work.getWorkId());
    }

    @Test
    void testSetWorkId() {
        work.setWorkId(2);
        assertEquals(2, work.getWorkId());
    }

    @Test
    void testGetStaffId() {
        assertEquals(101, work.getStaffId());
    }

    @Test
    void testSetStaffId() {
        work.setStaffId(102);
        assertEquals(102, work.getStaffId());
    }

    @Test
    void testGetType() {
        assertEquals("Teaching", work.getType());
    }

    @Test
    void testSetType() {
        work.setType("Research");
        assertEquals("Research", work.getType());
    }

    @Test
    void testGetActivity() {
        assertEquals("Lecture", work.getActivity());
    }

    @Test
    void testSetActivity() {
        work.setActivity("Seminar");
        assertEquals("Seminar", work.getActivity());
    }

    @Test
    void testGetDescription() {
        assertEquals("Delivering lectures", work.getDescription());
    }

    @Test
    void testSetDescription() {
        work.setDescription("Conducting workshops");
        assertEquals("Conducting workshops", work.getDescription());
    }

    @Test
    void testGetWeek() {
        assertEquals("Trimester 1", work.getWeek());
    }

    @Test
    void testSetWeek() {
        work.setWeek("Trimester 2");
        assertEquals("Trimester 2", work.getWeek());
    }

    @Test
    void testGetDuration() {
        assertEquals(2, work.getDuration());
    }

    @Test
    void testSetDuration() {
        work.setDuration(4);
        assertEquals(4, work.getDuration());
    }

    @Test
    void testGetInstances() {
        assertEquals(3, work.getInstances());
    }

    @Test
    void testSetInstances() {
        work.setInstances(5);
        assertEquals(5, work.getInstances());
    }

    @Test
    void testGetHours() {
        assertEquals(6, work.getHours());
    }

    @Test
    void testSetHours() {
        work.setHours(10);
        assertEquals(10, work.getHours());
    }

    @Test
    void testSetTypeValueAndGetTypeValue() {
        work.setTypeValue("Teaching", "20");
        assertEquals("20", work.getTypeValue("Teaching"));
    }

    @Test
    void testReadObject() {
        assertNotNull(work.getTypeValue("Teaching"));
    }

    @Test
    void testSetAndGetT1() {
        work.setT1(10);
        assertEquals(10, work.getT1());
    }

    @Test
    void testSetAndGetSum() {
        work.setSum(100.5);
        assertEquals(100.5, work.getSum());
    }
    @Test
    public void testHoursCalculation() {
        Work work = new Work();
        work.setDuration(3);
        work.setInstances(5);

        int expectedHours = work.getDuration() * work.getInstances();
        assertEquals(15, expectedHours);
    }
    @Test
    public void testDeserialisationInitialisation() throws IOException, ClassNotFoundException {
        Work originalWork = new Work();
        originalWork.setTypeValue("Research", "25");

        // Serialize object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(originalWork);

        // Deserialize object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        Work deserializedWork = (Work) objIn.readObject();

        assertEquals("25", deserializedWork.getTypeValue("Research"));
        assertNull(deserializedWork.getTypeValue("Admin"));
    }


}
