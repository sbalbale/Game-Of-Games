/*
 * File: PlayGamesTest.java
 * Purpose: JUnit 5 unit tests for the PlayGames class, verifying correct
 * behavior of the welcome message, scoreboard management, overall winner
 * declaration, and game-routing logic. Tests are organized to mirror the
 * use-case test document produced by the design team.
 * Author: Sean Balbale
 * Date: 4/21/2026
 */

package edu.trincoll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the PlayGames driver class.
 * Each test focuses on a single method, following the unit-test guidelines
 * in the implementation specification (test before integration, stub where
 * needed, verify and mark passed/failed).
 */
class PlayGamesTest {

    // Preserve original stdout so it can be restored after every test
    private final PrintStream originalOut = System.out;

    // Captures text that PlayGames prints so assertions can inspect it
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
    // displayWelcomeMessage() — sunny-day tests
    // -----------------------------------------------------------------------

    /**
     * Verifies the welcome banner includes the game title text.
     */
    @Test
    @DisplayName("Welcome message contains the 'Game of Games' title")
    void testWelcomeMessageContainsTitle() {
        PlayGames game = new PlayGames();
        game.displayWelcomeMessage();
        assertTrue(output().contains("Game of Games"),
                "The welcome banner must contain the phrase 'Game of Games'");
    }

    /**
     * Verifies the welcome banner contains decorative border characters.
     */
    @Test
    @DisplayName("Welcome message contains a decorative border character '='")
    void testWelcomeMessageContainsBorder() {
        PlayGames game = new PlayGames();
        game.displayWelcomeMessage();
        assertTrue(output().contains("="),
                "The welcome banner should include '=' border characters for readability");
    }

    // -----------------------------------------------------------------------
    // updateOverallScoreboard() — sunny-day tests
    // (Design test: Overall Game of Games, Sunny Day #4 / #5 / #6)
    // -----------------------------------------------------------------------

    /**
     * Verifies one user win is reflected on the printed scoreboard.
     */
    @Test
    @DisplayName("User win: scoreboard displays 'You: 1'")
    void testScoreboardAfterOneUserWin() {
        PlayGames game = new PlayGames();
        game.updateOverallScoreboard(true);
        assertTrue(output().contains("You: 1"),
                "After one user win the scoreboard should show 'You: 1'");
    }

    /**
     * Verifies one computer win is reflected on the printed scoreboard.
     */
    @Test
    @DisplayName("Computer win: scoreboard displays 'Computer: 1'")
    void testScoreboardAfterOneComputerWin() {
        PlayGames game = new PlayGames();
        game.updateOverallScoreboard(false);
        assertTrue(output().contains("Computer: 1"),
                "After one computer win the scoreboard should show 'Computer: 1'");
    }

    /**
     * Verifies mixed outcomes accumulate correctly on the scoreboard.
     */
    @Test
    @DisplayName("Scoreboard accurately accumulates multiple mixed results")
    void testScoreboardAccumulatesCorrectly() {
        PlayGames game = new PlayGames();

        // Simulate: user wins twice, computer wins once → You: 2 Computer: 1
        game.updateOverallScoreboard(true);
        game.updateOverallScoreboard(true);
        game.updateOverallScoreboard(false);

        String allOutput = output();
        assertTrue(allOutput.contains("You: 2") && allOutput.contains("Computer: 1"),
                "Accumulated scoreboard must reflect all recorded wins correctly");
    }

    // -----------------------------------------------------------------------
    // displayFinalTally() — sunny-day tests
    // (Design test: Overall Game of Games, Sunny Day #4)
    // -----------------------------------------------------------------------

    /**
     * Verifies the final-tally output prints the expected section header.
     */
    @Test
    @DisplayName("Final tally output contains the 'Final Tally' header")
    void testFinalTallyHeader() {
        PlayGames game = new PlayGames();
        game.displayFinalTally();
        assertTrue(output().contains("Final Tally"),
                "displayFinalTally() must print a header containing 'Final Tally'");
    }

    /**
     * Verifies initial final-tally output reports zero wins for both sides.
     */
    @Test
    @DisplayName("Final tally shows zero wins when no games have been played")
    void testFinalTallyZeroWinsInitially() {
        PlayGames game = new PlayGames();
        game.displayFinalTally();
        assertTrue(output().contains("0"),
                "When no games have been played, the final tally must report 0 for both sides");
    }

    /**
     * Verifies final-tally output reflects wins already accumulated in session.
     */
    @Test
    @DisplayName("Final tally reflects wins accumulated before quitting")
    void testFinalTallyReflectsSessionWins() {
        PlayGames game = new PlayGames();

        // Play two games so there is something non-zero to display
        game.updateOverallScoreboard(true);
        game.updateOverallScoreboard(false);

        // Reset the capture buffer so only the final-tally output is checked
        capturedOutput.reset();

        game.displayFinalTally();

        assertTrue(output().contains("1"),
                "The final tally should report the wins that were accumulated during the session");
    }

    // -----------------------------------------------------------------------
    // declareOverallWinner() — sunny-day and rainy-day tests
    // (Design test: Overall Game of Games, Sunny Day #5 / #6; Rainy Day #3)
    // -----------------------------------------------------------------------

