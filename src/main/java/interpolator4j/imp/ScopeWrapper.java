package interpolator4j.imp;

import interpolator4j.Scope;
import interpolator4j.util.Arguments;

public class ScopeWrapper implements Scope {

  private Scope scope;

  public ScopeWrapper(Scope scope) {
    this.scope = Arguments.requireNonNull(scope, "Unabled to define null scope");
  }

  @Override
  public String getId() {
    return scope.getId();
  }

  @Override
  public String eval(String expression) {
    return scope.eval(expression);
  }
}
