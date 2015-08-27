package power.io;

import static power.io.IO.copy;
import static power.util.Throwables.io;
import static power.util.Throwables.silently;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Iterator;

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
	 * @return all read bytes as String as UTF-8
	 */
	public String read() {
		return read( IO.DEFAULT_ENCONDING );
	}

	/**
	 * @param charsetName
	 * @return all read bytes as String
	 */
	public String read( String charsetName ) {
		return silently(()-> new String( Files.readAllBytes(toPath()), charsetName ) );
	}

	public FileInputStream openForRead() {
		return silently(() -> new FileInputStream(this));
	}

	/**
	 * @return a line reader
	 */
	public LineReaderIterable readLines() {
		return silently(() -> new LineReaderIterable(this));
	}

	/**
	 * @return file writer
	 */
	public FileWriter writer() {
		return silently(() -> new FileWriter(this));
	}

	/**
	 * @return {@link FileOutputStream}
	 */
	public FileOutputStream openForWrite() {
		return silently(() -> new FileOutputStream(this));
	}

	/**
	 * @return true if removed with success
	 */
	public boolean removeRecursively() {
		return deleteRecursively(this);
	}

	boolean deleteRecursively(File directory) {
		if ( directory.isDirectory() )
			for ( File file : directory )
				if ( !file.delete() && !deleteRecursively( file ) )
					return false;
		return directory.delete() || !directory.exists();
	}

	/**
	 * @param file
	 * @throws IOException if can't create the file or the {@code file}
	 * 	argument isn't a directory. 
	 */
	public void copyTo( File file ) throws IOException {
		file.getParentFile().ensureExistsAsDirectory();
		FileOutputStream output = file.openForWrite();
		FileInputStream from = openForRead();
		copy(from, output);
		output.close();
	}

	public File getParentFile(){
		return new File( super.getParentFile().getAbsolutePath() );
	}

	public void ensureExistsAsDirectory() throws IOException{
		if ( !mkdirs() && !exists() )
			throw io( "Can't create: %s", getAbsolutePath() );
	}
	
	/**
	 * @param name
	 * @return a new file
	 */
	public File newFileHere( String name ) {
		return new File( this, name );
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
			return new File(foundFiles[cursor++].getAbsolutePath());
		}
	}
}
