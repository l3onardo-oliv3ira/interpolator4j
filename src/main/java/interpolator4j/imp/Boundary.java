package interpolator4j.imp;

import interpolator4j.BoundaryChar;

public enum Boundary implements BoundaryChar{
  BRACES('{', '}'),
  BRACKETS('[', ']');

  private char begin;
  private char end;

  Boundary(char begin, char end) {
    this.begin = begin;
    this.end = end;
  }
  
  @Override
  public char getBegin() {
    return begin;
  }

  @Override
  public char getEnd() {
    return end;
  }
}
