package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.Interpolator;
import interpolator4j.imp.MathScope;
import interpolator4j.imp.SimpleMapScope;
import interpolator4j.imp.SysoutScope;

public class SysoutScopeTest extends InterpolatorTester {
  
  @BeforeClass
  public static void setup() {
    getDefaultProvider()
      .register(new MathScope())
      .register(new SysoutScope())
      .register(new SimpleMapScope.Builder()
        .map("1", "one")
        .map("2", "two")
        .map("3", "three")
        .build("map")
      );
  }
  
  @Test
  public void test() {
    String expression = "${sysout:leonardo ${sysout:oliveira} is the number ${sysout:${map:${sysout:${long:${sysout:${math:(3-2)}}}}}}}";
    String expected = "leonardo oliveira is the number one";
    Interpolator i = getDefaultProvider().build();
    String actual = i.interpolate(expression);
    assertEquals(expected, actual);
  }
}