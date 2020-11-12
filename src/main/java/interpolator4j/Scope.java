package interpolator4j;

public interface Scope {
  String getId();
  String eval(String expression);
}
