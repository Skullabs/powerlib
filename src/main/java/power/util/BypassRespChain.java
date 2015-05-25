package power.util;

import java.util.function.Function;

class BypassRespChain implements RespChain<Object> {

	@SuppressWarnings("unchecked")
	public <R> RespChain<R> add(Function<Object, R> processor) {
		return (RespChain<R>)this;
	}

	public Object process(Object comparable) {
		return comparable;
	}

	public RespChain<?> next() {
		return null;
	}
}