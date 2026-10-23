# Restaurant Rush

A turn-based command-line restaurant management game written in Java for SC2002.

### AI Declaration
No AI tools were used for the design, logic, or implementation of the Java source code. Generative AI (Google Gemini) was used solely to assist with structuring and formatting the Markdown tables and test notes in this README.

## How to Build and Run

From the project root:

### 1. Compile
```bash
javac -d bin src/project/*.java
```

### 2. Run
```bash
java -cp bin project.GameLoop
```

---

## Stage 1 Test Notes & Reproducible Runs

All runs use the fixed seed `2002` set in `CustomerManager`, giving deterministic customer arrivals and meal choices.

### 1. Reproducible Defeat Run (Passivity Defeat)
The manager chooses to `Wait` every single turn. Customers waiting in queue and seated at tables lose patience and leave without paying.

**Expected Outcome (Turn 18):**
- Paid customers: `0 (target 4)`
- Revenue: `$0.00 (target $45.00)`
- Average Satisfaction: `N/A (target 60)`
- Result: **YOU LOSE!**

#### Fast Run via Piped Input:
```bash
java -cp bin project.GameLoop < test/test_defeat.txt
```

#### Manual Steps:
On every turn, press Enter to pass the start/end of turn prompts, and select `4` (Wait for Waiter) and `2` (Wait for Chef):
- Repeat for all 18 turns: `[Enter]` -> `4` -> `2` -> `[Enter]`

---

### 2. Reproducible Victory Run
A strategy serving 4 customers (Mae, Mae, Michael, George) while managing prep units for single-unit and multi-unit dishes.

**Expected Outcome (Turn 18):**
- Paid customers: `4 (target 4)`
- Revenue: `$60.00 (target $45.00)`
- Average Satisfaction: `78.0 (target 60)`
- Result: **YOU WIN!**

#### Fast Run via Piped Input:
```bash
java -cp bin project.GameLoop < test/test_victory.txt
```

#### Turn-by-Turn Breakdown

| Turn | Start Prompt | Waiter Action | Chef Action | End Prompt | Notes |
|:---:|:---:|:---|:---|:---:|:---|
| 1 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Mae arrives, seated at Table 1 |
| 2 | Enter | `2` -> `1` (Take Order: T1) | `1` -> `1` (Cook Burger) | Enter | Mae orders Burger (1 prep); Chef cooks to READY |
| 3 | Enter | `3` -> `1` (Serve Order: T1) | `2` (Wait) | Enter | Mae is served |
| 4 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Mae arrives, seated at Table 2; Mae (T1) starts eating |
| 5 | Enter | `2` -> `1` (Take Order: T2) | `1` -> `1` (Cook Salad) | Enter | Mae orders Salad (1 prep); Chef cooks; Mae (T1) pays ($15.00), T1 freed |
| 6 | Enter | `3` -> `1` (Serve Order: T2) | `2` (Wait) | Enter | Mae (T2) is served |
| 7 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Michael arrives, seated at Table 1; Mae (T2) starts eating |
| 8 | Enter | `2` -> `1` (Take Order: T1) | `1` -> `1` (Cook Pasta) | Enter | Michael orders Pasta (2 prep); Chef does 1/2; Mae (T2) pays ($9.00), T2 freed |
| 9 | Enter | `4` (Wait) | `1` -> `1` (Cook Pasta) | Enter | Chef finishes unit 2/2 -> Pasta READY |
| 10 | Enter | `3` -> `1` (Serve Order: T1) | `2` (Wait) | Enter | George arrives; Michael is served |
| 11 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | George seated at Table 2; Michael starts eating |
| 12 | Enter | `2` -> `1` (Take Order: T2) | `1` -> `1` (Cook Pasta) | Enter | George orders Pasta (2 prep); Chef does 1/2; Michael pays ($18.00), T1 freed |
| 13 | Enter | `4` (Wait) | `1` -> `1` (Cook Pasta) | Enter | Oscar arrives; Chef finishes unit 2/2 -> Pasta READY |
| 14 | Enter | `3` -> `1` (Serve Order: T2) | `2` (Wait) | Enter | George is served |
| 15 | Enter | `4` (Wait) | `2` (Wait) | Enter | George starts eating |
| 16 | Enter | `4` (Wait) | `2` (Wait) | Enter | George pays ($18.00), T2 freed |
| 17-18 | Enter | `4` (Wait) | `2` (Wait) | Enter | Wait out turns 17 and 18 |

---

### Quick Copy-Paste Inputs

You can also copy and paste the raw text blocks below directly into the console:

<details>
<summary><b>Defeat Run Raw Inputs</b> (click to expand)</summary>

```text

4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2


4
2

```
</details>

<details>
<summary><b>Victory Run Raw Inputs</b> (click to expand)</summary>

```text

1
2


2
1
1
1


3
1
2


1
2


2
1
1
1


3
1
2


1
2


2
1
1
1


4
1
1


3
1
2


1
2


2
1
1
1


4
1
1


3
1
2


4
2


4
2


4
2


4
2

```
</details>
