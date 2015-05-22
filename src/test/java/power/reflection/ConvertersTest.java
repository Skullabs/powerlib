package power.reflection;

import static org.junit.Assert.assertTrue;

import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import lombok.val;

import org.junit.Before;
import org.junit.Test;

public class ConvertersTest {

	@Test
	public void ensureThatCanConvertAndCreateAnEmptyBorderObject(){
		val border = (EmptyBorder)Converters.convert( "empty-border 10, 10, 10, 10", Border.class);
		val gapInset = new Insets(10, 10, 10, 10);
		assertTrue( border.getBorderInsets().equals( gapInset ) );
	}

	@Before
	public void registerBorderConverter(){
		Converters.register(Border.class, ( name ) -> {
			int delimiterIndex = name.indexOf(' ');
			String methodName = name.substring( 0, delimiterIndex );
			String[] params = name.substring( delimiterIndex+1 ).split( ", *" );
			InvokableMethod method = Reflection.getStaticMethod( BorderFactory.class, "create"+methodName, params.length);
			return (Border)method.convertParamsAndInvoke(params);
		});
	}
}
