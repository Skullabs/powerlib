package power.io;

import static power.util.Util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import lombok.Cleanup;
import lombok.val;

public class IO {

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile( String fileName ) throws IOException {
		return readFile( file(fileName) );
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile( File file ) throws IOException {
		val buffer = new StringBuilder();
		@Cleanup val iterator = iterate(file);
		for ( val bytes : iterator )
			buffer.append( bytes.toString() );
		return buffer.toString();
	}

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ZipExtractor unzip( String fileName ) throws IOException {
		InputStream zip = resourceStream(fileName);
		if ( zip == null )
			zip = new FileInputStream(fileName);
		val zipStream = new ZipInputStream(zip);
		return new ZipExtractor(zipStream);
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static InputStream resourceStream(String fileName) {
		return ZipExtractor.class.getResourceAsStream("/" + fileName);
	}

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ZipCompressor zip( String fileName ) throws IOException {
		return new ZipCompressor(fileName);
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static InputStreamIterable iterate( final File file ) throws IOException {
		return iterate( new FileInputStream( file ) );
	}

	/**
	 * @param stream
	 * @return
	 */
	public static InputStreamIterable iterate( final InputStream stream ) {
		return new InputStreamIterable( stream );
	}
}
