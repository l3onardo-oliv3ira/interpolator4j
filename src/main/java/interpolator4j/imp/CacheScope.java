package interpolator4j.imp;

import java.util.HashMap;
import java.util.Map;

import interpolator4j.Scope;

public class CacheScope extends ScopeWrapper {

  protected final Map<String, String> cache = new HashMap<>();

  private boolean used = false;
  
  public CacheScope(Scope scope) {
    super(scope);
  }

  public void invalidate() {
    cache.clear();
    used = false;
  }
  
  public boolean isUsed() {
    return used;
  }
  
  @Override
  public String eval(String expression) {
    String value = cache.get(expression);
    if (value != null) {
      used = true;
      return value;
    }
    cache.put(expression, value = super.eval(expression));
    return value;
  }
}
