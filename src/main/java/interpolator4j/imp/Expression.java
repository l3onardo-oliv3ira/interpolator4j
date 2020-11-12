package interpolator4j.imp;

public enum Expression implements ExpressionChar {
  DOLLAR('$'),
  HASH('#'),
  AT('@'),
  AND('&'),
  PERCENT('%');
  
  private char chr;
  
  Expression(char chr) {
    this.chr = chr;
  }

  @Override
  public char getChar() {
    return chr;
  }
}
