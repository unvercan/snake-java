package tr.unvercanunlu.snake.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Move {

  UP(0, -1),
  DOWN(0, 1),
  LEFT(-1, 0),
  RIGHT(1, 0),
  IDLE(0, 0);

  private final int dx;
  private final int dy;

  public static Move resolve(Input input) {
    // null-check
    if (input == null) {
      return Move.IDLE;
    }

    return switch (input) {
      case W -> Move.UP;
      case S -> Move.DOWN;
      case A -> Move.LEFT;
      case D -> Move.RIGHT;
      default -> Move.IDLE;
    };
  }

}
