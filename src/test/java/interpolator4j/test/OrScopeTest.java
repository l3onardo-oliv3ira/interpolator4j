package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.imp.Interpolator;
import interpolator4j.imp.OrScope;

public class OrScopeTest extends InterpolatorTester{
	
	@BeforeClass
	public static void setup() {
		getBasicProvider().register(new OrScope());
	}
	
	@Test
	public void testSimpleExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${or:exp1,exp2,exp3}";
		String expected = "(exp1) OR (exp2) OR (exp3)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombineExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${or:exp1,${or:exp2,exp3},exp4}";
		String expected = "(exp1) OR ((exp2) OR (exp3)) OR (exp3)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombinedWithTextExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "text1 ${or:exp1, text2 ${or:exp2,exp3} text3, exp4}";
		String expected = "text1 (exp1) OR ( text2 (exp2) OR (exp3) text3) OR ( exp4)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}

	@Test
	public void testFieldExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${or:field;exp1,exp2,exp3}";
		String expected = "(field:\"exp1\") OR (field:\"exp2\") OR (field:\"exp3\")";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombinedWithFieldExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "text1 ${or:field;exp1,${or:exp2,exp3}} text2";
		String expected = "text1 (field:\"exp1\") OR (field:\"(exp2) OR (exp3)\") text2";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
}
