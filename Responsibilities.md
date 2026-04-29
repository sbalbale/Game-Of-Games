# Responsibilities Document — Game of Games

**Project:** Game of Games (Implementation Phase)  
**Course:** Trinity College  
**Due:** April 29, 2026 — 5:00 p.m.  
**Document prepared by:** Sean Balbale  
**Last updated:** April 27, 2026

---

## Team Members

| Name | GitHub |
|------|--------|
| Sean Balbale | `sbalbale` |
| Ben Lyons | `belyons123` |
| Abigail Gomes | `abeeinthesky` |
| Juan Marcano | `juandmarcanoc` |

---

## Responsibility Breakdown

### Sean Balbale

**Project Infrastructure & Setup**
- Created and configured the GitHub repository (GOG-4)
- Configured the Kanban board and Gantt chart in Jira (GOG-6)
- Established and managed the Slack communication channel (GOG-7)

**Documentation**
- Drafted all class UML diagrams, including `.puml` source and rendered `.svg` exports for all six classes (GOG-8)
- Created Method Glossaries for all classes (GOG-14)
- Built the unified Data Configuration Table across all classes (GOG-15)
- Designed the Player Mode and Test Mode architecture (GOG-18)
- Wrote the Deployment Document (`Deployment.md`) (GOG-25)
- Authored this Responsibilities Document (GOG-39)
- Updating the GitHub `README.md` with full file directory (GOG-43 — in progress)

**Coding**
- Implemented `PlayGames.java` — main menu loop, game routing switch, injectable `BooleanSupplier` runners for testability, overall scoreboard, final tally, and overall winner declaration (GOG-16)
- Added `--test` flag detection and `isTestMode` static flag architecture to `PlayGames.java` (GOG-24)
- Implemented `CoinFlip.java` — full best-of-N game loop, win threshold calculation, random coin flip, round winner evaluation, test-mode reveal, score display (GOG-20)
- Co-authored `GuessTheNumber.java` with Juan Marcano — number-of-rounds loop, range and guess-limit prompts, random target generation, guess evaluation with hint messages, test-mode reveal (GOG-21)
- Co-authored `EvenAndOdd.java` with Juan Marcano — role assignment, best-of-N threshold, round play, sum parity evaluation, score tracking (GOG-22)
- Co-authored `FindTheThimble.java` with Abigail Gomes — random thimble placement, L/R guess validation, round winner evaluation, test-mode reveal (GOG-19)

**Testing**
- Ran and recorded all black-box tests for Coin Flip, sunny and rainy day paths (GOG-27)
- Ran and recorded all black-box tests for the Overall Game of Games, sunny and rainy day paths (GOG-31)
- Authored and executed all unit tests for `GetInput` (`GetInputTest.java`) (GOG-32)
- Authored and executed all unit tests for `PlayGames` (`PlayGamesTest.java`) (GOG-33)
- Authored and executed all unit tests for `CoinFlip` (`CoinFlipTest.java`) (GOG-35)
- Co-authored unit tests for `GuessTheNumber` (`GuessTheNumberTest.java`) with Juan Marcano (GOG-36)
- Authored and executed all unit tests for `EvenAndOdd` (`EvenAndOddTest.java`) (GOG-37)
- Co-authored unit tests for `FindTheThimble` (`FindTheThimbleTest.java`) with Abigail Gomes (GOG-34)

---

### Ben Lyons

**Coding**
- Implemented `GetInput.java` — all validated input methods (`getInt`, `getIntInRange`, `getOddInt`, `getChar`), resilient to non-numeric and out-of-range input (GOG-17)
- Implemented `FindTheRedThread.java` — 20-spool game loop, random red thread placement, alternating user/computer turns, pull validation, remaining spool display, test-mode reveal, win detection (GOG-23)

**Testing**
- Ran and recorded all black-box tests for Find the Red Thread, sunny and rainy day paths (GOG-30)
- Authored and executed all unit tests for `FindTheRedThread` (`FindTheRedThreadTest.java`) (GOG-38)

---

### Abigail Gomes

**Coding**
- Co-authored `FindTheThimble.java` with Sean Balbale — random thimble placement, L/R guess validation via `GetInput`, round winner comparison and messaging, best-of-N threshold, score tracking, test-mode integration (GOG-19)

**Testing**
- Running black-box tests for Find the Thimble, sunny and rainy day paths (GOG-26 — in progress)
- Running black-box tests for Guess the Number, sunny and rainy day paths (GOG-28 — in progress)
- Co-authoring unit tests for `FindTheThimble` (`FindTheThimbleTest.java`) with Sean Balbale (GOG-34 — in review)

---

### Juan Marcano

**Coding**
- Co-authored `EvenAndOdd.java` with Sean Balbale — role assignment (`E`/`O`), best-of-N best-out-of prompt, round play loop, sum parity evaluation, score tracking, game winner declaration (GOG-22)
- Co-authored `GuessTheNumber.java` with Sean Balbale — multi-round outer loop, range and guess-limit prompts, random target generation, guess evaluation, loss messaging (GOG-21)

**Testing**
- Authored `EvenAndOddTest.java` — unit tests for threshold calculation, score updating, role-based round winner determination, and game winner declaration

---

## Summary Table

| Task | Sean | Ben | Abigail | Juan |
|------|:----:|:---:|:-------:|:----:|
| GitHub setup | ✓ | | | |
| Kanban / Gantt | ✓ | | | |
| Slack channel | ✓ | | | |
| UML diagrams (all classes) | ✓ | | | |
| Method glossaries | ✓ | | | |
| Data configuration table | ✓ | | | |
| Deployment document | ✓ | | | |
| Responsibilities document | ✓ | | | |
| README | ✓ | | | |
| `PlayGames.java` | ✓ | | | |
| `GetInput.java` | | ✓ | | |
| `CoinFlip.java` | ✓ | | | |
| `GuessTheNumber.java` | ✓ (lead) | | | ✓ (co) |
| `EvenAndOdd.java` | ✓ (lead) | | | ✓ (co) |
| `FindTheThimble.java` | ✓ (co) | | ✓ (lead) | |
| `FindTheRedThread.java` | | ✓ | | |
| Black-box — Coin Flip | ✓ | | | |
| Black-box — Guess the Number | | | ✓ | |
| Black-box — Even and Odd | ✓ | | | |
| Black-box — Find the Thimble | | | ✓ | |
| Black-box — Find the Red Thread | | ✓ | | |
| Black-box — Overall Game of Games | ✓ | | | |
| Unit tests — `GetInput` | ✓ | | | |
| Unit tests — `PlayGames` | ✓ | | | |
| Unit tests — `CoinFlip` | ✓ | | | |
| Unit tests — `GuessTheNumber` | ✓ (co) | | | ✓ (co) |
| Unit tests — `EvenAndOdd` | ✓ (lead) | | | ✓ (co) |
| Unit tests — `FindTheThimble` | ✓ (co) | | ✓ (lead) | |
| Unit tests — `FindTheRedThread` | | ✓ | | |

---

*All Jira ticket references (GOG-##) are verifiable at https://gameofgames.atlassian.net*