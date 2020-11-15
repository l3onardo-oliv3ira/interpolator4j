package interpolator4j.imp;

public class RuntimeScope extends ScopeWrapper {
  public RuntimeScope() {
    super(
      new SupplierScope.Builder()
        .map("availableProcessors", Runtime.getRuntime()::availableProcessors)
        .map("totalMemory", Runtime.getRuntime()::totalMemory)
        .map("freeMemory", Runtime.getRuntime()::freeMemory)
        .map("maxMemory", Runtime.getRuntime()::maxMemory)
        .build("runtime")
    );
  }
}

