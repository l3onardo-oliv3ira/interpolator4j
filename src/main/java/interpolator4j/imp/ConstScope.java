package interpolator4j.imp;

public final class ConstScope extends AbstractScope {

  private final String constant;

  public ConstScope(String constant) {
    this("const", constant);
  }
  
  public ConstScope(String id, String constant) {
    super(id);
    this.constant = constant;
  }

  @Override
  protected String doEval(String expression) {
    return constant;
  }
}
