package power.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;

public class Util {

	public static <T> List<T> emptyList() {
		return new ArrayList<>();
	}

	public static <T> Map<String, T> stringMap() {
		return new HashMap<>();
	}

	public static File file( String name ) {
		return new File(name);
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

	@SuppressWarnings("unchecked")
	public static <T> List<T> listFrom( Iterable<T> iterable ) {
		val list = (List<T>)emptyList();
		for ( val item : iterable )
			list.add( item );
		return list;
	}

	public static List<Integer> rangeList( int stop ) {
		return rangeList( 0, stop );
	}

	public static List<Integer> rangeList( int start, int stop ) {
		return rangeList( start, stop, 1 );
	}

	public static List<Integer> rangeList( int start, int stop, int step ) {
		return listFrom( new RangeIterable(start, stop, step) );
	}

	public static <F, S> KeyValue<F, S> tuple( F first, S second ) {
		return new KeyValue<F, S>(first, second);
	}

	public static boolean emptyString( String string ) {
		return string == null || string.isEmpty();
	}

	@SafeVarargs
	public static <T> T[] array( T...objects ){
		return objects;
	}

	public static <F,S> KeyValuePairedArrays<F,S> pair( F[] first, S[] second ) {
		return new KeyValuePairedArrays<>(first, second);
	}
}
