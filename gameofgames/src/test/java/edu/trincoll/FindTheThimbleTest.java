/*
 * File: FindTheThimbleTest.java
 * Provides unit tests for the FindTheThimble class using reflection and
 * test doubles so the implementation can keep its helpers private.
 * Author: Abby Gomes and Sean Balbale
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

class FindTheThimbleTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void redirectSystemOut() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    private String output() {
        return capturedOutput.toString();
    }

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

    private Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args)
            throws Exception {
        Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    private FindTheThimble newGameWithStubs(int oddValue, char guess, boolean nextBoolean) throws Exception {
        FindTheThimble game = new FindTheThimble();
        setField(game, "input", new StubGetInput(oddValue, guess));
        setField(game, "random", new StubRandom(nextBoolean));
        return game;
    }

    private static final class StubGetInput extends GetInput {
        private final int oddValue;
        private final char guess;

        private StubGetInput(int oddValue, char guess) {
            this.oddValue = oddValue;
            this.guess = guess;
        }

        @Override
        public int getOddInt() {
            return oddValue;
        }

        @Override
        public char getChar(char[] validChars) {
            return guess;
        }
    }

    private static final class StubRandom extends java.util.Random {
        private final boolean nextBooleanValue;

        private StubRandom(boolean nextBooleanValue) {
            this.nextBooleanValue = nextBooleanValue;
        }

        @Override
        public boolean nextBoolean() {
            return nextBooleanValue;
        }
    }

    @Test
    @DisplayName("getBestOutOfValue returns the validated odd value from GetInput")
    void testGetBestOutOfValue() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        int result = (int) invokeMethod(game, "getBestOutOfValue", new Class<?>[] {});

        assertEquals(5, result);
        assertTrue(output().contains("odd number"));
    }

    @Test
    @DisplayName("calculateWinThreshold stores the correct threshold")
    void testCalculateWinThreshold() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        invokeMethod(game, "calculateWinThreshold", new Class<?>[] { int.class }, 5);

        assertEquals(3, getIntField(game, "winThreshold"));
    }

    @Test
    @DisplayName("hideThimble returns L when Random.nextBoolean is true")
    void testHideThimbleLeft() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        char result = (char) invokeMethod(game, "hideThimble", new Class<?>[] {});

        assertEquals('L', result);
    }

    @Test
    @DisplayName("hideThimble returns R when Random.nextBoolean is false")
    void testHideThimbleRight() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', false);

        char result = (char) invokeMethod(game, "hideThimble", new Class<?>[] {});

        assertEquals('R', result);
    }

    @Test
    @DisplayName("getPlayerGuess returns the validated guess from GetInput")
    void testGetPlayerGuess() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'R', true);

        char result = (char) invokeMethod(game, "getPlayerGuess", new Class<?>[] {});

        assertEquals('R', result);
        assertTrue(output().contains("enter L for left or R for right"));
    }

    @Test
    @DisplayName("checkRoundWinner returns true when the guess matches the hidden hand")
    void testCheckRoundWinnerCorrect() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        boolean result = (boolean) invokeMethod(game, "checkRoundWinner", new Class<?>[] { char.class, char.class }, 'L', 'L');

        assertTrue(result);
        assertTrue(output().contains("Correct! You win this round."));
    }

    @Test
    @DisplayName("checkRoundWinner returns false when the guess does not match the hidden hand")
    void testCheckRoundWinnerIncorrect() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        boolean result = (boolean) invokeMethod(game, "checkRoundWinner", new Class<?>[] { char.class, char.class }, 'L', 'R');

        assertFalse(result);
        assertTrue(output().contains("Wrong! The computer wins this round."));
    }

    @Test
    @DisplayName("updateRoundScore increments userScore when the user wins")
    void testUpdateRoundScoreUser() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        setField(game, "userScore", 0);
        setField(game, "compScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[] { boolean.class }, true);

        assertEquals(1, getIntField(game, "userScore"));
        assertEquals(0, getIntField(game, "compScore"));
    }

    @Test
    @DisplayName("updateRoundScore increments compScore when the computer wins")
    void testUpdateRoundScoreComputer() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        setField(game, "userScore", 0);
        setField(game, "compScore", 0);
        invokeMethod(game, "updateRoundScore", new Class<?>[] { boolean.class }, false);

        assertEquals(0, getIntField(game, "userScore"));
        assertEquals(1, getIntField(game, "compScore"));
    }

    @Test
    @DisplayName("checkGameWinner returns true when the user reaches the threshold")
    void testCheckGameWinnerTrue() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        setField(game, "userScore", 3);
        setField(game, "compScore", 1);
        setField(game, "winThreshold", 3);

        boolean result = (boolean) invokeMethod(game, "checkGameWinner", new Class<?>[] {});

        assertTrue(result);
    }

    @Test
    @DisplayName("checkGameWinner returns false when neither player reaches the threshold")
    void testCheckGameWinnerFalse() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        setField(game, "userScore", 1);
        setField(game, "compScore", 1);
        setField(game, "winThreshold", 3);

        boolean result = (boolean) invokeMethod(game, "checkGameWinner", new Class<?>[] {});

        assertFalse(result);
    }

    @Test
    @DisplayName("declareGameWinner prints the user win message when userScore is higher")
    void testDeclareGameWinnerRuns() throws Exception {
        FindTheThimble game = newGameWithStubs(5, 'L', true);

        setField(game, "userScore", 3);
        setField(game, "compScore", 1);
        invokeMethod(game, "declareGameWinner", new Class<?>[] {});

        assertTrue(output().contains("You win, Find the Thimble!"));
        assertTrue(output().contains("Final score"));
    }

    @Test
    @DisplayName("playGame completes a single mocked round and returns the winner")
    void testPlayGameUserWins() throws Exception {
        FindTheThimble game = newGameWithStubs(1, 'L', true);

        boolean result = game.playGame();

        assertTrue(result);
        assertTrue(output().contains("First to 1 wins takes the game."));
        assertTrue(output().contains("You win, Find the Thimble!"));
    }
}
