package tr.unvercanunlu.snake.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleUtil {

  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void render(String text) {
    System.out.println(text);
  }

}
