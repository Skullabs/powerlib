package power.io;

import static power.io.IO.iterate;
import static power.util.Throwables.io;
import static power.util.Throwables.silently;
import static power.util.Util.list;
import static power.io.IO.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

/**
 * Allow developers to easily create {@code *.zip} packages.
 * 
 * @author Miere Teixeira
 */
public class ZipCompressor implements Closeable {

	static final String MESSAGE_CANT_CREATE_ZIP = "Can't create zip file";
	static final String MESSAGE_CANT_ADD_TO_ZIP = "Can't add file to zip";

	final List<String> filesPutIntoZip = list();
	final List<String> prefixesToStripOutFromName = list();
	final ZipOutputStream output;
	final FileOutputStream fileoutputStrem;
	@NonNull String rootDirectory = "";

	@Getter
	final String fileName;

	/**
	 * @param fileName
	 * @param rootDirectory
	 * @throws IOException
	 */
	public ZipCompressor( final String fileName ) throws IOException {
		this.fileName = fileName;
		this.fileoutputStrem = new FileOutputStream( fileName );
		this.output = new ZipOutputStream( fileoutputStrem );
	}

	/**
	 * @param fileName
	 * @param rootDirectory
	 * @throws IOException
	 */
	public ZipCompressor( final File file ) throws IOException {
		this.fileName = file.getName();
		this.fileoutputStrem = new FileOutputStream( file );
		this.output = new ZipOutputStream( fileoutputStrem );
	}

	/**
	 * @param fileName
	 * @param rootDirectory
	 * @throws IOException
	 */
	public ZipCompressor( final String fileName, final String rootDirectory ) throws IOException {
		this( fileName );
		this.rootDirectory = rootDirectory;
	}

	/**
	 * @param name
	 * @param content
	 * @return 
	 * @throws IOException 
	 */
	public ZipCompressor add( final String name, final File content ) {
		return silently( () -> {
			if ( content.exists() )
				add(name, new FileInputStream( content ));
			return this;
		});
	}

	/**
	 * @param name
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public ZipCompressor add( String name, InputStream content ) throws IOException {
		return silently( () -> {
			if ( !filesPutIntoZip.contains( name ) ){
				createNewEntryWith(name, content);
				filesPutIntoZip.add(name);
			}
			return this;
		});
	}

	private void createNewEntryWith(String name, InputStream content) throws IOException {
		output.putNextEntry( new ZipEntry( fixEntryName( name ) ) );
		@Cleanup InputStreamIterable contentIterator = iterate( content );
		for ( ByteBuffer bytes : contentIterator )
			output.write( bytes.buffer(), 0, bytes.length() );
		output.closeEntry();
	}

	String fixEntryName( String entryName ) {
		for ( val prefix : prefixesToStripOutFromName )
			entryName = entryName.replaceFirst( prefix, "" );
		val finalEntryName = rootDirectory + "/" + entryName;
		return finalEntryName.replaceFirst("^/", "");
	}
	
	/**
	 * @param prefix
	 * @return
	 */
	public ZipCompressor removePrefixFromFiles( String prefix ) {
		prefixesToStripOutFromName.add(prefix);
		return this;
	}

	/**
	 * @throws IOException
	 */
	public void close() throws IOException {
		output.close();
		fileoutputStrem.close();
	}

	/**
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public ZipCompressor addDirectory( final String directory ) throws IOException {
		return addDirectory( file(directory) );
	}

	/**
	 * @param directory
	 * @return 
	 * @throws IOException
	 */
	public ZipCompressor addDirectory( final File directory ) throws IOException {
		if ( directory.exists() && !directory.isDirectory() )
			throw io( "%s isn't a directory", directory.getAbsolutePath() );
		return addDirectory(directory, rootDirectory);
	}

	/**
	 * @param directory
	 * @param rootDirectory
	 * @return 
	 * @throws IOException
	 */
	public ZipCompressor addDirectory( final File directory, final String rootDirectory ) throws IOException {
		if ( directory.exists() )
			for ( val file : directory.listFiles() )
				copyToZip( rootDirectory, file );
		return this;
	}

	void copyToZip( final String rootDirectory, final File file )
			throws IOException, FileNotFoundException
	{
		val fileName = ( rootDirectory + "/" + file.getName() ).replaceFirst( "^/", "" );
		if ( file.isDirectory() )
			addDirectory( file, fileName );
		else
			add( fileName, file );
	}
}