package interpolator4j.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class SupplierScope extends MapScope<Supplier<Object>> {

  public static class Builder {
    private final Map<String, Supplier<Object>> map = new HashMap<>();

    public final Builder map(String key, Supplier<Object> value) {
      map.put(key, value);
      return this;
    }

    public SupplierScope build(String id) {
      return new SupplierScope(id, map);
    }
  }
  
	private SupplierScope(String id, Map<String, Supplier<Object>> map) {
    super(id, map);
  }

  @Override
  protected Object read(String expression) {
    return Optional.ofNullable(map.get(expression)).orElse(() -> "").get();
  }
}
