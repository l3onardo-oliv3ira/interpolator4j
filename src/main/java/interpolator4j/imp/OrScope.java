package interpolator4j.imp;

public class OrScope extends CacheScope {
  public OrScope() {
    super(new BinaryScope("OR"));
  }
}
