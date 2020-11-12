package interpolator4j.imp;

import static interpolator4j.util.Arguments.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import interpolator4j.util.Objects;

public class MapScope<T> extends AbstractScope {

  protected final Map<String, T> map;

  public MapScope(String id, Map<String, T> map) {
    this(id, new HashMap<>(requireNonNull(map, "map can't be null"))); //defensive copy
  }

  protected MapScope(String id, HashMap<String, T> map) {
    super(id);
    this.map = map; 
  }

  protected Object read(String expression) {
    return map.get(expression);
  }

  @Override
  protected String doEval(String expression) {
    return Objects.getDefault(read(expression), "").toString();
  }
}
