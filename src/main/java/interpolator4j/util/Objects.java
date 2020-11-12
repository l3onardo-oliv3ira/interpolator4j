package interpolator4j.util;

public class Objects {
  private Objects() {}

  public static <T> T getDefault(T value, T defaultIfNull) {
    return value == null ? defaultIfNull : value;
  }
}
