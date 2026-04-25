package edu.trincoll;
import java.util.Random;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;

    private final GetInput input = new GetInput();

    public boolean playGame() {

        numberRange = getRange();

        generateTargetNumber(numberRange);
        maxGuesses =  getMaxGuesses(numberRange);

        currentGuessesLeft = maxGuesses;

        while(currentGuessesLeft-- > 0){
            if(evaluateGuess(getPlayerGuess())){
                System.out.println("Correct! You have won this round\n");
                return true;
            }

            System.out.println("Wrong guess! Guesses left: " + currentGuessesLeft);
        }

        System.out.println("Wrong! The computer wins this round!\n");
        return false;
    }

    private int getRange() {
        return input.getInt("User, please input the range of numbers for this match");
    }

    private int  getMaxGuesses(int range) {
        int guesses = input.getInt("User, please input the max number of guesses");

        while(guesses > numberRange/2){
            guesses = input.getInt("The number of guesses is over half the range. Please type a different number:");
        }

        return guesses;
    }

    private void generateTargetNumber(int range) {

        Random random = new Random();

        this.targetNumber = random.nextInt(range);
        return;
    }

    private int getPlayerGuess() {
        return input.getInt("User, please input your guess");
    }

    private boolean evaluateGuess(int guess) {
        if(guess == targetNumber)return true;
        return false;
    }

}
