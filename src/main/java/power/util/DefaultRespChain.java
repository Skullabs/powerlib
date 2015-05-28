package power.util;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent=true )
@SuppressWarnings({"rawtypes","unchecked"})
public class DefaultRespChain<T> implements RespChain<T> {

	private Processor<T,?> processor;
	private RespChain next;

	@Override
	public <R> RespChain<R> add( Processor<T, R> processor) {
		this.next = new DefaultRespChain<R>();
		this.processor = processor;
		return next;
	}

	@Override
	public Object process( T object ) {
		Object processed = object;
		if ( processor != null )
			processed = processor.apply( object );
		if ( next != null )
			processed = next.process( processed );
		return processed;
	}
}