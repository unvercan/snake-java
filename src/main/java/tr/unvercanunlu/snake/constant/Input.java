package tr.unvercanunlu.snake.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Input {

  W(new char[]{'W', 'w'}),
  A(new char[]{'A', 'a'}),
  S(new char[]{'S', 's'}),
  D(new char[]{'D', 'd'}),
  UNREGISTERED(new char[]{});

  private final char[] keys;

  public static Input resolve(Character key) {
    // null-check
    if (key == null) {
      return Input.UNREGISTERED;
    }

    // default
    Input input = Input.UNREGISTERED;

    for (int i = 0; i < Input.values().length; i++) {
      for (int j = 0; j < Input.values()[i].keys.length; j++) {
        if (Input.values()[i].keys[j] == key) {
          input = Input.values()[i];
          break;
        }
      }
    }

    return input;
  }

}
