package power.io;

import static org.junit.Assert.*;
import static power.util.Util.array;
import static power.util.Util.emptyString;
import static power.util.Util.pair;
import static power.util.Util.range;
import static power.util.Util.rangeList;
import lombok.val;

import org.junit.Test;

public class UtilTest {

	@Test
	public void ensureThatCoultIterateOverARangeOfIntegers(){
		val zeroToNine = rangeList( 10 );
		for ( val i : range( 10 ) )
			assertTrue( zeroToNine.contains(i) );
	}

	@Test
	public void ensureThatCouldGenerateAListOfIntegerFromARange(){
		val zeroToNine = rangeList( 10 );
		for ( int i=0; i<10; i++ )
			assertTrue( "Should have: " + i, zeroToNine.contains(i) );
		assertEquals( 10, zeroToNine.size() );
	}

	@Test
	public void ensureThatCouldIdentifyBlankStrings(){
		assertTrue( emptyString( null ) );
		assertTrue( emptyString( "" ) );
		assertFalse( emptyString( "non-empty" ) );
	}

	@Test
	public void ensureThatCouldPairTwoArraysInKeyValues(){
		Integer[] zeroToNine = array(0,1,2,3,4,5,6,7,8,9);
		Integer[] nineToZero = array(9,8,7,6,5,4,3,2,1,0);

		for ( val pair : pair( zeroToNine, nineToZero ) )
			assertEquals( 9, pair.first()+pair.second() );

		assertTrue( pair( zeroToNine, nineToZero ).hasSameLenght() );
	}
}
