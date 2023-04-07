package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;
    private Event e2;

    //NOTE:  Lines (1) and (2) don't run in same millisecond for test, so their date is converted to string to compare

    @BeforeEach
    public void runBefore() {
        e = new Event("New entry added");   // (1)
        e2 = new Event("Diff entry added");
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("New entry added", e.getDescription());
        assertEquals(d.toString(), e.getDate().toString());
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(e2));
        assertTrue(e.equals(e));
        assertFalse(e.equals(null));
        assertFalse(e.equals(d));
        assertFalse(e.hashCode() == e2.hashCode());
        assertTrue(e.hashCode() == e.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New entry added", e.toString());
    }
}

