package interpolator4j.test;

import interpolator4j.ScopeProvider;
import interpolator4j.imp.BasicScopeProvider;
import interpolator4j.imp.DefaultScopeProvider;

public class InterpolatorTester {
	private static ScopeProvider BASIC_PROVIDER = new BasicScopeProvider();

	private static ScopeProvider DEFAULT_PROVIDER = new DefaultScopeProvider();

	protected static ScopeProvider getBasicProvider() {
		return BASIC_PROVIDER;
	}
	
	protected static ScopeProvider getDefaultProvider() {
		return DEFAULT_PROVIDER;
	}
}
