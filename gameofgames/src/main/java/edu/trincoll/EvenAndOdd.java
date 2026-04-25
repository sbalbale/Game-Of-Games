/**
 * EvenAndOdd.java
 * Author: Juan Marcano
 * 
 * Implementation of the Even and Odd Game
 * 
 * Date: 04/25/26
 */

package edu.trincoll;
import java.util.Random;

public final class EvenAndOdd {
    private int userScore = 0;
    private int compScore = 0;
    private int winThreshold;   // minimum wins needed to win the game
    private int bestOutOf;      // total number of rounds to play
    private boolean userIsEven; // true if user chose even, false if odd
    private final GetInput input = new GetInput();

    // Main game loop — returns true if user wins, false if computer wins
    public boolean playGame() {
        assignRoles();
        getBestOutOfValue();
        calculateWinThreshold();

        while (bestOutOf-- > 0) {
            int userThrow = getPlayerNumber();
            int compThrow = getComputerNumber();
            int roundSum = calculateSum(userThrow, compThrow);
            determineRoundWinner(roundSum, userThrow, compThrow);
            // Exit early if someone has already reached the win threshold
            if (userScore >= winThreshold || compScore >= winThreshold) break;
        }
        return checkGameWinner();
    }

    // Asks the user to pick even or odd and validates their input
    private void assignRoles() {
        char role = input.getChar("Would you like to be even or odd? Type E for even or O for odd");
        while (role != 'E' && role != 'O') {
            role = input.getChar("Invalid Role! Please try again (E or O):");
        }
        this.userIsEven = (role == 'E');
    }

    // Asks how many rounds to play and ensures it's an odd number
    private void getBestOutOfValue() {
        int matches = input.getInt("The winner should be the best out of how many games?");
        while (matches % 2 == 0) {
            matches = input.getInt("Invalid value! Must be odd. Please try again:");
        }
        this.bestOutOf = matches;
    }

    // Win threshold is the majority — e.g. best of 5 means first to 3 wins
    private void calculateWinThreshold() {
        this.winThreshold = (this.bestOutOf + 1) / 2;
    }

    // Asks the user for a throw between 1 and 5 and validates it
    private int getPlayerNumber() {
        int userThrow = input.getInt("User, what is your throw? (1-5)");
        while (userThrow < 1 || userThrow > 5) {
            userThrow = input.getInt("Invalid number! Please try again (1-5):");
        }
        return userThrow;
    }

    // Generates a random number between 1 and 5 for the computer
    private int getComputerNumber() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    // Returns the sum of both throws
    private int calculateSum(int userNum, int compNum) {
        return userNum + compNum;
    }

    // Determines who wins the round based on sum parity and the user's role
    private void determineRoundWinner(int sum, int userNum, int compNum) {
        boolean sumIsEven = sum % 2 == 0;
        // User wins if sum parity matches their chosen role
        boolean userWins = (sumIsEven && userIsEven) || (!sumIsEven && !userIsEven);

        String parity = sumIsEven ? "even" : "odd";
        if (userWins) {
            updateRoundScore(true);
            System.out.println(userNum + " + " + compNum + " = " + sum + " is " + parity + ", you win this round!");
        } else {
            updateRoundScore(false);
            System.out.println(userNum + " + " + compNum + " = " + sum + " is " + parity + ", computer wins this round!");
        }
    }

    // Increments the appropriate score based on who won the round
    private void updateRoundScore(boolean userWonRound) {
        if (userWonRound) {
            userScore++;
        } else {
            compScore++;
        }
    }

    // Checks if either player has reached the win threshold
    private boolean checkGameWinner() {
        if (userScore >= winThreshold) {
            declareGameWinner(true);
            return true;
        }
        declareGameWinner(false);
        return false;
    }

    // Prints the final game result
    private void declareGameWinner(boolean isUserWinner) {
        if (isUserWinner) {
            System.out.println("You win Even and Odd!");
        } else {
            System.out.println("The computer wins Even and Odd!");
        }
    }
}