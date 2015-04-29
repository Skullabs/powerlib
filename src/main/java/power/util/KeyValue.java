package power.util;

import static power.util.Util.str;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent=true)
@RequiredArgsConstructor
public class KeyValue<F, S> {
	final F first;
	final S second;
	
	@Override
	public String toString() {
		return str( "%s=%s", first, second );
	}
}