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

## Stage Overview

**Repository:** https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment

| Stage | Tag | Adds | New files | Changed files | Deleted |
|:---:|:---:|:---|:---|:---|:---:|
| 1. Basic operation | [`stage-1`](https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment/tree/stage-1) | Regular customers, 3-item menu, Waiter, Chef, Cashier, full turn sequence, victory and defeat | All 17 source files (baseline) | — | — |
| 2. Customer behaviour | [`stage-2`](https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment/tree/stage-2) | VIP and Critic customers | `VIPCustomer`, `CriticCustomer` | `Customer`, `CustomerManager`, `Cashier`, `SystemUI`, `test_victory.txt` | None |
| 3. New role and menu policy | [`stage-3`](https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment/tree/stage-3) | Host, ComboMeal, Happy Hour | `Dish`, `ComboMeal`, `Host`, `HappyHour` | `MenuItem`, `Order`, `Customer`, `Waiter`, `LogicController`, `SystemUI`, both test files | None |

**Compare links:**
- Stage 1 → 2: https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment/compare/stage-1...stage-2
- Stage 2 → 3: https://github.com/aniketmajee7-pixel/SC2002-Restaurant-Rush-Group-Assignment/compare/stage-2...stage-3

### Stage 1 Baseline
| Class | Responsibility |
|:---|:---|
| `GameLoop` | Entry point; runs 18 turns then ends the game |
| `LogicController` | Runs each turn's steps and validates manager choices |
| `GameState` | Holds the queue, tables, customer record, active orders and revenue |
| `SystemUI` | Displays state and reads the manager's input; no game rules |
| `CustomerManager` | Arrival schedule and per-turn customer updates |
| `Customer`, `Order`, `Table` | Domain objects for the restaurant |
| `MenuItem`, `CustomerStatus`, `OrderStatus`, `ActionError` | Enums for the menu, states and error messages |
| `Staff`, `ControllableStaff` | Interfaces for staff; `ControllableStaff` adds the choices the manager picks from |
| `Waiter`, `Chef`, `Cashier` | Seat/order/serve, cook, and bill |

---

## Stage Transitions & Changes

### Transition: Stage 1 → Stage 2
**Git Diff**: `git diff stage-1 stage-2`

#### SOLID & Design Improvements
* **Moved Desire Initialisation (Polymorphism & SRP)**: 
  * In Stage 1, `CustomerManager` created meal orders and passed them into `Customer`.
  * In Stage 2, I moved desire Initialisation into `Customer` via `makeDesire(random)`. This gives much better polymorphism: subclasses like `CriticCustomer` can now easily override `makeDesire()` to pick their own orders (e.g. always Pasta) without `CustomerManager` having to know or care about meal preferences.
* **Single Responsibility Principle (SRP)**:
  * `CustomerManager` only handles customer arrivals and queuing, not their orders.
  * `Customer` handles its own irritation rate, meal selection, discounts, and reactions.
  * `Cashier` only runs `c.bill()`, leaving discount math to the customer.
* **Open/Closed Principle (OCP)** & **Liskov Substitution Principle (LSP)**:
  * Added `VIPCustomer` and `CriticCustomer` by extending `Customer` and overriding hook methods (`getIrritation()`, `getDiscount()`, `makeDesire()`, `serveOrder()`).
  * Controllers, queues, and tables treat all customers uniformly as `Customer` without needing any `instanceof` checks.
* **Dependency Inversion Principle (DIP)**:
  * High-level classes (`Cashier`, `CustomerManager`, `SystemUI`) depend on the base `Customer` abstraction, not concrete customer types.

#### File Summary
* **`VIPCustomer.java` (New)**: Extends `Customer`. Slower irritation decay (5), 10% bill discount (0.9), and `"VIP"` tag.
* **`CriticCustomer.java` (New)**: Extends `Customer`. Faster irritation decay (12), always orders Pasta via overridden `makeDesire()`, and distinct reaction on service (`serveOrder()` adjusts satisfaction based on speed).
* **`Customer.java`**:
  * Moved order creation from `CustomerManager` into `makeDesire(Random)`.
  * Added hook methods: `getIrritation()`, `getDiscount()`, `getType()`, `makeDesire()`.
  * Renamed `finish()` to `bill()` to apply customer discounts.
  * Overrode `toString()` to display `[" + getType() + "] " + name`.
