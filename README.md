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

## Stage 2 Test Notes & Reproducible Runs

All runs use the base setting with fixed seed `2002` set in `CustomerManager`. This generates deterministic customer arrivals and orders:
- **Turn 1**: `[Regular] Mae` (Salad, $9.00)
- **Turn 4**: `[Regular] Mae` (Salad, $9.00)
- **Turn 7**: `[Critic] Lucille` (Pasta, $18.00)
- **Turn 10**: `[Regular] George` (Burger, $15.00)
- **Turn 13**: `[VIP] Buster` (Salad, $8.10 with 10% discount)
- **Turn 16**: `[Regular] Tobias` (Salad, $9.00)

---

### 1. Reproducible Defeat Run (Passivity Defeat)
The manager chooses to `Wait` every single turn. Queued and seated customers lose patience according to their individual rates (Regular: 8/turn, Critic: 12/turn, VIP: 5/turn) and leave without paying.

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
A strategy serving 5 customers (Mae, Mae, Critic Lucille, George, and VIP Buster) that handles Critic multi-unit cooking, speed bonus, and VIP billing.

**Expected Outcome (Turn 18):**
- Paid customers: `5 (target 4)`
- Revenue: `$59.10 (target $45.00)`
- Average Satisfaction: `81.0 (target 60)`
- Result: **YOU WIN!**

#### Fast Run via Piped Input:
```bash
java -cp bin project.GameLoop < test/test_victory.txt
```

#### Turn-by-Turn Breakdown

| Turn | Start Prompt | Waiter Action | Chef Action | End Prompt | Notes |
|:---:|:---:|:---|:---|:---:|:---|
| 1 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Mae arrives, seated at Table 1 |
| 2 | Enter | `2` -> `1` (Take Order: T1) | `1` -> `1` (Cook Salad) | Enter | Mae orders Salad (1 prep); Chef cooks to READY |
| 3 | Enter | `3` -> `1` (Serve Order: T1) | `2` (Wait) | Enter | Mae is served (starts eating next turn) |
| 4 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Mae arrives, seated at Table 2; Mae (T1) ready to pay |
| 5 | Enter | `2` -> `1` (Take Order: T2) | `1` -> `1` (Cook Salad) | Enter | Cashier bills Mae ($9.00), T1 freed; Chef cooks Salad to READY |
| 6 | Enter | `3` -> `1` (Serve Order: T2) | `2` (Wait) | Enter | Mae (T2) is served |
| 7 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | Critic Lucille arrives, seated at Table 1; Mae (T2) ready to pay |
| 8 | Enter | `2` -> `1` (Take Order: T1) | `1` -> `1` (Cook Pasta) | Enter | Cashier bills Mae ($9.00), T2 freed; Critic orders Pasta; Chef cooks unit 1/2 |
| 9 | Enter | `4` (Wait) | `1` -> `1` (Cook Pasta) | Enter | Chef finishes unit 2/2 -> Pasta READY |
| 10 | Enter | `3` -> `1` (Serve Order: T1) | `2` (Wait) | Enter | Critic served! Satisfaction: 64 + 12 (bonus) = 76 |
| 11 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | George arrives, seated at Table 2; Critic ready to pay |
| 12 | Enter | `2` -> `1` (Take Order: T2) | `1` -> `1` (Cook Burger) | Enter | Cashier bills Critic ($18.00), T1 freed; Chef cooks Burger to READY |
| 13 | Enter | `3` -> `1` (Serve Order: T2) | `2` (Wait) | Enter | George served; VIP Buster arrives |
| 14 | Enter | `1` (Seat Customer) | `2` (Wait) | Enter | VIP Buster seated at Table 1; George ready to pay |
| 15 | Enter | `2` -> `1` (Take Order: T1) | `1` -> `1` (Cook Salad) | Enter | Cashier bills George ($15.00), T2 freed; Chef cooks Salad to READY |
| 16 | Enter | `3` -> `1` (Serve Order: T1) | `2` (Wait) | Enter | VIP Buster served |
| 17 | Enter | `4` (Wait) | `2` (Wait) | Enter | VIP Buster eating; Tobias arrives |
| 18 | Enter | `4` (Wait) | `2` (Wait) | Enter | Cashier bills VIP Buster ($8.10 with 10% off), T1 freed |

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


4
2


4
2

```
</details>
