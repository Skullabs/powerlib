package power.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static power.util.Util.array;
import static power.util.Util.emptyString;
import static power.util.Util.equal;
import static power.util.Util.len;
import static power.util.Util.list;
import static power.util.Util.pair;
import static power.util.Util.range;
import static power.util.Util.str;

import java.util.List;

import lombok.val;

import org.junit.Test;

import power.util.Util;

public class UtilTest {

	@Test
	public void ensureThatCoultIterateOverARangeOfIntegers(){
		Integer[] array = array( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 );
		for ( val i : range( 10 ) )
			assertEquals( array[i], i );
	}

	@Test
	public void ensureThatCouldGenerateAListOfIntegerFromARange(){
		val zeroToNine = list( range( 10 ) );
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

	@Test
	public void ensureThatCouldDetermineTheLengthOfCollectionsOrArraysOrIterables() {
		Integer[] array = array( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 );
		assertEquals( 10, len( array ) );

		List<Integer> list = list( array );
		assertEquals( 10, len( list ) );

		Iterable<Integer> iterable = range( 10 );
		assertEquals( 10, len( iterable ) );
	}

	@Test
	public void ensureThatCanDetermineIfSomeValuesAreEqualsEachOther() {
		String str1 = "Hello";
		String str2 = "Hello";

		assertFalse( equal( str1, null ) );
		assertFalse( equal( str1, "World" ) );
		assertFalse( equal( null, str2 ) );
		assertTrue( equal( str1, str2 ) );
	}
	
	@Test
	public void ensureThatCanRetrieveTmpDir(){
		System.setProperty("java.io.tmpdir", "/tmp");
		assertEquals( "/tmp", Util.tmpDir() );
	}
	
	@Test
	public void ensureThatCanCreateStringFromStrMethod(){
		val newString = str( "Hello %s", "World" );
		assertEquals( "Hello World", newString );
		val emptyStringFromNull = str( null );
		assertEquals( "", emptyStringFromNull );
		assertEquals( "12", str( new Integer(12) ) );
		assertEquals( "12.3", str( 12.3 ) );
	}
}