* **`Cashier.java`**: Calls `c.bill()` instead of `c.finish()`, delegating discount calculations to the customer.
* **`CustomerManager.java`**: Removed order creation; passes `(name, random)` so customers initialize themselves. Spawns Critic on Turn 7 and VIP on Turn 13.
* **`SystemUI.java`**: Uses `c` instead of `c.getName()` in queue and table displays to show polymorphic `[VIP]`/`[Critic]` tags.
* **`test/test_victory.txt` & `README.md`**: Updated for Stage 2 customer mechanics and schedules.

### Transition: Stage 2 → Stage 3
**Git Diff**: `git diff stage-2 stage-3`

#### Design Changes
* **Price locked when the order is taken**: In Stage 2 the VIP discount was applied at payment (`Customer.bill()`). Happy Hour requires the price to be fixed when the order is taken, so the price is now calculated in `Waiter.order()` and stored by `Order.place()`. Stage 2 pricing results are unchanged.
* **`Dish` interface (OCP, LSP)**: `Order` now holds a `Dish` instead of a `MenuItem`. Both `MenuItem` and `ComboMeal` implement `Dish`, so `Order`, `Chef`, `Waiter` and `SystemUI` handle single items and combos the same way.
* **`HappyHour` as its own policy class (SRP)**: It tracks whether it has been used, how many turns are left, the 20% discount and the recovery amount. `LogicController` only asks it questions.
* **`Host` (ISP)**: Implements `ControllableStaff` with only the actions it can take (Seat, Wait). Seating validation is shared between Host and Waiter through `LogicController.seatingProblem()`.

#### Rules Chosen
* **Combo choice**: Customers pick uniformly from a menu of 6 dishes (Salad, Burger, Pasta, Salad + Burger, Salad + Pasta, Burger + Pasta) using the seeded `Random`. Critics still always order Pasta.
* **Turn order**: Happy Hour activation and recovery happen at the start of the turn, then Host, Waiter, Chef, Cashier. The Host acts before the Waiter, so the Waiter can take the order of a customer the Host seated that turn.
* **Discount order**: VIP discount first, then Happy Hour, each rounded to the nearest cent (VIP Burger during Happy Hour: $15 × 0.9 × 0.8 = $10.80).

