package tr.unvercanunlu.snake.constant;

import tr.unvercanunlu.snake.BoardConfig;

public enum Status {

  PLAY,
  WIN,
  LOSE;

  public static Status resolve(Action action, int snakeLength) {
    // default
    Status status = Status.PLAY;

    if (checkCollusion(action)) {
      status = Status.LOSE;
    } else if (isWin(action, snakeLength)) {
      status = Status.WIN;
    }

    return status;
  }

  private static boolean checkCollusion(Action action) {
    return action.equals(Action.COLLIDE_WITH_WALL)
        || action.equals(Action.COLLIDE_WITH_SELF);
  }

  private static boolean isSnakeLengthReachedMax(int snakeLength) {
    return snakeLength == ((BoardConfig.WIDTH - 1) * (BoardConfig.HEIGHT - 1));
  }

  private static boolean isWin(Action action, int snakeLength) {
    return !checkCollusion(action)
        && isSnakeLengthReachedMax(snakeLength);
  }

}
