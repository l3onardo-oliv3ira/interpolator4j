package interpolator4j.imp;

import interpolator4j.Scope;

public enum DefaultScope implements Scope {
  SYSTEM(new SystemScope()),

  MATH(new MathScope()),
  
  RUNTIME(new RuntimeScope()),
  
  JAVASCRIPT(new JavaScriptScope()),
  
  SYSOUT(new SysoutScope()),
  
  LONG(new LongScope()),
  
  UNDEFINED (new Scope(){
    @Override
    public String getId() {
      return "undefined";
    }
    @Override
    public String eval(String expression) {
      return "scope not found";
    }
  });
  
  private Scope s;
  
  DefaultScope(Scope s) {
    this.s = s;
  }

  @Override
  public String getId() {
    return s.getId();
  }

  @Override
  public String eval(String expression) {
    return s.eval(expression);
  }
  
  Scope getScope() {
    return s;
  }
}
