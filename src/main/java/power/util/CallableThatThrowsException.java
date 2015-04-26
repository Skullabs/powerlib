package power.util;

public interface CallableThatThrowsException<T> {
	T call() throws Exception;
}