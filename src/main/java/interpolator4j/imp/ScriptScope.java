package interpolator4j.imp;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import interpolator4j.util.Arguments;

public class ScriptScope extends AbstractScope{

  protected static final ScriptEngineManager DEFAULT_MANAGER = new ScriptEngineManager();

  protected final ScriptEngine engine;
  
  public ScriptScope(String id, ScriptEngine engine) {
    super(id);
    this.engine = Arguments.requireNonNull(engine, "engine can't be null");
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
