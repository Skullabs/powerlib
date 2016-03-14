package power.io;

import static power.util.Throwables.silently;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SocketChannelWriter {

	private final SocketChannel socketChannel;

	public void write( final String sendData ) throws IOException{
		write( sendData, IO.DEFAULT_ENCONDING );
	}

	public void write( final String sendData, final String encoding ) throws IOException{
		write( silently(()-> sendData.getBytes( encoding )) );
	}	
	
	public void write( final byte[] bytes ) throws IOException{
		socketChannel.write( ByteBuffer.wrap( bytes ) );
	}
	
}
