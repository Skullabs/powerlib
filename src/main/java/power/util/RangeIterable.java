package power.util;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class RangeIterable implements Iterable<Integer> {
	
	final int start;
	final int stop;
	final int step;

	@Override
	public Iterator<Integer> iterator() {
		return new RangeIterator();
	}

	class RangeIterator implements Iterator<Integer> {
		
		volatile int cursor = start;

		@Override
		public boolean hasNext() {
			return cursor < stop;
		}

		@Override
		public Integer next() {
			val current = cursor;
			cursor+= step;
			return current;
		}
	}
}
