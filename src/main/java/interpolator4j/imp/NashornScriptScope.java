package interpolator4j.imp;

import javax.script.ScriptEngine;

public class NashornScriptScope extends ScriptScope{

  private ScriptEngine engine;
  
  public NashornScriptScope(String id) {
    super("js", DEFAULT_MANAGER.getEngineByName("JavaScript"));
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
