package interpolator4j.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class Reflections {
  private Reflections() {}
  
  public static String setterMethodFor(String fieldName){
    return makeMethodName("set", fieldName);
  }
  
  public static String getterMethodFor(String fieldName){
    return makeMethodName("get", fieldName);
  }
  
  public static Optional<Field> getField(Class<?> clazz, String fieldName){
    try {
      return Optional.ofNullable(clazz.getField(fieldName));
    }catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public static Optional<Object> getFieldValue(Object instance, Field field){
    try {
      field.setAccessible(true);
      return Optional.ofNullable(field.get(instance));
    }catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public static Optional<Method> getMethod(Class<?> clazz, String methodName){
    try {
      return Optional.ofNullable(clazz.getMethod(methodName));
    }catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public static Optional<Object> invoke(Object instance, Method method) {
    try {
      method.setAccessible(true);
      return Optional.ofNullable(method.invoke(instance));
    }catch(Exception e) {
      return Optional.empty();
    }
  }
  
  private static String makeMethodName(String preffix, String fieldName){
    Arguments.requireTrue(Strings::hasText, fieldName, "fieldName can't be null or empty");
    char firstLetter = Character.toUpperCase(fieldName.charAt(0));
    return preffix + firstLetter + (fieldName.length() > 1 ? fieldName.substring(1): Strings.EMPTY);
  }
}
