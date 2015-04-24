package power.util;

import java.util.Iterator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyValuePairedArrays<F, S> implements Iterable<KeyValue<F, S>> {

	@NonNull F[] first;
	@NonNull S[] second;

	public boolean hasSameLenght(){
		return first.length == second.length;
	}

	@Override
	public Iterator<KeyValue<F, S>> iterator() {
		return new TuplesIterator();
	}
	
	class TuplesIterator implements Iterator<KeyValue<F,S>> {
		
		volatile int firstCursor = 0;
		volatile int secondCursor = 0;

		@Override
		public boolean hasNext() {
			return firstCursor < first.length
				&& secondCursor < second.length;
		}

		@Override
		public KeyValue<F, S> next() {
			return new KeyValue<>( first[firstCursor++], second[secondCursor++] );
		}
	}
}
