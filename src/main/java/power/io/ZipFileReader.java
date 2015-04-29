package power.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileReader {

	static final String MESSAGE_CANT_OPEN_ZIP = "Can't open zip file";
	final ZipFile zip;

	public ZipFileReader( final File file ) throws IOException {
		zip = new ZipFile( file );
	}

	public ZipFileReader read( final ReaderCallback listener ) throws IOException {
		final Enumeration<? extends ZipEntry> entries = zip.entries();
		while ( entries.hasMoreElements() ) {
			final ZipEntry entry = entries.nextElement();
			final InputStream inputStream = zip.getInputStream( entry );
			listener.onRead( entry, inputStream );
		}
		return this;
	}

	public void close() throws IOException {
		zip.close();
	}

	public interface ReaderCallback {
		void onRead( ZipEntry name, InputStream content ) throws IOException;
	}
}