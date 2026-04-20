package edu.trincoll;

public final class CoinFlip {
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

    private char getPlayerCall() {
        // TODO: Prompt user to call heads or tails and validate input.
        return 'H';
    }

    private char flipCoin() {
        // TODO: Return a randomized H/T coin result.
        return 'H';
    }

    private void checkRoundWinner(char call, char result) {
        // TODO: Compare call vs. result, print outcome, and update score.
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