#### File Summary
* **`Dish.java` (New)**: Interface for anything that can be ordered (`getName()`, `priceCents()`, `prepUnits()`).
* **`ComboMeal.java` (New)**: Two `MenuItem`s. Prep units are the sum; price is 90% of the combined price, rounded to the nearest cent.
* **`Host.java` (New)**: Seats one queued customer per turn, in addition to the Waiter.
* **`HappyHour.java` (New)**: Once per game, lasts 2 turns, 20% off orders taken while active, +15 satisfaction (max 100) for waiting customers at the start of each active turn.
* **`MenuItem.java`**: Implements `Dish`.
* **`Order.java`**: Holds a `Dish`; price is set in `place(tableNo, price)` instead of the constructor. Added `getBasePrice()` and `getPrice()`.
* **`Customer.java`**: Menu includes the 3 combos. Added `calculatePrice()` (applies the customer's own discount) and `recover()`. `bill()` returns the locked price. The waiting check used by `tickPatience()` is now shared with `recover()` through `isWaiting()`.
* **`Waiter.java`**: `order()` takes the `HappyHour` and locks the price (VIP discount, then Happy Hour).
* **`LogicController.java`**: Added `host`, `happyHour`, `manageHappyHour()`, `manageHost()` and `seatingProblem()`. Waiter seating uses `seatingProblem()`.
* **`SystemUI.java`**: Shows Happy Hour status and remaining turns, asks whether to activate it, lists the Host in staff actions, and shows the locked price when an order is placed.
* **`test/test_victory.txt`, `test/test_defeat.txt`**: Updated for the Host and Happy Hour prompts. Stage 2 test notes are in the README at tag `stage-2`.

---

## Stage 3 Test Notes & Reproducible Runs

All runs use the base setting with fixed seed `2002` set in `CustomerManager`. Arrivals and orders:
- **Turn 1**: `[Regular] Mae` (Salad + Burger Combo, $21.60, 2 units)
- **Turn 4**: `[Regular] Mae` (Salad + Burger Combo, $21.60, 2 units)
- **Turn 7**: `[Critic] Lucille` (Pasta, $18.00, 2 units)
- **Turn 10**: `[Regular] George` (Salad + Pasta Combo, $24.30, 3 units)
- **Turn 13**: `[VIP] Buster` (Salad, $8.10 with 10% discount)
- **Turn 16**: `[Regular] Tobias` (Salad, $9.00)

Each turn asks, in order: Enter, Happy Hour (only until it is used), Host, Waiter, Chef, Enter.

---

### 1. Reproducible Defeat Run (Passivity Defeat)
The manager never activates Happy Hour and chooses Wait for every staff member. Customers lose patience and leave without paying.

**Expected Outcome (Turn 18):**
- Paid customers: `0 (target 4)`
- Revenue: `$0.00 (target $45.00)`
- Average Satisfaction: `N/A (target 60)`
- Result: **YOU LOSE!**

```bash
java -cp bin project.GameLoop < test/test_defeat.txt
```

Every turn: `[Enter]` -> `2` (no Happy Hour) -> `2` (Host Wait) -> `4` (Waiter Wait) -> `2` (Chef Wait) -> `[Enter]`

---

### 2. Reproducible Victory Run
Uses the Host every time a customer arrives, two combos, and Happy Hour on turns 10-11.

**Expected Outcome (Turn 18):**
- Paid customers: `5 (target 4)`
- Revenue: `$88.74 (target $45.00)`
- Average Satisfaction: `86.0 (target 60)`
- Result: **YOU WIN!**

```bash
java -cp bin project.GameLoop < test/test_victory.txt
```

| Turn | Happy Hour | Host | Waiter | Chef | Notes |
|:---:|:---:|:---|:---|:---|:---|
| 1 | `2` | `1` Seat | `2` -> `1` Take Order | `1` -> `1` Cook | Host seats Mae at T1; she orders Salad + Burger Combo ($21.60) |
| 2 | `2` | `2` Wait | `4` Wait | `1` -> `1` Cook | Combo READY |
| 3 | `2` | `2` Wait | `3` -> `1` Serve | `2` Wait | Mae served |
| 4 | `2` | `1` Seat | `2` -> `1` Take Order | `1` -> `1` Cook | Mae (2nd) seated at T2, orders combo; Mae (T1) ready to pay |
| 5 | `2` | `2` Wait | `4` Wait | `1` -> `1` Cook | Cashier bills Mae $21.60 |
| 6 | `2` | `2` Wait | `3` -> `1` Serve | `2` Wait | Mae (T2) served |
| 7 | `2` | `1` Seat | `2` -> `1` Take Order | `1` -> `1` Cook | Critic seated at T1, orders Pasta |
| 8 | `2` | `2` Wait | `4` Wait | `1` -> `1` Cook | Cashier bills Mae $21.60; Pasta READY |
| 9 | `2` | `2` Wait | `3` -> `1` Serve | `2` Wait | Critic served at 76, +12 speed bonus = 88 |
| 10 | `1` Activate | `1` Seat | `2` -> `1` Take Order | `1` -> `1` Cook | George seated at T2, orders Salad + Pasta Combo: $24.30 × 0.8 = $19.44 |
| 11 | — | `2` Wait | `4` Wait | `1` -> `1` Cook | Happy Hour turn 2: George recovers 92 -> 100; Cashier bills Critic $18.00 |
| 12 | — | `2` Wait | `4` Wait | `1` -> `1` Cook | Combo READY (3 units) |
| 13 | — | `1` Seat | `3` -> `1` Serve | `2` Wait | VIP Buster seated at T1; George served |
| 14 | — | `2` Wait | `2` -> `1` Take Order | `1` -> `1` Cook | Buster orders Salad: $8.10 (Happy Hour over) |
| 15 | — | `2` Wait | `3` -> `1` Serve | `2` Wait | Cashier bills George $19.44; Buster served |
| 16 | — | `1` Seat | `2` -> `1` Take Order | `1` -> `1` Cook | Tobias seated at T2, orders Salad |
| 17 | — | `2` Wait | `3` -> `1` Serve | `2` Wait | Cashier bills Buster $8.10 |
| 18 | — | `2` Wait | `4` Wait | `2` Wait | Tobias eating; cannot pay within 18 turns |
