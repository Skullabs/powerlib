package power.io;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface SocketChannelReader {

	String read(SocketChannel channel, String charset, int bufferSize) throws IOException;
	String readAt(SocketChannel channel, String charset, int bufferSize, char character) throws IOException;

}
