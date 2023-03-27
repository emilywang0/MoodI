package ui;

import model.Entry;
import model.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Toolbar for Journal GUI
public class ToolBar extends JPanel implements ActionListener {
    private JButton addButton;
    private JButton deleteButton;
    private JButton findButton;
    private JButton refreshButton;
    private JournalFrame frame;
    private boolean changed;

    // EFFECTS: constructs toolbar with add, delete, find and view all buttons
    public ToolBar(JournalFrame frame) {
        this.frame = frame;
        this.addButton = new JButton("Add entry");
        this.deleteButton = new JButton("Delete entry");
        this.findButton = new JButton("Find past entry");
        this.refreshButton = new JButton("Refresh");

        designButton(addButton);
        designButton(deleteButton);
        designButton(findButton);
        designButton(refreshButton);

        add(addButton);
        add(deleteButton);
        add(findButton);
        add(refreshButton);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        findButton.addActionListener(this);
        refreshButton.addActionListener(this);

    }

    private void designButton(JButton button) {

        button.setBackground(SystemColor.activeCaption);
        button.setForeground(Color.WHITE);
    }

    // EFFECTS: specifies methods for each button when pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addEntry();
            update();
        }
        if (e.getSource() == deleteButton) {
            doDelete();
            update();
        }
        if (e.getSource() == findButton) {
            doFindEntry();
        }
        if (e.getSource() == refreshButton) {
            update();
            System.out.println("Refreshed");
        }
    }

    // EFFECTS: updates frame
    private void update() {
        frame.getEntryPanel().updateEntryPanel();
        revalidate();
        repaint();
    }

    // EFFECTS: add a new entry
    private void addEntry() {
        String dateString =
                JOptionPane.showInputDialog("Enter valid date like so (include dashes): YYYY-MM-DD");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e1) {
            JOptionPane.showMessageDialog(frame, "Not a valid date, try again.");
            return;
        }
        JTextField titleField = new JTextField(50);
        JTextField textField = new JTextField(50);
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new GridLayout(4, 0));
        entryPanel.add(new JLabel("Create a title for your journal entry:"));
        entryPanel.add(titleField);
        entryPanel.add(new JLabel("Let your thoughts flow freely and just write:"));
        entryPanel.add(textField);

        int result = JOptionPane.showConfirmDialog(null, entryPanel,
                "Your Journal Entry", JOptionPane.OK_CANCEL_OPTION);


        addEntryResponse(date, titleField, textField, result);
    }

    // MODIFIES: entryList (JournalModel)
    // EFFECTS: helper method for addEntry, creates new entry if OK option selected
    private void addEntryResponse(LocalDate date, JTextField titleField, JTextField textField, int result) {
        if (result == JOptionPane.OK_OPTION) {
            Entry newEntry = new Entry(titleField.getText(), textField.getText(), date);
            frame.getJournalApp().getJournalModel().addEntry(newEntry);
            selectTag(newEntry);
            changed = true;
            JOptionPane.showMessageDialog(frame,
                    "Successfully added journal entry. Glad you took some time to reflect on today!");
        } else if (result == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(frame,
                    "No new entry added.");
        }
    }

    // MODIFIES: newEntry
    // EFFECTS: prompts user to select a tag for an entry
    private void selectTag(Entry newEntry) {
        String[] buttons = {"Happy", "Sad", "Angry"};
        JPanel tagPanel = new JPanel();
        tagPanel.add(new JLabel("Create a title for your journal entry:"));
        JComboBox tagList = new JComboBox(buttons);
        tagPanel.add(tagList);
        JOptionPane.showConfirmDialog(null, tagPanel, "Select a Tag!",
                JOptionPane.DEFAULT_OPTION);
        int num = tagList.getSelectedIndex();
        if (num == 0) {
            newEntry.setTag(Tag.happyTag());
        } else if (num == 1) {
            newEntry.setTag(Tag.sadTag());
        } else if (num == 2) {
            newEntry.setTag(Tag.angryTag());
        }
    }

    // MODIFIES: entryList (JournalModel)
    // EFFECTS: deletes an existing entry
    private void doDelete() {
        String dateString =
                JOptionPane.showInputDialog("Enter valid date like so (include dashes): YYYY-MM-DD");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Not a valid date, try again.");
            return;
        }
        if (frame.getJournalApp().getJournalModel().deleteEntry(date)) {
            JOptionPane.showMessageDialog(frame, "Successfully deleted entry.");
            changed = true;
        } else {
            JOptionPane.showMessageDialog(frame, "Could not find entry with given date.");
        }
    }

    // EFFECTS: finds entry with given date
    private void doFindEntry() {
        String dateString =
                JOptionPane.showInputDialog("Enter the date (YYYY-MM-DD) of the entry that you would like to read:");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Not a valid date, try again.");
            return;
        }
        if (frame.getJournalApp().getJournalModel().getEntry(date) != null) {
            showJournalDetails(date);
        } else {
            JOptionPane.showMessageDialog(frame,"Could not find entry with given date.");
        }
    }

    // EFFECTS: displays date, title, text, and tag of an entry with given date
    private void showJournalDetails(LocalDate date) {
        Entry e = frame.getJournalApp().getJournalModel().getEntry(date);
        String entryDate = e.getDate().toString();
        String title = e.getTitle();
        String text = e.getText();
        String emotion = e.getTag().getEmotion();
        Color color = e.getTag().getColor();
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new GridLayout(4, 0));
        entryPanel.add(new JLabel(entryDate));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        entryPanel.add(titleLabel);
        entryPanel.add(new JLabel(text));
        JLabel tagLabel = new JLabel("Tag: " + emotion);
        entryPanel.setBackground(color);
        entryPanel.add(tagLabel);
        JOptionPane.showConfirmDialog(null, entryPanel, entryDate + ": " + title,
                JOptionPane.DEFAULT_OPTION);
    }

    // EFFECTS: returns true if changed
    public boolean isChanged() {
        return changed;
    }

}


