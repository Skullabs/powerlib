package power.reflection;

import java.lang.reflect.Executable;

import lombok.val;

public interface InvokableMethod {

	Object invoke(Object... args);

	default Object convertParamsAndInvoke( String...args ){
		val params = convertParams( args );
		return invoke( params );
	}

	default Object[] convertParams( String...args ){
		val params = new Object[args.length];
		val methodParams = executable().getParameterTypes();
		for ( int i=0; i<args.length; i++ )
			params[i] = Converters.convert( args[i].trim(), methodParams[i] );
		return params;
	}

	Executable executable();

}