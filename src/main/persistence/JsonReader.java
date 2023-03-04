package persistence;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import model.Entry;
import model.JournalModel;
import model.Tag;
import org.json.*;

// Adapted from: Json Serialization Demo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JournalModel read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private JournalModel parseJournal(JSONObject jsonObject) {
        // !!! String name = jsonObject.getString("name");
        JournalModel journal = new JournalModel();
        addEntries(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: parses entries from JSON object and adds them to journal
    private void addEntries(JournalModel journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(journal, nextEntry);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses entry from JSON object and adds it to journal
    private void addEntry(JournalModel journal, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String text = jsonObject.getString("text");
        Tag tag = convertJsonToTag(jsonObject.getJSONObject("tag"));
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        Entry entry = new Entry(title, text, date);
        entry.setTag(tag);
        journal.addEntry(entry);
    }

    // EFFECTS: converts JSONObject back to Tag
    private Tag convertJsonToTag(JSONObject tagJson) {
        String emotion = tagJson.getString("emotion");
        Color color = Color.decode(tagJson.getString("color"));
        Tag tag = new Tag(emotion, color);
        return tag;
    }
}
