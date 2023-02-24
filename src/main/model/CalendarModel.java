package model;

import java.time.LocalDate;
import java.util.ArrayList;

// Represents a calendar model that holds all entries in a list
public class CalendarModel {
    private ArrayList<Entry> entryList;

    // EFFECTS: constructs empty entry list
    public CalendarModel() {
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
    // EFFECTS: deletes the entry from entryList with the given date
    public void deleteEntry(LocalDate date) {
        for (Entry e : entryList) {
            if (e.getDate().compareTo(date) == 0) {
                entryList.remove(e);
                return;
            }
        }
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


}
