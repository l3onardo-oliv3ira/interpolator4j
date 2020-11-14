package interpolator4j.imp;

import java.io.PrintStream;

import interpolator4j.util.Arguments;

public class PrintStreamScope extends AbstractScope{

  protected final PrintStream printer;

  public PrintStreamScope(PrintStream printer) {
    this("print", printer);
  }

  public PrintStreamScope(String id, PrintStream printer) {
    super(id);
    this.printer = Arguments.requireNonNull(printer, "printer can't be null");
  }

  @Override
  protected String doEval(String expression) {
    printer.print(expression);
    return expression;
  }
}
