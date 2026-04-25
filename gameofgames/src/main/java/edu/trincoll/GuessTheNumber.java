/**
 * GuessTheNumber.java
 * 
 * Author: Juan Marcano
 * 
 * Implements the Guess the Number 
 * 
 * Date: 04/25/26
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
        return input.getInt("User, please input the range of numbers for this match");
    }

    // Prompts the user for max guesses and validates it does not exceed half the range
    private int getMaxGuesses(int range) {
        int guesses = input.getInt("User, please input the max number of guesses");
        while (guesses > range / 2) {
            guesses = input.getInt("The number of guesses is over half the range. Please type a different number:");
        }
        return guesses;
    }

    // Generates a random target number between 1 and range inclusive
    // nextInt(range) produces 0 to range-1, so +1 shifts to 1 to range
    private void generateTargetNumber(int range) {
        Random random = new Random();
        this.targetNumber = random.nextInt(range) + 1;
    }

    // Prompts the user to input their guess
    private int getPlayerGuess() {
        return input.getInt("User, please input your guess");
    }

    // Evaluates the user's guess and decrements currentGuessesLeft
    private boolean evaluateGuess(int guess) {
        currentGuessesLeft--;
        return guess == targetNumber;
    }

    // Prints the result of the round — correct guess or wrong with guesses remaining
    private void declareRoundResult(boolean userWon) {
        if (userWon) {
            System.out.println("Correct! You have won this round!");
        } else {
            System.out.println("Wrong guess! Guesses left: " + currentGuessesLeft);
        }
    }
}