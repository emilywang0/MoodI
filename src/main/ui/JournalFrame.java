package ui;


import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Journal GUI
public class JournalFrame extends JFrame implements MenuListener, WindowListener {

    private JournalApp journalApp;
    private CalendarPanel calendar;
    private EntryPanel entryPanel;
    private JPanel displayPanel;
    private ToolBar tools;
    private JLabel title;
    private JMenuBar menuBar;
    private JMenu saveMenu;
    private JMenu loadMenu;
    private JMenu deleteMenu;
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;
    private ImageIcon deleteIcon;
    private boolean changed;
    private EventLog eventLog = EventLog.getInstance();


    // EFFECTS: constructs and sets up main window
    public JournalFrame(JournalApp journalApp) {
        super("MoodI");
        this.journalApp = journalApp;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        setSize(500, 500);
        setUndecorated(false);
        this.getContentPane().setBackground(Color.WHITE);

        ImageIcon image = new ImageIcon("src/images/logo.png");
        setIconImage(image.getImage());

        createMenuBar();

        setLogoDisplay();

        setMainDisplay();


        setToolBar();
        changed = tools.isChanged();
        pack();
        setVisible(true);
    }

    // EFFECTS: displays CalendarPanel and EntryPanel
    private void setMainDisplay() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(0, 2));

        // 0-based indexing
        calendar = new CalendarPanel(3, 2023);
        displayPanel.add(calendar);

        entryPanel = new EntryPanel(this);
        displayPanel.add(entryPanel);
        add(displayPanel);
    }



    // EFFECTS: displays app title MoodI
    private void setLogoDisplay() {
        title = new JLabel();
        this.add(title, BorderLayout.NORTH);
        ImageIcon titleImage = new ImageIcon("src/images/title.png");
        title.setIcon(titleImage);
        title.setHorizontalAlignment(JLabel.CENTER);
    }

    // EFFECTS: sets up toolbar
    private void setToolBar() {
        tools = new ToolBar(this);
        add(tools, BorderLayout.SOUTH);

    }

    // EFFECTS: creates menuBar with save, load, and delete file buttons
    private void createMenuBar() {
        menuBar = new JMenuBar();

        saveMenu = new JMenu();
        loadMenu = new JMenu();
        deleteMenu = new JMenu();
        saveIcon = new ImageIcon("src/images/save.png");
        loadIcon = new ImageIcon("src/images/file.png");
        deleteIcon = new ImageIcon("src/images/trash.png");

        saveMenu.setIcon(saveIcon);
        loadMenu.setIcon(loadIcon);
        deleteMenu.setIcon(deleteIcon);
        menuBar.add(saveMenu);
        menuBar.add(loadMenu);
        menuBar.add(deleteMenu);

        this.setJMenuBar(menuBar);
        saveMenu.addMenuListener(this);
        loadMenu.addMenuListener(this);
        deleteMenu.addMenuListener(this);
    }

    // MODIFIES: journalApp
    // EFFECTS: asks for confirmation from user, then erases current journal on file
    private void eraseJournal() {
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete the current journal?", "Confirm", JOptionPane.OK_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (journalApp.eraseJournal()) {
                JOptionPane.showMessageDialog(this, "Successfully deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Unable to delete.");
            }
        }
    }

    // MODIFIES: journalApp
    // EFFECTS: saves current journal to file
    private void saveJournal() {
        if (journalApp.saveJournal()) {
            JOptionPane.showMessageDialog(this, "Successfully Saved!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save.");
        }
    }

    // MODIFIES: journalApp
    // EFFECTS: loads journal from file
    public void loadJournal() {
        if (changed) {
            int result = JOptionPane.showConfirmDialog(null,
                    "Save current changes before loading journal?", "Confirm", JOptionPane.OK_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                saveJournal();
                changed = false;
            }
        }

        if (journalApp.loadJournalFrame()) {
            JOptionPane.showMessageDialog(this, "Successfully Loaded!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load.");
        }

    }

    // EFFECTS: returns journalApp
    public JournalApp getJournalApp() {
        return journalApp;
    }

    // EFFECTS: returns entryPanel
    public EntryPanel getEntryPanel() {
        return entryPanel;
    }

    // EFFECTS: specifies methods for each menu button when pressed
    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == saveMenu) {
            saveJournal();
        } else if (e.getSource() == loadMenu) {
            loadJournal();
            this.getEntryPanel().updateEntryPanel();
            revalidate();
            repaint();
        } else if (e.getSource() == deleteMenu) {
            eraseJournal();
            this.getEntryPanel().updateEntryPanel();
            revalidate();
            repaint();
        }
    }

    // EFFECTS: specifies methods for deselected menu
    @Override
    public void menuDeselected(MenuEvent e) {

    }

    // EFFECTS: specifies methods for cancelled menu
    @Override
    public void menuCanceled(MenuEvent e) {

    }

    // EFFECTS: specifies methods for opened window
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: specifies methods for closing window
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event: eventLog) {
            System.out.println(event);
        }
        System.exit(0);
    }

    // EFFECTS: prints EventLog to console when window is closed
    @Override
    public void windowClosed(WindowEvent e) {
    }

    // EFFECTS: specifies methods for iconified window
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: specifies methods for deiconified window
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    // EFFECTS: specifies methods for activated window
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: specifies methods for deactivated window
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
