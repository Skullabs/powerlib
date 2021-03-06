package power.io;

import static power.io.IO.file;
import static power.util.Throwables.silently;

import java.io.IOException;
import java.util.stream.IntStream;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FileTest {
	
	File file = file("target/test_file");
	
	@Before
	public void setUp() throws IOException{
		Assert.assertTrue( !file.exists() || file.delete() );
		file.createNewFile();
	}
	
	@Test
	public void testaReadWithAccents(){
		val string = createString();
		Assert.assertEquals("", file.read());
		val writer = file.writer();
		writer.write( string );
		silently(()-> writer.close() );
		Assert.assertEquals(string, file.read());
	}

	@Test
	@SneakyThrows
	public void test(){
		File file = file("target/xxx");
		val w = file.writer();
		w.write( "AA\nBB\nCC\n" );
		w.close();
		for ( String fileLine : file.readLines() ){
			System.out.println( fileLine );
		}
	}

	private String createString() {
		final StringBuilder string = new StringBuilder();
		IntStream.range(0, 1023).forEach( i-> string.append(" ") );
		return string.append("ã").toString();
	}
	
}
