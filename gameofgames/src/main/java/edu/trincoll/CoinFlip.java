/*
 * File: CoinFlip.java
 * Purpose: Mini-game in which the user calls heads or tails, a coin is flipped,
 * and the first player to reach the win threshold in a best-out-of series wins.
 * Author: Sean Balbale
 * Date: 4/20/2026
 */

package edu.trincoll;

import java.util.Random;

public final class CoinFlip {

    // Round score trackers for this game instance
    private int userScore;
    private int compScore;

    // Number of round wins required to claim the game
    private int winThreshold;

    // Shared input utility and random number source
    private final GetInput input = new GetInput();
    private final Random rand = new Random();

    // Character constants for coin sides
    private static final char HEADS = 'H';
    private static final char TAILS = 'T';

    /**
     * Controls the full execution loop for Coin Flip.
     * Resets scores, asks for best-out-of value, then runs rounds until
     * one player reaches the win threshold.
     *
     * @return true if the user wins the game, false if the computer wins.
     */
    public boolean playGame() {
        // Reset scores for a fresh game
        userScore = 0;
        compScore = 0;

        // Establish how many rounds will be played and set the threshold
        int bestOutOf = getBestOutOfValue();
        calculateWinThreshold(bestOutOf);

        System.out.println("\nFirst to " + winThreshold + " wins takes the game.");

        // Round loop — continues until one player reaches the win threshold
        while (!checkGameWinner()) {
            System.out.println("\n--- Round " + (userScore + compScore + 1) + " ---");

            char result = flipCoin();

            // In test mode, reveal the flip result before the user sees it announced
            if (PlayGames.isTestMode) {
                System.out.println("[TEST MODE] Coin will land on: " + (result == HEADS ? "Heads" : "Tails"));
            }

            char call = getPlayerCall();

            checkRoundWinner(call, result);
        }

        declareGameWinner();
        return userScore > compScore;
    }

    /**
     * Prompts the user to enter an odd positive integer as the best-out-of value.
     *
     * @return integer, the validated odd best-out-of value.
     */
    private int getBestOutOfValue() {
        System.out.println("Enter the best-out-of value (must be an odd number):");
        return input.getOddInt();
    }

    /**
     * Calculates and stores the number of round wins required to win the game.
     * Formula: (bestOutOf + 1) / 2 using integer division.
     *
     * @param bestOutOf integer, the validated odd best-out-of value.
     */
    private void calculateWinThreshold(int bestOutOf) {
        this.winThreshold = (bestOutOf + 1) / 2;
    }

    /**
     * Prompts the user to call heads or tails before each flip.
     *
     * @return char, 'H' for heads or 'T' for tails.
     */
    private char getPlayerCall() {
        System.out.println("Call it — enter H for heads or T for tails:");
        return input.getChar(new char[]{HEADS, TAILS});
    }

    /**
     * Randomly generates the coin flip result.
     *
     * @return char, 'H' for heads or 'T' for tails.
     */
    private char flipCoin() {
        return rand.nextBoolean() ? HEADS : TAILS;
    }

    /**
     * Compares the user's call to the flip result, announces the result,
     * and delegates score tracking to updateRoundScore.
     *
     * @param call   char, the user's call ('H' or 'T').
     * @param result char, the coin flip outcome ('H' or 'T').
     */
    private void checkRoundWinner(char call, char result) {
        // Display the coin result first, per the use case flow
        String resultWord = (result == HEADS) ? "Heads" : "Tails";
        System.out.println("The coin landed on: " + resultWord + ".");

        boolean userWonRound = (call == result);

        if (userWonRound) {
            System.out.println("Correct call! You win this round.");
        } else {
            System.out.println("Wrong call! The computer wins this round.");
        }

        updateRoundScore(userWonRound);

        // Display the updated round scoreboard after every round
        System.out.println("You: " + userScore + ", Computer: " + compScore);
    }

    /**
     * Increments the appropriate player's round score.
     *
     * @param userWonRound boolean, true if the user won the round.
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
     *
     * @return boolean, true if the game is over.
     */
    private boolean checkGameWinner() {
        return userScore >= winThreshold || compScore >= winThreshold;
    }

    /**
     * Prints the final game winner once the win threshold has been reached.
     */
    private void declareGameWinner() {
        System.out.println();
        if (userScore > compScore) {
            System.out.println("You win Coin Flip!");
        } else {
            System.out.println("The computer wins Coin Flip!");
        }
    }
}