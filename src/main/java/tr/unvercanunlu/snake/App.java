package tr.unvercanunlu.snake;

import tr.unvercanunlu.snake.util.ConsoleUtil;

public class App {

  // default size
  private static final int DEFAULT_BOARD_WIDTH = 15;
  private static final int DEFAULT_BOARD_HEIGHT = 8;

  public static void main(String[] arguments) {
    // initializing with default values
    int boardWidth = DEFAULT_BOARD_WIDTH;
    int boardHeight = DEFAULT_BOARD_HEIGHT;

    // parsing arguments if exists
    if (arguments != null) {
      for (String argument : arguments) {
        String[] separated = argument.split("=");
        if (separated.length == 2) {
          String argumentName = separated[0].trim();
          String argumentValue = separated[1].trim();

          if (argumentName.equalsIgnoreCase("boardWidth")) {
            boardWidth = Integer.parseInt(argumentValue);
          } else if (argumentName.equalsIgnoreCase("boardHeight")) {
            boardHeight = Integer.parseInt(argumentValue);
          }
        }
      }
    }

    boolean valid = true;

    // validating argument values
    if (boardWidth < DEFAULT_BOARD_WIDTH) {
      ConsoleUtil.render("ERROR: Given width is too small.");
      valid = false;
    }

    if (boardHeight < DEFAULT_BOARD_HEIGHT) {
      ConsoleUtil.render("ERROR: Given height is too small.");
      valid = false;
    }

    // exit if not valid
    if (!valid) {
      return;
    }

    // give current setting info
    ConsoleUtil.render(String.format("Board size (width x height): %d x %d", boardWidth, boardHeight));

    Game game = new Game(boardWidth, boardHeight);
    game.play();
  }

}
