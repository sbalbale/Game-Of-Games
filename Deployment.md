# Deployment Document: The Game of Games

## 1. Access and Download
The source code for this project is hosted on GitHub. You can obtain it by cloning the repository to your local machine. Open your terminal and run the following commands:
```bash
git clone [https://github.com/sbalbale/Game-Of-Games](https://github.com/sbalbale/Game-Of-Games)
cd Game-Of-Games
```

## 2. Configuration & Prerequisites
Before compiling and running the game, ensure your host system meets the following software requirements:
* **Java Development Kit (JDK):** Version 23 or higher installed and configured in your system's PATH.
* **Apache Maven:** Version 3.6 or higher installed to manage dependencies, compile the project, and execute the runtime variations.

## 3. Compilation
The project utilizes Apache Maven for build lifecycle management. To compile the Java source files, execute the following command from the root directory of the repository (where the `pom.xml` file is located):
```bash
mvn clean compile
```
This command cleans any previous builds and compiles the latest `.java` files into executable `.class` binaries.

## 4. Execution Procedures
The application supports two distinct execution variations. Use the corresponding Maven commands to launch your desired mode.

### Variation A: Player Mode (Standard Execution)
This is the standard mode intended for end-users. No hidden variables or internal states are exposed during normal gameplay.
```bash
mvn exec:java
```

### Variation B: Test Mode (Debugging & Cheating)
This mode is strictly for testing and debugging. It exposes hidden internal states (such as the location of the thimble, the computer's chosen number, or the exact position of the red thread) to allow QA testers to verify the game logic without relying on random chance. 

To run the game in Test Mode, append the `--test` argument:
```bash
mvn exec:java -Dexec.args="--test"
```
*(Note: You will see a `[SYSTEM] Executing in Test Mode...` banner upon successful launch, and cheat outputs will be prefixed with `[TEST MODE]` during gameplay).*

## 5. File Directory Definitions
For a complete glossary defining all project files, class architectures, and configuration dependencies, please refer to the `README.md` file located at the root of the GitHub repository.
