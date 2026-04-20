package edu.trincoll;

public final class EvenAndOdd {
    private int userScore;
    private int compScore;
    private int winThreshold;
    private boolean userIsEven;

    public boolean playGame() {
        // TODO: Implement full game loop and return true when user wins.
        return false;
    }

    private void assignRoles() {
        // TODO: Prompt user for even/odd role and set userIsEven.
        this.userIsEven = true;
    }

    private int getBestOutOfValue() {
        // TODO: Prompt for and validate an odd best-out-of value.
        return 0;
    }

    private void calculateWinThreshold(int bestOutOf) {
        // TODO: Confirm threshold calculation behavior for all valid inputs.
        this.winThreshold = (bestOutOf + 1) / 2;
    }

    private int getPlayerNumber() {
        // TODO: Prompt for and validate player number input.
        return 0;
    }

    private int getComputerNumber() {
        // TODO: Generate a computer-selected number for the round.
        return 0;
    }

    private int calculateSum(int userNum, int compNum) {
        // TODO: Keep round-sum logic aligned with game rules.
        return userNum + compNum;
    }

    private void determineRoundWinner(int sum) {
        // TODO: Determine winner from parity and assigned roles.
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
