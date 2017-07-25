package power.io;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface SocketChannelWriter {
	
	void write(SocketChannel channel, byte[] bytes) throws IOException;
	
}
