package power.io;

import static power.io.IO.file;

import java.io.IOException;

import lombok.Cleanup;
import lombok.val;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {

	File outputFile = file( "target/generated-test/filewriter.test" );
	
	@Before
	public void setUp() throws IOException{
		file( "target/generated-test" ).mkdirs();
		Assert.assertTrue( !outputFile.exists() || outputFile.delete() );
	}
	
	@Test
	public void testCanWrite() throws IOException{
		val text = "ãéiOU";
		@Cleanup val writer = outputFile.writer();
		writer.write(text);
		writer.flush();
		Assert.assertTrue( outputFile.exists() );
		Assert.assertEquals( text, outputFile.read() );
	}

}
