/*
 * File: FindTheRedThreadTest.java
 * Purpose: JUnit 5 unit tests for the FindTheRedThread class, verifying correct
 * behavior of thread creation and pulling
 * Author: Ben Lyons
 * Date: 4/25/2026
 */

package edu.trincoll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindTheRedThreadTest {

    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream capturedOutput;

    /**
     * Redirects {@code System.out} to a capture buffer before each test.
     */
    @BeforeEach
    void redirectSystemOut() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }
    /**
     * Restores the original {@code System.out} after each test.
     */
    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }
    /**
     * Convenience helper — returns everything printed to System.out
     * during the current test as a single String.
     */
    private String output() {
        return capturedOutput.toString();
    }

    // -----------------------------------------------------------------------
    // Reflection Helpers
    // -----------------------------------------------------------------------

    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private int getIntField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(obj);
    }

    private boolean getBooleanField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getBoolean(obj);
    }

    private Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    //initializeSpools sets spools to 20 (Sunny-Day)
    @Test
    @DisplayName("initializeSpools sets remainingSpools to 20")
    void testInitializeSpools() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        invokeMethod(game, "initializeSpools", new Class<?>[]{});
        assertEquals(20, getIntField(game, "remainingSpools"), "remaining spools should be 20 after reset");
    }

    //Switch turn test switches turns propertly (sunny-days)
    //switches turn when true to false
    @Test
    @DisplayName("switch turn changes isUser from true to false")
    void switchTurnToComputer() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "isUserTurn", true);
        invokeMethod(game, "switchTurn", new Class<?>[]{});
        assertFalse(getBooleanField(game, "isUserTurn"), "isUserTurn should be false after switch");
    }
    //switches turn when false to true
    @Test
    @DisplayName("switch turn changes isUser from false to true")
    void switchTurnToUser() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "isUserTurn", false);
        invokeMethod(game, "switchTurn", new Class<?>[]{});
        assertTrue(getBooleanField(game, "isUserTurn"), "isUserTurn should be true after switch");
    }

    //Execute pull tests (Sunny & Rainy day)
    //execute pull test to see that remaining spools decreases by correct amount (sunny)
    @Test
    @DisplayName("executePull decreases remainingSpools by correct amount")
    void executePullDecrement() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "remainingSpools", 20);
        invokeMethod(game, "executePull", new Class<?>[]{int.class}, 3);
        assertEquals(17, getIntField(game, "remainingSpools"), "pulling 3 spools from remainingSpools should have it be 17");
    }
    //execute pull won't dip below zero when pulling more spools than there are (rainy)
    @Test
    @DisplayName("executePull won't cause remainingSpools to go negative")
    void executePullClamping() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "remainingSpools", 4);
        invokeMethod(game, "executePull", new Class<?>[]{int.class}, 6);
        assertEquals(0, getIntField(game, "remainingSpools"), "remainingSpools should clamp to 0, not go negative");
    }

    //CheckRedThreadPulled boundary tests (Sunny-day tests)
    //verifies red thread is pulled and returns true 
    @Test
    @DisplayName("checkRedThreadPulled returns true if pull amount overlaps with thread position")
    void checkRedThreadPulledReturnsTrue() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "remainingSpools", 20);
        setField(game, "redThreadPosition", 18);
        boolean result = (boolean)invokeMethod(game, "checkRedThreadPulled", new Class<?>[]{int.class}, 5);
        assertTrue(result, "Should return true after red thread is pulled");
    }
    //verifies red thread is not pulled and consequently returns false
    @Test
    @DisplayName("checkRedThread returns false if pull amount doesn't overlap with thread position")
    void checkRedThreadPulledReturnsFalse() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        setField(game, "remainingSpools", 20);
        setField(game, "redThreadPosition", 14);
        boolean result = (boolean)invokeMethod(game, "checkRedThreadPulled", new Class<?>[]{int.class}, 5);
        assertFalse(result, "Should return false when thread isn't pulled");
    }

    //declareGameWinner tests (Sunny-days)
    //Verifies correct message displayed when user pulls red thread
    @Test
    @DisplayName("declareGameWinner displays correct message for user win")
    void testDeclareGameWinnerUser() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        invokeMethod(game, "declareGameWinner", new Class<?>[]{boolean.class}, true);
        assertTrue(output().contains("You win Red Thread!"), "Should print 'You win Red Thread' when user wins");
    }
    //Verifies correct message displayed when computer pulls red thread
    @Test
    @DisplayName("declareGameWinner displays correct message for computer win")
    void testDeclareGameWinnerComputer() throws Exception {
        FindTheRedThread game = new FindTheRedThread();
        invokeMethod(game, "declareGameWinner", new Class<?>[]{boolean.class}, false);
        assertTrue(output().contains("Computer wins Red Thread!"), "Should print 'Computer wins Red Thread' when computer wins");
    }

}
