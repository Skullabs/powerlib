# PowerLib

Utility classes to boost your development.

Configure as maven dependency:
```xml
<dependency>
    <groupId>io.skullabs.powerlib</groupId>
    <artifactId>powerlib</artifactId>
    <version>0.2.5</version>
</dependency>
```

## File read / write
```java
import power.io.IO;
import power.io.File;
import power.io.FileWriter;

...

// Write new files
File newFile = IO.file( "/tmp/file-out.txt" ); // Or IO.file( java.io.File("/tmp/new-file.txt") )
FileWriter writer = newFile.writer();
writer.write( "File contents" );
writer.close();

// Read files
File myFile = IO.file( "/home/my-file.txt" );
String fileContent = myFile.read();
System.out.println( "File content is: " + fileContent );
// or iterate file lines:
for ( String fileLine : myFile.readLines() ){
    System.out.println( "Line is: " + fileLine );
}
```

## Formatters
```java
import power.util.Lang;

...

// Inject using CDI
@Inject
Lang lang;
// Or create over builder
Lang lang = new Lang.Builder()
    .currencyDecimalPlaces( 5 )
    .currencyThousandSeparator( '.' )
    .currencyDecimalSeparator( ',' )
    .build();
```

### Currency
```java
System.out.println( lang.currency( 400.666 ) );
```

## Read input stream
```java
import power.io.IO;

...

// Get your input stream
java.io.InputStream myStream = getMyStream();

// And read as string
String streamAsString = IO.readAsString( myStream );
System.out.println( "Stream content is: " + streamAsString );
// Or read as bytes
byte[] streamAsBytes = IO.readAsByteArray( myStream );
System.out.println( "Stream length is: " + streamAsString.length );

```

## Socket requests
```java
import power.io.SocketRequester;

...

// Create requester
final SocketRequester requester = new SocketRequester.Builder()
    .host( "localhost" )
    .port( 9999 )
    .timeout( 1000 ) // milliseconds
    .build();

// Send string and read server response
final String response = requester.send( "REQUEST-STRING" ).receive();
System.out.println( "Response is: " + response );

// Or send and read at character:
final String firstLineResponse = requester.send( "REQUEST-STRING" ).receiveAt( '\n' );
System.out.println( "First line of response is: " + firstLineResponse );

```
