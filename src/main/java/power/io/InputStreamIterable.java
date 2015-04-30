package power.io;

import static power.util.Throwables.silently;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InputStreamIterable implements Iterable<ByteBuffer>, Closeable {

	final InputStream stream;

	@Override
	public Iterator<ByteBuffer> iterator() {
		return new ChunkIterator();
	}

	@Override
	public void close() throws IOException {
		stream.close();
	}

	class ChunkIterator implements Iterator<ByteBuffer> {

		final ByteBuffer buffer = new ByteBuffer();

		@Override
		public boolean hasNext() {
			return silently( () -> {
				buffer.length = stream.read( buffer.buffer );
				return buffer.length >= 0;
			});
		}

		@Override
		public ByteBuffer next() {
				return buffer;
		}
	}
}
