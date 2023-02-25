package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Represents a journal model that holds all entries in a list
public class JournalModel {
    private List<Entry> entryList;

    // EFFECTS: constructs empty entry list
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

}
