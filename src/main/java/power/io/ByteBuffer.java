package power.io;

import static power.util.Throwables.silently;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent=true )
public class ByteBuffer {
	
	final static int BUFFER_SIZE = 1024;

	final byte[] buffer = new byte[BUFFER_SIZE];
	int length = 0;

	@Override
	public String toString() {
		return toString( "UTF-8" );
	}

	public String toString( String charset ) {
		return silently( ()-> new String(buffer, 0, length, charset) );
	}
}