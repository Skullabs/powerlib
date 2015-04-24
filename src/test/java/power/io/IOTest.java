package power.io;

import static org.junit.Assert.assertEquals;
import static power.io.IO.readFile;
import static power.io.IO.unzip;
import static power.io.IO.zip;
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

		val fileContent = readFile( "target/zip-content/content.txt" );
		assertEquals( "1234567890" , fileContent );
	}
}
