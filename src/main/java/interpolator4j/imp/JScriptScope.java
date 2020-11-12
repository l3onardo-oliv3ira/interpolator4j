package interpolator4j.imp;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import interpolator4j.AbstractScope;

public class JScriptScope extends AbstractScope{

  private static final ScriptEngineManager FACTORY = new ScriptEngineManager();

  private ScriptEngine engine;
  
  public JScriptScope() {
    super("js");
    engine = FACTORY.getEngineByName("JavaScript");
  }

  @Override
  public String doEval(String expression) {
    try {
      return engine.eval(expression).toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
