package interpolator4j.imp;

import interpolator4j.Scope;

public class DefaultScopeProvider extends BasicScopeProvider {
  public DefaultScopeProvider(){
    for (Scope scope: DefaultScope.values()) {
      register(scope);
    }
  }
}
