package persistence;

import model.Entry;
import model.JournalModel;
import model.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            JournalModel journal = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            JournalModel journal = reader.read();
            assertEquals(0, journal.getEntryList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            JournalModel journal = reader.read();
            List<Entry> entries = journal.getEntryList();
            assertEquals(2, entries.size());
            checkEntry("Sad", "It was sad", LocalDate.of(2023, 2, 23), Tag.sadTag(),
                    entries.get(0));
            checkEntry("Good", "It was good", LocalDate.of(2023, 2, 22), Tag.happyTag(),
                    entries.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
