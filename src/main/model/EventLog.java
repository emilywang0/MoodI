package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// Represents a log of journal events, uses Singleton Design Pattern
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;


    // EFFECTS: private constructor of EventLog prevents external construction
    private EventLog() {
        events = new ArrayList<Event>();
    }


    // EFFECTS: gets instance of EventLog - creates it if it doesn't already exist
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }


    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: iterates through events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

