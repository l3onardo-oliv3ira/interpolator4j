package interpolator4j.imp;

public class JavaScriptScope extends ScriptScope{
  public JavaScriptScope(String id) {
    super(id, DEFAULT_MANAGER.getEngineByName("JavaScript"));
  }
}
