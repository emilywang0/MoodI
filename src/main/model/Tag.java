package model;

import java.awt.*;

// Represents a tag having an emotion and associated color
public class Tag {
    private String emotion;
    private Color color;

    // EFFECTS: tag has given emotion and color
    public Tag(String emotion, Color color) {
        this.emotion = emotion;
        this.color = color;
    }

    // MODIFIES: this
    // EFFECTS: changes tag's color to new given color
    public void editColor(Color newColor) {
        this.color = newColor;
    }

    // MODIFIES: this
    // EFFECTS: changes tag's emotion to new given emotion
    public void editEmotion(String newEmotion) {
        this.emotion = newEmotion;
    }

    // EFFECTS: returns emotion
    public String getEmotion() {
        return emotion;
    }

    // EFFECTS: returns color
    public Color getColor() {
        return color;
    }

    // EFFECTS: returns default tag 1, happy emotion with green color
    public static Tag happyTag() {
        Tag happyTag = new Tag("happy", new Color(153, 255, 153));
        return happyTag;
    }

    // EFFECTS: returns default tag 2, sad emotion with blue color
    public static Tag sadTag() {
        Tag sadTag = new Tag("sad", new Color(102, 163, 255));
        return sadTag;
    }

    // EFFECTS: returns default tag 3, angry emotion with red color
    public static Tag angryTag() {
        Tag angryTag = new Tag("angry", new Color(255, 102, 102));
        return angryTag;
    }


}
