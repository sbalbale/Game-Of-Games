/**
 * File: EvenAndOdd.java
 * Purpose: Implementation of the Even and Odd game.
 * Author: Sean Balbale and Juan Marcano
 * Date: 04/25/26
 */

package edu.trincoll;

import java.util.Random;

public final class EvenAndOdd {
    private int userScore;
    private int compScore;
    private int winThreshold;
    private boolean userIsEven;

    private final GetInput input = new GetInput();
    private final Random rand = new Random();

    /**
     * Runs a complete Even and Odd game.
     *
     * @return true if the user wins, false if the computer wins.
     */
    public boolean playGame() {
        userScore = 0;
        compScore = 0;

        assignRoles();
        int bestOutOf = getBestOutOfValue();
        calculateWinThreshold(bestOutOf);

        System.out.println("First to " + winThreshold + " wins takes the game.");

        while (!checkGameWinner()) {
            int userNum = getPlayerNumber();
            int compNum = getComputerNumber();
            int sum = calculateSum(userNum, compNum);
            String parity = sum % 2 == 0 ? "even" : "odd";

            System.out.println(userNum + " + " + compNum + " = " + sum + " is " + parity + ".");
            determineRoundWinner(sum);
            System.out.println("You: " + userScore + ", Computer: " + compScore);
        }

        declareGameWinner();
        return userScore > compScore;
    }

    private void assignRoles() {
        System.out.println("Would you like to be even or odd? Enter E for even or O for odd:");
        char role = input.getChar(new char[] { 'E', 'O' });
        userIsEven = role == 'E';

        if (userIsEven) {
            System.out.println("You are even.");
        } else {
            System.out.println("You are odd.");
        }
    }

    private int getBestOutOfValue() {
        System.out.println("The winner should be best out of how many rounds?");
        return input.getOddInt();
    }

    private void calculateWinThreshold(int bestOutOf) {
        winThreshold = (bestOutOf + 1) / 2;
    }

    private int getPlayerNumber() {
        System.out.println("User, what is your throw? (1-5)");
        return input.getIntInRange(1, 5);
    }

    private int getComputerNumber() {
        return rand.nextInt(5) + 1;
    }

    private int calculateSum(int userNum, int compNum) {
        return userNum + compNum;
    }

    private void determineRoundWinner(int sum) {
        boolean sumIsEven = sum % 2 == 0;
        boolean userWonRound = sumIsEven == userIsEven;

        updateRoundScore(userWonRound);

        if (userWonRound) {
            System.out.println("You win this round!");
        } else {
            System.out.println("Computer wins this round!");
        }
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
        System.out.println();
        if (userScore > compScore) {
            System.out.println("You win Even and Odd!");
        } else {
            System.out.println("The computer wins Even and Odd!");
        }
    }
}
