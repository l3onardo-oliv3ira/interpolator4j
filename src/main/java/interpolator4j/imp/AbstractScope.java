package interpolator4j.imp;

import interpolator4j.Scope;
import interpolator4j.util.Arguments;
import interpolator4j.util.Strings;

public abstract class AbstractScope implements Scope {
  
  private String id; 
  private boolean initialized = false;

  protected AbstractScope(String id) {
    this.id = Arguments.requireTrue(Strings::hasText, id, "Unsupported null or empty id for scope").toLowerCase();
  }

  public final String getId() {
    return this.id;
  }

  /*
   * On Init life cycle
   * */
  protected void onInit() {
  }

  public final String eval(String expression) {
    if (!initialized) {
      onInit();
      initialized = true;
    }
    return Strings.safeTrim(doEval(expression));
  }

  protected abstract String doEval(String expression);

  @Override
  public final int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Scope))
      return false;
    Scope other = (Scope) obj;
    if (id == null) {
      if (other.getId() != null)
        return false;
    } else if (!id.equals(other.getId()))
      return false;
    return true;
  }
}
