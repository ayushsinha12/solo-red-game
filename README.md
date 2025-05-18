# Solo Red

> A single-player, text-based Java implementation of the Red7 card game featuring both basic and advanced rule engines within an MVC architecture.

---
# Full Game Explanation
## 🔴 What is Solo Red?

**Solo Red** is a text-based, single-player card game inspired by the real-world game **Red7**. The goal is to stay **“winning”** at the end of your turn under the current rule (determined by a canvas card’s color). If you ever fail to meet the rule you lose. If you survive the entire deck, you win.

---

## 🃏 Cards and Colors

Each card in the game has:

- A **color** (one of seven: Red, Orange, Yellow, Green, Blue, Indigo, Violet).  
- A **number** from 1 to 7.

**Example cards:**

- Red 7  
- Blue 4  
- Violet 2  

---

## 🎨 The 7 Color Rules

The color of the top **canvas card** determines which rule defines a **winning palette**:

| Color   | Rule Description                             |
|:--------|:---------------------------------------------|
| **Red**    | Highest **single** card wins                |
| **Orange** | Most cards of the **same number**           |
| **Yellow** | Most cards with **different colors**        |
| **Green**  | Most **even-numbered** cards                |
| **Blue**   | Most cards of the **same color**            |
| **Indigo** | Longest **consecutive run** of numbers      |
| **Violet** | Most cards with **values less than 4**      |

---

## 🎮 How the Game Works (Gameplay Overview)

### You (The Player) Start With:

- **Hand**: a set of cards you can play  
- **Palette**: where you place cards to meet the current rule  
- **Canvas**: holds the current rule (color = rule)  
- **Deck**: the remaining cards you draw from  

### 💌 Turn Input Options

On each turn, the controller asks:
- **`palette`** → play a card from your hand to your palette  
- **`canvas`** → play a card from your hand to change the rule  
- **`q`** → quit the game immediately  

### ✅ Making a Legal Move

After you play:

- **If you’re winning** under the current rule, the turn succeeds and you **draw** one card from the deck.  
- **If you’re not winning**, and no subsequent play can fix it, you **lose** immediately.  

---

## 🎯 Game Objective

- **Survive** until the **deck runs out**, and your palette still wins.  
- **Avoid** making any move that leaves you **not winning** under the active rule.  
- **Win** = survival.  
- **Lose** = unable to make a legal move.  

---

## 🧱 Components and Architecture (MVC)

This project follows the **Model-View-Controller** pattern:

1. **Model (Game Logic)**  
   - Represents rules, game state, deck, hand, canvas, and palette  
   - Validates plays and tracks win/loss  

2. **View (Text Display)**  
   - Renders game state to the console  
   - Shows hand, palette, canvas, and status messages  

3. **Controller (User Input)**  
   - Reads and parses console input  
   - Invokes model actions and handles quitting/invalid input  

---

## 🧠 Game Features

### 🔄 Palette System

- Place cards here to try to satisfy the current rule.  
- After each palette play, the model checks if you’re winning; if so, you draw a card.  

### 🖼 Canvas System

- Play a card here to **change** the rule (color → new rule).  
- The model re-evaluates your palette under the new rule.  

### 🃏 Hand and Deck

- **Hand**: cards available to play  
- **Deck**: draw pile; you draw only after a successful palette play  
- If the deck empties while you remain winning, you **win**!  

---

## 🏁 Game End Scenarios

- **Win**  
  - You exhaust all deck cards  
  - Your palette still satisfies the final rule  

- **Loss**  
  - You cannot make any legal move  
  - All possible plays would leave you not winning  

- **Invalid Input**  
  - Handled gracefully by error messages, retry prompts, and allowing `q` to quit  

---

## 🔄 Differences Between Basic and Advanced Versions

### 🔵 BASIC (`SoloRedGameModel`)

- Supports only **one rule** (Red: highest card)  
- No dynamic rule evaluation  
- Canvas plays may not alter behavior  
- Used for basic flow and input handling tests  

### 🔴 ADVANCED (`AdvancedSoloRedGameModel`)

- Fully supports **all 7 Red7 rules**  
- Playing to canvas **dynamically** changes the rule  
- Each rule has its own method (`redCanvasWinningPal()`, `violetCanvasWinningPal()`, etc.)  
- Enforces every rule’s conditions and edge cases  
- Enables strategic play around rule-setting and rule-breaking

## 🧪 Testing and Validations

Includes unit tests for:

- Valid and invalid plays to palette/canvas  
- Drawing logic  
- Win/loss detection  
- Edge cases such as empty hand, last card in deck, and invalid index input  

---

## 📁 Full File Directory and Responsibilities

```bash
src/cs3500/solored/
├── SoloRed.java
│   └─ Entry point: sets up the model & controller and starts the game loop

├── controller/
│   ├── RedGameController.java
│   │   └─ Interface defining the 'playGame()' method
│   └── SoloRedTextController.java
│       └─ Reads user input, validates moves, runs the game loop, handles quitting

├── model/
│   ├── hw02/
│   │   ├── Card.java
│   │   │   └─ Interface for a card’s color & value
│   │   ├── Cards.java
│   │   │   └─ Enum/factory of all possible cards
│   │   ├── CardComparator.java
│   │   │   └─ Compares cards for rule evaluation
│   │   ├── RedGameModel.java
│   │   │   └─ Interface defining core operations (play, draw, isGameOver)
│   │   ├── GameState.java
│   │   │   └─ Tracks hand, palette, canvas, and deck state
│   │   └── SoloRedGameModel.java
│   │       └─ Basic implementation with limited rule logic

│   └── hw04/
│       ├── AdvancedSoloRedGameModel.java
│       │   └─ Full Red7 ruleset; dynamic rule evaluation per color
│       └── RedGameCreator.java
│           └─ Factory that returns basic or advanced model based on input

├── view/
│   └── hw02/
│       ├── RedGameView.java
│       │   └─ Interface for rendering the game state
│       └── SoloRedGameTextView.java
│           └─ Text-based view that prints hand, palette, canvas, and messages

test/cs3500/solored/
├── ControllerTest.java
│   └─ Validates controller behavior (input handling, quitting)
├── ModelTest.java
│   └─ Tests basic game logic (play validity, deck drawing)
├── AdvancedModelTest.java
│   └─ Tests all 7 rules, invalid inputs, edge cases
├── PlayingCardTest.java
│   └─ Tests card comparison, equality, and sorting
└── ViewTest.java
    └─ Verifies console output of the text view
```

## ▶️ How to Run the Game

1. **Compile and run** using **any Java IDE or the `javac`/`java` commands**.  
2. **Locate and open** `SoloRed.java`.  
3. **Run** the `main` method.  
4. **When prompted**, use the following commands:  
   - **`palette`**: play a card to your palette  
   - **`canvas`**: play a card to change the rule  
   - **Index numbers**: enter the number corresponding to a card in your hand or palette  
   - **`q`**: quit the game  
