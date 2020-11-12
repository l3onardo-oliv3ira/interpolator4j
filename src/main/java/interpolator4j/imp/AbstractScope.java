package interpolator4j.imp;

import interpolator4j.Scope;
import interpolator4j.util.Arguments;
import interpolator4j.util.Strings;

public abstract class AbstractScope implements Scope {
  
  private String id; 

  protected AbstractScope(String id) {
    this.id = Arguments.requireTrue(Strings::hasText, id, "Unsupported null or empty id for scope").toLowerCase();
  }

  public final String getId() {
    return this.id;
  }

  public final String eval(String expression) {
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
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractScope other = (AbstractScope) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
}
