package interpolator4j.imp;

import javax.script.ScriptEngine;

public class NashornScope extends ScriptScope{

  private ScriptEngine engine;
  
  public NashornScope(String id) {
    super(id, DEFAULT_MANAGER.getEngineByName("nashorn"));
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
