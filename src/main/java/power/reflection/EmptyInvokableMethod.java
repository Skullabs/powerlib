package power.reflection;

import java.lang.reflect.Executable;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class EmptyInvokableMethod implements InvokableMethod {
	
	final Object target;
	final String name;

	@Override
	public Object invoke(Object... args) {
		notifyThatMethodDoesNotExists();
		return null;
	}

	@Override
	public Object convertParamsAndInvoke(String... args) {
		notifyThatMethodDoesNotExists();
		return null;
	}

	@Override
	public Executable executable() {
		return null;
	}

	void notifyThatMethodDoesNotExists(){
		log.warning( "Method does not exists: " + target.getClass().getCanonicalName() + "." + name );
	}
}
