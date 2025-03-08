package tr.unvercanunlu.snake.constant;

public enum Action {

  EAT_FOOD,
  COLLIDE_WITH_SELF,
  COLLIDE_WITH_WALL,
  MOVE;

  public static Action resolve(int[] snakeX, int[] snakeY, int snakeLength, int foodX, int foodY, int boardWidth, int boardHeight) {
    // default
    Action action = Action.MOVE;

    if (isSnakeCollidingWithWall(snakeX, snakeY, boardWidth, boardHeight)) {
      action = Action.COLLIDE_WITH_WALL;
    } else if (isSnakeCollidingWithSelf(snakeX, snakeY, snakeLength)) {
      action = Action.COLLIDE_WITH_SELF;
    } else if (isSnakeEatingFood(snakeX, snakeY, foodX, foodY)) {
      action = Action.EAT_FOOD;
    }

    return action;
  }

  private static boolean isSnakeCollidingWithWall(int[] snakeX, int[] snakeY, int boardWidth, int boardHeight) {
    return (snakeX[0] == (boardWidth - 1))
        || (snakeY[0] == (boardHeight - 1))
        || (snakeX[0] == 0)
        || (snakeY[0] == 0);
  }

  private static boolean isSnakeCollidingWithSelf(int[] snakeX, int[] snakeY, int snakeLength) {
    boolean collidedWithSelf = false;

    for (int i = 1; i < snakeLength; i++) {
      if ((snakeX[0] == snakeX[i])
          && (snakeY[0] == snakeY[i])) {
        collidedWithSelf = true;
        break;
      }
    }

    return collidedWithSelf;
  }

  private static boolean isSnakeEatingFood(int[] snakeX, int[] snakeY, int foodX, int foodY) {
    return (snakeX[0] == foodX)
        && (snakeY[0] == foodY);
  }

}
