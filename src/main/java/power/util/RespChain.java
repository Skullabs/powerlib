package power.util;


public interface RespChain<T> {
	<R> RespChain<R> add(Processor<T, R> processor);

	Object process(T object);

	RespChain<?> next();

	public interface Processor<T,R> {
	    R apply(T t);
	}
}