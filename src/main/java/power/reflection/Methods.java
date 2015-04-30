package power.reflection;

import static power.util.Util.pair;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import lombok.val;
import power.util.KeyValuePairedArrays;

public class Methods {
	
	private Methods(){}

	/**
	 * @param target
	 * @param name
	 * @param expectedTypes
	 * @return
	 */
	public static InvokableMethod getMethod( Object target, String name, Class<?>...expectedTypes ) {
		for ( val method : target.getClass().getMethods() )
			if ( methodNameMatches(method, name)
			&&   parametersMatchesToExpectedTypes(method, expectedTypes))
				return new DefaultInvokableMethod(method,target);
		return new EmptyInvokableMethod( target, name );
	}

	/**
	 * @param target
	 * @param name
	 * @param numberOfParameters
	 * @return
	 */
	public static InvokableMethod getStaticMethod( Class<?> target, String name, int numberOfParameters ) {
		for ( val method : target.getMethods() )
			if ( methodNameMatches(method, name)
			&&   method.getParameterCount() == numberOfParameters
			&&   Modifier.isStatic( method.getModifiers() ) )
				return new DefaultInvokableMethod(method,null);
		return new EmptyInvokableMethod( target, name );
	}
	
	/**
	 * @param target
	 * @param numberOfParameters
	 * @return
	 */
	public static InvokableMethod getConstructor( Class<?> target, int numberOfParameters ) {
		for ( val constructor : target.getConstructors() )
			if ( constructor.getParameterCount() == numberOfParameters )
				return new ConstructorInvokableMethod( constructor );
		return new EmptyInvokableMethod( target, "<init>" );
	}
	
	/**
	 * @param target
	 * @param numberOfParameters
	 * @return
	 */
	public static InvokableMethod getConstructor( Class<?> target, Class<?>...expectedTypes ) {
		for ( val constructor : target.getConstructors() )
			if ( parametersMatchesToExpectedTypes(constructor, expectedTypes) )
				return new ConstructorInvokableMethod( constructor );
		return new EmptyInvokableMethod( target, "<init>" );
	}

	static boolean methodNameMatches( Method method, String name ) {
		val fixedName = name.replace("-", "");
		return fixedName.equalsIgnoreCase( method.getName() );
	}

	static boolean parametersMatchesToExpectedTypes( Executable method, Class<?>...expectedTypes ) {
		val tuples = pair(method.getParameterTypes(), expectedTypes);

		if ( !tuples.hasSameLenght() && expectedTypes.length != 0 )
			return false;

		return parametersMatchesToExpectedTypes(tuples);
	}

	private static boolean parametersMatchesToExpectedTypes(
			final KeyValuePairedArrays<Class<?>, Class<?>> tuples) {
		for ( val tuple : tuples )
			if ( !tuple.first().isAssignableFrom( tuple.second() ) )
				return false;
		return true;
	}
}
