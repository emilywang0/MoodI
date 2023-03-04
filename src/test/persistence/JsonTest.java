package persistence;

import java.time.LocalDate;

import model.Entry;
import model.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkEntry(String title, String text, LocalDate date, Tag tag, Entry entry) {
        assertEquals(title, entry.getTitle());
        assertEquals(text, entry.getText());
        assertEquals(date, entry.getDate());
        assertEquals(tag.getEmotion(), entry.getTag().getEmotion());
        assertEquals(tag.getColor(), entry.getTag().getColor());
    }
}
