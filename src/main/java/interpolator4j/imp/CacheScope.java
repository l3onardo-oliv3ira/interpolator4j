package interpolator4j.imp;

import java.util.HashMap;
import java.util.Map;

import interpolator4j.Scope;

public class CacheScope extends ScopeWrapper {

  protected final Map<String, String> cache = new HashMap<>();

  private boolean hit = false;
  
  public CacheScope(Scope scope) {
    super(scope);
  }

  public void invalidate() {
    cache.clear();
    hit = false;
  }
  
  public boolean isHit() {
    return hit;
  }
  
  @Override
  public String eval(String expression) {
    String value = cache.get(expression);
    if (value != null) {
      hit = true;
      return value;
    }
    cache.put(expression, value = super.eval(expression));
    return value;
  }
}
