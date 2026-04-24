# Final Project for BCIT CST Object Oriented Programming 1 (COMP 2522)

A command-line game hub with 3 playable games, built as the term project for BCIT's COMP 2522 Object Oriented Programming 1 course.

**Authors:** Jason Lau

---

## Games

### Word Game
A geography quiz that tests your knowledge of world capitals and country facts. Each session presents 10 randomized questions — you get 2 attempts per question. Scores are tracked across sessions, saved to a local file, and compared against your personal high score.

### Number Game
*(Coming soon)*

### Hex Game
*(Coming soon)*

---

## Tech Stack

- **Language:** Java 21
- **IDE:** IntelliJ IDEA (plain module — no Maven or Gradle)
- **Testing:** JUnit Jupiter 5.8.1

---

## Installation

### Prerequisites
- [Java 21 JDK](https://www.oracle.com/java/technologies/downloads/#java21) installed
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community or Ultimate)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/jpg157/Comp2522_Term_Project.git
   ```

2. **Open the project in IntelliJ IDEA**
   - Go to **File → Open**
   - Navigate to `Comp2522_Term_Project/Term_Project_Jason_Lau` and click **Open**

3. **Set the Project SDK to Java 21**
   - Go to **File → Project Structure → Project**
   - Set the SDK to **Java 21** (download it from the IDE if not installed)

4. **Run the application**
   - Open `src/Main.java`
   - Click the green **Run** button next to the `main` method, or right-click the file and select **Run 'Main'**

5. **Follow the on-screen prompts** to choose a game:
   - Press `W` — Word Game
   - Press `N` — Number Game
   - Press `M` — Hex Game
   - Press `Q` — Quit
