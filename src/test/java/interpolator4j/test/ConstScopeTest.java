package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.Interpolator;
import interpolator4j.imp.ConstScope;

public final class ConstScopeTest extends InterpolatorTester {
  
  
  @BeforeClass
  public static void setup() {
    getBasicProvider()
      .register(new ConstScope("imutable value"))
      .register(new ConstScope("final", "imutable value"));
  }
  
  @Test
  public void testConstScope() {
    Interpolator i = getBasicProvider().build();
    String expression = "this is a ${const:imutable value}";
    final String expected = "this is a imutable value";
    String actual = i.interpolate(expression);
    assertEquals(actual, expected);
    expression = "this is a ${final:imutable value}";
    assertEquals(actual, expected);
  }
}