/*
 * File: CoinFlipTest.java
 * Purpose: JUnit 5 unit tests for the CoinFlip class, verifying correct
 * behavior of win thresholds, score updating, winner evaluation, and round logic.
 * Author: Sean Balbale
 * Date: 4/22/2026
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
 * Unit tests for the CoinFlip mini-game class.
 * Because the class primarily uses private helper methods to execute game
 * logic (as per design specifications), tests use Reflection to isolate 
 * and verify each internal method's behavior safely.
 */
class CoinFlipTest {

    // Preserve original stdout so it can be restored after every test
    private final PrintStream originalOut = System.out;

    // Captures text that CoinFlip prints so assertions can inspect it
    private ByteArrayOutputStream capturedOutput;

    // -----------------------------------------------------------------------
    // JUnit lifecycle: redirect / restore System.out around each test
    // -----------------------------------------------------------------------

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

    private Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    // -----------------------------------------------------------------------
    // calculateWinThreshold() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies threshold calculation handles 3-round best-out-of properly.
     */
    @Test
    @DisplayName("calculateWinThreshold sets threshold correctly for 3")
    void testCalculateWinThreshold3() throws Exception {
        CoinFlip game = new CoinFlip();
        invokeMethod(game, "calculateWinThreshold", new Class<?>[]{int.class}, 3);
        int threshold = getIntField(game, "winThreshold");
        assertEquals(2, threshold, "Best out of 3 should require 2 wins");
    }

    /**
     * Verifies threshold calculation handles 7-round best-out-of properly.
     */
    @Test
    @DisplayName("calculateWinThreshold sets threshold correctly for 7")
    void testCalculateWinThreshold7() throws Exception {
        CoinFlip game = new CoinFlip();
        invokeMethod(game, "calculateWinThreshold", new Class<?>[]{int.class}, 7);
        int threshold = getIntField(game, "winThreshold");
        assertEquals(4, threshold, "Best out of 7 should require 4 wins");
    }

    // -----------------------------------------------------------------------
    // updateRoundScore() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies user score correctly increments when passing true.
     */
    @Test
    @DisplayName("updateRoundScore increments userScore when user wins round")
    void testUpdateRoundScoreUserWins() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "userScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, true);
        assertEquals(1, getIntField(game, "userScore"), "userScore should increment by 1");
        assertEquals(0, getIntField(game, "compScore"), "compScore should remain 0");
    }

    /**
     * Verifies computer score correctly increments when passing false.
     */
    @Test
    @DisplayName("updateRoundScore increments compScore when computer wins round")
    void testUpdateRoundScoreComputerWins() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "compScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[]{boolean.class}, false);
        assertEquals(0, getIntField(game, "userScore"), "userScore should remain 0");
        assertEquals(1, getIntField(game, "compScore"), "compScore should increment by 1");
    }

    // -----------------------------------------------------------------------
    // checkGameWinner() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies game is correctly flagged over when a player reaches threshold.
     */
    @Test
    @DisplayName("checkGameWinner returns true if user meets threshold")
    void testCheckGameWinnerUserMeetsThreshold() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "winThreshold", 3);
        setField(game, "userScore", 3);
        setField(game, "compScore", 1);
        boolean isGameOver = (boolean) invokeMethod(game, "checkGameWinner", new Class<?>[]{});
        assertTrue(isGameOver, "Game should be over if user reaches win threshold");
    }

    /**
     * Verifies game remains ongoing while scores fall below the threshold.
     */
    @Test
    @DisplayName("checkGameWinner returns false if no one meets threshold")
    void testCheckGameWinnerNoWinnerYet() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "winThreshold", 3);
        setField(game, "userScore", 2);
        setField(game, "compScore", 2);
        boolean isGameOver = (boolean) invokeMethod(game, "checkGameWinner", new Class<?>[]{});
        assertFalse(isGameOver, "Game should not be over if no one reaches the threshold");
    }

    // -----------------------------------------------------------------------
    // checkRoundWinner() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies correct output messaging and score incrementing for accurate calls.
     */
    @Test
    @DisplayName("checkRoundWinner correctly handles a correct call")
    void testCheckRoundWinnerCorrectCall() throws Exception {
        CoinFlip game = new CoinFlip();
        invokeMethod(game, "checkRoundWinner", new Class<?>[]{char.class, char.class}, 'H', 'H');
        String out = output();
        assertTrue(out.contains("Correct call!"), "Should print 'Correct call!' when guess matches result");
        assertEquals(1, getIntField(game, "userScore"), "User score should be updated");
    }

    /**
     * Verifies correct output messaging and score incrementing for inaccurate calls.
     */
    @Test
    @DisplayName("checkRoundWinner correctly handles a wrong call")
    void testCheckRoundWinnerWrongCall() throws Exception {
        CoinFlip game = new CoinFlip();
        invokeMethod(game, "checkRoundWinner", new Class<?>[]{char.class, char.class}, 'H', 'T');
        String out = output();
        assertTrue(out.contains("Wrong call!"), "Should print 'Wrong call!' when guess does not match result");
        assertEquals(1, getIntField(game, "compScore"), "Computer score should be updated");
    }

    // -----------------------------------------------------------------------
    // declareGameWinner() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies correct final winning message when user has a higher score.
     */
    @Test
    @DisplayName("declareGameWinner announces user when userScore > compScore")
    void testDeclareGameWinnerUserWins() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "userScore", 3);
        setField(game, "compScore", 1);
        invokeMethod(game, "declareGameWinner", new Class<?>[]{});
        assertTrue(output().contains("You win Coin Flip!"), "Should announce user win");
    }

    /**
     * Verifies correct final winning message when computer has a higher score.
     */
    @Test
    @DisplayName("declareGameWinner announces computer when compScore > userScore")
    void testDeclareGameWinnerComputerWins() throws Exception {
        CoinFlip game = new CoinFlip();
        setField(game, "userScore", 1);
        setField(game, "compScore", 3);
        invokeMethod(game, "declareGameWinner", new Class<?>[]{});
        assertTrue(output().contains("The computer wins Coin Flip!"), "Should announce computer win");
    }
    
    // -----------------------------------------------------------------------
    // flipCoin() — sunny-day tests
    // -----------------------------------------------------------------------
    
    /**
     * Verifies random generation guarantees a valid H or T character.
     */
    @Test
    @DisplayName("flipCoin returns either 'H' or 'T'")
    void testFlipCoinReturnsValidCharacter() throws Exception {
        CoinFlip game = new CoinFlip();
        char result = (char) invokeMethod(game, "flipCoin", new Class<?>[]{});
        assertTrue(result == 'H' || result == 'T', "flipCoin should only return 'H' or 'T'");
    }
}