package ui;


import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new JournalApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run journal: file not found");
        }

    }


}
