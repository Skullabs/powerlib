package power.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LangTest {

	Lang lang = new Lang.Builder().build();

	@Test
	public void ensureThatCanCreateFormatMonetary(){
		assertEquals( "12,30", lang.currency(12.3) );
		assertEquals( "111.222.333.444.555.666.777.888.999,34", lang.currency(new BigDecimal("111222333444555666777888999.34")) );
		assertEquals( "0,00", lang.currency(0D) );
		assertEquals( "1.222,33", lang.currency(1222.3333) );
		assertEquals( "222.333.555.666.777,22", lang.currency(222333555666777.22D) );
		assertNull( lang.currency(null) );
	}

	@Test
	public void testCustomLang(){
		Lang customLang = new Lang.Builder()
				.currencyDecimalPlaces( 3 )
				.currencyThousandSeparator( ',' )
				.currencyDecimalSeparator( '.' )
				.build();
		assertEquals("4,000.555", customLang.currency(4000.5555));
	}

}
