package power.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static power.io.IO.iterate;

import java.io.File;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import org.junit.Test;

public class IntegerStreamIterableTest {

	static final String CONTENT_FILE = "src/test/resources/inputstream-chunked-iterator.content";
	static final String SMALL_FILE = "src/test/resources/zip-content/content.txt";
	static final String[] EXPECTED_CHUNKS = new String[]{
		createExpectedContentFile( 0, 1024 ),
		createExpectedContentFile( 1024, 1100 )
	};

	@Test
	@SneakyThrows
	public void ensureThatHaveSplittedIntoValidChunks(){
		val file = new File( CONTENT_FILE );
		@Cleanup val iterator = iterate(file);
		int cursor = 0;
		for ( val bytes : iterator )
			assertEquals(EXPECTED_CHUNKS[cursor++], bytes.toString());
	}

	@Test
	@SneakyThrows
	public void ensureThatCouldReadSmallFileChunks(){
		val file = new File( SMALL_FILE );
		@Cleanup val iterable = iterate(file);
		val iterator = iterable.iterator();
		assertTrue( iterator.hasNext() );
		assertEquals( "1234567890", iterator.next().toString() );
		assertFalse( iterator.hasNext() );
	}

	private static String createExpectedContentFile( int offset, int size ) {
		val buffer = new StringBuilder();
		for ( int i=offset; i<size; i++ ){
			val modOfIdByTen = (i+1) % 10;
			buffer.append( Integer.valueOf(modOfIdByTen).toString() );
		}
		return buffer.toString();
	}
}
