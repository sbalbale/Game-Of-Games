# The Game of Games

A Java-based console application that lets a user play five mini-games against the computer.
The program tracks scores across games and declares a final overall winner when the user quits.

> **Repository:** https://github.com/sbalbale/Game-Of-Games  
> **Java version:** 23 &nbsp;·&nbsp; **Build tool:** Apache Maven

---

## Team

| Name | Contributions |
|------|---------------|
| Sean Balbale | Project lead · architecture · `PlayGames` · `CoinFlip` · co-author `EvenAndOdd`, `GuessTheNumber`, `FindTheThimble` · most tests & all docs |
| Ben Lyons | `GetInput` · `FindTheRedThread` · red thread tests |
| Abigail Gomes | `FindTheThimble` (co-author) · thimble & guess-the-number tests |
| Juan Marcano | `EvenAndOdd` (co-author) · `GuessTheNumber` (co-author) · `EvenAndOddTest` |

---

## Prerequisites

| Requirement | Version |
|-------------|---------|
| Java Development Kit (JDK) | 23 or higher |
| Apache Maven | 3.6 or higher |

Both `java` and `mvn` must be on your system `PATH`.

---

## Repository Structure

```
Game-Of-Games/                            ← repository root
│
├── README.md                             ← this file
├── Deployment.md                         ← step-by-step access, build, and run instructions
├── CodeDesign.md                         ← UML diagrams, method glossaries,
│                                           data config tables, and unit-test tables
├── LICENSE                               ← Apache License 2.0
├── .gitignore
│
├── diagrams/                             ← exported class diagram assets
│   ├── CoinFlip.puml / CoinFlip.svg
│   ├── EvenAndOdd.puml / EvenAndOdd.svg
│   ├── FindTheRedThread.puml / FindTheRedThread.svg
│   ├── FindTheThimble.puml / FindTheThimble.svg
│   ├── GetInput.puml / GetInput.svg
│   ├── GuessTheNumber.puml / GuessTheNumber.svg
│   └── PlayGames.puml / PlayGames.svg
│
├── Reference Docs/                       ← original design documents from the design team
│   ├── The Game of Games – Implementation Time.docx
│   └── GameOfGamesTestingEliasIzzyShivangAleksandra.pdf
│
└── gameofgames/                          ← Maven project (all Java source lives here)
    ├── pom.xml                           ← build descriptor (dependencies, plugins, Java 23)
    ├── checkstyle.xml                    ← Checkstyle rule set (Sun style, 120-char line limit)
    ├── checkstyle-suppressions.xml       ← suppresses checks inside /target/
    └── src/
        ├── main/java/edu/trincoll/
        │   ├── PlayGames.java            ← main driver: menu loop, routing, overall scoreboard
        │   ├── GetInput.java             ← validated user-input utility (int, odd int, char)
        │   ├── CoinFlip.java             ← game: best-of-N heads-or-tails coin flip
        │   ├── EvenAndOdd.java           ← game: best-of-N even/odd finger-sum
        │   ├── FindTheThimble.java       ← game: best-of-N left/right hand guess
        │   ├── FindTheRedThread.java     ← game: alternating spool-pull to find the red thread
        │   └── GuessTheNumber.java       ← game: guess a hidden number within a range & guess limit
        └── test/java/edu/trincoll/
            ├── PlayGamesTest.java        ← JUnit 5 unit tests for PlayGames
            ├── GetInputTest.java         ← JUnit 5 unit tests for GetInput
            ├── CoinFlipTest.java         ← JUnit 5 unit tests for CoinFlip
            ├── EvenAndOddTest.java       ← JUnit 5 unit tests for EvenAndOdd
            ├── FindTheThimbleTest.java   ← JUnit 5 unit tests for FindTheThimble
            ├── FindTheRedThreadTest.java ← JUnit 5 unit tests for FindTheRedThread
            └── GuessTheNumberTest.java   ← JUnit 5 unit tests for GuessTheNumber
```

