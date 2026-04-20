package edu.trincoll;

public final class FindTheThimble {
    private int userScore;
    private int compScore;
    private int winThreshold;

    public boolean playGame() {
        // TODO: Implement full game loop and return true when user wins.
        return false;
    }

    private int getBestOutOfValue() {
        // TODO: Prompt for and validate an odd best-out-of value.
        return 0;
    }

    private void calculateWinThreshold(int bestOutOf) {
        // TODO: Confirm threshold calculation behavior for all valid inputs.
        this.winThreshold = (bestOutOf + 1) / 2;
    }

    private char hideThimble() {
        // TODO: Randomly hide the thimble in left or right hand.
        return 'L';
    }

    private char getPlayerGuess() {
        // TODO: Prompt user for L/R guess and validate input.
        return 'L';
    }

    private void checkRoundWinner(char guess, char hidden) {
        // TODO: Compare guess to hidden side and update scores.
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
    }
}
