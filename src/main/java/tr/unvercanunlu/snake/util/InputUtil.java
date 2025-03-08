package tr.unvercanunlu.snake.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tr.unvercanunlu.snake.constant.Input;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputUtil {

  private static final BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
  private static final BlockingQueue<Input> inputQueue = new LinkedBlockingQueue<>();

  public static Character readKey() {
    Character key = null;

    try {
      key = Optional.ofNullable(
              reader.readLine()
          ).map(String::trim)
          .map(String::toUpperCase)
          .filter(s -> !s.isBlank())
          .map(s -> s.charAt(0))
          .orElse(null);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return key;
  }

  public static Input getValidInputTimeout(long timeout, TimeUnit unit, Input defaultInput) {
    Runnable keyReaderTask = () -> {
      inputQueue.clear();

      do {
        Input input = InputUtil.getValidInput();
        inputQueue.offer(input);

      } while (inputQueue.isEmpty());
    };

    Thread keyReaderThread = new Thread(keyReaderTask);
    keyReaderThread.setDaemon(true);
    keyReaderThread.start();

    Input input;

    try {
      input = inputQueue.poll(timeout, unit);

      input = Optional.ofNullable(input).orElse(Input.UNREGISTERED);

      if (input.equals(Input.UNREGISTERED)) {
        input = defaultInput;
      }

    } catch (InterruptedException e) {
      keyReaderThread.interrupt();
      input = defaultInput;
    }

    return input;
  }

  public static Input getValidInput() {
    Input input;

    do {
      Character key = InputUtil.readKey();
      input = Input.resolve(key);

      input = Optional.ofNullable(input).orElse(Input.UNREGISTERED);

      if (input.equals(Input.UNREGISTERED)) {
        ConsoleUtil.render("Your input is not valid. Please, enter again:");
      }

    } while (input.equals(Input.UNREGISTERED));

    return input;
  }

}
