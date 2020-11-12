package interpolator4j.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import interpolator4j.imp.AndScope;
import interpolator4j.imp.Interpolator;

public class AndScopeTest extends InterpolatorTester {
	
	@BeforeClass
	public static void setup() {
		getBasicProvider().register(new AndScope());
	}
	
	@Test
	public void testSimpleExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${and:exp1,exp2,exp3}";
		String expected = "(exp1) AND (exp2) AND (exp3)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombineExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${and:exp1,${and:exp2,exp3},exp4}";
		String expected = "(exp1) AND ((exp2) AND (exp3)) AND (exp3)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombinedWithTextExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "text1 ${and:exp1, text2 ${and:exp2,exp3} text3, exp4}";
		String expected = "text1 (exp1) AND ( text2 (exp2) AND (exp3) text3) AND ( exp4)";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}

	@Test
	public void testFieldExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "${and:field;exp1,exp2,exp3}";
		String expected = "(field:\"exp1\") AND (field:\"exp2\") AND (field:\"exp3\")";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCombinedWithFieldExpression() {
		Interpolator i = getBasicProvider().build();
		String expression = "text1 ${and:field;exp1,${and:exp2,exp3}} text2";
		String expected = "text1 (field:\"exp1\") AND (field:\"(exp2) AND (exp3)\") text2";
		String actual = i.interpolate(expression);
		assertEquals(expected, actual);
	}
}
