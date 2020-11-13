package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.Interpolator;
import interpolator4j.imp.CacheScope;
import interpolator4j.imp.MathScope;

public class CacheScopeTest  extends InterpolatorTester {
  
  private static final CacheScope cache = new CacheScope(new MathScope());
  
  @BeforeClass
  public static void setup() {
    getBasicProvider().register(cache);
  }
  
  @Test
  public void testCacheAccess() {
    assertEquals(false, cache.isUsed());
    
    Interpolator i = getBasicProvider().build();
    String expression = "leonardo is the number ${math:(4-3)}";
    String expected = "leonardo is the number 1.0";
    String actual = i.interpolate(expression);
    assertEquals(actual, expected);
    assertEquals(false, cache.isUsed());

    actual = i.interpolate(expression);

    assertEquals(true, cache.isUsed());
    assertEquals(actual, expected);
    cache.invalidate();
  }
}
