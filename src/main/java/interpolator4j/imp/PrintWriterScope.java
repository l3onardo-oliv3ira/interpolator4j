package interpolator4j.imp;

import java.io.PrintWriter;
import java.io.Writer;

import interpolator4j.util.Arguments;

public class PrintWriterScope extends AbstractScope{

  private final PrintWriter printWriter;

  public PrintWriterScope(Writer writer) {
    this("print", writer);
  }

  public PrintWriterScope(String id, Writer writer) {
    super(id);
    this.printWriter = new PrintWriter(Arguments.requireNonNull(writer, "printer can't be null"));
  }

  @Override
  protected String doEval(String expression) {
    printWriter.print(expression);
    return expression;
  }
}
