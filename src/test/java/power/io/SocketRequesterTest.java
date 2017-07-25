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

public class SocketRequesterTest {

	private final int testPort = 12345;
	private final TestServer server = new TestServer();
	
	@Test
	public void testReceiveSampleString(){
		server.returnStringOnRequest( "UNIT-TEST-STRING" );
		final SocketRequester requester = new SocketRequester.Builder().port( testPort ).build();
		final String received = requester.receive();
		Assert.assertEquals("UNIT-TEST-STRING", received);
		Assert.assertFalse(requester.getSocketChannel().isConnectionPending());
	}
	
	@Test
	public void testSendSampleString(){
		server.receiveStringAndReturnStringOnRequest( "UNIT-TEST-STRING-RESPONSE" );
		final SocketRequester requester = new SocketRequester.Builder().port( testPort ).build();
		final String received = requester.send( "UNIT-TEST-STRING-REQUEST" ).receive();
		Assert.assertEquals("UNIT-TEST-STRING-RESPONSE", received);
		Assert.assertEquals("UNIT-TEST-STRING-REQUEST", server.receivedString);
	}

	@Test
	public void testSendSampleStringAndReceiveAtCharacter(){
		server.receiveStringAndReturnStringOnRequest( "RESPONSE-STRING" );
		final SocketRequester requester = new SocketRequester.Builder().port( testPort ).build();
		final String received = requester.send( "REQUEST-STRING" ).receiveAt( '-' );
		Assert.assertEquals("RESPONSE", received);
		Assert.assertEquals("REQUEST-STRING", server.receivedString);
	}

	@Test
	public void testEncodingOnReceiveString(){
		server.receiveStringAndReturnStringOnRequest( "receive \t -> \n MÂÂõõÊÊ\n" );
		final SocketRequester requester = new SocketRequester.Builder().port( testPort ).build();
		final String received = requester.send( "send \t -> \n MÂÂõõÊÊ\n", "UTF-8" ).receive( "UTF-8", 1 );
		Assert.assertEquals("receive \t -> \n MÂÂõõÊÊ\n", received);
		Assert.assertEquals("send \t -> \n MÂÂõõÊÊ\n", server.receivedString);
	}
	
	@Test( expected=SocketRequester.ConnectException.class )
	public void testConnectionError(){
		server.returnStringOnRequest( "test" );
		new SocketRequester.Builder().port( testPort+1 ).build();
	}
	
	@Test( expected=SocketRequester.TimeoutException.class )
	@Ignore
	//TODO: Testar timeout
	public void testTimeoutError(){
		final SocketRequester requester = new SocketRequester.Builder().port( testPort ).timeout( 1 ).build();
		requester.send( "test" ).receive();
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
