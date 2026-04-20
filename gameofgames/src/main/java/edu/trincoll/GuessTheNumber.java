package edu.trincoll;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;

    public boolean playGame() {
        // TODO: Implement full game loop and return true when user wins.
        return false;
    }

    private int getRange() {
        // TODO: Prompt for and validate the upper bound of the guessing range.
        return 0;
    }

    private int getMaxGuesses(int range) {
        // TODO: Prompt for max guesses and validate against the chosen range.
        return 0;
    }

    private void generateTargetNumber(int range) {
        // TODO: Generate a random target number between 1 and range inclusive.
        this.targetNumber = 0;
    }

    private int getPlayerGuess() {
        // TODO: Prompt for and validate a single guess.
        return 0;
    }

    private boolean evaluateGuess(int guess) {
        // TODO: Compare guess to target and update guesses remaining.
        return false;
    }

    private void declareRoundResult(boolean isCorrect) {
        // TODO: Print win/loss messaging based on isCorrect.
    }
}
