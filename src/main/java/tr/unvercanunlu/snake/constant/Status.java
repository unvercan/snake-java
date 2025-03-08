package tr.unvercanunlu.snake.constant;


public enum Status {

  PLAY,
  WIN,
  LOSE;

  public static Status resolve(Action action, int snakeLength, int boardWidth, int boardHeight) {
    // default
    Status status = Status.PLAY;

    if (checkCollusion(action)) {
      status = Status.LOSE;
    } else if (isWin(action, snakeLength, boardWidth, boardHeight)) {
      status = Status.WIN;
    }

    return status;
  }

  private static boolean checkCollusion(Action action) {
    return action.equals(Action.COLLIDE_WITH_WALL)
        || action.equals(Action.COLLIDE_WITH_SELF);
  }

  private static boolean isSnakeLengthReachedMax(int snakeLength, int boardWidth, int boardHeight) {
    return snakeLength == ((boardWidth - 1) * (boardHeight - 1));
  }

  private static boolean isWin(Action action, int snakeLength, int boardWidth, int boardHeight) {
    return !checkCollusion(action)
        && isSnakeLengthReachedMax(snakeLength, boardWidth, boardHeight);
  }

}
