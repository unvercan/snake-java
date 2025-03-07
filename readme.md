# Snake Game

## Description

This is a simple console-based Snake game written in Java. The player controls a snake, moving it around the board to consume food and grow in length. The objective is to achieve the maximum snake length without colliding with the walls or itself.

## Features

- Classic Snake game mechanics
- Player-controlled movement using `W`, `A`, `S`, `D` keys
- Food generation at random locations
- Collision detection (walls and self)
- Win condition when the snake reaches the maximum possible length
- Console-based interface with real-time updates
- Adjustable board size

## Technologies Used

- **Java** (Core logic and game implementation)
- **Lombok** (Used for annotations such as `@Getter`, `@RequiredArgsConstructor`, etc.)

## How to Play

1. Run the application.
2. Control the snake using the following keys:
    - `W` → Move up
    - `A` → Move left
    - `S` → Move down
    - `D` → Move right
3. Eat food (`*`) to grow longer.
4. Avoid crashing into walls (`#`) or yourself.
5. The game ends when:
    - The snake collides with a wall or itself (lose condition).
    - The snake reaches the maximum possible length (win condition).

## Installation and Setup

### Prerequisites

- **Java 17** or later
- **Maven** (if building the project manually)
- **Docker** (if running inside a container)

### Running the Game (Java and Maven)

1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd snake-game
   ```
2. Build the project using Maven:
   ```sh
   mvn clean package
   ```
    - This command will clean any previous builds and package the project into a JAR file.
3. Run the game using the JAR file:
   ```sh
   java -jar target/snake.jar
   ```
   
### Running the Game (Docker)

1. **Build the Docker Image:**
   ```sh
   docker build -t snake-game .
   ```
2. **Run the Docker Container:**
   ```sh
   docker run --rm -it snake-game
   ```
    - `-t` adds the name of the image while building the Docker file.
    - `--rm` ensures the container is removed after it stops.
    - `-it` keeps the container interactive for user input.

Now you can play the Snake game directly in the terminal inside the Docker container!

## Project Structure

```
├── src
│   ├── tr.unvercanunlu.snake     # Main package
│      ├── App.java               # Main entry point
│      ├── Game.java              # Game logic
│      ├── BoardConfig.java       # Board size configuration
│      ├── Helper.java            # Utility methods
│      ├── util                   # Package for Utility classes
│          ├── ConsoleUtil.java   # Console related Utility class
│          ├── InputUtil.java     # Input related Utility class
│          ├── NumberUtil.java    # Number related Utility class
│      ├── constant               # Package for Enums
│          ├── Tile.java          # Enum for board tiles
│          ├── Status.java        # Enum for game statuses
│          ├── Move.java          # Enum for movement directions
│          ├── Input.java         # Enum for player inputs
│          ├── Action.java        # Enum for possible actions
├── pom.xml                       # Maven configuration file
├── Dockerfile                    # Docker setup
```

## License

This project is open-source and available under the **MIT License**.

## Author

Ünver Can Ünlü

