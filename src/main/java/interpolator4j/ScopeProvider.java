package interpolator4j;

import interpolator4j.imp.CharConfig;
import interpolator4j.imp.DefaultCharConfig;
import interpolator4j.imp.Interpolator;

public interface ScopeProvider {
	Scope get(String scopeName);
	
  ScopeProvider register(Scope scope);
  
  Interpolator build(CharConfig charConfig);
  
  default Interpolator build() {
  	return build(DefaultCharConfig.DOLLAR_BRACES);
  }
}
