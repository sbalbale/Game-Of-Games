/**
 * File: GuessTheNumber.java
 * Purpose: Implementation of the Guess the Number Game
 * Author: Sean Balbale and Juan Marcano
 * Date: 4/26/2026
 **/

package edu.trincoll;

import java.util.Random;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;
    private int numberOfRounds;
    private int userScore = 0;
    private int compScore = 0;

    private final GetInput input = new GetInput();

    // Main game loop — returns true if user wins, false if computer wins
    public boolean playGame() {
        numberOfRounds = getNumberOfRounds();
        for (int round = 1; round <= numberOfRounds; round++) {
            System.out.println("\n--- Round " + round + " ---");
            boolean userWonRound = playRound();
            if (userWonRound) {
                userScore++;
                if (userScore > numberOfRounds / 2) { // User has won more than half the rounds, so they win the game
                    declareGameResult();
                    return true;
                }
            } else {
                compScore++;
                if (++compScore > numberOfRounds / 2) { // Computer has won more than half the rounds, so it wins the
                                                        // game
                    declareGameResult();
                    return false;
                }
            }
        }
        declareGameResult();
        return false; // User did not win any round

    }

    private boolean playRound() {
        numberRange = getRange();
        generateTargetNumber(numberRange);
        maxGuesses = getMaxGuesses(numberRange);
        currentGuessesLeft = maxGuesses;

        // Reveal target number if running in test mode
        if (PlayGames.isTestMode) {
            System.out.println("[TEST MODE] Target number is: " + targetNumber);
        }

        while (currentGuessesLeft > 0) {
            if (evaluateGuess(getPlayerGuess())) {
                declareRoundResult(true);
                return true;
            }
            declareRoundResult(false);
        }
        System.out.println("Wrong! The computer wins this round!");
        return false;
    }

    // Prompts the user to input the range of numbers for the game
    private int getRange() {
        System.out.println("User, please input the range of numbers for this match");
        return input.getIntInRange(2, Integer.MAX_VALUE);
    }

    // Prompts the user for max guesses and validates it does not exceed half the
    // range
    private int getMaxGuesses(int range) {
        int maxAllowedGuesses = range / 2;
        System.out.println("User, please input the max number of guesses");
        return input.getIntInRange(1, maxAllowedGuesses);
    }

    // Generates a random target number between 1 and range inclusive
    // nextInt(range) produces 0 to range-1, so +1 shifts to 1 to range
    private void generateTargetNumber(int range) {
        Random random = new Random();
        this.targetNumber = random.nextInt(range) + 1;
    }

    // Prompts the user to input their guess
    private int getPlayerGuess() {
        System.out.println("User, please input your guess");
        return input.getIntInRange(1, numberRange);
    }

    // Evaluates the user's guess and decrements currentGuessesLeft
    private boolean evaluateGuess(int guess) {
        if (guess == targetNumber) {
            return true;
        }

        currentGuessesLeft--;
        return false;
    }

    // Prints the result of the round — correct guess or wrong with guesses
    // remaining
    private void declareRoundResult(boolean isCorrect) {
        if (isCorrect) {
            System.out.println("Correct! You have won this round!");
        } else {
            System.out.println("Wrong guess! Guesses left: " + currentGuessesLeft);
        }
    }

    private int getNumberOfRounds() {
        System.out.println("How many rounds would you like to play? (Best of how many rounds?)");
        return input.getIntInRange(1, Integer.MAX_VALUE);
    }

    // Prints the final game result once the user has either won or used all guesses
    private void declareGameResult() {
        if (currentGuessesLeft > 0) {
            System.out.println("You win Guess the Number!");
        } else {
            System.out.println("The computer wins Guess the Number!");
        }
    }
}
