/*
 * File: FindTheThimble.java
 * Purpose: Implements the Find the Thimble game. The computer randomly hides a thimble
 * in one of two hands (L or R), and the user guesses which hand. Played as a best-out-of
 * series; the first player to reach the win threshold wins the game.
 * Author: Abby Gomes and Sean Balbale
 * Date: 4/20/2026
 */
package edu.trincoll;

import java.util.Random;

public final class FindTheThimble {

    // Score trackers for the current game session
    private int userScore;
    private int compScore;
    private int winThreshold;

    // Shared input utility — validates all user input
    private final GetInput input = new GetInput();
    private final Random random = new Random();

    /**
     * Runs a full Find the Thimble game session.
     * @return true if the user won, false if the computer won
     */
    public boolean playGame() {

        int bestOutOf = getBestOutOfValue();
        calculateWinThreshold(bestOutOf);

        // Reset scores for a fresh game
        userScore = 0;
        compScore = 0;

        System.out.println("\nFirst to " + winThreshold + " wins takes the game.");

        while (true) {
            char hidden = hideThimble();

            // Test mode: reveal the hidden hand before the player guesses
            if (PlayGames.isTestMode) {
                System.out.println("[TEST MODE] The thimble is in hand: " + hidden);
            }

            char guess = getPlayerGuess();
            boolean userWonRound = checkRoundWinner(guess, hidden);
            updateRoundScore(userWonRound);

            // Display running score after each round
            System.out.println("Score — You: " + userScore + " | Computer: " + compScore);
            System.out.println();

            if (checkGameWinner()) {
                declareGameWinner();
                return userScore > compScore;
            }
        }
    }

    /**
     * Prompts the user for a valid odd best-out-of value using GetInput.
     * @return a validated odd positive integer
     */
    private int getBestOutOfValue() {
        System.out.println("Enter the best-out-of value (must be an odd number):");
        return input.getOddInt();
    }

    /**
     * Sets the win threshold from the best-out-of value.
     * @param bestOutOf the validated odd best-out-of value chosen by the user
     */
    private void calculateWinThreshold(int bestOutOf) {
        this.winThreshold = (bestOutOf + 1) / 2;
    }

    /**
     * Randomly hides the thimble in the left or right hand.
     * @return 'L' for left hand, 'R' for right hand
     */
    private char hideThimble() {
        return random.nextBoolean() ? 'L' : 'R';
    }

    /**
     * Prompts the player for a hand guess and validates it via GetInput.
     * @return 'L' or 'R', guaranteed valid
     */
    private char getPlayerGuess() {
        System.out.println("Guess which hand holds the thimble — enter L for left or R for right:");
        return input.getChar(new char[]{'L', 'R'});
    }

    /**
     * Compares the player's guess to the hidden hand and prints the round result.
     * @param guess  the character the user guessed ('L' or 'R')
     * @param hidden the character representing the hand the thimble is actually in
     * @return true if the user guessed correctly, false otherwise
     */
    private boolean checkRoundWinner(char guess, char hidden) {
        System.out.println("The thimble was in hand: " + hidden);
        if (guess == hidden) {
            System.out.println("Correct! You win this round.");
            return true;
        }
        System.out.println("Wrong! The computer wins this round.");
        return false;
    }

    /**
     * Updates the appropriate score counter based on who won the round.
     * @param userWonRound true if the user won the round, false if the computer did
     */
    private void updateRoundScore(boolean userWonRound) {
        if (userWonRound) {
            userScore++;
        } else {
            compScore++;
        }
    }

    /**
     * Checks whether either player has reached the win threshold.
     * @return true if the game is over, false if play should continue
     */
    private boolean checkGameWinner() {
        return userScore >= winThreshold || compScore >= winThreshold;
    }

    /**
     * Prints the final game result and score once the win threshold is reached.
     */
    private void declareGameWinner() {
        if (userScore > compScore) {
            System.out.println("You win, Find the Thimble!");
        } else {
            System.out.println("The computer wins, Find the Thimble!");
        }
        System.out.println("Final score — You: " + userScore + " | Computer: " + compScore);
        System.out.println();
    }
}