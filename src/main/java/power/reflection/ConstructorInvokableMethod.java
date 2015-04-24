package power.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent=true )
@RequiredArgsConstructor
public class ConstructorInvokableMethod implements InvokableMethod {
	
	final Constructor<?> executable;

	@Override
	public Object invoke(Object... args) {
		try {
			return executable.newInstance(args);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public String toString() {
		return executable.toString();
	}
}
