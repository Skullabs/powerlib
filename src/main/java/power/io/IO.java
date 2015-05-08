package power.io;

import static power.util.Throwables.silently;
import static power.util.Util.str;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import lombok.val;

public class IO {

	private IO(){}

	/**
	 * @param original
	 * @return file
	 */
	public static power.io.File file( File original ) {
		return new power.io.File( original.getAbsolutePath() );
	}

	/**
	 * @param name
	 * @return file
	 */
	public static power.io.File file( String name, Object...args ) {
		return new power.io.File( str( name, args) );
	}

	/**
	 * @param fileName
	 * @return {@link ZipExtractor}
	 */
	public static ZipExtractor unzip( String fileName ) {
		return unzip( file( fileName ) );
	}

	/**
	 * @param file
	 * @return {@link ZipExtractor}
	 */
	public static ZipExtractor unzip( File file ) {
		return silently( () -> {
			final InputStream zip = new FileInputStream(file);
			return unzip( zip );
		});
	}

	/**
	 * @param fileName
	 * @return {@link ZipExtractor}
	 */
	public static ZipExtractor unzip( InputStream fileName ) {
		return silently( () -> {
			final ZipInputStream zipStream = new ZipInputStream(fileName);
			return new ZipExtractor(zipStream);
		});
	}

	/**
	 * @param fileName
	 * @return {@link InputStream}
	 */
	public static InputStream resourceFile(String fileName) {
		return ZipExtractor.class.getResourceAsStream("/" + fileName);
	}

	/**
	 * @param fileName
	 * @return {@link ZipCompressor}
	 */
	public static ZipCompressor zip( final String fileName ) {
		return silently( () -> new ZipCompressor(fileName) );
	}

	/**
	 * @param file
	 * @return {@link ZipCompressor}
	 */
	public static ZipCompressor zip( final File file ) {
		return silently( () -> new ZipCompressor(file) );
	}

	/**
	 * @param zipFileName
	 * @return {@link ZipFileReader}
	 */
	public static ZipFileReader zipReader( final String zipFileName ) {
		val fixedName = zipFileName.replace( "%20", " " );
		val file = file( fixedName );
		return zipReader(file);
	}

	/**
	 * @param file
	 * @return {@link ZipFileReader}
	 */
	public static ZipFileReader zipReader(final File file) {
		return silently( () ->
			new ZipFileReader( file ) );
	}

	/**
	 * @param file
	 * @return {@link InputStreamIterable}
	 */
	public static InputStreamIterable iterate( final File file ) {
		return silently( () -> iterate( new FileInputStream( file ) ) );
	}

	/**
	 * @param stream
	 * @return {@link InputStreamIterable}
	 */
	public static InputStreamIterable iterate( final InputStream stream ) {
		return new InputStreamIterable( stream );
	}
}
