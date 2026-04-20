/*
 * File: PlayGames.java
 * Purpose: Main driver for the Game of Games application, managing game
 * selection, session flow, and overall scorekeeping.
 * Author: Sean Balbale
 * Date: 4/20/2026
 */

package edu.trincoll;

public final class PlayGames {
    // Instance variables for overall scoreboard
    private int userOverallWins = 0;
    private int compOverallWins = 0;

    // Instantiate the GetInput utility class for resilient input handling
    private final GetInput input = new GetInput();

    public static void main(String[] args) {
        PlayGames gameOfGames = new PlayGames();
        gameOfGames.displayWelcomeMessage();

        boolean keepPlaying = true;
        while (keepPlaying) {
            int choice = gameOfGames.displayMainMenu();

            if (choice == 6) {
                keepPlaying = false; // Trigger exit
            } else {
                gameOfGames.routeToGame(choice);
            }
        }

        // Execute exit sequence
        gameOfGames.displayFinalTally();
        gameOfGames.declareOverallWinner();
    }

    public void displayWelcomeMessage() {
        System.out.println("=================================");
        System.out.println("   Welcome to the Game of Games! ");
        System.out.println("=================================");
    }

    public int displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Guess the number");
        System.out.println("2. Coin Flip");
        System.out.println("3. Even/Odd");
        System.out.println("4. Find the Thimble");
        System.out.println("5. Find the Red Thread");
        System.out.println("6. Quit");

        // Utilizing GetInput to ensure a resilient, valid choice between 1 and 6
        return input.getIntInRange(
            "Enter your choice: ",
            1,
            6,
            "Invalid choice. Please enter a number between 1 and 6." // Rainy day test string
        );
    }

    public void routeToGame(int choice) {
        boolean userWon = false;

        // Instantiate and play the selected game, returning a boolean win state
        switch (choice) {
            case 1:
                System.out.println("\nYou selected: Guess the Number");
                GuessTheNumber gtn = new GuessTheNumber();
                userWon = gtn.playGame();
                break;
            case 2:
                System.out.println("\nYou selected: Coin Flip");
                CoinFlip cf = new CoinFlip();
                userWon = cf.playGame();
                break;
            case 3:
                System.out.println("\nYou selected: Even/Odd");
                EvenAndOdd eo = new EvenAndOdd();
                userWon = eo.playGame();
                break;
            case 4:
                System.out.println("\nYou selected: Find the Thimble");
                FindTheThimble ftt = new FindTheThimble();
                userWon = ftt.playGame();
                break;
            case 5:
                System.out.println("\nYou selected: Find the Red Thread");
                FindTheRedThread frt = new FindTheRedThread();
                userWon = frt.playGame();
                break;
            default:
                System.out.println("System Error: Invalid routing.");
                return;
        }

        // Update overall session tallies based on the game's result
        updateOverallScoreboard(userWon);
    }

    public void updateOverallScoreboard(boolean userWon) {
        if (userWon) {
            userOverallWins++;
        } else {
            compOverallWins++;
        }

        // Display the overall session scoreboard
        System.out.println("\n--- Overall Scoreboard ---");
        System.out.println("You: " + userOverallWins + " | Computer: " + compOverallWins);
    }

    public void displayFinalTally() {
        System.out.println("\n=== Final Tally ===");
        System.out.println("Total User Wins: " + userOverallWins);
        System.out.println("Total Computer Wins: " + compOverallWins);
    }

    public void declareOverallWinner() {
        // Output the final winner based on accumulated wins
        if (userOverallWins > compOverallWins) {
            System.out.println("Congratulations! You are the Winner of the Game of Games!");
        } else if (compOverallWins > userOverallWins) {
            System.out.println("The Computer is the Winner of the Game of Games!");
        } else {
            System.out.println("The Game of Games ends in a draw!"); // Rainy day tie-breaker test string
        }
    }
}
