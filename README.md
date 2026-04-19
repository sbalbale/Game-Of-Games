# The Game of Games

## Description
The Game of Games is a Java-based console application that allows a user to play a suite of classic mini-games against the computer. The application features a centralized main menu that tracks the overall scoreboard and declares a final winner upon exiting.

## Games Included
1. **Guess the Number:** The computer randomly selects a number within a user-defined range, and the player attempts to guess it within a limited number of tries.
2. **Coin Flip:** The player calls "heads" or "tails" against a best-out-of win threshold.
3. **Even/Odd:** The player and computer are assigned "Even" or "Odd" roles. Both pick numbers, and the sum determines the winner of the round.
4. **Find the Thimble:** The computer hides a thimble in its left or right hand, and the player must guess the correct hand.
5. **Find the Red Thread:** Players take turns pulling a specified number of spools from a pool of 20. The player who pulls the single red thread wins.

## System Architecture
- **`PlayGames.java`**: The main driver class that manages the overall execution loop, displays the main menu, tracks total wins/losses, and manages the transitions between individual games.
- **`GetInput.java`**: A dedicated utility class containing resilient, generic methods to prompt the user, handle exceptions, and validate that input matches expected types and ranges.
- **Game Classes**: Each of the five mini-games is encapsulated within its own dedicated Java class.

## Execution Modes
The application supports two distinct execution variations:
- **Player Mode:** The standard user experience.
- **Test Mode:** A debugging mode that displays hidden internal variables (for example, showing which hand holds the thimble or where the red thread is) to facilitate accurate testing without relying on random chance.

## Setup and Execution
*(Note: Update these instructions based on your specific deployment documentation)*
1. Ensure you have the Java Development Kit (JDK) installed.
2. Clone this repository to your local machine.
3. Navigate to the project directory in your terminal.
4. Compile the Java files:
   ```bash
   javac *.java
   ```
5. Execute the main driver class:
   ```bash
   java PlayGames
   ```

## Documentation
This project is supported by the following documentation artifacts:
- **Code Design Document:** Includes class UML diagrams, method glossaries, and a unified Data Configuration Table.
- **Testing Suites:** Comprehensive unit tests and black-box activity diagrams mapping Sunny Day and Rainy Day paths.
- **Deployment Document:** Step-by-step instructions for downloading, configuring, and executing the codebase.
- **Responsibilities Document:** An accurate breakdown of implementation contributions.