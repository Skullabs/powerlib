package power.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SocketChannelReader {

	@Getter
	private final SocketChannel socketChannel;
	
	public String read() throws IOException {
		return read( IO.DEFAULT_ENCONDING );
	}

	public String read( final String charset ) throws IOException {
		return read( charset, 1024 );
	}

	//UNCHECKED: mais linhas do que o permitido
	public String read( final String charset, int bufferSize ) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocate( bufferSize );
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		while ( true ){
			final int readedBytes = getSocketChannel().read( buffer );
			if ( readedBytes == -1 ) break;
			if ( readedBytes > 0 ) {
				final byte[] bytes = readedBytes == bufferSize ? buffer.array() : Arrays.copyOf(buffer.array(), readedBytes);
				byteStream.write( bytes );
			}
			buffer.clear();
		}
		return new String( byteStream.toByteArray(), charset );
	}
	//CHECKED

}
