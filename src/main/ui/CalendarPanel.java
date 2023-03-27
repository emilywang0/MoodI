package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Calendar interface
public class CalendarPanel extends JPanel {

    private static final long serialVersionUID  = 1L;

    protected int month;
    protected int year;

    protected String[] monthNames = { "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    protected String[] dayNames = { "S", "M", "T", "W", "T", "F", "S"};

    // EFFECTS: constructs calendar GUI
    public CalendarPanel(int month, int year) {
        this.month = month;
        this.year = year;

        this.add(createDisplay());

    }

    // EFFECTS: create display for calendar
    protected JPanel createDisplay() {
        JPanel monthPanel = new JPanel(true);
        monthPanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        monthPanel.setLayout(new BorderLayout());
        monthPanel.setBackground(Color.WHITE);
        monthPanel.setForeground(Color.BLACK);
        monthPanel.add(createTitle(), BorderLayout.NORTH);
        monthPanel.add(createDays(), BorderLayout.SOUTH);

        return monthPanel;
    }

    // EFFECTS: creates title with month and year
    protected JPanel createTitle() {
        JPanel titlePanel = new JPanel(true);
        titlePanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(monthNames[month] + " " + year);
        label.setForeground(SystemColor.activeCaption);

        titlePanel.add(label, BorderLayout.CENTER);

        return titlePanel;
    }

    // EFFECTS: creates dayPanel with all days of the month
    protected JPanel createDays() {
        JPanel dayPanel = new JPanel(true);
        dayPanel.setLayout(new GridLayout(0, dayNames.length));

        Calendar today = Calendar.getInstance();
        int todayMonth = today.get(Calendar.MONTH);
        int todayYear = today.get(Calendar.YEAR);
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar iterator = (Calendar) calendar.clone();
        iterator.add(Calendar.DAY_OF_MONTH,
                -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

        Calendar maximum = (Calendar) calendar.clone();
        maximum.add(Calendar.MONTH, +1);

        createDayLabels(dayPanel);

        int count = 0;
        int limit = dayNames.length * 6;

        count = getCount(dayPanel, todayMonth, todayYear, todayDay, iterator, maximum, count);

        createDayTiles(dayPanel, count, limit);

        return dayPanel;
    }

    // EFFECTS: adds each dayLabel up to limit as a panel to dayPanel
    private void createDayTiles(JPanel dayPanel, int count, int limit) {
        for (int i = count; i < limit; i++) {
            JPanel d1Panel = new JPanel(true);
            d1Panel.setBorder(BorderFactory.createLineBorder(new Color(159, 143, 255)));
            JLabel dayLabel = new JLabel();
            dayLabel.setText(" ");
            d1Panel.setBackground(Color.WHITE);
            d1Panel.add(dayLabel);
            dayPanel.add(d1Panel);
        }
    }

    // EFFECTS: get count of number of days in given month
    private int getCount(JPanel dayPanel, int todayMonth, int todayYear,
                         int todayDay, Calendar iterator, Calendar maximum, int count) {
        while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {
            int l1Month = iterator.get(Calendar.MONTH);
            int l1Year = iterator.get(Calendar.YEAR);

            JPanel d1Panel = new JPanel(true);
            d1Panel.setBorder(BorderFactory.createLineBorder(new Color(159, 143, 255)));
            JLabel dayLabel = new JLabel();

            if ((l1Month == month) && (l1Year == year)) {
                int l1Day = iterator.get(Calendar.DAY_OF_MONTH);
                dayLabel.setText(Integer.toString(l1Day));
                if ((todayMonth == month) && (todayYear == year) && (todayDay == l1Day)) {
                    d1Panel.setBackground(new Color(159, 143, 255));
                } else {
                    d1Panel.setBackground(Color.WHITE);
                }
            } else {
                //dayLabel.setText(" ");
                d1Panel.setBackground(Color.WHITE);
            }
            d1Panel.add(dayLabel);
            dayPanel.add(d1Panel);
            iterator.add(Calendar.DAY_OF_YEAR, +1);
            count++;
        }
        return count;
    }

    // EFFECTS: creates panel showing each day of the week (dayNames) and adds to dayPanel
    private void createDayLabels(JPanel dayPanel) {
        for (int i = 0; i < dayNames.length; i++) {
            JPanel d1Panel = new JPanel(true);
            d1Panel.setBorder(BorderFactory.createLineBorder(new Color(159, 143, 255)));
            JLabel d1Label = new JLabel(dayNames[i]);
            d1Panel.add(d1Label);
            dayPanel.add(d1Panel);
        }
    }

}