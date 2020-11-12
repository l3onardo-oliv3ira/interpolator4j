package interpolator4j.imp;

import java.util.HashMap;
import java.util.Map;

import interpolator4j.Scope;

public class CacheScope extends ScopeWrapper {

  protected final Map<String, String> cache = new HashMap<>();

  public CacheScope(Scope scope) {
    super(scope);
  }

  @Override
  public String eval(String expression) {
    String value = cache.get(expression);
		if (value != null)
      return value;
    cache.put(expression, value = super.eval(expression));
    return value;
  }
}
