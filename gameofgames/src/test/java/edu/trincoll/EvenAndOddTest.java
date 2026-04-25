/*
 * EvenAndOddTest.java
 *
 * JUnit 5 unit tests for the EvenAndOdd class, verifying correct
 * behavior of role assignment, win threshold calculation, score updating,
 * round winner determination, and game winner declaration.
 *
 * Author: Juan Marcano
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

/**
 * Unit tests for the EvenAndOdd class.
 * Uses Reflection to access private fields and methods without
 * changing their visibility, as the class is private by design.
 */
class EvenAndOddTest {

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
    // calculateWinThreshold() — sunny-day tests
    // -----------------------------------------------------------------------

    // Best of 3 should require 2 wins
    @Test
    @DisplayName("calculateWinThreshold sets threshold correctly for 3")
    void testCalculateWinThreshold3() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "bestOutOf", 3);
        invokeMethod(game, "calculateWinThreshold", new Class<?>[]{});
        assertEquals(2, getIntField(game, "winThreshold"), "Best out of 3 should require 2 wins");
    }

    // Best of 5 should require 3 wins
    @Test
    @DisplayName("calculateWinThreshold sets threshold correctly for 5")
    void testCalculateWinThreshold5() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "bestOutOf", 5);
        invokeMethod(game, "calculateWinThreshold", new Class<?>[]{});
        assertEquals(3, getIntField(game, "winThreshold"), "Best out of 5 should require 3 wins");
    }

    // -----------------------------------------------------------------------
    // calculateWinThreshold() — rainy-day tests
    // -----------------------------------------------------------------------

    // Best of 7 should require 4 wins
    @Test
    @DisplayName("calculateWinThreshold sets threshold correctly for 7")
    void testCalculateWinThreshold7() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "bestOutOf", 7);
        invokeMethod(game, "calculateWinThreshold", new Class<?>[]{});
        assertEquals(4, getIntField(game, "winThreshold"), "Best out of 7 should require 4 wins");
    }

    // -----------------------------------------------------------------------
    // updateRoundScore() — sunny-day tests
    // -----------------------------------------------------------------------

    // User score should increment when user wins a round
    @Test
    @DisplayName("updateRoundScore increments userScore when user wins")
    void testUpdateRoundScoreUserWins() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, true);
        assertEquals(1, getIntField(game, "userScore"), "userScore should increment by 1");
        assertEquals(0, getIntField(game, "compScore"), "compScore should remain 0");
    }

    // Computer score should increment when computer wins a round
    @Test
    @DisplayName("updateRoundScore increments compScore when computer wins")
    void testUpdateRoundScoreComputerWins() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "compScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, false);
        assertEquals(0, getIntField(game, "userScore"), "userScore should remain 0");
        assertEquals(1, getIntField(game, "compScore"), "compScore should increment by 1");
    }

    // -----------------------------------------------------------------------
    // updateRoundScore() — rainy-day tests
    // -----------------------------------------------------------------------

    // Scores should accumulate correctly across multiple rounds
    @Test
    @DisplayName("updateRoundScore accumulates scores correctly across rounds")
    void testUpdateRoundScoreAccumulates() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, true);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, true);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, false);
        assertEquals(2, getIntField(game, "userScore"), "userScore should be 2");
        assertEquals(1, getIntField(game, "compScore"), "compScore should be 1");
    }

    // -----------------------------------------------------------------------
    // determineRoundWinner() — sunny-day tests
    // -----------------------------------------------------------------------

    // Even sum and user is even — user should win
    @Test
    @DisplayName("determineRoundWinner user wins when sum is even and user is even")
    void testDetermineRoundWinnerUserEvenSumEven() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userIsEven", true);
        invokeMethod(game, "determineRoundWinner", new Class<?>[]{int.class, int.class, int.class}, 4, 2, 2);
        assertEquals(1, getIntField(game, "userScore"), "User should win when sum is even and user is even");
    }

    // Odd sum and user is odd — user should win
    @Test
    @DisplayName("determineRoundWinner user wins when sum is odd and user is odd")
    void testDetermineRoundWinnerUserOddSumOdd() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userIsEven", false);
        invokeMethod(game, "determineRoundWinner", new Class<?>[]{int.class, int.class, int.class}, 5, 2, 3);
        assertEquals(1, getIntField(game, "userScore"), "User should win when sum is odd and user is odd");
    }

    // -----------------------------------------------------------------------
    // determineRoundWinner() — rainy-day tests
    // -----------------------------------------------------------------------

    // Even sum and user is odd — computer should win
    @Test
    @DisplayName("determineRoundWinner computer wins when sum is even and user is odd")
    void testDetermineRoundWinnerCompWinsEvenSum() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userIsEven", false);
        invokeMethod(game, "determineRoundWinner", new Class<?>[]{int.class, int.class, int.class}, 4, 2, 2);
        assertEquals(1, getIntField(game, "compScore"), "Computer should win when sum is even and user is odd");
    }

    // Odd sum and user is even — computer should win
    @Test
    @DisplayName("determineRoundWinner computer wins when sum is odd and user is even")
    void testDetermineRoundWinnerCompWinsOddSum() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userIsEven", true);
        invokeMethod(game, "determineRoundWinner", new Class<?>[]{int.class, int.class, int.class}, 5, 2, 3);
        assertEquals(1, getIntField(game, "compScore"), "Computer should win when sum is odd and user is even");
    }

    // -----------------------------------------------------------------------
    // declareGameWinner() — sunny-day tests
    // -----------------------------------------------------------------------

    // Should print win message when user wins the game
    @Test
    @DisplayName("declareGameWinner prints win message when user wins")
    void testDeclareGameWinnerUserWins() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userScore", 3);
        setField(game, "compScore", 1);
        invokeMethod(game, "declareGameWinner", new Class<?>[]{boolean.class}, true);
        assertTrue(output().contains("You win Even and Odd!"), "Should print user win message");
    }

    // -----------------------------------------------------------------------
    // declareGameWinner() — rainy-day tests
    // -----------------------------------------------------------------------

    // Should print computer win message when computer wins the game
    @Test
    @DisplayName("declareGameWinner prints win message when computer wins")
    void testDeclareGameWinnerComputerWins() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        setField(game, "userScore", 1);
        setField(game, "compScore", 3);
        invokeMethod(game, "declareGameWinner", new Class<?>[]{boolean.class}, false);
        assertTrue(output().contains("The computer wins Even and Odd!"), "Should print computer win message");
    }

    // -----------------------------------------------------------------------
    // getComputerNumber() — sunny-day tests
    // -----------------------------------------------------------------------

    // Computer throw should always be between 1 and 5 inclusive
    @Test
    @DisplayName("getComputerNumber returns a number between 1 and 5")
    void testGetComputerNumberWithinRange() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        for(int i = 0; i < 100; i++){
            int result = (int) invokeMethod(game, "getComputerNumber", new Class<?>[]{});
            assertTrue(result >= 1 && result <= 5, "Computer throw should be between 1 and 5 inclusive");
        }
    }

    // -----------------------------------------------------------------------
    // calculateSum() — sunny-day tests
    // -----------------------------------------------------------------------

    // Sum of two numbers should be correct
    @Test
    @DisplayName("calculateSum returns correct sum of two numbers")
    void testCalculateSum() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        int result = (int) invokeMethod(game, "calculateSum", new Class<?>[]{int.class, int.class}, 3, 4);
        assertEquals(7, result, "Sum of 3 and 4 should be 7");
    }

    // -----------------------------------------------------------------------
    // calculateSum() — rainy-day tests
    // -----------------------------------------------------------------------

    // Sum of two max values should be correct
    @Test
    @DisplayName("calculateSum returns correct sum at max values")
    void testCalculateSumMaxValues() throws Exception {
        EvenAndOdd game = new EvenAndOdd();
        int result = (int) invokeMethod(game, "calculateSum", new Class<?>[]{int.class, int.class}, 5, 5);
        assertEquals(10, result, "Sum of 5 and 5 should be 10");
    }
}