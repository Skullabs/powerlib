package power.io;

import static org.junit.Assert.assertEquals;
import static power.util.Throwables.illegalState;
import static power.util.Throwables.io;
import static power.util.Throwables.runtime;
import static power.util.Throwables.silently;

import org.junit.Test;

public class ThrowablesTest {

	@Test(expected=RuntimeException.class)
	public void ensureThatThrowsRuntimeException (){
		throw runtime( io( "RuntimeTimeException" ) );
	}

	@Test(expected=IllegalStateException.class)
	public void ensureThatThrowsIllegalStateException (){
		throw illegalState( io( "IllegalStateException" ) );
	}

	@Test(expected=RuntimeException.class)
	public void ensureThatCouldRunMethodWithoutWorryAboutException(){
		silently( ()-> {} );
		silently( ()-> {
			throw io( "RuntimeTimeException" );
		});
	}

	@Test
	public void ensureThatCouldReceiveAReturnedValueFromSilentlyMethod(){
		int returnValue = silently( ()-> {
			return 1;
		});

		assertEquals( 1, returnValue );
	}
}
