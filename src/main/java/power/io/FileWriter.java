package power.io;

import static power.util.Throwables.silently;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileWriter extends BufferedWriter {

	public FileWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
		this( file, IO.DEFAULT_ENCONDING );
	}
	
	public FileWriter(File file, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
		this( new FileOutputStream(file), charsetName );
	}

	public FileWriter(OutputStream stream) throws FileNotFoundException, UnsupportedEncodingException {
		this( stream, IO.DEFAULT_ENCONDING );
	}

	public FileWriter(OutputStream stream, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {
		super( new OutputStreamWriter( stream, charsetName ) );
	}
	
	public void writeln( String line ) {
		write(line);
		newLine();
	}

	public void write( String str ) {
		silently( ()-> super.write(str) );
	}

	public void newLine() {
		silently( ()-> super.newLine() );
	}

}
