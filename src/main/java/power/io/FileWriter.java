package power.io;

import static power.util.Throwables.silently;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileWriter extends BufferedWriter {

	public FileWriter(File file) throws FileNotFoundException {
		this(new FileOutputStream(file));
	}

	public FileWriter(OutputStream stream) throws FileNotFoundException {
		super( new OutputStreamWriter( stream ) );
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
