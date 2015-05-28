package power.util;

import power.util.RespChain.Processor;

@SuppressWarnings({"rawtypes","unchecked"})
public class RespChainBuilder {

	final RespChain root = new DefaultRespChain<>();
	RespChain last = root;

	public <T,R> RespChainBuilder add(Processor<T,R> processor) {
		last = last.add(processor);
		return this;
	}

	public Object process(Object object) {
		return root.process(object);
	}
}
