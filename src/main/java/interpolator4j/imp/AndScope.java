package interpolator4j.imp;

public class AndScope extends CacheScope {
  public AndScope() {
    super(new BinaryScope("AND"));
  }
}
