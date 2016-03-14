package power.io;

import static power.util.Throwables.runAnyWay;
import static power.util.Throwables.silently;

import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;

import lombok.Getter;

@Getter
public class SocketRequest {

	private final SocketChannel socketChannel;
	private final SocketChannelReader reader;
	private final SocketChannelWriter writer;
	
	protected SocketRequest( final String host, final int port ){
		try{
			socketChannel = SocketChannel.open();
			socketChannel.connect( new InetSocketAddress(host, port) );
			reader = createReader();
			writer = createWriter();
		} catch ( Exception e ){
			throw new ConnectException("Erro ao criar/connectar socket", e);
		}
	}

	protected SocketChannelWriter createWriter() {
		return new SocketChannelWriter( socketChannel );
	}

	protected SocketChannelReader createReader() {
		return new SocketChannelReader( socketChannel );
	}
	
	public static SocketRequest create( final int port ){
		return create( "localhost", port );
	}
	
	public static SocketRequest create( final String host, final int port ){
		return new SocketRequest(host, port);
	}
	
	public SocketRequest timeout( final int timeoutMilliseconds ){
		silently(() -> socketChannel.socket().setSoTimeout( timeoutMilliseconds ) );
		return this;
	}

	public SocketRequest send( final String sendData ){
		return send( sendData, IO.DEFAULT_ENCONDING );
	}

	public SocketRequest send( final String sendData, final String encoding ){
		return send( silently(()-> sendData.getBytes( encoding )) );
	}	

	public SocketRequest send( final byte[] bytes ){
		try {
			writer.write( bytes );
			return this;
		} catch ( final SocketTimeoutException e ) {
			close();
			throw new TimeoutException();
		} catch ( final Exception e ) {
			close();
			throw new ConnectException( "Erro ao enviar dados", e );
		}
	}
	
	public String receive(){
		return receive( IO.DEFAULT_ENCONDING );
	}

	public String receive( final String charset ){
		return receive( charset, 1024 );
	}
	
	public String receive( final String charset, final int bufferSize ){
		try{
			return reader.read( charset, bufferSize );
		} catch ( Exception e ){
			throw new ConnectException( "Erro ao ler resposta", e );
		} finally {
			close();
		}
	}
	
	/**
	 * Método apenas precisa ser executado se é feita uma requisicao na qual não 
	 * são executados os métodos de leitura de resposta ({@link #receive()}, {@link #receive(String)}, {@link #receive(String, int)})
	 * Em casos de erros, a conexao também é encerrada automaticamente
	 */
	public void close() {
		runAnyWay(()-> socketChannel.close() );
	}

	public static class TimeoutException extends RuntimeException{
		private static final long serialVersionUID = -9099410845561886761L;
		public TimeoutException() {
			super( "Erro de timeout" );
		}
	}
	
	public static class ConnectException extends RuntimeException{
		private static final long serialVersionUID = -4799727943518531682L;
		public ConnectException( final String message, final Exception e ) {
			super( message, e );
		}
	}
	
}