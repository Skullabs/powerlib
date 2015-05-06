package power.io;

import static power.util.Throwables.silently;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;

import lombok.Cleanup;

public class File extends java.io.File implements Iterable<File> {

	private static final long serialVersionUID = 6998470422524368263L;

	public File(String pathname) {
		super(pathname);
	}

	public File(URI pathname) {
		super(pathname);
	}

	public File(File file, String name) {
		super(file, name);
	}

	/**
	 * @return all read bytes as String
	 */
	public String read(){
		return silently( () -> {
			final StringBuilder buffer = new StringBuilder();
			@Cleanup final InputStreamIterable iterator = reader();
			for ( final ByteBuffer bytes : iterator )
				buffer.append( bytes.toString() );
			return buffer.toString();
		});
	}

	public InputStreamIterable reader(){
		final InputStream fileStream = openForRead();
		return new InputStreamIterable( fileStream );
	}

	public FileInputStream openForRead() {
		return silently( () -> new FileInputStream(this) );
	}

	/**
	 * @return a line reader
	 */
	public LineReaderIterable readLines(){
		return silently( ()-> new LineReaderIterable(this) );
	}

	/**
	 * @return
	 */
	public FileWriter writer(){
		return silently( ()-> new FileWriter(this) );
	}
	
	/**
	 * @return
	 */
	public FileOutputStream openForWrite(){
		return silently( () -> new FileOutputStream(this) );
	}

	@Override
	public Iterator<File> iterator() {
		return new FileIterator();
	}

	class FileIterator implements Iterator<File> {

		final java.io.File[] foundFiles = listFiles();
		int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor < foundFiles.length;
		}

		@Override
		public File next() {
			return new File( foundFiles[cursor++].getAbsolutePath() );
		}
	}
}
