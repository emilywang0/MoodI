package ui;

import model.Entry;

import javax.swing.*;
import java.awt.*;

// Interface showing all journal entries
public class EntryPanel extends JPanel {
    private JournalFrame frame;
    private JPanel pastEntriesPanel;
    private JPanel entryPanel;

    // EFFECTS: constructs entryPanel GUI
    public EntryPanel(JournalFrame frame) {
        this.frame = frame;
        this.entryPanel = new JPanel(true);
        createDisplay();

    }

    // EFFECTS: creates display for all entries
    protected void createDisplay() {
        entryPanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        entryPanel.setLayout(new BorderLayout());
        entryPanel.setBackground(Color.WHITE);
        entryPanel.add(createTitle(), BorderLayout.NORTH);
        updateEntryPanel();
    }

    // EFFECTS: updates entry panel
    public void updateEntryPanel() {
        pastEntriesPanel = new JPanel();
        if (frame.getJournalApp().getJournalModel() == null) {
            this.add(entryPanel);
        } else {
            pastEntriesPanel = createList();
            entryPanel.add(new JScrollPane(pastEntriesPanel), BorderLayout.SOUTH);
            this.add(entryPanel);
        }
    }


    // EFFECTS: creates a list of all past journal entries showing date, title, emotion, returns
    //          reminder that no entries added if journalModel is null
    private JPanel createList() {
        for (Entry e : frame.getJournalApp().getJournalModel().getEntryList()) {
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new GridLayout(2, 0));
            entryPanel.setBorder(BorderFactory
                    .createLineBorder(e.getTag().getColor(), 3));
            String title = e.getTitle();
            JLabel dateLabel = new JLabel(e.getDate().toString());
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

            entryPanel.add(dateLabel);
            entryPanel.add(titleLabel);

            pastEntriesPanel.add(entryPanel);
        }
        pastEntriesPanel.setLayout(new BoxLayout(pastEntriesPanel, BoxLayout.Y_AXIS));

        return pastEntriesPanel;

    }


    // EFFECTS: creates title for journal entries
    private JPanel createTitle() {
        JPanel titlePanel = new JPanel(true);
        titlePanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Your Journal Entries:");
        label.setForeground(SystemColor.activeCaption);
        titlePanel.add(label, BorderLayout.CENTER);

        return titlePanel;
    }

}
