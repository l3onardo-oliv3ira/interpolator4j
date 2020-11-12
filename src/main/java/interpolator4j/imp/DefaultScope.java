package interpolator4j.imp;

import interpolator4j.Scope;

public enum DefaultScope implements Scope{
  SYSTEM{
    @Override
    public String getId() {
      return "system";
    }

    @Override
    public String eval(String expression) {
      return expression.isEmpty() ? "" : System.getProperty(expression, "");
    }
  },
  LONG{
		@Override
		public String getId() {
			return "long";
		}

		@Override
		public String eval(String expression) {
			return Long.toString((long)Double.parseDouble(expression));
		}
	},
  RUNTIME{
    private SupplierScope s = new SupplierScope.Builder()
      .map("availableProcessors", Runtime.getRuntime()::availableProcessors)
      .map("totalMemory", Runtime.getRuntime()::totalMemory)
      .map("freeMemory", Runtime.getRuntime()::freeMemory)
      .map("maxMemory", Runtime.getRuntime()::maxMemory)
      .build("runtime");

    @Override
    public String getId() {
      return s.getId();
    }

    @Override
    public String eval(String expression) {
      return s.eval(expression);
    }
  },
	UNDEFINED{
		@Override
		public String getId() {
			return "undefined";
		}
		@Override
		public String eval(String expression) {
			return "scope not found";
		}
  },
}
