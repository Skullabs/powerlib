package power.io;

import static power.util.Throwables.silently;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class LineReaderIterable implements Iterable<String>, Closeable {
	
	final BufferedReader reader;
	
	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public LineReaderIterable( File file ) throws FileNotFoundException {
		reader = new BufferedReader( new FileReader(file) );
	}
	
	/**
	 * @param inputstream
	 * @throws FileNotFoundException
	 */
	public LineReaderIterable( InputStream  inputstream  ) {
		reader = new BufferedReader( new InputStreamReader( inputstream ) );
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public Iterator<String> iterator() {
		return new LineIterator();
	}

	class LineIterator implements Iterator<String> {
		
		String line;

		@Override
		public boolean hasNext() {
			return silently( () -> (line = reader.readLine()) != null );
		}

		@Override
		public String next() {
			return line;
		}
	}
}
