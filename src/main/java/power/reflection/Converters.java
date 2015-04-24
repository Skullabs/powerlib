package power.reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Converters {

	static final Map<Class<?>, Function<String,?>> converters = new HashMap<>();

	static {
		register(Integer.class, (v)->Integer.valueOf(v));
		register(Integer.TYPE, (v)->Integer.valueOf(v));
		register(Long.class, (v)->Long.valueOf(v));
		register(Long.TYPE, (v)->Long.valueOf(v));
		register(Boolean.class, (v)->Boolean.valueOf(v));
		register(Boolean.TYPE, (v)->Boolean.valueOf(v));
		register(Float.class, (v)->Float.valueOf(v));
		register(Float.TYPE, (v)->Float.valueOf(v));
		register(String.class, (v)->v);
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert( String original, Class<T> targetClass ) {
		try {
			return (T)converters.get(targetClass).apply(original);
		} catch ( NullPointerException cause ){
			throw new UnsupportedOperationException( "No converter found for " + targetClass );
		}
	}

	public static <T> void register( Class<T> clazz, Function<String,T> converter ) {
		converters.put( clazz, converter );
	}
}
