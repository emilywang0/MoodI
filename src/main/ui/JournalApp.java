package ui;

import model.JournalModel;
import model.Entry;
import model.Tag;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Journal application
public class JournalApp {
    private static final String JSON_STORE = "./data/journal.json";

    private JournalModel journalModel;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean changed = false;
    private JournalFrame journalFrame;

    // EFFECTS: constructs journal app and runs application
    public JournalApp() throws FileNotFoundException {
        journalFrame = new JournalFrame(this);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runJournal();
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
                if (changed) {
                    System.out.println("You've made changes to your journal. Save those changes? (y/n)");
                    String confirmCommand = input.nextLine();
                    confirmCommand = confirmCommand.toLowerCase();
                    if (confirmCommand.equals("y")) {
                        saveJournal();
                        changed = false;
                    }
                }
                keepGoing = false;
            } else {
                processCommand(command, input);
            }
        }
        System.out.println("\nCome back tomorrow! :)");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command, Scanner input) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("v")) {
            doViewAll();
        } else if (command.equals("o")) {
            doOpenDetails();
        } else if (command.equals("s")) {
            saveJournal();
        } else if (command.equals("l")) {
            loadJournal(input);
        } else if (command.equals("e")) {
            System.out.println("WARNING: Are you sure you want to permanently delete this file? (y/n)");
            String confirmCommand = input.nextLine();
            confirmCommand = confirmCommand.toLowerCase();
            if (confirmCommand.equals("y")) {
                eraseJournal();
                changed = false;
            }
        } else {
            System.out.println("Sorry, please try another command! :)");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        journalModel = new JournalModel();
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
        System.out.println("\ts -> Save journal to file");
        System.out.println("\tl -> Load journal from file");
        System.out.println("\te -> Erase journal from file");
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

        this.journalModel.addEntry(newEntry);
        changed = true;
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
        if (journalModel.deleteEntry(date)) {
            System.out.println("Successfully deleted entry.");
            changed = true;
        } else {
            System.out.println("Could not find entry with given date.");
        }
    }

    // EFFECTS: displays all existing entries
    private void doViewAll() {
        for (Entry e : this.journalModel.getEntryList()) {

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
        if (journalModel.getEntry(date) != null) {
            Entry e = journalModel.getEntry(date);
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

    // EFFECTS: saves the journal to file
    public boolean saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journalModel);
            jsonWriter.close();
            changed = false;
            System.out.println("Saved to " + JSON_STORE);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file
    public void loadJournal(Scanner input) {
        try {
            if (changed) {
                System.out.println("Save current changes before loading journal? (y/n)");
                String confirmCommand = input.nextLine();
                confirmCommand = confirmCommand.toLowerCase();
                if (confirmCommand.equals("y")) {
                    saveJournal();
                    changed = false;
                }
            }
            journalModel = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file, adapted to work for JournalFrame
    public boolean loadJournalFrame() {
        try {
            journalModel = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: erases journal from file
    public boolean eraseJournal() {
        File file = new File(JSON_STORE);
        if (file.delete()) {
            journalModel.getEntryList().clear();
            System.out.println("Deleted journal successfully");
            return true;
        } else {
            System.out.println("Failed to delete the journal");
            return false;
        }
    }

    // EFFECTS: returns journalModel
    public JournalModel getJournalModel() {
        return journalModel;
    }

}
