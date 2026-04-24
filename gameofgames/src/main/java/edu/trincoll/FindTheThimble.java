package edu.trincoll;
import java.util.Random;
import java.util.Scanner;

public final class FindTheThimble {
    private int userScore;
    private int compScore;
    private int winThreshold;

    private Scanner scanner =  new Scanner(System.in);
    private Random random = new Random();

    public boolean playGame() {
        // TODO: Implement full game loop and return true when user wins.

        int bestOutOf = getBestOutOfValue();
        calculateWinThreshold(bestOutOf);

        userScore = 0;
        compScore = 0;

        while (true) {
            char hidden = hideThimble();
            char guess = getPlayerGuess();
            boolean userWon = checkRoundWinner(guess, hidden);
            updateRoundScore(userWon);
            if (checkGameWinner()){
                declareGameWinner();
                return userScore > compScore;
            }


        }
    }

    private int getBestOutOfValue() {
        // TODO: Prompt for and validate an odd best-out-of value.
        while (true) {
            System.out.print("Enter your best out-of-value (Must be odd): ");
            int bestOutOfValue = scanner.nextInt();
            if (bestOutOfValue % 2 == 1) {
                return bestOutOfValue;
            }
            System.out.println("Invalid entry. The value must be an odd whole number. Please try again:");
        }
    }

    private void calculateWinThreshold(int bestOutOf) {
        // TODO: Confirm threshold calculation behavior for all valid inputs.
        this.winThreshold = (bestOutOf + 1) / 2;
    }

    private char hideThimble() {
        // TODO: Randomly hide the thimble in left or right hand.
        return random.nextBoolean() ? 'L' : 'R';
        }

    private char getPlayerGuess() {
        // TODO: Prompt user for L/R guess and validate input.
        while (true) {
            System.out.println("Pick the hand you think the thimble is in (L/R):");
            String playerGuess = scanner.next().toUpperCase();
            if (playerGuess.equals("L") || playerGuess.equals("R")) {
                return playerGuess.charAt(0);
            }
            System.out.println("Invalid input. Please enter L for left or R for right:");
        }
    }

    private boolean checkRoundWinner(char guess, char hidden) {
        // TODO: Compare guess to hidden side and update scores.
        boolean won;
        if (guess == hidden) {
            System.out.println("Correct! You win this round.");
            won = true;
        }
        else {
            System.out.println("Wrong! The computer wins this round.");
            won = false;
        }
        System.out.println("Current score: ");
        System.out.println("You: " + userScore);
        System.out.println("Computer: " + compScore);
        System.out.println();
        return won;
    }

    private void updateRoundScore(boolean userWonRound) {
        if (userWonRound) {
            userScore++;
        } else {
            compScore++;
        }
    }

    private boolean checkGameWinner() {
        return userScore >= winThreshold || compScore >= winThreshold;
    }

    private void declareGameWinner() {
        // TODO: Print final winner once threshold is reached.
        if (userScore > compScore) {
            System.out.println("You win, Find the Thimble!");
        } else {
            System.out.println("The computer wins, Find the Thimble!");
        }
        System.out.println();
        System.out.println("Final score: ");
        System.out.println("You: " + userScore);
        System.out.println("Computer: " + compScore);
        System.out.println();
    }
}