---

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/sbalbale/Game-Of-Games
cd Game-Of-Games/gameofgames
```

### 2. Compile
```bash
mvn clean compile
```

### 3. Run — Player Mode (standard gameplay)
```bash
mvn exec:java
```

### 4. Run — Test Mode (hidden state revealed for QA)
```bash
mvn exec:java -Dexec.args="--test"
```

A `[SYSTEM] Executing in Test Mode` banner appears on launch. During gameplay,
normally-hidden information (e.g., which hand holds the thimble, the red thread's
exact position, the secret target number) is printed with a `[TEST MODE]` prefix,
allowing testers to verify correctness without relying on random chance.

### 5. Build and run a standalone JAR
```bash
mvn package
java -jar target/gameofgames-1.0-SNAPSHOT.jar           # player mode
java -jar target/gameofgames-1.0-SNAPSHOT.jar --test    # test mode
```

---

## Games

| Menu # | Game | Class | Mechanic |
|--------|------|-------|----------|
| 1 | Guess the Number | `GuessTheNumber` | Guess a hidden number within a configurable range and guess limit |
| 2 | Coin Flip | `CoinFlip` | Call H or T; first to reach the win threshold in a best-of-N series wins |
| 3 | Even and Odd | `EvenAndOdd` | Choose even or odd; the parity of both players' number sum decides each round |
| 4 | Find the Thimble | `FindTheThimble` | Guess which hand (L/R) hides the thimble in a best-of-N series |
| 5 | Find the Red Thread | `FindTheRedThread` | Take turns pulling spools; whoever pulls the red one wins |
| 6 | Quit | — | Exits the loop, displays the final tally, and declares the overall winner |

Each game returns a `boolean` to `PlayGames` (`true` = user win). The session
scoreboard is updated after every game, and a final overall winner is declared on exit.

---

## Input Validation — `GetInput`

All user input is routed through the shared `GetInput` utility class.
Invalid input prints a descriptive error message and re-prompts without crashing.

| Method | Accepts |
|--------|---------|
| `getInt()` | Any positive integer |
| `getIntInRange(int min, int max)` | Integer in `[min, max]` inclusive |
| `getOddInt()` | Any positive odd integer |
| `getChar(char[] validChars)` | Exactly one character from the supplied array (case-sensitive) |

---

## Execution Modes

| Mode | How to activate | Behavior |
|------|-----------------|----------|
| **Player** | Run without `--test` | Standard gameplay; no hidden state exposed |
| **Test** | Pass `--test` as a command-line argument | Sets `PlayGames.isTestMode = true`; game classes print hidden variables prefixed with `[TEST MODE]` |

`PlayGames.isTestMode` is a `public static` field so every game class can read
it directly without additional coupling.

---

## Architecture Notes

- **`PlayGames`** accepts injected `BooleanSupplier` game runners via a
  package-private constructor, making the driver class fully unit-testable
  without requiring interactive `Scanner` input.
- All game classes are `final` with a single `public` entry point (`playGame()`).
  All helper methods are `private` and exercised through Java Reflection in the
  JUnit 5 test suite.
- **`GetInput`** holds a single `Scanner` instance shared across all its methods.

---

## Build Plugins

| Plugin | Purpose |
|--------|---------|
| `maven-compiler-plugin` | Compiles source to Java 23 |
| `exec-maven-plugin` | `mvn exec:java` runs `PlayGames.main` |
| `maven-surefire-plugin` | Discovers and runs JUnit 5 tests |
| `jacoco-maven-plugin` | Generates a coverage report in `target/site/jacoco/` |
| `maven-checkstyle-plugin` | Enforces Sun-style coding standards (120-char line limit) during `process-classes` |

---

## Design Documentation

All use cases, activity diagrams, test case tables, UML class diagrams, method
glossaries, data configuration tables, and unit-test tables are compiled into a
single PDF submitted to Moodle. The `CodeDesign.md` file at the repository root
contains the full design document in Markdown form. The `diagrams/` folder holds
the corresponding PlantUML source (`.puml`) and rendered SVG (`.svg`) files for
every class.

Step-by-step deployment instructions (clone, compile, execute) are in `Deployment.md`.
