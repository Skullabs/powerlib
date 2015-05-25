package power.util;

import java.util.function.Function;

@SuppressWarnings({"rawtypes","unchecked"})
public class RespChainBuilder {

	final RespChain root = new DefaultRespChain<>();
	RespChain last = root;

	public <T,R> RespChainBuilder add(Function<T,R> processor) {
		last = last.add(processor);
		return this;
	}

	public Object process(Object object) {
		return root.process(object);
	}
}
