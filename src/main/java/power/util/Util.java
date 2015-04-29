package power.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;

public class Util {

	@SafeVarargs
	public static <T> T[] array( T... objects ) {
		return objects;
	}

	public static <T> List<T> list( T[] array ) {
		return Arrays.asList( array );
	}

	public static <T> List<T> list() {
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> list( Iterable<T> iterable ) {
		val list = (List<T>)list();
		for ( val item : iterable )
			list.add( item );
		return list;
	}

	public static <T> Map<String, T> stringMap() {
		return new HashMap<>();
	}

	public static Iterable<Integer> range( int stop ) {
		return range( 0, stop );
	}

	public static Iterable<Integer> range( int start, int stop ) {
		return range( start, stop, 1 );
	}

	public static Iterable<Integer> range( int start, int stop, int step ) {
		return new RangeIterable(start, stop, step);
	}

	public static <F, S> KeyValue<F, S> keyValue( F first, S second ) {
		return new KeyValue<F, S>(first, second);
	}

	public static boolean emptyString( String string ) {
		return string == null || string.isEmpty();
	}

	public static <F,S> KeyValuePairedArrays<F,S> pair( F[] first, S[] second ) {
		return new KeyValuePairedArrays<>(first, second);
	}

	public static <T> int len( T[] array ) {
		return array.length;
	}

	public static <T> int len( Collection<T> array ) {
		return array.size();
	}

	public static <T> int len( Iterable<T> array ) {
		int length = 0;
		for ( @SuppressWarnings( "unused" ) val t : array )
			length++;
		return length;
	}

	public static <T> boolean equal( T f, T s ) {
		if ( f == null && s == null )
			return true;
		if ( f != null )
			return f.equals( s );
		if ( s != null )
			return s.equals( f );
		return false;
	}
	
	public static String tmpDir(){
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String str( String template, Object...params ) {
		return String.format( template, params );
	}
	
	public static String str( Object obj ) {
		if ( obj == null )
			return "";
		return obj.toString();
	}
}
