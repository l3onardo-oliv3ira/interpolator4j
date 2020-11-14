package interpolator4j.imp;

import java.lang.reflect.Method;
import java.util.Optional;

import interpolator4j.util.Arguments;
import interpolator4j.util.Reflections;
import interpolator4j.util.Strings;

public class BeanScope extends AbstractScope {

  private Object bean;
  
  public BeanScope(String id, Object bean) {
    super(id);
    this.bean = Arguments.requireNonNull(bean, "bean can't be null");
  }
  
  @Override
  protected String doEval(String expression) {
    if (bean instanceof Number || bean instanceof CharSequence || bean instanceof Character) {
      return bean.toString();
    }
    Optional<Object> currentInstance = Optional.of(bean);
    for(String member: Strings.split(expression, '.')) {
      
      Class<?> clazz = currentInstance.orElseThrow(
        () -> new NullPointerException(member + " is undefined for null")
      ).getClass();
      
      Object instance = currentInstance.get();
      
      Optional<Method> method = Reflections.getMethod(clazz, Reflections.getterMethodFor(member));
      if (!method.isEmpty()) {
        currentInstance = Reflections.invoke(instance, method.get());
        continue;
      } 
      
      currentInstance = Reflections.getFieldValue(
        instance, 
        Reflections.getField(clazz, member).orElseThrow(() -> new RuntimeException(member + " not found"))
      );
    }
    return currentInstance.orElseGet(() -> Strings.EMPTY).toString();
  }
}
