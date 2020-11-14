package interpolator4j.imp;

import interpolator4j.CharConfig;
import interpolator4j.DebugMode;
import interpolator4j.DebugOption;
import interpolator4j.Interpolator;
import interpolator4j.ScopeProvider;
import interpolator4j.util.Arguments;
import interpolator4j.util.Strings;

final class InterpolatorParser implements Interpolator {
  
  private final ScopeProvider provider;
  private final CharConfig charConfig;
  private final String expression;
  private DebugMode mode;

  private int current;
  private int endCurrent;
  
  InterpolatorParser(CharConfig charConfig, ScopeProvider provider){
    this(charConfig, provider, DebugOption.SILENT, "");
  }
  
  private InterpolatorParser(CharConfig charConfig, ScopeProvider provider, DebugMode mode, String expression){
    this.charConfig = charConfig;
    this.endCurrent = (this.expression = expression.trim()).length();
    this.current = 0;
    this.provider = provider;
    this.mode = mode;
  }

  @Override
  public String interpolate(String expression, DebugMode debugMode){
    this.mode = Arguments.requireNonNull(debugMode, "debugMode can't be null");
    String eval = eval(expression);
    mode.debug(expression, eval);
    return eval;
  }
  
  private String resolve(){
    return expression();
  }
  
  private void advance(){
    current++;
  }
  
  private void fallback(){
    while(endCurrent > current && expression.charAt(--endCurrent) != charConfig.getEnd())
      ;
    if (endCurrent == current)
      throw new ParseException("sintax error");
  }
  
  private void match(char chr){
    if (chr != expression.charAt(current))
      throw new ParseException("Sintax error. Character " + (char)chr + " expected!");
    advance();
    if (chr == charConfig.getBegin())
      fallback();
  }
  
  private String expression(){
    if (!expression.isEmpty())
      if (expression.charAt(current) == charConfig.getChar()){
        return eval();
      }
    return expression;
  }

  private String eval(){
    match(charConfig.getChar());
    match(charConfig.getBegin());
    String value = value();
    match(charConfig.getEnd());
    return value;
  }
  
  private String value(){
    String scopeName = scopeName();
    match(':');
    return rawValue(scopeName);
  }
  
  private String scopeName() {
    int begin = current;
    advance();
    while(expression.charAt(current) != ':')
      advance();
    return expression.substring(begin, current);
  }

  private String rawValue(String scopeName) {
    int begin = current;
    int end = endCurrent;
    out:
    while(end > begin){
      char chr = expression.charAt(end - 1);
      if (chr == charConfig.getEnd())
        break;
      if (chr == ':'){
        int dots = (end - 1) - 1;
        while(dots > begin 
            && expression.charAt(dots) != ' ' 
            && expression.charAt(dots) != charConfig.getBegin())
          dots--;
        if (dots > begin){
          if (expression.charAt(dots) != ' '){
            begin = end;
            break out;
          }
        }
      }
      end--;
    }
    if (begin == end)
      end = endCurrent;
    
    current = end;
    String nextExpression = expression.substring(begin, end);

    return compute(
      scopeName, 
      new InterpolatorParser(
        charConfig, 
        provider,
        mode,
        nextExpression
      ).resolve()
    );
  }

  private String compute(String scopeName, String value) {
    try{
      String eval = provider.get(scopeName).eval(value);
      debug(scopeName, value, eval);
      return eval;
    }catch(Exception e){
      return "[unable to eval '" + scopeName + "' on expression >" + value + "<]";
    }
  }

  private void debug(String scopeName, String value, String eval) {
    if (!DebugOption.SILENT.equals(mode)) {
      mode.debug(String.format("%c%c%s:%s%c",
        charConfig.getChar(),
        charConfig.getBegin(),
        scopeName,
        value,
        charConfig.getEnd()),
        eval
      );
    }    
  }

  private String eval(String expression){
    if (!Strings.hasText(expression))
      return Strings.EMPTY;
    
    int start = expression.indexOf(Character.toString(charConfig.getChar()) + charConfig.getBegin());
    if (start < 0)
      return expression;
    
    if (start > 0){
      String before = expression.substring(0, start);
      String after  = expression.substring(start);
      return before + eval(after);
    }
    //start equals 0 here
    int idx = 0;
    while(idx < expression.length() && 
      expression.charAt(idx) != charConfig.getEnd() && 
      expression.charAt(idx) != ' ' && 
      expression.charAt(idx) != ':')
      idx++;
    
    if (idx == expression.length()) //syntax error return not evaluated expression
      return expression;
    
    if (expression.charAt(idx) != ':') //syntax error return not evaluated expression
      return expression.substring(0, idx + 1) + eval(expression.substring(idx + 1));
      
    int beginIndex = idx + 1;
    if (beginIndex >= expression.length()) //end string? then return empty string
      return "";
    
    do{
      int w = beginIndex;
      while(w < expression.length() && 
        expression.charAt(w) != charConfig.getChar() && 
        expression.charAt(w) != charConfig.getEnd())
        w++;
      
      if (w == expression.length())
        return expression;
      
      if (expression.charAt(w) == charConfig.getChar()){
        String before = expression.substring(0, w);
        String after  = expression.substring(w);
        String back    = before + eval(after);
        if (expression.equals(back))
          return expression;
        expression = back;
      }else{ //w is a charConfig.getEnd()
        String before = expression.substring(start, w + 1);
        String after  = expression.substring(w + 1);
        return new InterpolatorParser(
          charConfig, 
          provider,
          mode,
          before
        ).resolve() + eval(after);
      }
    }while(true);
  }
}

