package interpolator4j.imp;

import java.util.HashMap;
import java.util.Map;

import interpolator4j.CharConfig;
import interpolator4j.Interpolator;
import interpolator4j.Scope;
import interpolator4j.ScopeProvider;
import interpolator4j.util.Arguments;
import interpolator4j.util.Objects;

public class BasicScopeProvider implements ScopeProvider {
  private final Map<String, Scope> scopes = new HashMap<String, Scope>();

  public BasicScopeProvider(){
  }

  @Override
  public ScopeProvider register(Scope scope) {
    Arguments.requireNonNull(scope, "scope can't be null");
    scopes.put(scope.getId(), scope);
    return this;
  }

  @Override
  public Scope get(String scopeName) {
    return Objects.getDefault(scopes.get(scopeName), DefaultScope.UNDEFINED);
  }

  @Override
  public Interpolator build(CharConfig charConfig) {
    return new InterpolatorParser(charConfig, this);
  }
}
