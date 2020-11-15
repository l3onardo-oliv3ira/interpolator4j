package interpolator4j.imp;

public class JavaScriptScope extends ScriptScope {
  public JavaScriptScope() {
    this("js");
  }
  public JavaScriptScope(String id) {
    super(id, DEFAULT_MANAGER.getEngineByName("JavaScript"));
  }
}
