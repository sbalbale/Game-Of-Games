/**
 * File: GuessTheNumber.java
 * Description: This class implements the logic for a "Guess the Number" game where the user 
 * tries to guess a randomly generated number within a specified range and limited number of 
 * guesses. The class handles user input, generates the target number, evaluates guesses, 
 * and manages the game flow. It also includes functionality to reveal the target number when 
 * running in test mode for debugging purposes.
 * Author: Sean Balbale and Juan Marcanocuellar
 * Date: 4/26/2026
 **/

package edu.trincoll;

import java.util.Random;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;
    private final GetInput input = new GetInput();

    // Main game loop — returns true if user wins, false if computer wins
    public boolean playGame() {
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
}