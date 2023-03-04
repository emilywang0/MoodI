package persistence;

import model.Entry;
import model.JournalModel;
import model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    private Entry testEntry1;
    private Entry testEntry2;


    @Test
    void testWriterInvalidFile() {
        try {
            JournalModel journal = new JournalModel();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            JournalModel journal = new JournalModel();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals(0, journal.getEntryList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            JournalModel journal = new JournalModel();
            testEntry1 = new Entry("Good", "It was good", LocalDate.of(2023, 2, 22));
            testEntry2 = new Entry("Sad", "It was sad", LocalDate.of(2023, 2, 23));
            Tag happy = Tag.happyTag();
            Tag sad = Tag.sadTag();
            testEntry1.setTag(happy);
            testEntry2.setTag(sad);
            journal.addEntry(testEntry1);
            journal.addEntry(testEntry2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            journal = reader.read();
            List<Entry> entries = journal.getEntryList();
            assertEquals(2, entries.size());
            checkEntry("Good", "It was good", LocalDate.of(2023, 2, 22), happy,
                    entries.get(0));
            checkEntry("Sad", "It was sad", LocalDate.of(2023, 2, 23), sad,
                    entries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
