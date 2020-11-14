package interpolator4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.DebugOption;
import interpolator4j.Interpolator;
import interpolator4j.imp.AndScope;
import interpolator4j.imp.DefaultCharConfig;
import interpolator4j.imp.DefaultScopeProvider;
import interpolator4j.imp.MathScope;
import interpolator4j.imp.OrScope;
import interpolator4j.imp.SimpleMapScope;

public class InterpolatorTest {

  private final static DefaultScopeProvider PROVIDER = new DefaultScopeProvider();

  @BeforeClass
  public static void setup(){
    System.setProperty("one", "1");
    System.setProperty("two", "2");
    System.setProperty("three", "3");
    System.setProperty("four", "4");
    System.setProperty("five", "5");
    System.setProperty("six", "6");
    
    PROVIDER
    .register(new MathScope())
    .register(new AndScope())
    .register(new OrScope())
    .register(
      new SimpleMapScope.Builder()
        .map("1", "one")
        .map("2", "two")
        .map("3", "three")
        .map("4", "four")
        .map("5", "five")
        .map("6", "six")
        .map("s", "system")
        .build("map")
      )
    .register(new SimpleMapScope.Builder()
      .map("fast", "quick")
      .map("dark", "brown")
      .map("lion", "fox")
      .map("skip", "jumps")
      .map("on", "over")
      .map("the", "a")
      .map("lazy", "slow")
      .map("dog", "cat")
      .map("a slow cat", "the lazy dog")
      .build("syn")
    );
  }
  
  @Test
  public void testRuntime() {
    Interpolator r = PROVIDER.build();
    String processors = r.interpolate("${runtime:availableProcessors}");
    assertTrue("Available processors must be positive", Integer.parseInt(processors) > 0);
    String totalMemory = r.interpolate("${runtime:totalMemory}");
    assertTrue("totalMemory must be positive", Integer.parseInt(totalMemory) > 0);
    String freeMemory = r.interpolate("${runtime:freeMemory}");
    assertTrue("freeMemory must be positive", Integer.parseInt(freeMemory) > 0);
    String maxMemory = r.interpolate("${runtime:maxMemory}");
    assertTrue("maxMemory must be positive", Integer.parseInt(maxMemory) > 0);
  }

  @Test
  public void testTrivial() {
    Interpolator r = PROVIDER.build();
    assertEquals("1", r.interpolate("${system:one}"));
  }

  @Test
  public void testEmpty(){
    Interpolator r = PROVIDER.build();
    String expression = "${system:}";
    assertEquals("", r.interpolate(expression));
  }  
  
  @Test
  public void testInterpolation(){
    Interpolator r = PROVIDER.build();
    String expression = "word ${map:1} equals ${system:one}";
    assertEquals("word one equals 1", r.interpolate(expression, DebugOption.SYSOUT));
  }
  
  @Test
  public void testSimple(){
    Interpolator r = PROVIDER.build();
    String expression = "${system:invalid_key}";
    assertEquals("", r.interpolate(expression));
  }  

  @Test
  public void testChain(){
    Interpolator r = PROVIDER.build();
    String expression = "${system:${map:${system:${map:1}}}}";
    assertEquals("1", r.interpolate(expression));
  }
  
  @Test
  public void testReplacementAfter(){
    Interpolator r = PROVIDER.build();
    String expression = "one ${system:one}";
    assertEquals("one 1", r.interpolate(expression));
  }
  
  @Test
  public void testReplacementBefore(){
    Interpolator r = PROVIDER.build(DefaultCharConfig.HASH_BRACKETS);
    String expression = "#[map:1] 1";
    assertEquals("one 1", r.interpolate(expression));
  }
  
  @Test
  public void testReplacementMiddle(){
    Interpolator r = PROVIDER.build();
    String expression = "i am the number ${system:one} (winner)";
    assertEquals("i am the number 1 (winner)", r.interpolate(expression));
  }
  
  @Test
  public void testLiteraly(){
    Interpolator r = PROVIDER.build();
    String expression = "${system} leonardo";
    assertEquals("${system} leonardo", r.interpolate(expression));
  }
  
  @Test
  public void testSintaxError1(){
    Interpolator r = PROVIDER.build();
    String expression = "${system:$";
    assertEquals(expression, r.interpolate(expression));
  }

  @Test
  public void testSintaxError2(){
    Interpolator r = PROVIDER.build();
    String expression = "${system:{";
    assertEquals(expression, r.interpolate(expression));
  }

  @Test
  public void testUnknownScope(){
    Interpolator r = PROVIDER.build();
    String expression = "${whatever:value}";
    assertEquals("scope not found", r.interpolate(expression));
  }

  @Test
  public void testReplacementDoubleMiddle(){
    Interpolator r = PROVIDER.build();
    String expression = "one ${map:1} one ${system:one} one";
    assertEquals("one one one 1 one", r.interpolate(expression));
  }
  
  @Test
  public void testDots(){
    Interpolator r = PROVIDER.build();
    String expression = "${map:1}:${map:1}";
    assertEquals("one:one", r.interpolate(expression));
    expression = "${map:1}${map:1}";
    assertEquals("oneone", r.interpolate(expression));
    expression = "${map:1}:\"${map:1}\"";
    assertEquals("one:\"one\"", r.interpolate(expression));
  }
  
  @Test
  public void testComplex(){
    Interpolator r = PROVIDER.build();
    String expression = "# ${syn:fast} ${syn:dark} ${syn:lion} ${syn:skip} ${syn:on} "
        + "${syn:${syn:the} ${syn:lazy} ${syn:dog}} #";
    String answer = "# quick brown fox jumps over the lazy dog #";
    String output = r.interpolate(expression);
    assertEquals(answer, output);
  }

  @Test
  public void testOrScope(){
    Interpolator r = PROVIDER.build();
    String expression = "${or:field;1,2}";
    assertEquals("(field:\"1\") OR (field:\"2\")", r.interpolate(expression));
  }
  
  @Test
  public void testAndScope(){
    Interpolator r = PROVIDER.build();
    String expression = "${and:field;one,${map:1},${system:one}}";
    assertEquals("(field:\"one\") AND (field:\"one\") AND (field:\"1\")", r.interpolate(expression));
  }
  
  @Test
  public void testAndScopeWithoutField(){
    Interpolator r = PROVIDER.build();
    String expression = "${and:leonardo,${map:1},oliveira}";
    assertEquals("(leonardo) AND (one) AND (oliveira)", r.interpolate(expression));
  }
  
  @Test
  public void testAndFieldScope(){
    Interpolator r = PROVIDER.build();
    String expression = "${and:${map:2};one,${map:1},${system:one}}";
    assertEquals("(two:\"one\") AND (two:\"one\") AND (two:\"1\")", r.interpolate(expression));
  }
  
  @Test
  public void testMathScope(){
    Interpolator r = PROVIDER.build();
    String expression = "${math:((3+4)*2)}";
    assertEquals("14.0", r.interpolate(expression));
  }
  
  @Test
  public void testMathScopeSyn(){
    Interpolator r = PROVIDER.build(DefaultCharConfig.AT_BRACKETS);
    String expression = "@[long:@[math:((3*4)-10)]]";
    assertEquals("2", r.interpolate(expression));
  }
}
