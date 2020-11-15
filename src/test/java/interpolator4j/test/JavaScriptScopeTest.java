package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.Interpolator;
import interpolator4j.imp.AndScope;
import interpolator4j.imp.ConstScope;
import interpolator4j.imp.DefaultCharConfig;
import interpolator4j.imp.JavaScriptScope;

public class JavaScriptScopeTest extends InterpolatorTester {
  
  @BeforeClass
  public static void setup() {
    getBasicProvider().register(new AndScope());
  }
  
  @Test
  public void test() {
    String code = "function sum(a, b) { return a + b; } sum(3,4);";
    Interpolator i = getBasicProvider()
      .register(new ConstScope("code", code))
      .register(new JavaScriptScope("js")).build(DefaultCharConfig.HASH_BRACKETS);
    String e1 = "Result is #[js:#[code:source]]";
    String actual = i.interpolate(e1);
    String expected = "Result is 7.0";
    assertEquals(expected, actual);
  }
}
