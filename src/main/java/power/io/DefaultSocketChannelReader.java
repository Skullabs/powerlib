package power.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class DefaultSocketChannelReader implements SocketChannelReader {

	//UNCHECKED: mais linhas do que o permitido
	@Override
	public String read( final SocketChannel channel, final String charset, int bufferSize ) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocate( bufferSize );
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		while ( true ){
			final int readBytes = channel.read( buffer );
			if ( readBytes == -1 ) break;
			if ( readBytes > 0 ) {
				final byte[] bytes = readBytes == bufferSize ? buffer.array() : Arrays.copyOf(buffer.array(), readBytes);
				byteStream.write( bytes );
			}
			buffer.clear();
		}
		return new String( byteStream.toByteArray(), charset );
	}

	@Override
	public String readAt(SocketChannel channel, String charset, int bufferSize, char character) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocate( 1 );
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		while ( true ){
			final int readBytes = channel.read( buffer );
			if ( readBytes == -1 )
				throw new IOException( "Expected end of string character not found on server response" );
			if (  readBytes == 1 ){
				final byte b = buffer.array()[0];
				if ( b == character ) break;
				byteStream.write( b );
			}
			buffer.clear();
		}
		return new String( byteStream.toByteArray(), charset );
	}
	//CHECKED

}
