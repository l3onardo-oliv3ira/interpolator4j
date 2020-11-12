package interpolator4j.imp;

import interpolator4j.util.MathParser;

public class MathScope extends AbstractScope {

  public MathScope() {
    this("math");
  }
  
  public MathScope(String id) {
    super(id);
  }

  @Override
  protected String doEval(String expression) {
    return Double.toString(MathParser.parse(expression));
  }
}
