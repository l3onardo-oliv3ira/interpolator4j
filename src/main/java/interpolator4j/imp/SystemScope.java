package interpolator4j.imp;

public class SystemScope extends AbstractScope {

  public SystemScope() {
    this("system");
  }
  
  public SystemScope(String id) {
    super(id);
  }

  @Override
  protected String doEval(String expression) {
    return expression.isEmpty() ? "" : System.getProperty(expression, "");
  }
}
