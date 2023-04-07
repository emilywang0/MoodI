package model;

import java.util.Calendar;
import java.util.Date;



// Represents a journal event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


    // EFFECTS: Creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }


    // EFFECTS: gets the date of this event (includes time)
    public Date getDate() {
        return dateLogged;
    }


    // EFFECTS: gets description of event
    public String getDescription() {
        return description;
    }

    // EFFECTS: checks if two events are the same based on date and description
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: creates hashcode
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: turns date into a string
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

