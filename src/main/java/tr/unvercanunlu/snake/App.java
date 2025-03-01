package tr.unvercanunlu.snake;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class App {
  // score
  int score = 0;

  // snake
  static int[] snakeX = new int[Config.Snake.LENGTH_MAX];
  static int[] snakeY = new int[Config.Snake.LENGTH_MAX];
  static int snakeLength = 1;

  // map
  static char[][] map = new char[Config.Map.HEIGHT][Config.Map.WIDTH];

  static void gameLoop() throws IOException, InterruptedException {
    while (!isGameEnded()) {
      updateMap();
      render();
      char input = getPlayerInput();
      moveSnake(input);
      clearScreen();
    }
  }

  static boolean isGameEnded() {
    return collidedWithWall() || collidedWithSelf();
  }

  static boolean collidedWithWall() {
    return (snakeX[0] == Config.Map.WIDTH - 1) || (snakeY[0] == Config.Map.HEIGHT - 1);
  }

  static boolean collidedWithSelf() {
    return (snakeX[0] == Config.Map.WIDTH - 1) || (snakeY[0] == Config.Map.HEIGHT - 1);
  }

  static void fillEmptyTiles() {
    for (int i = 1; i < Config.Map.HEIGHT - 1; i++) {
      for (int j = 1; j < Config.Map.WIDTH - 1; j++) {
        map[i][j] = Tile.EMPTY;
      }
    }
  }

  static void fillWallTiles() {
    // vertical
    for (int i = 0; i < Config.Map.HEIGHT; i++) {
      map[i][0] = Tile.WALL;
      map[i][Config.Map.WIDTH - 1] = Tile.WALL;
    }

    // horizontal
    for (int j = 0; j < Config.Map.WIDTH; j++) {
      map[0][j] = Tile.WALL;
      map[Config.Map.HEIGHT - 1][j] = Tile.WALL;
    }
  }

  static void fillSnakeHeadTile() {
    map[snakeY[0]][snakeX[0]] = Tile.SNAKE_HEAD;
  }


  static void fillSnakeTailTiles() {
    for (int i = 1; i < snakeLength; i++) {
      map[snakeY[i]][snakeX[i]] = Tile.SNAKE_TAIL;
    }
  }

  static void fillSnakeTiles() {
    fillSnakeHeadTile();
    fillSnakeTailTiles();
  }

  static void updateMap() {
    fillEmptyTiles();
    fillWallTiles();
    fillSnakeTiles();
  }

  static void render() {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < Config.Map.HEIGHT; i++) {
      for (int j = 0; j < Config.Map.WIDTH; j++) {
        builder.append(map[i][j]);
      }

      builder.append("\n");
    }

    System.out.println(builder);
  }

  static void clearScreen() throws IOException, InterruptedException {
    Process process = new ProcessBuilder("cmd", "/c", "cls")
        .inheritIO()
        .start();

    process.waitFor();
  }

  static char readInput() {
    Scanner scanner = new Scanner(System.in);

    return scanner.next()
        .toUpperCase()
        .charAt(0);
  }

  static void moveSnake(char input) {
    moveSnakeHead(input);
    moveSnakeTail();
  }

  static void moveSnakeHead(char input) {
    switch (input) {
      case Input.UP -> snakeY[0] -= 1;
      case Input.LEFT -> snakeX[0] -= 1;
      case Input.DOWN -> snakeY[0] += 1;
      case Input.RIGHT -> snakeX[0] += 1;
    }
  }

  static void moveSnakeTail() {
    for (int i = snakeLength - 1; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }
  }

  static char getPlayerInput() {
    List<Character> validInputs = List.of(Input.UP, Input.LEFT, Input.DOWN, Input.RIGHT);

    char input = readInput();

    while (!validInputs.contains(input)) {
      System.out.println("wrong input");

      input = readInput();
    }

    return input;
  }

  static void placeSnake() {
    snakeX[0] = (Config.Map.WIDTH / 2) - 1;
    snakeY[0] = (Config.Map.HEIGHT / 2) - 1;
  }

  static void placeFood(){

  }

  static boolean isFoodEaten(){
    return false;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    placeSnake();
    placeFood();
    updateMap();
    gameLoop();
  }
}
