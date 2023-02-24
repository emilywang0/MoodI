package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {
    private Tag testTag;

    @BeforeEach
    void runBefore() {
        testTag = new Tag("excited!", new Color(223, 128, 255));
    }

    @Test
    void testConstructor() {
        assertEquals("excited!", testTag.getEmotion());
        assertEquals(new Color(223, 128, 255), testTag.getColor());
    }

    @Test
    void testEditColor() {
        assertEquals(new Color(223, 128, 255), testTag.getColor());
        testTag.editColor(new Color(0, 0, 0));
        assertEquals(new Color(0, 0, 0), testTag.getColor());
    }

    @Test
    void testEditEmotion() {
        assertEquals("excited!", testTag.getEmotion());
        testTag.editEmotion("hyped!");
        assertEquals("hyped!", testTag.getEmotion());
    }

    @Test
    void testHappyTag() {
        assertEquals("happy", Tag.happyTag().getEmotion());
        assertEquals(new Color(153, 255, 153), Tag.happyTag().getColor());
    }

    @Test
    void testSadTag() {
        assertEquals("sad", Tag.sadTag().getEmotion());
        assertEquals(new Color(102, 163, 255), Tag.sadTag().getColor());
    }

    @Test
    void testAngryTag() {
        assertEquals("angry", Tag.angryTag().getEmotion());
        assertEquals(new Color(255, 102, 102), Tag.angryTag().getColor());
    }

}
