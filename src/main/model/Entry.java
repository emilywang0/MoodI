package model;

import java.time.LocalDate;


// Represents a journal entry with a title, tag, date, and text
public class Entry {
    private String title;
    private String text;
    private LocalDate date;
    private Tag tag;


    // EFFECTS: entry has given title, body text, date, and no tag set
    public Entry(String title, String text, LocalDate date) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.tag = null;
    }

    // MODIFIES: this
    // EFFECTS: changes title to new given title
    public void editTitle(String newTitle) {
        this.title = newTitle;
    }

    // MODIFIES: this
    // EFFECTS: changes text to new given text
    public void editText(String newText) {
        this.text = newText;
    }

    // REQUIRES: new date given is not a date already assigned to another entry in entryList (CalendarModel)
    // MODIFIES: this
    // EFFECTS: changes date to new given date
    public void editDate(LocalDate newDate) {
        this.date = newDate;
    }

    // REQUIRES: given tag cannot be null
    // MODIFIES: this
    // EFFECTS: sets the entry's tag as the given tag
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    // EFFECTS: returns title
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns body text
    public String getText() {
        return text;
    }

    // EFFECTS: returns date
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns tag
    public Tag getTag() {
        return tag;
    }

    // EFFECTS: returns true if Tag is not null
    public boolean doesTagExist() {
        return this.tag != null;
    }

}