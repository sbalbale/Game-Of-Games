/*
* File: GuessTheNumberTest.java\
* Purpose: Unit tests for the GuessTheNumber class
* Author: Sean Balbale and Juan Marcano
* Date: 4/26/2026
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

/**
 * Unit tests for the GuessTheNumber class.
 * Uses Reflection to access private fields and methods without
 * changing their visibility, as the class is private by design.
 */
class GuessTheNumberTest {

    // Save original stdout to restore after each test
    private final PrintStream originalOut = System.out;

    // Captures output printed during tests
    private ByteArrayOutputStream capturedOutput;

    // Redirect System.out before each test
    @BeforeEach
    void redirectSystemOut() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    // Restore System.out after each test
    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    // Returns everything printed during the test
    private String output() {
        return capturedOutput.toString();
    }

    // -----------------------------------------------------------------------
    // Reflection Helpers
    // -----------------------------------------------------------------------

    // Sets a private field on an object
    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    // Gets a private int field from an object
    private int getIntField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(obj);
    }

    // Invokes a private method on an object
    private Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    // -----------------------------------------------------------------------
    // generateTargetNumber() — sunny-day tests
    // -----------------------------------------------------------------------

    // Target number should be between 1 and range inclusive
    @Test
    @DisplayName("generateTargetNumber produces a number within valid range")
    void testGenerateTargetNumberWithinRange() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        invokeMethod(game, "generateTargetNumber", new Class<?>[] { int.class }, 10);
        int target = getIntField(game, "targetNumber");
        assertTrue(target >= 1 && target <= 10, "Target should be between 1 and 10 inclusive");
    }

    // -----------------------------------------------------------------------
    // generateTargetNumber() — rainy-day tests
    // -----------------------------------------------------------------------

    // Target number should never be 0 — catches off-by-one errors
    @Test
    @DisplayName("generateTargetNumber never produces 0")
    void testGenerateTargetNumberNeverZero() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        for (int i = 0; i < 100; i++) {
            invokeMethod(game, "generateTargetNumber", new Class<?>[] { int.class }, 10);
            int target = getIntField(game, "targetNumber");
            assertTrue(target >= 1, "Target number should never be 0");
        }
    }

    // Target number should never exceed the range
    @Test
    @DisplayName("generateTargetNumber never produces a number above range")
    void testGenerateTargetNumberNeverExceedsRange() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        for (int i = 0; i < 100; i++) {
            invokeMethod(game, "generateTargetNumber", new Class<?>[] { int.class }, 10);
            int target = getIntField(game, "targetNumber");
            assertTrue(target <= 10, "Target number should never exceed the range");
        }
    }

    // -----------------------------------------------------------------------
    // evaluateGuess() — sunny-day tests
    // -----------------------------------------------------------------------

    // Correct guess should return true
    @Test
    @DisplayName("evaluateGuess returns true on correct guess")
    void testEvaluateGuessCorrect() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "targetNumber", 5);
        setField(game, "currentGuessesLeft", 3);
        boolean result = (boolean) invokeMethod(game, "evaluateGuess", new Class<?>[] { int.class }, 5);
        assertTrue(result, "Should return true when guess matches target");
    }

    // Wrong guess should decrement currentGuessesLeft
    @Test
    @DisplayName("evaluateGuess decrements currentGuessesLeft on wrong guess")
    void testEvaluateGuessDecrementsGuesses() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "targetNumber", 5);
        setField(game, "currentGuessesLeft", 3);
        invokeMethod(game, "evaluateGuess", new Class<?>[] { int.class }, 2);
        assertEquals(2, getIntField(game, "currentGuessesLeft"), "currentGuessesLeft should decrement by 1");
    }

    // -----------------------------------------------------------------------
    // evaluateGuess() — rainy-day tests
    // -----------------------------------------------------------------------

    // Wrong guess should return false
    @Test
    @DisplayName("evaluateGuess returns false on wrong guess")
    void testEvaluateGuessWrong() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "targetNumber", 5);
        setField(game, "currentGuessesLeft", 3);
        boolean result = (boolean) invokeMethod(game, "evaluateGuess", new Class<?>[] { int.class }, 3);
        assertFalse(result, "Should return false when guess does not match target");
    }

    // Last wrong guess should bring currentGuessesLeft to 0
    @Test
    @DisplayName("evaluateGuess decrements guesses to 0 on final wrong guess")
    void testEvaluateGuessHitsZero() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "targetNumber", 5);
        setField(game, "currentGuessesLeft", 1);
        invokeMethod(game, "evaluateGuess", new Class<?>[] { int.class }, 2);
        assertEquals(0, getIntField(game, "currentGuessesLeft"),
                "currentGuessesLeft should reach 0 on last wrong guess");
    }

    // -----------------------------------------------------------------------
    // declareRoundResult() — sunny-day tests
    // -----------------------------------------------------------------------

    // Should print win message when user guesses correctly
    @Test
    @DisplayName("declareRoundResult prints win message when user wins")
    void testDeclareRoundResultUserWins() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        invokeMethod(game, "declareRoundResult", new Class<?>[] { boolean.class }, true);
        assertTrue(output().contains("Correct! You have won this round!"), "Should print win message");
    }

    // -----------------------------------------------------------------------
    // declareRoundResult() — rainy-day tests
    // -----------------------------------------------------------------------

    // Should print wrong guess message with remaining guesses
    @Test
    @DisplayName("declareRoundResult prints wrong guess message with guesses left")
    void testDeclareRoundResultWrongGuess() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "currentGuessesLeft", 2);
        invokeMethod(game, "declareRoundResult", new Class<?>[] { boolean.class }, false);
        assertTrue(output().contains("Wrong guess!"), "Should print wrong guess message");
        assertTrue(output().contains("2"), "Should display remaining guesses");
    }

    // Should print 0 remaining guesses on last wrong guess
    @Test
    @DisplayName("declareRoundResult prints 0 guesses left on final wrong guess")
    void testDeclareRoundResultZeroGuessesLeft() throws Exception {
        GuessTheNumber game = new GuessTheNumber();
        setField(game, "currentGuessesLeft", 0);
        invokeMethod(game, "declareRoundResult", new Class<?>[] { boolean.class }, false);
        assertTrue(output().contains("0"), "Should display 0 remaining guesses");
    }
}