package tr.unvercanunlu.snake;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import tr.unvercanunlu.snake.constant.Action;
import tr.unvercanunlu.snake.constant.Input;
import tr.unvercanunlu.snake.constant.Move;
import tr.unvercanunlu.snake.constant.Status;
import tr.unvercanunlu.snake.constant.Tile;
import tr.unvercanunlu.snake.util.ConsoleUtil;
import tr.unvercanunlu.snake.util.InputUtil;
import tr.unvercanunlu.snake.util.NumberUtil;

@Data
public class Game {

  private Status status;
  private int score;
  private Move lastMove;

  // snake
  private int[] snakeX;
  private int[] snakeY;
  private int snakeLength;
  private Action action;

  // food
  private int foodX;
  private int foodY;

  // board
  private Tile[][] board;

  public Game() {
    initGame();
  }

  public void initGame() {
    resetGame();
    generateFood();
    generateSnakeHead();
  }

  public void resetGame() {
    resetScore();
    resetStatus();
    resetLastMove();

    // board
    initBoard();
    resetBoard();

    // food
    resetFood();

    // snake
    initSnake();
    resetSnake();
    resetAction();
    resetSnakeLength();
  }

  public void initBoard() {
    board = new Tile[BoardConfig.HEIGHT][BoardConfig.WIDTH];
  }

  public void resetFood() {
    foodX = -1;
    foodY = -1;
  }

  public void resetLastMove() {
    lastMove = Move.IDLE;
  }

  public void resetScore() {
    score = 0;
  }

  public void resetStatus() {
    status = Status.PLAY;
  }

  public void resetAction() {
    action = Action.MOVE;
  }

  public void resetSnakeLength() {
    snakeLength = 1;
  }

  public void initSnake() {
    snakeX = new int[(BoardConfig.WIDTH - 1) * (BoardConfig.HEIGHT - 1)];
    snakeY = new int[(BoardConfig.WIDTH - 1) * (BoardConfig.HEIGHT - 1)];
  }

  public void resetSnake() {
    for (int i = 0; i < (BoardConfig.WIDTH - 1) * (BoardConfig.HEIGHT - 1); i++) {
      snakeX[i] = -1;
      snakeY[i] = -1;
    }
  }

  // reset board by filling with empty tiles
  public void resetBoard() {
    for (int y = 0; y < BoardConfig.HEIGHT - 1; y++) {
      for (int x = 0; x < BoardConfig.WIDTH - 1; x++) {
        board[y][x] = Tile.EMPTY;
      }
    }
  }

  public void drawVerticalWallsOnBoard() {
    for (int y = 0; y < BoardConfig.HEIGHT; y++) {
      board[y][0] = Tile.WALL;
      board[y][BoardConfig.WIDTH - 1] = Tile.WALL;
    }
  }

  public void drawHorizontalWallsOnBoard() {
    for (int x = 0; x < BoardConfig.WIDTH; x++) {
      board[0][x] = Tile.WALL;
      board[BoardConfig.HEIGHT - 1][x] = Tile.WALL;
    }
  }

  public void drawWallsOnBoard() {
    drawVerticalWallsOnBoard();
    drawHorizontalWallsOnBoard();
  }

  public void drawFoodOnBoard() {
    board[foodY][foodX] = Tile.FOOD;
  }

  public void drawSnakeTailsOnBoard() {
    for (int i = 1; i <= snakeLength - 1; i++) {
      board[snakeY[i]][snakeX[i]] = Tile.SNAKE_TAIL;
    }
  }

  public void drawSnakeHeadOnBoard() {
    board[snakeY[0]][snakeX[0]] = Tile.SNAKE_HEAD;
  }

  public void drawSnakeOnBoard() {
    drawSnakeTailsOnBoard();
    drawSnakeHeadOnBoard();
  }

  // draw walls, food, and snake (head and tails) tiles on board
  public void drawTilesOnBoard() {
    drawWallsOnBoard();
    drawFoodOnBoard();
    drawSnakeOnBoard();
  }

  public void updateAction() {
    action = Action.resolve(snakeX, snakeY, snakeLength, foodX, foodY);
  }

  public void updateStatus() {
    status = Status.resolve(action, snakeLength);
  }

  public String convertBoardToText() {
    StringBuilder builder = new StringBuilder();

    for (int y = 0; y < BoardConfig.HEIGHT; y++) {
      for (int x = 0; x < BoardConfig.WIDTH; x++) {
        builder.append(board[y][x].getRepresent());
      }

      builder.append("\n");
    }

    return builder.toString();
  }

  public void play() {
    while (status.equals(Status.PLAY)) {
      ConsoleUtil.clear();

      // update board
      resetBoard();
      drawTilesOnBoard();

      // render board
      String boardText = convertBoardToText();
      ConsoleUtil.render(boardText);

      // show score
      ConsoleUtil.render(String.format("Score: %d", score));

      // update action and status
      updateAction();
      updateStatus();

      switch (status) {
        case WIN -> ConsoleUtil.render("You win!");
        case LOSE -> ConsoleUtil.render("You lose! Please play again.");
        case PLAY -> playAction();
      }
    }
  }

  public void generateSnakeHead() {
    snakeX[0] = NumberUtil.generateNumberExcluding(1, (BoardConfig.WIDTH - 2), foodX);
    snakeY[0] = NumberUtil.generateNumberExcluding(1, (BoardConfig.HEIGHT - 2), foodY);
  }

  public void generateFood() {
    foodX = NumberUtil.generateNumberExcluding(1, (BoardConfig.WIDTH - 2), snakeX);
    foodY = NumberUtil.generateNumberExcluding(1, (BoardConfig.HEIGHT - 2), snakeY);
  }

  public void playAction() {
    ConsoleUtil.render("Movement: 'W' for up, 'A' for left, 'D' for right, 'S' for down.");
    ConsoleUtil.render("Next move: ");

    Move currentMove;
    if (lastMove.equals(Move.IDLE)) {
      Input input = InputUtil.getValidInput();
      currentMove = Move.resolve(input);
    } else {
      Input input = InputUtil.getInput(4, TimeUnit.SECONDS, Input.UNREGISTERED);
      currentMove = Move.resolve(input);

      // using last move
      if (currentMove.equals(Move.IDLE)) {
        ConsoleUtil.render("Your input is not valid. Your last move will be used.");
        currentMove = lastMove;
      }
    }

    // check if the snake eats food after moving
    if (checkSnakeWillEatFood(currentMove)) {
      snakeLength++;
      generateFood();
      score++;
    }

    moveSnake(currentMove);

    lastMove = currentMove;
  }

  public boolean checkSnakeWillEatFood(Move move) {
    return (snakeX[0] + move.getDx()) == foodX
        && (snakeY[0] + move.getDy()) == foodY;
  }

  public void moveSnakeTails() {
    // shifting
    for (int i = snakeLength - 1; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }
  }

  public void moveSnakeHead(Move move) {
    snakeX[0] += move.getDx();
    snakeY[0] += move.getDy();
  }

  public void moveSnake(Move move) {
    moveSnakeTails();
    moveSnakeHead(move);
  }

}
