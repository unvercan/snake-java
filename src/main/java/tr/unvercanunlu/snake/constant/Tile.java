package tr.unvercanunlu.snake.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tile {

  EMPTY('.'),
  FOOD('*'),
  WALL('#'),
  SNAKE_HEAD('@'),
  SNAKE_TAIL('O');

  private final char represent;

}
