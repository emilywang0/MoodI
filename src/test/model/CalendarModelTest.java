package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarModelTest {
    private CalendarModel testEntryList;
    private Entry testEntry1;
    private Entry testEntry2;
    private Entry testEntry3;

    @BeforeEach
    void runBefore() {
        testEntryList = new CalendarModel();
        testEntry1 = new Entry("Good Day", "I got ice cream", LocalDate.of(2023, 2, 22));
        testEntry2 = new Entry("Ok Day", "It was ok", LocalDate.of(2023, 2, 23));
        testEntry3 = new Entry("Crazy Day", "!!!", LocalDate.of(2023, 2, 24));
    }

    @Test
    void testConstructor() {
        assertEquals(0, testEntryList.length());
    }

    @Test
    void testAddOneEntryToEmpty() {
        assertTrue(testEntryList.addEntry(testEntry1));
        assertEquals(1, testEntryList.length());
        assertTrue(testEntryList.contains(testEntry1));
    }

    @Test
    void testAddMultipleEntry() {
        assertTrue(testEntryList.addEntry(testEntry1));
        assertTrue(testEntryList.addEntry(testEntry2));
        assertTrue(testEntryList.addEntry(testEntry3));
        assertEquals(3, testEntryList.length());
        assertTrue(testEntryList.contains(testEntry1));
        assertTrue(testEntryList.contains(testEntry2));
        assertTrue(testEntryList.contains(testEntry3));
    }

    @Test
    void testFailAddRepeatEntry() {
        assertTrue(testEntryList.addEntry(testEntry1));
        assertFalse(testEntryList.addEntry(testEntry1));
        assertEquals(1, testEntryList.length());
    }

    @Test
    void testFailAddSameDateOnlyEntry() {
        Entry sameDateEntry = new Entry("Wow!", "Nice!", LocalDate.of(2023, 2, 22));
        assertTrue(testEntryList.addEntry(testEntry1));
        assertFalse(testEntryList.addEntry(sameDateEntry));
        assertEquals(1, testEntryList.length());
        assertTrue(testEntryList.contains(testEntry1));
        assertFalse(testEntryList.contains(sameDateEntry));
    }

    @Test
    void testDeleteEntry() {
        assertTrue(testEntryList.addEntry(testEntry1));
        assertTrue(testEntryList.addEntry(testEntry2));
        assertTrue(testEntryList.addEntry(testEntry3));
        assertEquals(3, testEntryList.length());

        // delete one entry
        testEntryList.deleteEntry(LocalDate.of(2023, 2, 22));
        assertEquals(2, testEntryList.length());
        assertFalse(testEntryList.contains(testEntry1));

        // attempt to delete an entry but given date does not correspond to any entry
        testEntryList.deleteEntry(LocalDate.of(2023, 3, 22));
        assertEquals(2, testEntryList.length());

        // delete multiple entries until empty
        testEntryList.deleteEntry(LocalDate.of(2023, 2, 23));
        testEntryList.deleteEntry(LocalDate.of(2023, 2, 24));
        assertEquals(0, testEntryList.length());
    }

    @Test
    void testGetEntry() {
        assertTrue(testEntryList.addEntry(testEntry1));
        assertTrue(testEntryList.addEntry(testEntry2));
        assertTrue(testEntryList.addEntry(testEntry3));
        assertEquals(3, testEntryList.length());

        // get all entries
        assertEquals(testEntry1, testEntryList.getEntry(LocalDate.of(2023, 2, 22)));
        assertEquals(testEntry2, testEntryList.getEntry(LocalDate.of(2023, 2, 23)));
        assertEquals(testEntry3, testEntryList.getEntry(LocalDate.of(2023, 2, 24)));

        // no entry with given date
        assertEquals(null, testEntryList.getEntry(LocalDate.of(2024, 2, 22)));

        assertEquals(3, testEntryList.length());

    }


}
