package interpolator4j.imp;

import java.io.PrintStream;

import interpolator4j.AbstractScope;
import interpolator4j.util.Arguments;

public class PrintScope extends AbstractScope{

  private final PrintStream printer;

  public PrintScope(PrintStream printer) {
    this("print", printer);
  }

  public PrintScope(String id, PrintStream printer) {
    super(id);
    this.printer = Arguments.requireNonNull(printer, "printer can't be null");
  }

  @Override
  protected String doEval(String expression) {
    printer.println(expression);
    return expression;
  }
}
