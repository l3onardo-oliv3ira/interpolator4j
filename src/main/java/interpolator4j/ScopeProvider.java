package interpolator4j;

import interpolator4j.imp.DefaultCharConfig;

public interface ScopeProvider {
  Scope get(String scopeId);
  
  Scope unregister(String scopeId);

  ScopeProvider register(Scope scope);
  
  Interpolator build(CharConfig charConfig);
  
  default Interpolator build() {
    return build(DefaultCharConfig.DOLLAR_BRACES);
  }
}
