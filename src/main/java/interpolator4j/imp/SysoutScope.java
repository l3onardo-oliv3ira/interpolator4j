package interpolator4j.imp;

public class SysoutScope extends PrintStreamScope {
  public SysoutScope() {
    this("sysout");
  }
  
  public SysoutScope(String id) {
    super(id, System.out);
  }
  
  @Override
  protected String doEval(String expression) {
    printer.println(expression); //print line by line
    return expression;
  }
}
