package power.io;

import static power.io.IO.iterate;
import static power.io.IO.file;
import static power.util.Throwables.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import lombok.experimental.Accessors;

/**
 * 
 * @author Miere Teixeira
 */
@Setter
@Accessors( fluent=true )
@RequiredArgsConstructor
public class ZipExtractor implements Closeable {

	@NonNull Consumer<String> notifier = (name)->{};
	final ZipInputStream zipFile;

	/**
	 * Extracts the zip content into a file. It only automatically closes the
	 * stream when an exception is thrown. 
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public ZipExtractor into( String directory ) throws IOException {
		return into( file( directory ) );
	}

	/**
	 * Extracts the zip content into a file. It only automatically closes the
	 * stream when an exception is thrown.
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public ZipExtractor into( File directory ) throws IOException {
		try {
			ensureThatDirectoryExists( directory );
			writeEntriesIntoDirectory(directory);
			return this;
		} catch ( IOException cause ) {
			close();
			throw cause;
		}
	}

	private void writeEntriesIntoDirectory(File directory) throws IOException {
		ZipEntry entry;
		while ( (entry = zipFile.getNextEntry()) != null ){
			notifier.accept( entry.getName() );
			writeEntryIntoDirectory( directory, entry );
		}
	}

	private void ensureThatDirectoryExists(File directory) throws IOException {
		if ( directory.exists() && !directory.isDirectory() )
			throw io("Not a directory: %s", directory);
		if ( !directory.exists() )
			if ( !directory.mkdirs() )
				throw io("Can create the directory: %s", directory);
	}

	private void writeEntryIntoDirectory(File directory, ZipEntry entry) throws IOException {
		val outputpath = directory.getAbsolutePath() + "/" + entry.getName();
		if ( entry.isDirectory() ) {
			ensureThatDirectoryExists( new File( outputpath ) );
			return;
		}

        copyCurrentEntryContentToOutputFile(outputpath);
	}

	private void copyCurrentEntryContentToOutputFile(String outputpath) throws IOException {
		ensureThatParentDirectoryExists(outputpath);
		@Cleanup FileOutputStream outputFile = new FileOutputStream(outputpath);
        for ( val bytes : iterate(zipFile) )
        	outputFile.write(bytes.buffer(), 0, bytes.length());
	}

	private void ensureThatParentDirectoryExists(String outputpath) throws IOException {
		val file = file( file( outputpath ).getParent() );
		if ( !file.mkdirs() && !file.exists() )
			throw io("Can create the directory: %s", outputpath);
	}

	/**
	 * Close the stream
	 */
	public void close() {
		try {
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
