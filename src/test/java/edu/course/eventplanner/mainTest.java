package edu.course.eventplanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class mainTest {

    @Test
    void testMainRunsWithoutCrashing() {
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("Main method should run without throwing an exception");
        }
    }
    //
}