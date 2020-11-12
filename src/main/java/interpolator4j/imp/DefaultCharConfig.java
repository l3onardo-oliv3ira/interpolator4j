package interpolator4j.imp;

import static interpolator4j.imp.Boundary.BRACES;
import static interpolator4j.imp.Boundary.BRACKETS;
import static interpolator4j.imp.Expression.AND;
import static interpolator4j.imp.Expression.AT;
import static interpolator4j.imp.Expression.DOLLAR;
import static interpolator4j.imp.Expression.HASH;
import static interpolator4j.imp.Expression.PERCENT;

public enum DefaultCharConfig implements CharConfig {
	DOLLAR_BRACKETS			(DOLLAR, BRACKETS),
	DOLLAR_BRACES				(DOLLAR, BRACES),
	
	HASH_BRACKETS				(HASH, BRACKETS),
	HASH_BRACES					(HASH, BRACES),
	
	AT_BRACKETS					(AT, BRACKETS),
	AT_BRACES						(AT, BRACES),
	
	AND_BRACKETS				(AND, BRACKETS),
	AND_BRACES					(AND, BRACES),
	
	PERCENT_BRACKETS		(PERCENT, BRACKETS),
	PERCENT_BRACES			(PERCENT, BRACES);

	private ExpressionChar expressionChar;
	private Boundary boundary;

	DefaultCharConfig(ExpressionChar expressionChar, Boundary boundary){
		this.expressionChar = expressionChar;
		this.boundary = boundary;
	}
	
	@Override
	public char getChar() {
		return expressionChar.getChar();
	}

	@Override
	public char getBegin() {
		return boundary.getBegin();
	}

	@Override
	public char getEnd() {
		return boundary.getEnd();
	}
}
