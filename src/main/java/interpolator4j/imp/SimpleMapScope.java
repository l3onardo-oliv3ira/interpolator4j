package interpolator4j.imp;

import java.util.Map;

public class SimpleMapScope extends MapScope<Object> {
  
  public static class Builder extends MapScope.Builder<Object> {
    @SuppressWarnings("unchecked")
    @Override
    public SimpleMapScope build(String id) {
      return new SimpleMapScope(id, map);
    }
  }
    
  private SimpleMapScope(String id, Map<String, Object> map) {
    super(id, map);
  }
}
