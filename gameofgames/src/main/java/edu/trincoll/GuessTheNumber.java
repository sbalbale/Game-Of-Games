package edu.trincoll;
import java.util.Random;

public final class GuessTheNumber {
    private int targetNumber;
    private int maxGuesses;
    private int currentGuessesLeft;
    private int numberRange;

    public boolean playGame() {

        numberRange = getRange();

        generateTargetNumber(range);
        maxGuesses =  getMaxGuesses(range);

        currentGuessesLeft = maxGuesses;

        while(currentGuessesLeft--){
            if(evaluateGuess(getPlayerGuess())){
                printf("Correct! You have won this round");
                return true;
            }

            printf("Wrong guess! Guesses left: %d", currentGuessesLeft - 1);
        }

        printf("Wrong! The computer wins this round!");
        return false;
    }

    private int getRange() {
        printf("User, please input the range of numbers for this match")

        int range;
        scanf("%d", &range);

        return range;
    }

    private int  getMaxGuesses(int range) {
        printf("User, please input the max number of guesses")
        
        int guesses;
        scanf("%d", &guesses);

        while(guesses > numberRange/2){
            printf("The number of guesses is over half the range. Please type a different number:");
            scanf("%d", &guesses);
        }

        return guess;
    }

    private void generateTargetNumber(int range) {

        Random random = new Random();

        this.targetNumber = random.nextInt(range);

    }

    private int getPlayerGuess() {
        printf("User, please input tyour guess")

        int guess;
        scanf("%d", &guess);

        return guess;
    }

    private boolean evaluateGuess(int guess) {
        if(guess == targetNumber)return true;
        return false;
    }

}
