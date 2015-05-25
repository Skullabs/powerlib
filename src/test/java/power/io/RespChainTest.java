package power.io;

import static org.junit.Assert.assertEquals;
import static power.util.Util.chain;
import lombok.val;

import org.junit.Test;

public class RespChainTest {

	@Test
	public void ensureThatCanConvertStringIntoIntergerAndThanAddTwoToResult(){
		val chain = chain().add( this::toInteger ).add( this::addTwo );
		assertEquals( 10, chain.process( "8" ) );
	}

	Integer toInteger( String string ) {
		return Integer.valueOf(string);
	}
	
	Integer addTwo( Integer number ) {
		return number+2;
	}
}
