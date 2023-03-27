package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Represents a journal model that holds all entries in a list
public class JournalModel implements Writable {
    private List<Entry> entryList;

    // EFFECTS: constructs a journal model with an empty entry list
    public JournalModel() {
        entryList = new ArrayList<>();
    }


    // MODIFIES: this
    // EFFECTS: adds entry to list and returns true if not already another entry with same date, otherwise return false
    public boolean addEntry(Entry newEntry) {
        for (Entry e : entryList) {
            if (e.getDate().compareTo(newEntry.getDate()) == 0) {
                return false;
            }
        }
        entryList.add(newEntry);
        return true;
    }


    // MODIFIES: this
    // EFFECTS: if an entry with the given date is found, delete entry and return true; otherwise, return false
    public boolean deleteEntry(LocalDate date) {
        for (Entry e : entryList) {
            if (e.getDate().compareTo(date) == 0) {
                entryList.remove(e);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns the entry in entryList with the given date, otherwise return null
    public Entry getEntry(LocalDate date) {
        for (Entry e : entryList) {
            if (e.getDate().compareTo(date) == 0) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: returns length of entryList
    public int length() {
        return entryList.size();
    }

    // EFFECTS: returns true if entry is found in entryList
    public boolean contains(Entry entry) {
        return entryList.contains(entry);
    }

    // EFFECTS: returns entryList
    public List<Entry> getEntryList() {
        return this.entryList;
    }

    // EFFECTS: returns journal entries as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entry e : entryList) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

}


