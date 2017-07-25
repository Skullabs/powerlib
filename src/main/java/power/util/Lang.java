package power.util;

import java.text.DecimalFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.val;

@AllArgsConstructor( access = AccessLevel.PRIVATE )
public class Lang {

	final DecimalFormat currencyFormatter;
	
	public String currency( final Number value )
	{
		if ( value == null ) return null;
		return currencyFormatter.format( value );
	}

	public static class Builder {

		@Setter final char currencyThousandSeparator = '.';
		@Setter final char currencyDecimalSeparator = ',';
		@Setter final int currencyDecimalPlaces = 2;

		public Lang build(){
			// Currency formatter
			val currencyFormatter = new DecimalFormat();
			currencyFormatter.setMinimumIntegerDigits(1);
			currencyFormatter.setMinimumFractionDigits(currencyDecimalPlaces);
			currencyFormatter.setMaximumFractionDigits(currencyDecimalPlaces);
			val symbols = currencyFormatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(currencyThousandSeparator);
			symbols.setDecimalSeparator(currencyDecimalSeparator);
			currencyFormatter.setDecimalFormatSymbols(symbols);
			return new Lang( currencyFormatter );
		}

	}

}
