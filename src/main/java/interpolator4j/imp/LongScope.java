package interpolator4j.imp;

public class LongScope extends AbstractScope {
  public LongScope() {
    this("long");
  }
  
  public LongScope(String id) {
    super(id);
  }
  
  @Override
  protected String doEval(String expression) {
    return Long.toString((long)Double.parseDouble(expression));
  }
}
