package tr.unvercanunlu.snake.util;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtil {

  private static final Random random = new Random();

  public static int generateNumber(int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }

  public static int generateNumberExcluding(int min, int max, int... excludes) {
    int number;

    boolean overlap;

    do {
      overlap = false;

      number = NumberUtil.generateNumber(min, max);

      for (int exclude : excludes) {
        if (exclude == number) {
          overlap = true;
          break;
        }
      }
    } while (overlap);

    return number;
  }

}
