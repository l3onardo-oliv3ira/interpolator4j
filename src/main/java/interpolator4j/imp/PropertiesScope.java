package interpolator4j.imp;

import java.util.Properties;

import interpolator4j.AbstractScope;
import interpolator4j.util.Arguments;

public class PropertiesScope extends AbstractScope {

  private final Properties properties;

	public PropertiesScope(String id, Properties properties) {
    super(id);
    this.properties = Arguments.requireNonNull(properties, "properties can't be null");
	}

	@Override
	protected String doEval(String expression) {
		return properties.getProperty(expression, "");
	}
}
