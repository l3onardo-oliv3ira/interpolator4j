package interpolator4j;

public enum DebugOption implements DebugMode {
  SILENT(){
    @Override
    public void debug(String expression, String evaluated) {
      ;//Nothing to do!
    }
  },
  SYSOUT() {
    @Override
    public void debug(String expression, String evaluated) {
      System.out.println(expression + " -> " + evaluated);
    }
  };
}
