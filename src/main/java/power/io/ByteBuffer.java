package power.io;

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
		return new String(buffer, 0, length);
	}
}