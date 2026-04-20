# The Game of Games

## Overview
**The Game of Games** is a Java-based console application that allows a user to play a suite of classic mini-games against the computer. The application tracks scores across multiple games and declares an overall winner upon exiting.

## Directory & File Definitions
This repository contains the source code, configuration files, and necessary documentation to build, run, and understand the project. Below is a definition of all key files in the directory:

### Java Source Code (`/src/main/java/edu/trincoll/`)
* **`PlayGames.java`**: The main driver class. It manages the overall execution loop, displays the main menu, routes the user to the selected mini-games, and tracks the overall session scoreboard. It also supports a `--test` flag for debugging.
* **`GetInput.java`**: A centralized utility class that contains resilient methods for prompting the user and validating input (e.g., ensuring integers fall within specific bounds, verifying odd numbers, and checking character inputs).
* **`CoinFlip.java`**: Encapsulates the logic for the "Coin Flip" mini-game.
* **`EvenAndOdd.java`**: Encapsulates the logic for the "Even and Odd" mini-game.
* **`FindTheRedThread.java`**: Encapsulates the logic for the "Find the Red Thread" mini-game.
* **`FindTheThimble.java`**: Encapsulates the logic for the "Find the Thimble" mini-game.
* **`GuessTheNumber.java`**: Encapsulates the logic for the "Guess the Number" mini-game.

### Testing (`/src/test/java/edu/trincoll/`)


### Configuration & Build Files
* **`pom.xml`**: The Apache Maven Project Object Model file. It manages the project's dependencies, build lifecycle, compilation targets (Java 23), and plugins.
* **`.gitignore`**: Specifies which files and directories (like compiled `.class` files or IDE settings) Git should ignore.
* **`checkstyle.xml` & `checkstyle-suppressions.xml`**: Configuration files for the Maven Checkstyle plugin to enforce consistent coding standards and styling across the project.
* **`LICENSE`**: The open-source license governing the use and distribution of this repository.

### Documentation & Design
* **`README.md`**: This file, providing an overview of the repository and defining the directory structure.
* **`Deployment.md`**: Provides step-by-step instructions on how to access, download, configure, compile, and run the program in both Player Mode and Test Mode.
* **`CodeDesign.md`**: The comprehensive design document containing the unified Data Configuration Table, PlantUML diagrams, and method glossaries for all classes.
* **`/diagrams/`**: A directory containing the exported `.puml` and `.svg` visual files for the class UML diagrams.
* **`/Reference Docs/`**: A directory containing the original assignment constraints (`The Game of Games – Implementation Time.docx`) and the black-box test cases provided by the design team (`GameOfGamesTestingEliasIzzyShivangAleksandra.pdf`).