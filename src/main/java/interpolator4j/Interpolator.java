package interpolator4j;

public interface Interpolator {
  default String interpolate(String input) {
    return interpolate(input, DebugOption.SILENT);
  }
  
  String interpolate(String input, DebugMode mode);
}
