package power.io;

import static power.util.Throwables.silently;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SocketRequestTest {

	private final int testPort = 12345;
	private final TestServer server = new TestServer();
	
	@Test
	public void testReceiveSampleString(){
		final String expected = "test";
		server.returnStringOnRequest( expected );
		final SocketRequest request = SocketRequest.create( testPort );
		final String received = request.receive();
		Assert.assertEquals(expected, received);
		Assert.assertFalse(request.getSocketChannel().isConnectionPending());
	}
	
	@Test
	public void testSendSampleString(){
		final String expectedSended = "expectedSended";
		final String expectedReceived = "expectedReceived";
		server.receiveStringAndReturnStringOnRequest( expectedReceived );
		final String received = SocketRequest.create( testPort ).send( expectedSended ).receive();
		Assert.assertEquals(expectedReceived, received);
		Assert.assertEquals(expectedSended, server.receivedString);
	}
	
	@Test
	public void testEncodingOnReceiveString(){
		final String expectedSended = "send \t -> \n MÂÂõõÊÊ\n";
		final String expectedReceived = "receive \t -> \n MÂÂõõÊÊ\n";
		server.receiveStringAndReturnStringOnRequest( expectedReceived );
		final String received = SocketRequest.create( testPort ).send( expectedSended, "UTF-8" ).receive( "UTF-8", 1 );
		Assert.assertEquals(expectedReceived, received);
		Assert.assertEquals(expectedSended, server.receivedString);
	}
	
	@Test( expected=SocketRequest.ConnectException.class )
	public void testConnectionError(){
		server.returnStringOnRequest( "test" );
		SocketRequest.create( testPort + 1 );
	}
	
	@Test( expected=SocketRequest.TimeoutException.class )
	@Ignore
	//TODO: Testar timeout
	public void testTimeoutError(){
		SocketRequest.create( testPort ).timeout(1).send( "test" ).receive();
	}
	
	private class TestServer{
		
		private String receivedString;
		
		private void returnStringOnRequest( final String stringForReturn ){
			new Thread(()-> processRequest(stringForReturn, false)).start();
			silently(()-> Thread.sleep( 500 ) );
		}
		
		private void receiveStringAndReturnStringOnRequest( final String stringForReturn ){
			new Thread(()-> processRequest(stringForReturn, true)).start();
			silently(()-> Thread.sleep( 500 ) );
		}
	
		private void processRequest( final String stringForReturn, final boolean receiveString ){
			silently(()->{
				final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
				serverSocketChannel.socket().bind(new InetSocketAddress( testPort ));
				final SocketChannel socketChannel = serverSocketChannel.accept();
				if ( receiveString )
					this.receivedString = receiveString( socketChannel );
				else
					this.receivedString = null;
				socketChannel.write( ByteBuffer.wrap( stringForReturn.getBytes( "UTF-8" ) ) );
				socketChannel.close();
				serverSocketChannel.close();
			});
		}
		
		private String receiveString( final SocketChannel socketChannel ) throws Exception{
			final ByteBuffer buffer = ByteBuffer.allocate( 1024 );
			int readedBytes = socketChannel.read(buffer);
			return new String( Arrays.copyOf(buffer.array(), readedBytes), "UTF-8" );
		}
		
	}
	
}
