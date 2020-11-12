package interpolator4j.imp;

public class ParseException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public ParseException(){
    this("parse exception");
  }
  
  public ParseException(String message){
    super(message);
  }
}
