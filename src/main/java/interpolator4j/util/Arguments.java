package interpolator4j.util;

import java.util.function.Predicate;

public final class Arguments {
  private Arguments(){}

  public static <T> T requireFalse(Predicate<T> predicate, T input, String message) {
    return requireFalse(predicate.test(input), input, message);
  }

  public static <T> T requireFalse(boolean test, T input, String message) {
    return requireTrue(!test, input, message);
  }

  public static <T> T requireNonNull(T input, String message) {
    return requireTrue(input != null, input, message);
  }

  public static <T> T requireNull(T input, String message) {
    return requireTrue(input == null, input, message);
  }

  public static <T> T requireTrue(Predicate<T> predicate, T input, String message) {
    return requireTrue(predicate.test(input), input, message);
  }

  public static <T> T requireTrue(boolean test, T input, String message) {
    if (!test)
      throw new IllegalArgumentException(message);
    return input;
  }
}
