package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.Interpolator;
import interpolator4j.imp.MathScope;
import interpolator4j.imp.PrintWriterScope;

public class PrintWriterScopeText extends InterpolatorTester {
  
  private static final StringWriter OUTPUT = new StringWriter();
  
  @BeforeClass
  public static void setup() {
    getBasicProvider()
      .register(new MathScope())
      .register(new PrintWriterScope(OUTPUT));
  }
 
  @Test
  public void test() {
    String expression = "${print:leonardo is the number ${math:(3-2)}}";
    String expected = "leonardo is the number 1.0";
    Interpolator i = getBasicProvider().build();
    String actual = i.interpolate(expression);
    assertEquals(expected, actual);
    assertEquals(expected, OUTPUT.toString());
  }
}
  