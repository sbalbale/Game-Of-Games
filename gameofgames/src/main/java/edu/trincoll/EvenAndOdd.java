package edu.trincoll;
import java.util.Random;

public final class EvenAndOdd {
    private int userScore = 0;
    private int compScore = 0;
    private int winThreshold;
    private int bestOutOf;
    private boolean userIsEven;

    public boolean playGame() {
        assignRoles();

        getBestOutOfValue();
        calculateWinThreshold();
        
        while (bestOutOf--){
            int userThrow = getPlayerNumber();
            int compThrow = getComputerNumber();

            int roundSum = calculateSum(userThrow, compThrow);

            determineRoundWinner(roundSum, userThrow, compThrow);

            if(userScore >= winThreshold || compScore >= winThreshold)break;
        }

        return checkGameWinner();
    }

    private void assignRoles() {
        printf("Would you like to be even or odd?\n Type E for even or O for odd\n");

        char role;
        scanf("%c", &role);

        if(role == 'E'){
            this.userIsEven = true;
        }
        else if (role == 'O'){
            this.userIsEven = false;
        }
        else{
            while(role != 'E' && role != 'O'){
                printf("Invalid Role! Please try again\n");
                scanf("%c", &role);
            }
        }

        return;
    }

    private void getBestOutOfValue() {
        printf("The winner should be the best out of how many games?\n");

        int matches;
        scanf("%d", &matches);

        while(matches %2 == 0){
            printf("Invalid value! Please try again:\n");
            scanf("%c", &matches);
        }

        this.bestOutOf = matches;
        return;
    }

    private void calculateWinThreshold() {
        this.winThreshold = (this.bestOutOf + 1) / 2;
        return;
    }

    private int getPlayerNumber() {
        printf("User, what is your throw? (1-5)\n")

        int userThrow;
        scanf("%d", &userThrow);

        while(userThrow < 1 || userThrow > 5){
            printf("Invalid number! Please try again:\n");
            scanf("%c", &userThrow);
        }

        return userThrow;
    }

    private int getComputerNumber() {
        Random random = new Random();

        int compThrow = random.nextInt(5)+1;

        return compThrow;
    }

    private int calculateSum(int userNum, int compNum) {
        return userNum + compNum;
    }

    private void determineRoundWinner(int sum, int userNum, int compNum) {
        if(sum % 2 == 0 && userIsEven || sum %2 == 0 && !userIsEven)
        {
            updateRoundScore(true);

            if(userIsEven){
                printf("%d + %d  = %d is even, you win this round!\n", userNum, compNum, sum);
            }
            else
            {
                printf("%d + %d  = %d is odd, you win this round!\n", userNum, compNum, sum);
            }
            return;
        }

        updateRoundScore(false);

        if(userIsEven){
            printf("%d + %d  = %d is odd, computer wins this round!\n", userNum, compNum, sum);
        }
        else
        {
            printf("%d + %d  = %d is even, computer wins this round!\n", userNum, compNum, sum);
        }

        return;

    }

    private void updateRoundScore(boolean userWonRound) {
        if (userWonRound) {
            userScore++;
        } else {
            compScore++;
        }
        return;
    }

    private boolean checkGameWinner() {
        if(userScore >= winThreshold){
            declareGameWinner(true);
            return true;

        } 

        declareGameWinner(false);
        return false;
    }

    private void declareGameWinner(boolean isUserWinner) {
        if(isUserWinner){
            printf("You win Even and Odd!\n");
            return;
        }

        printf("The computer wins Even and Odd!\n");
        return;
    }
}
