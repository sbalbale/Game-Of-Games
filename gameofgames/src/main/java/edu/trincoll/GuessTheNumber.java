package edu.trincoll;
import java.util.Random;
import java.util.Scanner;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;

    Scanner scanner = new Scanner(System.in);

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
        System.out.println("User, please input the range of numbers for this match\n");

        int range = scanner.nextInt();

        return range;
    }

    private int  getMaxGuesses(int range) {
        System.out.println("User, please input the max number of guesses\n");
        
        int guesses = scanner.nextInt();

        while(guesses > numberRange/2){
            System.out.println("The number of guesses is over half the range. Please type a different number:\n");
            guesses = scanner.nextInt();
        }

        return guesses;
    }

    private void generateTargetNumber(int range) {

        Random random = new Random();

        this.targetNumber = random.nextInt(range);
        return;
    }

    private int getPlayerGuess() {
        System.out.println("User, please input your guess\n");

        int guess = scanner.nextInt();

        return guess;
    }

    private boolean evaluateGuess(int guess) {
        if(guess == targetNumber)return true;
        return false;
    }

}
