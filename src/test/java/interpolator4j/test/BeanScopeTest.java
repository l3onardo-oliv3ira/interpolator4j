package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.DebugOption;
import interpolator4j.Interpolator;
import interpolator4j.imp.BeanScope;

public class BeanScopeTest extends InterpolatorTester {
  
  @SuppressWarnings("unused")
  private static class C extends Object {
    public final String field1 = "value01";
    public final String getField2() {
      return "value02";
    }
  }
  
  @SuppressWarnings("unused")
  private static class B extends C {
    public final String field3 = "value03";
    public final String getField4() {
      return "value04";
    }
  }
  
  @SuppressWarnings("unused")
  private static class A {
    public final B field5 = new B();
    public final A getField6() {
      return new A();
    }
  }
  
  private static final A POJO = new A();
  
  @BeforeClass
  public static void setup() {
    getBasicProvider().register(new BeanScope("pojo", POJO));
  }
  
  @Test
  public void test() {
    Interpolator i = getBasicProvider().build();
    String expression = "${pojo:field5.field3} ${pojo:field6.field6.field5.field4} ${pojo:field6.field5.field1} ${pojo:field6.field5.field2}";
    String expected = "value03 value04 value01 value02";
    String actual = i.interpolate(expression, DebugOption.SYSOUT);
    assertEquals(expected, actual);
  }
}
