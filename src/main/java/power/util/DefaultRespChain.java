package power.util;

import java.util.function.Function;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent=true )
@SuppressWarnings({"rawtypes","unchecked"})
public class DefaultRespChain<T> implements RespChain<T> {

	private Function<T,?> processor = (i)->i;
	private RespChain next = new BypassRespChain();

	@Override
	public <R> RespChain<R> add( @NonNull Function<T, R> processor) {
		final RespChain<R> entry = new DefaultRespChain<R>();
		this.next = entry;
		this.processor = processor;
		return entry;
	}

	@Override
	public Object process( @NonNull T object ) {
		final Object processed = processor.apply( object );
		return next.process( processed );
	}
}