    /**
     * Verifies the user is declared winner when user wins exceed computer wins.
     */
    @Test
    @DisplayName("User is declared Game of Games winner when user has more wins")
    void testDeclareWinnerUser() {
        PlayGames game = new PlayGames();
        game.updateOverallScoreboard(true); // User: 1, Computer: 0
        capturedOutput.reset();

        game.declareOverallWinner();

        String result = output();
        assertTrue(result.contains("Congratulations") || result.contains("You"),
                "User should be declared winner when they have more total wins");
    }

    /**
     * Verifies the computer is declared winner when computer wins are higher.
     */
    @Test
    @DisplayName("Computer is declared Game of Games winner when computer has more wins")
    void testDeclareWinnerComputer() {
        PlayGames game = new PlayGames();
        game.updateOverallScoreboard(false); // User: 0, Computer: 1
        capturedOutput.reset();

        game.declareOverallWinner();

        assertTrue(output().contains("Computer"),
                "Computer should be declared winner when it has more total wins");
    }

    /**
     * Verifies draw output is shown when user and computer win totals are equal.
     */
    @Test
    @DisplayName("Draw is declared when user and computer have equal wins (Rainy Day #3)")
    void testDeclareWinnerDraw() {
        PlayGames game = new PlayGames();
        game.updateOverallScoreboard(true); // 1-1 tie
        game.updateOverallScoreboard(false);
        capturedOutput.reset();

        game.declareOverallWinner();

        assertTrue(output().contains("draw"),
                "A draw message must be displayed when both players have identical win counts");
    }

    // -----------------------------------------------------------------------
    // routeToGame() — sunny-day routing tests
    // Stubs in each game class return false immediately, so no Scanner input
    // is consumed and no crash should occur.
    // (Design test: Overall Game of Games, Sunny Day #1 / #2)
    // -----------------------------------------------------------------------

    /**
     * Verifies menu choice 1 routes to Guess the Number without exceptions.
     */
    @Test
    @DisplayName("Choice 1 routes to Guess the Number without throwing")
    void testRouteToGuessTheNumber() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(1),
                "Routing choice 1 to Guess the Number must not throw any exception");
        assertTrue(output().contains("Guess the Number"),
                "The routing output should confirm 'Guess the Number' was selected");
    }

    /**
     * Verifies menu choice 2 routes to Coin Flip without exceptions.
     */
    @Test
    @DisplayName("Choice 2 routes to Coin Flip without throwing")
    void testRouteToCoinFlip() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(2),
                "Routing choice 2 to Coin Flip must not throw any exception");
        assertTrue(output().contains("Coin Flip"),
                "The routing output should confirm 'Coin Flip' was selected");
    }

    /**
     * Verifies menu choice 3 routes to Even/Odd without exceptions.
     */
    @Test
    @DisplayName("Choice 3 routes to Even/Odd without throwing")
    void testRouteToEvenAndOdd() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(3),
                "Routing choice 3 to Even/Odd must not throw any exception");
        assertTrue(output().contains("Even"),
                "The routing output should confirm 'Even/Odd' was selected");
    }

    /**
     * Verifies menu choice 4 routes to Find the Thimble without exceptions.
     */
    @Test
    @DisplayName("Choice 4 routes to Find the Thimble without throwing")
    void testRouteToFindTheThimble() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(4),
                "Routing choice 4 to Find the Thimble must not throw any exception");
        assertTrue(output().contains("Thimble"),
                "The routing output should confirm 'Find the Thimble' was selected");
    }

    /**
     * Verifies menu choice 5 routes to Find the Red Thread without exceptions.
     */
    @Test
    @DisplayName("Choice 5 routes to Find the Red Thread without throwing")
    void testRouteToFindTheRedThread() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(5),
                "Routing choice 5 to Find the Red Thread must not throw any exception");
        assertTrue(output().contains("Red Thread"),
                "The routing output should confirm 'Find the Red Thread' was selected");
    }

    // -----------------------------------------------------------------------
    // routeToGame() — rainy-day invalid-choice test
    // (Design test: Overall Game of Games, Rainy Day #1 / #2)
    // -----------------------------------------------------------------------

    /**
     * Verifies invalid routing choices report an error and do not crash.
     */
    @Test
    @DisplayName("Invalid routing choice prints an error and does not throw")
    void testRouteToGameInvalidChoice() {
        PlayGames game = new PlayGames();
        assertDoesNotThrow(() -> game.routeToGame(99),
                "An out-of-range routing choice must not crash the program");

        String result = output();
        assertTrue(result.contains("Error") || result.contains("Invalid"),
                "An invalid routing choice must display an error message to the user");
    }

    // -----------------------------------------------------------------------
    // isTestMode flag — unit tests for the global test-mode toggle
    // -----------------------------------------------------------------------

    /**
     * Verifies test mode is disabled by default.
     */
    @Test
    @DisplayName("isTestMode is false before the --test flag is applied")
    void testIsTestModeDefaultIsFalse() {
        // Guarantee a clean state independent of test-execution order
        PlayGames.isTestMode = false;
        assertFalse(PlayGames.isTestMode,
                "isTestMode should default to false in player mode");
    }

    /**
     * Verifies test mode can be enabled for diagnostics.
     */
    @Test
    @DisplayName("isTestMode can be set to true to activate test mode")
    void testIsTestModeCanBeEnabled() {
        boolean original = PlayGames.isTestMode;
        try {
            PlayGames.isTestMode = true;
            assertTrue(PlayGames.isTestMode,
                    "isTestMode should be true once the test-mode flag is activated");
        } finally {
            // Restore so this test does not pollute other tests
            PlayGames.isTestMode = original;
        }
    }
}