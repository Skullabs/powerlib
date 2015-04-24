package power.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent=true )
@RequiredArgsConstructor
public class DefaultInvokableMethod implements InvokableMethod {

	final Method executable;
	final Object instance;

	@Override
	public Object invoke( Object...args ){
		try {
			return executable.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public String toString() {
		return executable.toString();
	}
}
