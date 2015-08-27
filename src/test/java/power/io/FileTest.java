package power.io;

import static power.io.IO.file;
import static power.util.Throwables.silently;

import java.io.IOException;
import java.util.stream.IntStream;

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

	private String createString() {
		final StringBuilder string = new StringBuilder();
		IntStream.range(0, 1023).forEach( i-> string.append(" ") );
		return string.append("ã").toString();
	}
	
}
