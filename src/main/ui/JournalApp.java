package ui;

import model.JournalModel;
import model.Entry;
import model.Tag;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Journal application
public class JournalApp {
    private JournalModel journal;
    private Scanner input;

    // EFFECTS: constructs journal app
    public JournalApp() {

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runJournal() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("x")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nCome back tomorrow! :)");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("v")) {
            doViewAll();
        } else if (command.equals("o")) {
            doOpenDetails();
        } else {
            System.out.println("Sorry, please try another command! :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        journal = new JournalModel();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to MoodI! Select from:");
        System.out.println("\ta -> Add new journal entry");
        System.out.println("\td -> Delete journal entry");
        System.out.println("\tv -> View all journal entries");
        System.out.println("\to -> Read a past journal entry");
        System.out.println("\tx -> Close journal");
    }

    // MODIFIES: this
    // EFFECTS: adds a new journal entry
    private void doAdd() {
        System.out.println("Enter valid date like so (include dashes): YYYY-MM-DD");
        String dateString = input.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Not a valid date, try again.");
            return;
        }
        System.out.println("Create a title for your journal entry:");
        String title = input.nextLine();
        System.out.println("Let your thoughts flow freely and just write:");
        String text = input.nextLine();
        Entry newEntry = new Entry(title, text, date);
        System.out.println("Now, select an emotion tag of the day:");
        System.out.println("\t1 -> happy");
        System.out.println("\t2 -> sad");
        System.out.println("\t3 -> angry");
        selectTag(newEntry);

        this.journal.addEntry(newEntry);
        System.out.println("Successfully added journal entry. Glad you took some time to reflect on today!");
    }

    // EFFECTS: prompts user to select a tag for an entry
    private void selectTag(Entry newEntry) {
        boolean keepGoing = true;
        while (keepGoing) {
            String command = input.nextLine();
            keepGoing = false;
            if (command.equals("1")) {
                newEntry.setTag(Tag.happyTag());
            } else if (command.equals("2")) {
                newEntry.setTag(Tag.sadTag());
            } else if (command.equals("3")) {
                newEntry.setTag(Tag.angryTag());
            } else {
                System.out.println("Tag does not exist, try again.");
                keepGoing = true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes an existing entry
    private void doDelete() {
        System.out.println("Enter the date (YYYY-MM-DD) of the journal entry that you would like to delete:");
        String dateString = input.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Not a valid date, try again.");
            return;
        }
        if (journal.deleteEntry(date)) {
            System.out.println("Successfully deleted entry.");
        } else {
            System.out.println("Could not find entry with given date.");
        }
    }

    // EFFECTS: displays all existing entries
    private void doViewAll() {
        for (Entry e: this.journal.getEntryList()) {

            String date = e.getDate().toString();
            String title = e.getTitle();
            String emotion = e.getTag().getEmotion();
            System.out.println(date + " " + title + " " + emotion);
        }
    }

    // EFFECTS: displays text of selected entry along with date, title and tag
    private void doOpenDetails() {
        System.out.println("Enter the date (YYYY-MM-DD) of the journal entry that you would like to read:");
        String dateString = input.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Not a valid date, try again.");
            return;
        }
        if (journal.getEntry(date) != null) {
            Entry e = journal.getEntry(date);
            String entryDate = e.getDate().toString();
            String title = e.getTitle();
            String text = e.getText();
            String emotion = e.getTag().getEmotion();
            System.out.println("Date: " + entryDate);
            System.out.println(title);
            System.out.println(text);
            System.out.println("Tag: " + emotion);
        } else {
            System.out.println("Could not find entry with given date.");
        }
    }

}
