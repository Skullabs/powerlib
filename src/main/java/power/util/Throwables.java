package power.util;

import java.io.IOException;

public class Throwables {
	
	private Throwables(){}

	public static RuntimeException runtime( Throwable cause ) {
		return new RuntimeException(cause);
	}

	public static IllegalStateException illegalState( Throwable cause ) {
		return new IllegalStateException(cause);
	}

	public static IOException io( String message, Object...params ) {
		return new IOException( String.format( message, params ) );
	}

	public static <V> V silently( CallableThatThrowsException<V> callable ) {
		try {
			return callable.call();
		// UNCHECKED: Really need to catch all exceptions here
		} catch ( Throwable cause ) {
		// CHECKED
			throw runtime(cause);
		}
	}

	public static void silently( RunnableThatThrowsException runnable ){
		try {
			runnable.run();
		// UNCHECKED: Really need to catch all exceptions here
		} catch ( Throwable cause ) {
		// CHECKED
			throw runtime(cause);
		}
	}

	public static void runAnyWay( RunnableThatThrowsException runnable ){
		try {
			runnable.run();
		// UNCHECKED: Really need to catch all exceptions here
		} catch ( Throwable cause ) {
		// CHECKED
			cause.printStackTrace();
		}
	}
}
