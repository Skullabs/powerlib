package power.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static power.util.Monetary.toMonetary;

import java.math.BigDecimal;

import org.junit.Test;

public class MonetaryTest {

	@Test
	public void ensureThatCanCreateFormatMonetary(){
		assertEquals( "12,30", toMonetary(12.3) );
		assertEquals( "111.222.333.444.555.666.777.888.999,34", toMonetary(new BigDecimal("111222333444555666777888999.34")) );
		assertEquals( "0,00", toMonetary(0D) );
		assertEquals( "1.222,33", toMonetary(1222.3333) );
		assertEquals( "222.333.555.666.777,22", toMonetary(222333555666777.22D) );
		assertNull( toMonetary(null) );
	}

}
