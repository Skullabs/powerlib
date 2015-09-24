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
	public String read( final String charset, final int bufferSize ) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocate( bufferSize );
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int readedBytes;
		while ( (readedBytes = socketChannel.read(buffer)) > 0 ){
			if ( readedBytes == bufferSize ){
				byteArrayOutputStream.write( buffer.array() );
				buffer.clear();
			} else {
				byteArrayOutputStream.write( Arrays.copyOf(buffer.array(), readedBytes) );
				break;
			}
		}
		return new String( byteArrayOutputStream.toByteArray(), charset );
	}
	//CHECKED

}
