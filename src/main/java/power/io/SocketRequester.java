package power.io;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;

import static power.util.Throwables.runAnyWay;
import static power.util.Throwables.silently;

@Getter
@AllArgsConstructor( access = AccessLevel.PRIVATE )
public class SocketRequester {

	private final SocketChannel socketChannel;
	private final SocketChannelReader reader;
	private final SocketChannelWriter writer;
	private final boolean autoClosable;

	// Send string methods

	public SocketRequester send(final String sendData ){
		return send( sendData, IO.DEFAULT_ENCONDING );
	}

	public SocketRequester send(final String sendData, final String encoding ){
		return send( silently(()-> sendData.getBytes( encoding )) );
	}	

	public SocketRequester send(final byte[] bytes ){
		try {
			writer.write( socketChannel, bytes );
			return this;
		} catch ( final SocketTimeoutException e ) {
            closeIfAutoClosable();
			throw new TimeoutException();
		} catch ( final Exception e ) {
            closeIfAutoClosable();
			throw new ConnectException( "Erro ao enviar dados", e );
		}
	}

	// Receive string methods
	
	public String receive(){
		return receive( IO.DEFAULT_ENCONDING );
	}

	public String receive( final String charset ){
		return receive( charset, 1024 );
	}
	
	public String receive( final String charset, final int bufferSize ){
		try{
			return reader.read( socketChannel, charset, bufferSize );
		} catch ( Exception e ){
			throw new ConnectException( "Erro ao ler resposta", e );
		} finally {
            closeIfAutoClosable();
		}
	}

	// Receive string at methods

	public String receiveAt( final char character ){
		return receiveAt( character, IO.DEFAULT_ENCONDING );
	}

	public String receiveAt( final char character, final String charset ){
		return receiveAt( character, charset, 1024 );
	}

	public String receiveAt( final char character, final String charset, final int bufferSize ){
		try{
			return reader.readAt( socketChannel, charset, bufferSize, character );
		} catch ( Exception e ){
			throw new ConnectException( "Erro ao ler resposta", e );
		} finally {
            closeIfAutoClosable();
		}
	}

	private void closeIfAutoClosable(){
	    if ( autoClosable ) {
            close();
        }
    }

	/**
	 * Método apenas precisa ser executado se é feita uma requisicao que é closeIfAutoClosable e na qual não
	 * são executados os métodos de leitura de resposta ({@link #receive()}, {@link #receive(String)}, {@link #receive(String, int)})
	 * Em casos de erros, a conexao também é encerrada automaticamente (se closeIfAutoClosable for true)
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

	@Accessors( fluent = true )
	public static class Builder {

		// Writer and reader singleton instances used by default
		private static SocketChannelReader readerInstance = new DefaultSocketChannelReader();
		private static DefaultSocketChannelWriter writerInstance = new DefaultSocketChannelWriter();

		@Setter String host = "localhost";
		@Setter int port = 80;
		@Setter int timeout = 0;
		@Setter SocketChannelReader reader = readerInstance;
		@Setter SocketChannelWriter writer = writerInstance;
		@Setter boolean autoClosable = true;

		public SocketRequester build() throws ConnectException {
			try {
				final SocketChannel channel = SocketChannel.open();
				channel.connect(new InetSocketAddress(host, port));
				silently(() -> channel.socket().setSoTimeout( timeout ) );
				return new SocketRequester( channel, reader, writer, autoClosable );
			} catch ( Exception e ){
				throw new ConnectException("Erro ao criar/connectar socket", e);
			}
		}

	}
	
}