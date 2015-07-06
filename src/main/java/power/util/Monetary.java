package power.util;

import java.text.DecimalFormat;

import lombok.val;


public class Monetary {
	
	private Monetary(){}

	public static String toMonetary( final Number value ){
		return toMonetary( value, '.', ',', 2 );
	}
	
	public static String toMonetary( final Number value, final char thousandSeparator, 
			final char decimalSeparator, final int decimalPlaces )
	{
		if (value == null)
			return null;
		val result = createFormatter( thousandSeparator, decimalSeparator, decimalPlaces ).format( value );
		if ( result.startsWith("" + decimalSeparator) )
			return "0".concat(result);
		return result;
	}
	
	private static DecimalFormat createFormatter( final char thousandSeparator, 
			final char decimalSeparator, final int decimalPlaces )
	{
		val formatter = new DecimalFormat();
		formatter.setMinimumIntegerDigits(0);
		formatter.setMinimumFractionDigits(decimalPlaces);
		formatter.setMaximumFractionDigits(decimalPlaces);
		val symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(thousandSeparator);
		symbols.setDecimalSeparator(decimalSeparator);
		formatter.setDecimalFormatSymbols(symbols);
		return formatter;
	}

}
