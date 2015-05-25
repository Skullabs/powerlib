package power.util;

import java.util.function.Function;

public interface RespChain<T> {
	<R> RespChain<R> add(Function<T, R> processor);

	Object process(T object);

	RespChain<?> next();
}