package interpolator4j.imp;

public class DefaultScopeProvider extends BasicScopeProvider {
  public DefaultScopeProvider(){
    for (DefaultScope scope: DefaultScope.values()) {
      register(scope.getScope());
    }
  }
}
