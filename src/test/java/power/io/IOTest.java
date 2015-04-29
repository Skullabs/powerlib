package power.io;

import static org.junit.Assert.assertEquals;
import static power.io.IO.unzip;
import static power.io.IO.zip;
import static power.io.IO.file;
import static power.util.Util.array;
import lombok.SneakyThrows;
import lombok.val;

import org.junit.Test;

public class IOTest {

	static final String ZIP_FILE_NAME = "target/content.zip";

	@Test
	@SneakyThrows
	public void ensureThatCouldUnzipSampleContentFile(){
		zip( ZIP_FILE_NAME )
			.addDirectory("src/test/resources/zip-content")
			.removePrefixFromFiles( "src/test/resources/" )
			.close();

		unzip( ZIP_FILE_NAME ).into("target/zip-content/").close();

		val fileContent = file("target/zip-content/content.txt").read();
		assertEquals( "1234567890" , fileContent );
	}

	@Test
	@SneakyThrows
	public void ensureThatCouldReadAllLinesFromAFile(){
		val expectedLines = array("Hello", "Enjoy the day", "World");
		try ( val lines = file( "src/test/resources/msg.hello" ).readLines() ) {
			int cursor = 0;
			for ( val line : lines )
				assertEquals( expectedLines[cursor++], line );
		}
	}
}
