package interpolator4j.imp;

import static interpolator4j.util.Strings.split;

import java.util.List;

import interpolator4j.AbstractScope;
import interpolator4j.util.Arguments;
import interpolator4j.util.Strings;

public class BinaryScope extends AbstractScope {

  private String operator;

  public BinaryScope(String id) {
    this(id, id);
  }

  protected BinaryScope(String id, String operator) {
    super(id);
    this.operator = Arguments.requireTrue(Strings::hasText, operator, "Operator can't be null or empty");
  }

  @Override
  protected String doEval(String expression) {
    int index = expression.indexOf(';');
    String field, members;
    if (index < 0) {
      field = "";
      members = expression;
    } else {
      field = expression.substring(0, index).trim();
      members = expression.substring(index + 1);
    }
    boolean hasField = !field.isEmpty();
    List<String> parts = split(members, ',');
		StringBuilder b = new StringBuilder();
		for(String m: parts){
			if (!Strings.hasText(m))
				continue;
			if (b.length() > 0)
				b.append(' ').append(operator).append(' ');
			b.append('(').append(hasField ? field + ":\"" : "").append(m).append(hasField ? "\"" : "").append(')');
		}
		return b.toString();
	}
}