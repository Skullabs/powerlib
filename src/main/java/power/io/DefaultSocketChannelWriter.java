package power.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class DefaultSocketChannelWriter implements SocketChannelWriter {

	@Override
	public void write( final SocketChannel channel, final byte[] bytes ) throws IOException {
		channel.write( ByteBuffer.wrap( bytes ) );
	}
	
}
