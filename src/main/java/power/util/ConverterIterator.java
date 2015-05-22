package power.util;

import java.util.Iterator;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConverterIterator<T, R> implements Iterator<R> {

	final Iterator<T> original;
	final Function<T, R> converter;

	@Override
	public boolean hasNext() {
		return original.hasNext();
	}

	@Override
	public R next() {
		return converter.apply( original.next() );
	}

}
