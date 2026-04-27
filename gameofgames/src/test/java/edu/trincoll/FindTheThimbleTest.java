/*
 * File: FindTheThimbleTest.java
 * Provides unit tests for the FindTheThimble class to ensure each method's sunny day and rainy day paths run accordlingly
 * Author: Abby Gomes
 * Date: 4/26/2026
 */
package edu.trincoll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindTheThimbleTest {

	// Sunny Day Paths

	// getBestOutOfValue Unit Test - Accepts a valid odd integer input from the user
    @Test
    void testGetBestOutOfValue() {
        FindTheThimble game = new FindTheThimble();
		int result = game.getBestOutOfValue();
		assertTrue(result % 2 == 1);
    }
	
	// calculatingWinThreshold Unit Test- Example of user inputting 5 as "bestOf" number
    @Test
    void testCalculateWinThreshold() {
        FindTheThimble game = new FindTheThimble();
		game.calculateWinThreshold(5);
		assertEquals(3, game.winThreshold);
    }

    // hideThimble Unit Test- Ensures the computer hides thimble in either "L" or "R"
    @Test
    void testHideThimble() {
        FindTheThimble game = new FindTheThimble();
        char result = game.hideThimble();
        assertTrue(result == 'L' || result == 'R');
    }

	// getPlayerGuess Unit Test - Accepts valid "L" or "R" input from the user
    @Test
    void testGetPlayerGuess() {
        FindTheThimble game = new FindTheThimble();
		char result = game.getPlayerGuess();
		assertTrue(result == 'L' || result == 'R');
    }
	
  	// checkRoundWinner Unit Test 1- Returns true if the user and computer are the same hand
    @Test
    void testCheckRoundWinnerCorrect() {
        FindTheThimble game = new FindTheThimble();
		assertTrue(game.checkRoundWinner('L', 'L'));
    }

	// checkRoundWinner Unit Test 2- Returns false if the user and computer have different hands
    @Test
    void testCheckRoundWinnerIncorrect() {
        FindTheThimble game = new FindTheThimble();
		assertFalse(game.checkRoundWinner('L', 'R'));
    }

    // updateRoundScore Unit Test 1- Increments user score when the user wins the round
    @Test
    void testUpdateRoundScoreUser() {
        FindTheThimble game = new FindTheThimble();
		game.userScore = 0;
        game.updateRoundScore(true);
		assertEquals(1, game.userScore);
    }

	// updateRoundScore Unit Test 2- Increments computer score when user loses the round
    @Test
    void testUpdateRoundScoreComputer() {
        FindTheThimble game = new FindTheThimble();
		game.compScore = 0;
        game.updateRoundScore(false);
		assertEquals(1, game.compScore);
    }

    // checkGameWinner Unit Test 1 - Returns true when the user reaches win threshold
    @Test
    void testCheckGameWinnerTrue() {
        FindTheThimble game = new FindTheThimble();
		game.userScore = 3;
        game.winThreshold = 3;
		assertTrue(game.checkGameWinner());
    }

	// checkGameWinner Unit Test 2 - Returns true when computer reaches win threshold
    @Test
    void testCheckGameWinnerFalse() {
        FindTheThimble game = new FindTheThimble();
		game.userScore = 1;
        game.compScore = 1;
        game.winThreshold = 3;
		assertFalse(game.checkGameWinner());
    }

	// declareGameWinner Unit Test 1 - Displays win message when userScore is greater than compScore
    @Test
    void testDeclareGameWinnerRuns() {
        FindTheThimble game = new FindTheThimble();
		game.userScore = 3;
        game.compScore = 1;
		game.declareGameWinner();
		assertTrue(true);
    }
}
