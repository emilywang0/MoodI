package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    private Tag testTag;
    private Entry testEntry;

    @BeforeEach
    void runBefore() {
        testTag = new Tag("excited!", new Color(223, 128, 255));
        testEntry = new Entry("Good Day", "I got ice cream!", LocalDate.of(2023, 2, 22));
    }

    @Test
    void testConstructor() {
        assertEquals("Good Day", testEntry.getTitle());
        assertEquals("I got ice cream!", testEntry.getText());
        assertEquals(LocalDate.of(2023, 2, 22), testEntry.getDate());
        assertEquals(null, testEntry.getTag());
    }

    @Test
    void testEditTitle() {
        assertEquals("Good Day", testEntry.getTitle());
        testEntry.editTitle("Super great day!");
        assertEquals("Super great day!", testEntry.getTitle());
    }

    @Test
    void testEditDate() {
        assertEquals(LocalDate.of(2023, 2, 22), testEntry.getDate());
        testEntry.editDate(LocalDate.of(2023, 4, 16));
        assertEquals(LocalDate.of(2023, 4, 16), testEntry.getDate());
    }

    @Test
    void testEditText() {
        assertEquals("I got ice cream!", testEntry.getText());
        testEntry.editText("I got a popsicle!");
        assertEquals("I got a popsicle!", testEntry.getText());
    }

    @Test
    void testSetTag() {
        assertEquals(null, testEntry.getTag());
        assertFalse(testEntry.doesTagExist());
        testEntry.setTag(testTag);
        assertEquals(testTag, testEntry.getTag());
        assertTrue(testEntry.doesTagExist());
    }

}
