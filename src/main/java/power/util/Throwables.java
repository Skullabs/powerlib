package power.util;

import java.io.IOException;

public class Throwables {

	private Throwables(){}

	public static RuntimeException runtime( final Throwable cause ) {
		return new RuntimeException(cause);
	}

	public static IllegalStateException illegalState( final Throwable cause ) {
		return new IllegalStateException(cause);
	}

	public static IOException io( final String message, final Object...params ) {
		return new IOException( String.format( message, params ) );
	}

	public static SilentlyException silently( final Throwable cause ) {
		return new SilentlyException(cause);
	}

	public static SilentlyException silently( final Throwable cause, final String message, final Object...params  ) {
		return new SilentlyException( String.format( message, params ), cause);
	}

	public static <V> V silently( final CallableThatThrowsException<V> callable ) {
		try {
			return callable.call();
			// UNCHECKED: Really need to catch all exceptions here
		} catch ( final Throwable cause ) {
			// CHECKED
			throw silently(cause);
		}
	}

	public static void silently( final RunnableThatThrowsException runnable ){
		try {
			runnable.run();
			// UNCHECKED: Really need to catch all exceptions here
		} catch ( final Throwable cause ) {
			// CHECKED
			throw silently(cause);
		}
	}

	public static void runAnyWay( final RunnableThatThrowsException runnable ){
		try {
			runnable.run();
			// UNCHECKED: Really need to catch all exceptions here
		} catch ( final Throwable cause ) {
			// CHECKED
			cause.printStackTrace();
		}
	}
}

class SilentlyException extends RuntimeException {

	private static final long serialVersionUID = -4055198982175011545L;

	public SilentlyException( final Throwable cause ) {
		super( cause.getMessage(), cause, true, false );
	}

	public SilentlyException( final String message, final Throwable cause ) {
		super( message, cause, true, false );
	}
}