package interpolator4j.util;

import java.util.ArrayList;
import java.util.List;

public final class Strings {

  public static final String EMPTY = "";

  private Strings() {
  }

  /**
   * Test if input has text without create temporary string instances 
   * @param input
   * @return
   */
  public static boolean hasText(String input) {
		if (input == null)
			return false;
		int length = input.length();
		if (length == 0)
			return false;
		int idx = 0;
		while(idx < length && Character.isWhitespace(input.charAt(idx)))
			idx++;
		return idx != length;
  }

  public static String requireText(String input, String message) {
    if (!hasText(input))
      throw new NullPointerException(message);
    return input;
  }

  public static String safeTrim(String input) {
    return hasText(input) ? input.trim() : EMPTY;
  }

  public static List<String> split(String value, char chr){
		return split(value, Character.toString(chr));
  }
  
  public static List<String> split(String value, String separator){
		List<String> values = new ArrayList<String>();
		if (!hasText(separator)){
			if(hasText(value))
				values.add(value);
			return values;
		}
		int begin = 0, index;
		do{
			index = value.indexOf(separator, begin);
			if (index < 0){
				String v = value.substring(begin);
				if (hasText(v))
					values.add(v);
				break;
			}
			if(begin != index)
				values.add(value.substring(begin, index));
			begin = index + separator.length();
		}while(true);
		return values;
	}
}
